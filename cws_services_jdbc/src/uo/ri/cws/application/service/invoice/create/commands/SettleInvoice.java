package uo.ri.cws.application.service.invoice.create.commands;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.charge.ChargeGateway;
import uo.ri.cws.application.persistence.charge.ChargeGateway.ChargeRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class SettleInvoice implements Command<Void> {

    private InvoiceGateway ig = Factories.persistence.forInvoice();
    private PaymentMeanGateway pmg = Factories.persistence.forPaymentMean();
    private ChargeGateway cg = Factories.persistence.forCharge();
    private String invoiceId;
    private Map<String, BigDecimal> charges;

    public SettleInvoice(String invoiceId, Map<String, BigDecimal> charges) {
        ArgumentChecks.isNotNull(invoiceId, "El id de la factura no puede ser nulo");
        ArgumentChecks.isNotBlank(invoiceId, "El id de la factura no puede estar en blanco");
        ArgumentChecks.isNotNull(charges, "El mapa de cargos no puede ser nulo");
        ArgumentChecks.isFalse(charges.isEmpty(), "Debe haber al menos un cargo");

        for (Map.Entry<String, BigDecimal> entry : charges.entrySet()) {
            ArgumentChecks.isNotNull(entry.getKey(), "El id del medio de pago no puede ser nulo");
            ArgumentChecks.isNotBlank(entry.getKey(), "El id del medio de pago no puede estar en blanco");
            ArgumentChecks.isNotNull(entry.getValue(), "El importe del cargo no puede ser nulo");
        }

        this.invoiceId = invoiceId;
        this.charges = charges;
    }

    @Override
    public Void execute() throws BusinessException {
        // validación de facturas
        Optional<InvoiceRecord> oInvoice = ig.findById(invoiceId);
        InvoiceRecord invoiceRecord = checkAndValidateInvoice(oInvoice);
        // se valida que coinciden los cargos con el total
        checkCharges(invoiceRecord.total);
        // se valida cada medio de pago
        checkPaymentMeans();

        // se crean los cargos y se actualizan los medios de pago
        for (Map.Entry<String, BigDecimal> entry : charges.entrySet()) {
            String pmId = entry.getKey();
            BigDecimal amount = entry.getValue().setScale(2, RoundingMode.HALF_EVEN);
            PaymentMeanRecord pm = pmg.findById(pmId).get();

            // Inserto cargo
            ChargeRecord cr = new ChargeRecord();
            cr.id = UUID.randomUUID().toString();
            cr.version = 1L;
            cr.amount = amount;
            cr.invoice_id = invoiceRecord.id;
            cr.paymentMean_id = pm.id;
            cr.entityState = "ENABLED";
            cg.add(cr);

            // Actualizo lo acumulado y disponible si es bono
            pm.accumulated = pm.accumulated.add(amount);
            if ("Voucher".equalsIgnoreCase(pm.dtype)) {
                pm.available = pm.available.subtract(amount);
            }
            pmg.update(pm);
        }

        invoiceRecord.state = "PAID";
        ig.update(invoiceRecord);
        return null;
    }

    /**
     * Se valida que todos los métodos de pago de los cargos existen.
     * - En caso de que sea tarjeta de crédito se comprueba que no esté caducada.
     * - En caso de que sea bono se comprueba que el cargo no supere el saldo
     * disponible.
     */
    private void checkPaymentMeans() throws BusinessException {
        for (Map.Entry<String, BigDecimal> entry : charges.entrySet()) {
            String pmId = entry.getKey();
            BigDecimal amount = entry.getValue().setScale(2, RoundingMode.HALF_EVEN);

            Optional<PaymentMeanRecord> oPm = pmg.findById(pmId);
            BusinessChecks.isTrue(oPm.isPresent(), "El medio de pago no existe");

            PaymentMeanRecord pm = oPm.get();
            if ("CreditCard".equalsIgnoreCase(pm.dtype)) {
                YearMonth cardExpiration = YearMonth.from(pm.cardValidThru.toLocalDate());
                BusinessChecks.isFalse(cardExpiration.isBefore(YearMonth.now()), "La tarjeta de crédito está caducada");
            } else if ("Voucher".equalsIgnoreCase(pm.dtype)) {
                BusinessChecks.isTrue(amount.compareTo(pm.available) <= 0, "El bono no tiene suficiente saldo");
            }
        }
    }

    /**
     * Comprueba que los cargos sean todos mayores que 0 y que la suma de todos
     * coincida con el total de la factura
     * 
     * @throws BusinessException
     */
    private void checkCharges(BigDecimal total) throws BusinessException {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> entry : charges.entrySet()) {
            BigDecimal charge = entry.getValue();
            charge.setScale(2, RoundingMode.HALF_EVEN);
            BusinessChecks.isTrue(charge.compareTo(BigDecimal.ZERO) > 0);
            totalAmount = totalAmount.add(charge);
        }
        BusinessChecks.isTrue(totalAmount.compareTo(total) == 0,
                "La suma de los cargos debe coincidir con el precio total de la factura");
    }

    /**
     * Se busca y se valida la factura
     * 
     * @throws BusinessException
     */
    private InvoiceRecord checkAndValidateInvoice(Optional<InvoiceRecord> oInvoice) throws BusinessException {
        BusinessChecks.isTrue(oInvoice.isPresent(), "La factura no existe");
        InvoiceRecord invoiceRecord = oInvoice.get();
        BusinessChecks.isTrue(invoiceRecord.state.equals("ISSUED"), "La factura no está en estado ISSUED");
        return invoiceRecord;
    }

}
