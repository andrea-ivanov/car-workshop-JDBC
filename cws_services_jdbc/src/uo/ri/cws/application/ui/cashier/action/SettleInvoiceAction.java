package uo.ri.cws.application.ui.cashier.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.UserInteractionChecks;
import uo.ri.util.exception.UserInteractionException;
import uo.ri.util.menu.Action;

public class SettleInvoiceAction implements Action {

    private InvoicingService iService = Factories.service.forCreateInvoiceService();

    /**
     * Algorithm: - Ask user invoice number - Retrieve invoice info - Display
     * invoice info - Check is unpaid (state <> 'PAID') - List payment methods
     * accepted for the customer - Ask user to type amount to charge in each payment
     * method - Check that sum of amounts equals invoice amount - Save payments in
     * the DDBB - Increase total for each payment method - Decrease money available
     * in coupon if any has been redeemed - Finally, mark invoice as paid
     * 
     * @throws UserInteractionException
     * 
     */
    @Override
    public void execute() throws BusinessException, UserInteractionException {
        // Se muestra la factura
        Long number = Console.readLong("Invoice number");

        Optional<InvoiceDto> oInvoiceDto = iService.findInvoiceByNumber(number);
        UserInteractionChecks.exists(oInvoiceDto, "No existe la factura");
        InvoiceDto invoiceDto = oInvoiceDto.get();

        Printer.printInvoice(invoiceDto);

        // Se muestra la información de los pagos
        String nif = Console.readString("Client nif");
        List<PaymentMeanDto> paymentMeansDtos = iService.findPayMeansByClientNif(nif);
        UserInteractionChecks.isFalse(paymentMeansDtos.isEmpty(),
            "El cliente no tienen metodos de pago asociados o el NIF es incorrecto");
        Map<String, BigDecimal> charges = askForCharges(paymentMeansDtos, invoiceDto.total);

        iService.settleInvoice(invoiceDto.id, charges);
    }

    private Map<String, BigDecimal> askForCharges(List<PaymentMeanDto> paymentMeansDtos, BigDecimal total) {
        Map<String, BigDecimal> charges = new HashMap<>();
        do {
            Printer.printPaymentMeans(paymentMeansDtos);
            String id = Console.readString("Payment mean id");
            BigDecimal amount = Console.readBigDecimal("Amount to charge");
            charges.put(id, amount);
        } while (total.compareTo(sumAmounts(charges.values())) > 0);

        return charges;
    }

    private BigDecimal sumAmounts(Collection<BigDecimal> values) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            total = total.add(value);
        }
        return total;
    }

    /*
     * 
     * Algoritmo: - Solicitar al usuario el número de factura - Recuperar la
     * información de la factura - Mostrar la información de la factura - Verificar
     * que esté impagada (estado <> 'PAGADA') - Listar los métodos de pago aceptados
     * para el cliente - Solicitar al usuario que indique el importe a cobrar
     * mediante cada método de pago - Comprobar que la suma de los importes sea
     * igual al importe de la factura - Guardar los pagos en la base de datos -
     * Incrementar el total para cada método de pago - Disminuir el saldo disponible
     * en el cupón, si se ha canjeado alguno - Finalmente, marcar la factura como
     * pagada
     */
}
