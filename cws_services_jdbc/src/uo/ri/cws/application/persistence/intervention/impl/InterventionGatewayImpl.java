package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.NotYetImplementedException;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public void add(InterventionRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void remove(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public Optional<InterventionRecord> findById(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<InterventionRecord> findByMechanicId(String idMechanic) {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINTERVENTIONS_FINDBYMECHANICID"))) {
            pst.setString(1, idMechanic);
            try (ResultSet rs = pst.executeQuery()) {
                return new BaseRecordAssembler<>(InterventionRecord::new).toRecordList(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
