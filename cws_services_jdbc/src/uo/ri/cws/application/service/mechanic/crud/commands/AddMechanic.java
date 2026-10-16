package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;
import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddMechanic implements Command<MechanicDto> {

	// private static final String TMECHANICS_ADD = "insert into TMechanics" + "(id,
	// nif, name, surname, version, "
	// + "createdAt, updatedAt, entityState) " + "values (?, ?, ?, ?, ?, ?, ?, ?)";
	// private static final String TMECHANICS_FINDBYNIF = "select * from TMechanics
	// where nif = ?";
	private MechanicDto dto;
	private MechanicGateway mg = Factories.persistence.forMechanic();

	public AddMechanic(MechanicDto dto) throws BusinessException {
		ArgumentChecks.isNotNull(dto, "The mechanic cannot be null");
		ArgumentChecks.isNotBlank(dto.nif, "The nif cannot be blank or null");
		ArgumentChecks.isNotBlank(dto.name, "The name cannot be blank or null");
		ArgumentChecks.isNotBlank(dto.surname, "The surname cannot be blank or null");
		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {

		Optional<MechanicRecord> check = mg.findByNif(dto.nif);
		BusinessChecks.isTrue(check.isEmpty(), "Ya existe un mecanico con nif " + dto.nif);

		MechanicRecord record = MechanicDtoAssembler.toRecord(dto);
		mg.add(record);

		dto.id = record.id;
		dto.version = record.version;

		return dto;
	}

}
