package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

public class InvoiceRecordAssembler extends BaseRecordAssembler<InvoiceRecord> {

    public InvoiceRecordAssembler() {
        super(InvoiceRecord::new);
    }

    @Override
    public InvoiceRecord toRecord(ResultSet rs) throws SQLException {
        InvoiceRecord r = super.toRecord(rs);
        r.date = rs.getDate("DATE").toLocalDate();
        r.number = rs.getLong("NUMBER");
        r.state = rs.getString("STATE");
        r.usedForVoucher = rs.getBoolean("USEDFORVOUCHER");
        r.subtotal = rs.getBigDecimal("SUBTOTAL");
        r.total = rs.getBigDecimal("TOTAL");
        r.vatAmount = rs.getBigDecimal("VATAMOUNT");
        r.vatPercentage = rs.getBigDecimal("VATRATE");
        return r;
    }

}
