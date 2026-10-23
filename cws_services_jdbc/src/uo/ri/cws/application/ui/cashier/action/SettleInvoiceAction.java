package uo.ri.cws.application.ui.cashier.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class SettleInvoiceAction implements Action {

	InvoicingService iService = Factories.service.forCreateInvoiceService();

	/**
	 * Algorithm: - Ask user invoice number - Retrieve invoice info - Display
	 * invoice info - Check is unpaid (state <> 'PAID') - List payment methods
	 * accepted for the customer - Ask user to type amount to charge in each payment
	 * method - Check that sum of amounts equals invoice amount - Save payments in
	 * the DDBB - Increase total for each payment method - Decrease money available
	 * in coupon if any has been redeemed - Finally, mark invoice as paid
	 * 
	 */
	@Override
	public void execute() throws BusinessException {
		String invoiceNumber = Console.readString("Invoice number");

	}

	/*
	 * 
	 * Algoritmo: - Solicitar al usuario el número de factura - Recuperar la
	 * información de la factura - Mostrar
	 * la información de la factura - Verificar que esté impagada (estado <>
	 * 'PAGADA') - Listar los métodos de pago
	 * aceptados para el cliente - Solicitar al usuario que indique el importe a
	 * cobrar mediante cada método
	 * de pago - Comprobar que la suma de los importes sea igual al importe de la
	 * factura - Guardar los pagos en
	 * la base de datos - Incrementar el total para cada método de pago - Disminuir
	 * el saldo disponible
	 * en el cupón, si se ha canjeado alguno - Finalmente, marcar la factura como
	 * pagada
	 */
}
