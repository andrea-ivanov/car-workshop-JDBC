package uo.ri.cws.application.persistence.paymentmean.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class PaymentMeanGatewayImpl implements PaymentMeanGateway {

    @Override
    public void add(PaymentMeanRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPAYMENTMEANS_UPDATE"))) {
            pst.setString(1, t.id);
            pst.setString(2, t.dtype);
            pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pst.setString(4, t.entityState);
            pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pst.setLong(6, t.version);
            pst.setBigDecimal(7, t.accumulated);
            pst.setString(8, t.client_id);

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
    public void update(PaymentMeanRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPAYMENTMEANS_UPDATE"))) {
            pst.setBigDecimal(1, t.accumulated);
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pst.setString(3, t.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<PaymentMeanRecord> findById(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPAYMENTMEANS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next()
                        ? Optional.of(new PaymentMeanRecordAssembler().toRecord(rs))
                        : Optional.empty();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<PaymentMeanRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<PaymentMeanRecord> findPayMeansByClientNif(String nif) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TPAYMENTMEANS_FIND_BY_CLIENT_NIF"))) {
            pst.setString(1, nif);
            try (ResultSet rs = pst.executeQuery()) {
                return new PaymentMeanRecordAssembler().toRecordList(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
