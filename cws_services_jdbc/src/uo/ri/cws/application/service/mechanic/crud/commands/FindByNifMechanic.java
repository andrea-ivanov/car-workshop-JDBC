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

public class FindByNifMechanic implements Command<Optional<MechanicDto>> {

    private String nif;
    private MechanicGateway mg = Factories.persistence.forMechanic();

    public FindByNifMechanic(String nif) {
        ArgumentChecks.isNotNull(nif, "El NIF del mecanico no puede ser nulo.");
        ArgumentChecks.isNotBlank(nif, "El NIF del mecanico no puede estar en blanco.");
        this.nif = nif;
    }

    @Override
    public Optional<MechanicDto> execute() throws BusinessException {
        Optional<MechanicRecord> mechanic = mg.findByNif(nif);
        return MechanicDtoAssembler.toDto(mechanic);
    }

}
