package uo.ri.cws.application.persistence.creditcard.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.NotYetImplementedException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class CreditCardGatewayImpl implements CreditCardGateway {

    @Override
    public void add(CreditCardRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void remove(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void update(CreditCardRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public Optional<CreditCardRecord> findById(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TCREDITCARDS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? Optional.of(new CreditCardRecordAssembler().toRecord(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<CreditCardRecord> findAll() throws PersistenceException {
        throw new NotYetImplementedException();
    }

}
