package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.console.Console;

public class ListAllMechanic {
	
	private static final String TMECHANICS_FINDALL = "SELECT ID, NAME, " + "SURNAME, NIF, VERSION FROM TMECHANICS";

	public List<MechanicDto> execute() {
		List<MechanicDto> listOfMechanics = new ArrayList<MechanicDto>(); 
		
		Console.println("\nList of mechanics \n");
		// Process
		try (Connection c = Jdbc.createThreadConnection()) {
			try (PreparedStatement pst = c.prepareStatement(TMECHANICS_FINDALL)) {
				try (ResultSet rs = pst.executeQuery();) {
					while (rs.next()) {
						Console.printf("\t%s %s %s %s %d\n", rs.getString(1), rs.getString(2), rs.getString(3),
								rs.getString(4), rs.getLong(5));
						MechanicDto dto = new MechanicDto();
						dto.id = rs.getString("id");
						dto.nif = rs.getString("nif");
						dto.name = rs.getString("name");
						dto.surname = rs.getString("surname");
						dto.version = rs.getLong("version");
						listOfMechanics.add(dto);
					}
					return listOfMechanics;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
