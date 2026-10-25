package uo.ri.cws.application.service.invoice.create.commands;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoiceDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class InvoiceWorkorder implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private WorkOrderGateway wg = Factories.persistence.forWorkOrder();
	private InvoiceGateway ig = Factories.persistence.forInvoice();

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
		List<WorkOrderRecord> workOrders = new ArrayList<>();

		checkWorkOrderExistsAndIsApprovedd(workOrders);

		// Calculo importes de la invoice
		long invoiceNumber = ig.getNextInvoiceNumber();
		LocalDate invoiceDate = LocalDate.now();
		BigDecimal subtotal = calculateSubtotal(workOrders);
		BigDecimal vatPercentage = calculateVatPercentage(invoiceDate);
		BigDecimal vatAmount = calculateVatAmount(subtotal, vatPercentage);
		BigDecimal total = subtotal.add(vatAmount);

		// Añado la invoice
		InvoiceRecord invoiceRecord = new InvoiceRecord();
		invoiceRecord.number = invoiceNumber;
		invoiceRecord.date = invoiceDate;
		invoiceRecord.subtotal = subtotal;
		invoiceRecord.vatPercentage = vatPercentage;
		invoiceRecord.vatAmount = vatAmount;
		invoiceRecord.total = total;
		invoiceRecord.state = "ISSUED";
		invoiceRecord.usedForVoucher = false;
		ig.add(invoiceRecord);

		addWorkOrdersToInvoice(workOrders, invoiceRecord);

		return InvoiceDtoAssembler.toDto(invoiceRecord);
	}

	/**
	 * Calcula vatAmount redondeando a dos decimales
	 * 
	 * @param subtotal
	 * @param vatPercentage
	 * @return
	 */
	private BigDecimal calculateVatAmount(BigDecimal subtotal, BigDecimal vatPercentage) {
		return subtotal.multiply(vatPercentage)
				.divide(new BigDecimal("100"))
				.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Vincula las workOrders a la invoice y las paso a INVOICED
	 * 
	 * @param workOrders
	 * @param invoiceRecord
	 */
	private void addWorkOrdersToInvoice(List<WorkOrderRecord> workOrders, InvoiceRecord invoiceRecord) {
		for (WorkOrderRecord woR : workOrders) {
			woR.invoiceId = invoiceRecord.id;
			woR.state = "INVOICED";
			wg.update(woR);
		}
	}

	/**
	 * Calcula el porcentaje de IVA según la fecha, antes del 2012-07-01 era el 18%.
	 * 
	 * @param invoiceDate
	 * @return
	 */
	private BigDecimal calculateVatPercentage(LocalDate invoiceDate) {
		LocalDate vatChangeDate = LocalDate.parse("2012-07-01");
		if (vatChangeDate.isBefore(invoiceDate))
			return new BigDecimal("21");
		else
			return new BigDecimal("18");
	}

	/**
	 * Cálculo del subtotal de la suma del precio de la lista de todas las
	 * workOrders recibidas
	 * 
	 * @param workOrders
	 * @return
	 */
	private BigDecimal calculateSubtotal(List<WorkOrderRecord> workOrders) {
		BigDecimal subtotal = BigDecimal.ZERO;
		for (WorkOrderRecord woRecord : workOrders) {
			subtotal = subtotal.add(woRecord.amount);
		}
		return subtotal.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * Verificación de que todas las workOrders existen y están con estado APPROVED
	 * 
	 * @param workOrders
	 * @throws BusinessException
	 */
	private void checkWorkOrderExistsAndIsApprovedd(List<WorkOrderRecord> workOrders) throws BusinessException {
		for (String id : workOrderIds) {
			Optional<WorkOrderRecord> woOptionalrecord = wg.findById(id);
			BusinessChecks.isFalse(woOptionalrecord.isEmpty(), "La workOrder no existe");

			WorkOrderRecord woRecord = woOptionalrecord.get();
			BusinessChecks.isTrue(woRecord.state.equals("APPROVED"), "La workOrder debe estar aprobada");

			workOrders.add(woRecord);
		}
	}

}
