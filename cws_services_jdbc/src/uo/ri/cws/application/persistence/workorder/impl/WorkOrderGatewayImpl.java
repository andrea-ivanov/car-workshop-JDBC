package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_UPDATE"))) {
            pst.setString(1, t.invoiceId);
            pst.setString(2, t.state);
            pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(4, t.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<WorkOrderRecord> findById(String id) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection();
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_BY_ID"))) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next()
                        ? Optional.of(new WorkOrderRecordAssembler().toRecord(rs))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<WorkOrderRecord> findByMechanicId(String idMechanic) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FINDBYMECHANICID"))) {
            pst.setString(1, idMechanic);
            try (ResultSet rs = pst.executeQuery()) {
                return new BaseRecordAssembler<>(WorkOrderRecord::new).toRecordList(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<WorkOrderRecord> findNotInvoicedWorkOrdersByClient(String nif) throws PersistenceException {
        Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión

        try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
            pst.setString(1, nif);

            try (ResultSet rs = pst.executeQuery()) {
                return new WorkOrderRecordAssembler().toRecordList(rs);
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
