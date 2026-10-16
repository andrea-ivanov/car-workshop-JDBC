package uo.ri.cws.application.persistence.mechanic;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.util.exception.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {

	Optional<MechanicRecord> findByNif(String nif) throws PersistenceException;
	
	public class MechanicRecord extends Gateway.BaseRecord {
		public String nif;
		public String name;
		public String surname;
		
	}
}
