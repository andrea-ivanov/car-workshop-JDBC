package uo.ri.cws.application.persistence.voucher.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class VoucherGatewayImpl implements VoucherGateway {

    @Override
    public void add(VoucherRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void update(VoucherRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<VoucherRecord> findById(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<VoucherRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<VoucherRecord> findVouchersByClientNif(String nif) {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence(""))) {

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
