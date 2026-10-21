package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.util.exception.PersistenceException;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

	@Override
	public void add(InvoiceRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InvoiceRecord t) throws PersistenceException {
		// TODO Auto-generated method stub

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

}
