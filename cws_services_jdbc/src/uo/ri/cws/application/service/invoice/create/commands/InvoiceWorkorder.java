package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.exception.BusinessException;
import uo.ri.cws.application.persistence.util.command.Command;

public class InvoiceWorkorder implements Command<InvoiceDto>{

	public InvoiceWorkorder(List<String> workOrderIds) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
