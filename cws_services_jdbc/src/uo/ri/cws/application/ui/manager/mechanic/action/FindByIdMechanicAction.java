package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindByIdMechanicAction implements Action {

	private MechanicCrudService service = Factories.service.forMechanicCrudService();

	@Override
	public void execute() throws Exception {
		String idMechanic = Console.readString("Type mechanic id: ");

		Optional<MechanicDto> dto = service.findById(idMechanic);

		if (dto.isPresent())
			Printer.printMechanic(dto.get());
		else
			Console.println("Mecánico no encontrado");
	}

}
