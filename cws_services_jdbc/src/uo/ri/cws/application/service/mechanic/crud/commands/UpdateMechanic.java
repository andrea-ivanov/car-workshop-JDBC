package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanic {

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

	public void execute() throws BusinessException {

		// check mechanic exists
		Optional<MechanicRecord> checkExists = mg.findById(dto.id);
		BusinessChecks.isFalse(checkExists.isEmpty());

		// update
		MechanicRecord mr = MechanicDtoAssembler.toRecord(dto);
		mg.update(mr);

		// Print result
		Console.println("Mechanic updated");
	}

}
