package uo.ri.cws.application.service.invoice.create.commands;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class FindNotInvoicedWorkOrdersByClient implements Command<List<InvoicingWorkOrderDto>>{

	private String nif;
	WorkOrderGateway wg = Factories.persistence.forWorkOrder();
	
	public FindNotInvoicedWorkOrdersByClient(String nif) {
		ArgumentChecks.isNotNull(nif, "El nif no puede ser nulo");
		ArgumentChecks.isNotBlank(nif, "El nif no puede estar en blanco");
		this.nif = nif;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		
		List<InvoicingWorkOrderDto> listMechanicRecord = wg.findNotInvoicedWorkOrdersByClient(nif);
		
		return InvoiceDtoAssembler.toDto(listMechanicRecord);
	}

}
