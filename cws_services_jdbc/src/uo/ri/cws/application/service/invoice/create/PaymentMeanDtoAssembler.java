package uo.ri.cws.application.service.invoice.create;

import java.time.YearMonth;

import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.CardDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CashDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.InvoicingService.VoucherDto;

public class PaymentMeanDtoAssembler {

    public static PaymentMeanDto toDto(PaymentMeanRecord record) {
        PaymentMeanDto dto;

        if (record.dtype.equals("CreditCard")) {
            CardDto card = new CardDto();
            card.cardNumber = record.cardNumber;
            card.cardType = record.cardType;
            if (record.cardValidThru != null)
                card.cardExpiration = YearMonth.from(record.cardValidThru.toLocalDate());
            dto = card;
        } else if (record.dtype.equals("Voucher")) {
            VoucherDto voucher = new VoucherDto();
            voucher.code = record.code;
            voucher.description = record.description;
            voucher.availableAmount = record.available;
            dto = voucher;
        } else {
            dto = new CashDto();
        }
        dto.id = record.id;
        dto.version = record.version;
        dto.clientId = record.client_id;
        dto.accumulated = record.accumulated;

        return dto;
    }

}