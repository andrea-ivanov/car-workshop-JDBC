package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

	@Override
	public void add(MechanicRecord t) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_ADD"))) {
			pst.setString(1, t.id);
			pst.setString(2, t.nif);
			pst.setString(3, t.name);
			pst.setString(4, t.surname);
			pst.setLong(5, t.version);
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
		Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_DELETE"))) {
			pst.setString(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(MechanicRecord t) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_UPDATE"))) {
			pst.setString(1, t.name);
			pst.setString(2, t.surname);
			pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pst.setString(4, t.id);

			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<MechanicRecord> findById(String id) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
			pst.setString(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next()
						? Optional.of(new MechanicRecordAssembler().toRecord(rs))
						: Optional.empty();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<MechanicRecord> findAll() throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDALL"))) {
			try (ResultSet rs = pst.executeQuery();) {
				return new MechanicRecordAssembler().toRecordList(rs);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<MechanicRecord> findByNif(String nif) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection(); // fuera del try para que al salir no se cierre la conexión
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TMECHANICS_FINDBYNIF"))) {
			pst.setString(1, nif);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next()
						? Optional.of(new MechanicRecordAssembler().toRecord(rs))
						: Optional.empty();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
