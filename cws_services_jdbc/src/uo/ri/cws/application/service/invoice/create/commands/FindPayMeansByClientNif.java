package uo.ri.cws.application.service.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway.CreditCardRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.create.PaymentMeanDtoAssembler;
import uo.ri.cws.application.service.util.NifChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayMeansByClientNif implements Command<List<PaymentMeanDto>> {

    private String nif;
    private PaymentMeanGateway pmg = Factories.persistence.forPaymentMean();
    private VoucherGateway vg = Factories.persistence.forVoucher();
    private CreditCardGateway ccg = Factories.persistence.forCreditCard();

    public FindPayMeansByClientNif(String nif) {
        NifChecks.checkValidNif(nif);
        this.nif = nif;
    }

    @Override
    public List<PaymentMeanDto> execute() throws BusinessException {
        List<PaymentMeanRecord> paymentMeanRecords = pmg.findPayMeansByClientNif(nif);
        List<PaymentMeanDto> paymentMeanDtos = new ArrayList<>();
        for (PaymentMeanRecord record : paymentMeanRecords) {
            if ("CreditCard".equalsIgnoreCase(record.dtype)) {
                CreditCardRecord cc = ccg.findById(record.id).get();
                paymentMeanDtos.add(PaymentMeanDtoAssembler.toCardDto(cc));
            } else if ("Voucher".equalsIgnoreCase(record.dtype)) {
                VoucherRecord vr = vg.findById(record.id).get();
                paymentMeanDtos.add(PaymentMeanDtoAssembler.toVoucherDto(vr));
            } else {
                paymentMeanDtos.add(PaymentMeanDtoAssembler.toCashDto(record));
            }
        }
        return paymentMeanDtos;
    }

}
