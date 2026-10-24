package uo.ri.cws.application.persistence.paymentmean.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

public class PaymentMeanRecordAssembler extends BaseRecordAssembler<PaymentMeanRecord> {

    public PaymentMeanRecordAssembler() {
        super(PaymentMeanRecord::new);
    }

    public PaymentMeanRecord toRecord(ResultSet rs) throws SQLException {
        PaymentMeanRecord pmr = super.toRecord(rs);
        pmr.dtype = rs.getString("dtype");
        pmr.accumulated = rs.getBigDecimal("accumulated");
        pmr.client_id = rs.getString("client_id");
        return pmr;
    }
}
