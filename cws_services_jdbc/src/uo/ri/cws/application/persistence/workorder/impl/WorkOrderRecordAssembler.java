package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class WorkOrderRecordAssembler extends BaseRecordAssembler<WorkOrderRecord> {

	public WorkOrderRecordAssembler() {
		super(WorkOrderRecord::new);
	}

	@Override
	public WorkOrderRecord toRecord(ResultSet rs) throws SQLException {
		WorkOrderRecord wr = super.toRecord(rs);
		wr.vehicleId = rs.getString("vehicle_id");
		wr.description = rs.getString("description");
		wr.date = rs.getTimestamp("date").toLocalDateTime();
		wr.amount = rs.getBigDecimal("total_amount");
		wr.state = rs.getString("state");
		wr.mechanicId = rs.getString("mechanic_id");
		wr.invoiceId = rs.getString("invoice_id");
		wr.usedForVoucher = rs.getBoolean("usedforvoucher");
		return wr;
	}

}
