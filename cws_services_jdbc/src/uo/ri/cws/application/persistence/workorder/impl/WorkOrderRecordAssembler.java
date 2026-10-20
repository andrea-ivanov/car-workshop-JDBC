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
		WorkOrderRecord wr = new WorkOrderRecord();
		wr.id = rs.getString("id");
		wr.description = rs.getString("description");
		wr.date = rs.getTimestamp("date").toLocalDateTime();
		wr.state = rs.getString("state");
		wr.amount = rs.getBigDecimal("total_amount");
		return wr;
	}

}
