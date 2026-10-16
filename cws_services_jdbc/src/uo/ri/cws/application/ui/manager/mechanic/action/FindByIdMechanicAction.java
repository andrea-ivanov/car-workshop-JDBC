package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindByIdMechanicAction implements Action{

	private MechanicCrudService service = Factories.service.forMechanicCrudService();
	
	@Override
	public void execute() throws Exception {
		String idMechanic = Console.readString("Type mechanic id: ");
		service.findById(idMechanic);
		Console.println("Mechanic found");
	}

}
