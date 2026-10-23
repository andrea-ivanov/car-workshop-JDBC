package uo.ri.cws.application.persistence.charge.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.charge.ChargeGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class ChargeGatewayImpl implements ChargeGateway {

    @Override
    public void add(ChargeRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TCHARGES_INSERT"))) {
            pst.setString(1, t.id);
            pst.setLong(2, t.version);
            pst.setBigDecimal(3, t.amount);
            pst.setString(4, t.invoice_id);
            pst.setString(5, t.paymentMean_id);
            pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.setString(8, t.entityState);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void update(ChargeRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<ChargeRecord> findById(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<ChargeRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
