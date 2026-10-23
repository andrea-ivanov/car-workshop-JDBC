package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.impl.InterventionGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentMean.impl.PaymentMeanGatewayImpl;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.impl.VoucherGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;

public class PersistenceFactoryImpl implements PersistenceFactory {

	@Override
	public MechanicGateway forMechanic() {
		return new MechanicGatewayImpl();
	}

	@Override
	public WorkOrderGateway forWorkOrder() {
		return new WorkOrderGatewayImpl();
	}

	@Override
	public InvoiceGateway forInvoice() {
		return new InvoiceGatewayImpl();
	}

	@Override
	public InterventionGateway forIntervention() {
		return new InterventionGatewayImpl();
	}

	@Override
	public VoucherGateway forVoucher() {
		return new VoucherGatewayImpl();
	}

	@Override
	public PaymentMeanGateway forPaymentMean() {
		return new PaymentMeanGatewayImpl();
	}

}
