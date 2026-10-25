package uo.ri.cws.application.service.voucher.commands;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.exception.BusinessException;

public class GenerateVouchers implements Command<int[]> {

    private ClientGateway cg = Factories.persistence.forClient();
    private WorkOrderGateway wg = Factories.persistence.forWorkOrder();
    private InvoiceGateway ig = Factories.persistence.forInvoice();
    private PaymentMeanGateway pmg = Factories.persistence.forPaymentMean();
    private VoucherGateway vg = Factories.persistence.forVoucher();

    @Override
    public int[] execute() throws BusinessException {
        int[] generated = new int[] { 0, 0, 0 };
        List<ClientRecord> clients = cg.findAll();

        for (ClientRecord client : clients) {
            generated[0] += generateForWorkOrders(client.id);
            generated[2] += generateForInvoicesOver500(client.id);
        }

        return generated;
    }

    private int generateForInvoicesOver500(String id) {
        List<InvoiceRecord> invoices = ig.findPaidAndUsedOver500ByClientId(id);
        for (InvoiceRecord i : invoices) {
            i.usedForVoucher = true;
            ig.update(i);
            createVoucher(id, new BigDecimal("30.00"), "Por factura de más de 500");
        }
        return invoices.size();
    }

    private int generateForWorkOrders(String id) {
        List<WorkOrderRecord> workOrders = wg.findPaidAndUnusedByClientId(id);
        int vouchersToGenerate = workOrders.size() / 3;

        for (int i = 0; i < vouchersToGenerate; i++) {
            for (int j = 0; j < 3; j++) {
                WorkOrderRecord wo = workOrders.get(i * 3 + j);
                wo.usedForVoucher = true;
                wg.update(wo);
            }
            createVoucher(id, new BigDecimal("20.00"), "Por tres órdenes de trabajo");
        }
        return vouchersToGenerate;
    }

    private void createVoucher(String id, BigDecimal amount, String description) {
        VoucherRecord v = new VoucherRecord();
        v.id = UUID.randomUUID().toString();
        v.version = 1L;
        v.entityState = "ENABLED";
        v.dtype = "Voucher";
        v.client_id = id;
        v.accumulated = BigDecimal.ZERO;
        v.code = "B-" + UUID.randomUUID().toString();
        v.description = description;
        v.available = amount;

        pmg.add(v);
        vg.add(v);
    }

}
