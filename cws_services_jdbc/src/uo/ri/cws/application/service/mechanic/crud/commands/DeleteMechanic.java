package uo.ri.cws.application.service.mechanic.crud.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class DeleteMechanic implements Command<Void> {

	private String idMechanic;
	private MechanicGateway mg = Factories.persistence.forMechanic();
	private WorkOrderGateway wog = Factories.persistence.forWorkOrder();
	private InterventionGateway ig = Factories.persistence.forIntervention();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId, "El id del mecanico no puede ser nulo");
		ArgumentChecks.isNotBlank(mechanicId, "El id del mecanico no puede ser nulo");

		this.idMechanic = mechanicId;
	}

	public Void execute() throws BusinessException {

		// Check existe el mecanico a eliminar
		Optional<MechanicRecord> checkMehcanic = mg.findById(idMechanic);
		BusinessChecks.isFalse(checkMehcanic.isEmpty(), "No existe mecanico con id " + idMechanic);

		// Check no tiene workorders
		List<WorkOrderRecord> checkWorkorder = wog.findByMechanicId(idMechanic);
		BusinessChecks.isTrue(checkWorkorder.isEmpty(),
				"El mecanico no se puede borrar porque tiene averías asignadas.");

		// Check no tiene intervenciones
		List<InterventionRecord> checkIntervention = ig.findByMechanicId(idMechanic);
		BusinessChecks.isTrue(checkIntervention.isEmpty(),
				"El mecanico no se puede borrar porque tiene intervenciones");

		mg.remove(idMechanic);

		return null;
	}

}
