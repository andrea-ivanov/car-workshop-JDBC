package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {

	private MechanicCrudService service = Factories.service.forMechanicCrudService();

	@Override
	public void execute() throws BusinessException {

		service.findAll();
	}
}