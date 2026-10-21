package uo.ri.cws.application.service.voucher;

import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService.VoucherDto;

public class VoucherDtoAssembler {

    public static VoucherDto toDto(VoucherRecord r) {
        VoucherDto dto = new VoucherDto();
        dto.id = r.id;
        dto.version = r.version;
        dto.clientId = r.clientId;
        dto.accumulatedAmount = r.accumulatedAmount;
        dto.code = r.code;
        dto.description = r.description;
        dto.availableAmount = r.availableAmount;
        return dto;
    }

}
