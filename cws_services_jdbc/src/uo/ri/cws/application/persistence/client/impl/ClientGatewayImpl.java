package uo.ri.cws.application.persistence.client.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.NotYetImplementedException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class ClientGatewayImpl implements ClientGateway {

    @Override
    public void add(ClientRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void remove(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void update(ClientRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public Optional<ClientRecord> findById(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TCLIENTS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? Optional.of(new ClientRecordAssembler().toRecord(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ClientRecord> findAll() throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TCLIENTS_FIND_ALL"))) {
            try (ResultSet rs = pst.executeQuery();) {
                return new ClientRecordAssembler().toRecordList(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
