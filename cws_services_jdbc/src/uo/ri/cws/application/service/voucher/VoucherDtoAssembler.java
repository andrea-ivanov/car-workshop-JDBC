package uo.ri.cws.application.service.voucher;

import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService.VoucherDto;

public class VoucherDtoAssembler {

    public static VoucherDto toDto(VoucherRecord r) {
        VoucherDto dto = new VoucherDto();
        dto.id = r.id;
        dto.version = r.version;
        dto.clientId = r.client_id;
        dto.accumulatedAmount = r.accumulated;
        dto.code = r.code;
        dto.description = r.description;
        dto.availableAmount = r.available;
        return dto;
    }

}
