package uo.ri.cws.application.service.voucher.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService.VoucherDto;
import uo.ri.cws.application.service.voucher.VoucherDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindVouchersByClientNif implements Command<List<VoucherDto>> {

    private String nif;
    VoucherGateway vg = Factories.persistence.forVoucher();

    public FindVouchersByClientNif(String nif) {
        ArgumentChecks.isNotNull(nif, "El nif del cliente no puede ser nulo");
        ArgumentChecks.isNotBlank(nif, "El nif del cliente no puede estar en blanco");
        this.nif = nif;
    }

    @Override
    public List<VoucherDto> execute() throws BusinessException {
        List<VoucherRecord> voucherRecords = vg.findVouchersByClientNif(nif);
        List<VoucherDto> voucherDtos = new ArrayList<>();

        for (VoucherRecord r : voucherRecords)
            voucherDtos.add(VoucherDtoAssembler.toDto(r));

        return voucherDtos;
    }

}
