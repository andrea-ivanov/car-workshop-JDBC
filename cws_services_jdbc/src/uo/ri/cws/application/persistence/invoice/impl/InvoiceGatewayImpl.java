package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

	@Override
	public void add(InvoiceRecord t) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_INSERT"))) {
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			pst.setString(1, t.id);
			pst.setLong(2, t.version);
			pst.setLong(3, t.number);
			pst.setDate(4, java.sql.Date.valueOf(t.date));
			pst.setString(5, t.state);
			pst.setBigDecimal(6, t.subtotal);
			pst.setBigDecimal(7, t.total);
			pst.setBigDecimal(8, t.vatPercentage);
			pst.setBigDecimal(9, t.vatAmount);
			pst.setBoolean(10, t.usedForVoucher);
			pst.setTimestamp(11, now);
			pst.setTimestamp(12, now);
			pst.setString(13, t.entityState);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InvoiceRecord t) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_UPDATE"))) {
			pst.setString(1, t.state);
			pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pst.setString(3, t.id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Optional<InvoiceRecord> findById(String id) throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_FIND_BY_ID"))) {
			pst.setString(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next()
						? Optional.of(new InvoiceRecordAssembler().toRecord(rs))
						: Optional.empty();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<InvoiceRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_FIND_BY_NUMBER"))) {
			pst.setLong(1, number);
			try (ResultSet rs = pst.executeQuery()) {
				return rs.next()
						? Optional.of(new InvoiceRecordAssembler().toRecord(rs))
						: Optional.empty();
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Long getNextInvoiceNumber() throws PersistenceException {
		Connection c = Jdbc.getCurrentConnection();
		try (PreparedStatement pst = c.prepareStatement(Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"))) {
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getLong(1) + 1;
				}
				return 1L;
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

}
