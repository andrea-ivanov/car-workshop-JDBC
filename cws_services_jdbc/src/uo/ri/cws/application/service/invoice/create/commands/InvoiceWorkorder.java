package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.cws.application.persistence.util.command.Command;

public class InvoiceWorkorder implements Command<InvoiceDto> {

	private List<String> workOrderIds;

	public InvoiceWorkorder(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		ArgumentChecks.isFalse(workOrderIds.isEmpty());
		for (String id : workOrderIds) {
			ArgumentChecks.isNotNull(id, "El id de la orden de trabajo no puede ser nulo");
			ArgumentChecks.isNotBlank(id, "El id de la orden de trabajo no puede estar en blanco");
		}
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
