package uo.ri.cws.application.persistence.voucher.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.util.exception.NotYetImplementedException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class VoucherGatewayImpl implements VoucherGateway {

    @Override
    public void add(VoucherRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TVOUCHERS_ADD"))) {
            pst.setString(1, t.id);
            pst.setString(2, t.code);
            pst.setString(3, t.description);
            pst.setBigDecimal(4, t.available);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void update(VoucherRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TVOUCHERS_UPDATE"))) {
            pst.setBigDecimal(1, t.available);
            pst.setString(2, t.id);

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<VoucherRecord> findById(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TVOUCHERS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? Optional.of(new VoucherRecordAssembler().toRecord(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<VoucherRecord> findAll() throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<VoucherRecord> findVouchersByClientNif(String nif) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TVOUCHERS_FIND_BY_CLIENT_NIF"))) {
            pst.setString(1, nif);
            try (ResultSet rs = pst.executeQuery()) {
                return new VoucherRecordAssembler().toRecordList(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
