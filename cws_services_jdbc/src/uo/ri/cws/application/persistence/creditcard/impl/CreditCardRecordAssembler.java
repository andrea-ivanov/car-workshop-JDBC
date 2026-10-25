package uo.ri.cws.application.persistence.creditcard.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.creditcard.CreditCardGateway.CreditCardRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

public class CreditCardRecordAssembler extends BaseRecordAssembler<CreditCardRecord> {

    public CreditCardRecordAssembler() {
        super(CreditCardRecord::new);
    }

    @Override
    public CreditCardRecord toRecord(ResultSet rs) throws SQLException {
        CreditCardRecord r = super.toRecord(rs);
        r.dtype = rs.getString("dtype");
        r.accumulated = rs.getBigDecimal("accumulated");
        r.clientId = rs.getString("client_id");
        r.number = rs.getString("number");
        r.type = rs.getString("type");
        r.validThru = rs.getDate("validthru");
        return r;
    }
}
