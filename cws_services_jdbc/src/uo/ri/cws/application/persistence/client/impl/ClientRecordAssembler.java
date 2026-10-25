package uo.ri.cws.application.persistence.client.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

public class ClientRecordAssembler extends BaseRecordAssembler<ClientRecord> {

    public ClientRecordAssembler() {
        super(ClientRecord::new);
    }

    public ClientRecord toRecord(ResultSet rs) throws SQLException {
        ClientRecord cr = super.toRecord(rs);
        cr.city = rs.getString("city");
        cr.country = rs.getString("country");
        cr.postalCode = rs.getString("postalcode");
        cr.street = rs.getString("street");
        cr.name = rs.getString("name");
        cr.surname = rs.getString("surname");
        cr.nif = rs.getString("nif");
        cr.phone = rs.getString("phone");
        cr.email = rs.getString("email");
        return cr;
    }
}
