package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    public List<InterventionRecord> findByMechanicId(String idMechanic);

    public class InterventionRecord extends Gateway.BaseRecord {
        public LocalDateTime date;
        public int minutes;
        public String mechanicId;
        public String workOrderId;
    }
}
