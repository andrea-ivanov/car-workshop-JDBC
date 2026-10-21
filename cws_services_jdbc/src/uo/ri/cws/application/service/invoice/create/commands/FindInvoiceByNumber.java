package uo.ri.cws.application.service.invoice.create.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>> {

    private Long number;
    private InvoiceGateway ig = Factories.persistence.forInvoice();

    public FindInvoiceByNumber(Long number) {
        ArgumentChecks.isNotNull(number);
        this.number = number;
    }

    @Override
    public Optional<InvoiceDto> execute() throws BusinessException {
        Optional<InvoiceRecord> invoiceRecord = ig.findByNumber(number);
        return InvoiceDtoAssembler.toDto(invoiceRecord);
    }

}
