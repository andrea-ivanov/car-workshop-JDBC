package uo.ri.cws.application.service.invoice.create;

import java.time.YearMonth;

import uo.ri.cws.application.persistence.creditcard.CreditCardGateway.CreditCardRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.CardDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CashDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.InvoicingService.VoucherDto;

public class PaymentMeanDtoAssembler {

    public static CardDto toCardDto(CreditCardRecord record) {
        CardDto dto = new CardDto();
        fillCommonFields(record, dto);
        dto.cardNumber = record.number;
        dto.cardType = record.type;
        if (record.validThru != null) {
            dto.cardExpiration = YearMonth.from(record.validThru.toLocalDate());
        }
        return dto;
    }

    public static VoucherDto toVoucherDto(VoucherRecord record) {
        VoucherDto dto = new VoucherDto();
        fillCommonFields(record, dto);
        dto.code = record.code;
        dto.description = record.description;
        dto.availableAmount = record.available;
        return dto;
    }

    public static CashDto toCashDto(PaymentMeanRecord record) {
        CashDto dto = new CashDto();
        fillCommonFields(record, dto);
        return dto;
    }

    private static void fillCommonFields(PaymentMeanRecord record, PaymentMeanDto dto) {
        dto.id = record.id;
        dto.version = record.version;
        dto.clientId = record.clientId;
        dto.accumulated = record.accumulated;
    }

}