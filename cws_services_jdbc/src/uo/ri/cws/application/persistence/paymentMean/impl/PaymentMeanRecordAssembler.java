package uo.ri.cws.application.persistence.paymentMean.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway.PaymentMeanRecord;
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

        // campos para creditCard
        pmr.cardNumber = rs.getString("number");
        pmr.cardType = rs.getString("type");
        pmr.cardValidThru = rs.getDate("validthru");
        // campos para voucher
        pmr.code = rs.getString("code");
        pmr.description = rs.getString("description");
        pmr.available = rs.getBigDecimal("available");
        return pmr;
    }
}
