package uo.ri.cws.application.persistence.intervention;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    public class InterventionRecord extends Gateway.BaseRecord {
        private String intervention_Id;
    }

    public List<InterventionRecord> findByMechanicId(String idMechanic);

}
