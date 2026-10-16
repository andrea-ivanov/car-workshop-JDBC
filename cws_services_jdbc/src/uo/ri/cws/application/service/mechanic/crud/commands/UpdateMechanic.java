package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic {

	private static final String TMECHANICS_FINDBYID = "select * from TMechanics where id = ?";
	private static final String TMECHANICS_UPDATE = "update TMechanics set nif = ?,  name = ?, surname = ?, "
			+ "version = version + 1, updatedat = ?" + "where id = ? and version = ?";

	
	private MechanicDto dto;
	
	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public void execute() throws BusinessException {

		// Get info
		String id = dto.id;
		long version = dto.version;

		// check mechanic exists
		checkMechanicExists(id);
		// Ask for new data
		
		
		// nif is the identity, cannot be changed	pero para el test hay que cambiarlo
		String nif = dto.nif;
		
		
		String name = dto.name;
		String surname = dto.surname;

		// update
		updateMechanic(nif, id, name, surname, version);

		// Print result
		Console.println("Mechanic updated");
	}

	private void updateMechanic(String nif, String id, String name, String surname, long version) throws BusinessException {

		// Process
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c.prepareStatement(TMECHANICS_UPDATE)) {
				pst.setString(1, nif);
				pst.setString(2, name);
				pst.setString(3, surname);
				pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				pst.setString(5, id);
				pst.setLong(6, version);

				int updatedRows = pst.executeUpdate();
				if(updatedRows == 0)
					throw new BusinessException("Mecanico modificado concurrentemente.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void checkMechanicExists(String id) throws BusinessException {
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c.prepareStatement(TMECHANICS_FINDBYID)) {
				pst.setString(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					if (!rs.next()) {
						throw new BusinessException("Mechanic does not exist");
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
