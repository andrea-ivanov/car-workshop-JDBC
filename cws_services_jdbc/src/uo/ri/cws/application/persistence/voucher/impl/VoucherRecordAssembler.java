package uo.ri.cws.application.persistence.voucher.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;

public class VoucherRecordAssembler extends BaseRecordAssembler<VoucherRecord> {

    public VoucherRecordAssembler() {
        super(VoucherRecord::new);
    }

    @Override
    public VoucherRecord toRecord(ResultSet rs) throws SQLException {
        VoucherRecord vr = super.toRecord(rs);
        vr.clientId = rs.getString("client_id");
        vr.accumulatedAmount = rs.getBigDecimal("accumulated");
        vr.code = rs.getString("code");
        vr.description = rs.getString("description");
        vr.availableAmount = rs.getBigDecimal("available");
        return vr;
    }
}
