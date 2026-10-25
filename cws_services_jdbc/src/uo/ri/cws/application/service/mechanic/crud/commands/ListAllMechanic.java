package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;

public class ListAllMechanic implements Command<List<MechanicDto>> {

    private MechanicGateway mg = Factories.persistence.forMechanic();

    public List<MechanicDto> execute() {
        List<MechanicRecord> mechanicRecords = mg.findAll();
        List<MechanicDto> mechanicsDtos = new ArrayList<MechanicDto>();

        for (MechanicRecord mr : mechanicRecords) {
            MechanicDto dto = MechanicDtoAssembler.toDto(mr);
            mechanicsDtos.add(dto);
        }

        return mechanicsDtos;
    }

}
