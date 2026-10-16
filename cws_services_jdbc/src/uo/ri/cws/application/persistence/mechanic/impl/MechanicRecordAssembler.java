package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

public class MechanicRecordAssembler extends BaseRecordAssembler<MechanicRecord>{

	public MechanicRecordAssembler() {
		super(MechanicRecord::new);
	}

	@Override
    public MechanicRecord toRecord(ResultSet m) throws SQLException {
        MechanicRecord record = super.toRecord(m);
        record.nif = m.getString("nif");
        record.name = m.getString("name");
        record.surname = m.getString("surname");
        return record;
    }
	
}
