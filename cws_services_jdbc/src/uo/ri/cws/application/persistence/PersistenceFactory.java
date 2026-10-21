package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public interface PersistenceFactory {

	MechanicGateway forMechanic();

	WorkOrderGateway forWorkOrder();

	InvoiceGateway forInvoice();

	InterventionGateway forIntervention();

	VoucherGateway forVoucher();

	// SparePartGateway forSparePart();
	// SubstitutionGateway forSubstitutionsGateway();
	// VehicleGateway forVehicle();
	// VehicleTypeGateway forVehicleType();

}
