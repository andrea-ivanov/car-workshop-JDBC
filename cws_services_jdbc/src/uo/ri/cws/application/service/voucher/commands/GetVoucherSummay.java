package uo.ri.cws.application.service.voucher.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService.VoucherSummaryDto;
import uo.ri.util.exception.BusinessException;

public class GetVoucherSummay implements Command<List<VoucherSummaryDto>> {

    private ClientGateway cg = Factories.persistence.forClient();
    private VoucherGateway vg = Factories.persistence.forVoucher();

    @Override
    public List<VoucherSummaryDto> execute() throws BusinessException {
        List<ClientRecord> clients = cg.findAll();
        List<VoucherSummaryDto> vouchers = new ArrayList<>();

        for (ClientRecord client : clients) {
            List<VoucherRecord> vouchersOfClient = vg.findVouchersByClientNif(client.nif);
            if (vouchersOfClient.isEmpty())
                continue;

            long issuedQty = vouchersOfClient.size();
            BigDecimal availableAmount = BigDecimal.ZERO;
            BigDecimal consumedAmount = BigDecimal.ZERO;

            for (VoucherRecord voucher : vouchersOfClient) {
                availableAmount = availableAmount.add(voucher.available);
                consumedAmount = consumedAmount.add(voucher.accumulated);
            }

            BigDecimal totalAmount = availableAmount.add(consumedAmount);

            VoucherSummaryDto voucherSummary = new VoucherSummaryDto(
                    client.nif,
                    client.name,
                    client.surname,
                    issuedQty,
                    totalAmount,
                    availableAmount,
                    consumedAmount);
            vouchers.add(voucherSummary);
        }
        // ordenados descendentemente
        vouchers.sort((a, b) -> b.availableAmount().compareTo(a.availableAmount()));
        return vouchers;
    }

}
