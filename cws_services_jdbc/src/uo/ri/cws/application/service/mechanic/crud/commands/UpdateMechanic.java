package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicGateway mg = Factories.persistence.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotBlank(dto.id);
		ArgumentChecks.isNotBlank(dto.nif);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isNotBlank(dto.surname);
		this.dto = dto;
	}

	public Void execute() throws BusinessException {

		// check mechanic exists
		Optional<MechanicRecord> checkExists = mg.findById(dto.id);
		BusinessChecks.isFalse(checkExists.isEmpty());

		MechanicRecord mr = checkExists.get();
		BusinessChecks.hasVersion(dto.version, mr.version);

		// update con los id y versión del mecánico de la base de datos
		mr.id = dto.id;
		mr.version = mr.version;
		mr.name = dto.name;
		mr.surname = dto.surname;
		mg.update(mr);

		return null;
	}

}
