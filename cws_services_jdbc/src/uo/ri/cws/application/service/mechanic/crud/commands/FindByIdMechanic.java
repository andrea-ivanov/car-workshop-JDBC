package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindByIdMechanic implements Command<Optional<MechanicDto>> {

	private String id;
	private MechanicGateway mg = Factories.persistence.forMechanic();

	public FindByIdMechanic(String id) {
		ArgumentChecks.isNotNull(id, "The id cannot be null");
		ArgumentChecks.isNotBlank(id, "The id cannot be blank");
		this.id = id;
	}

	@Override
	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<MechanicRecord> omr = mg.findById(id);
		return MechanicDtoAssembler.toDto(omr);
	}

}
