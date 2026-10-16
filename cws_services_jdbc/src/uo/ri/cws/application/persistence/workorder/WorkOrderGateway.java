package uo.ri.cws.application.persistence.workorder;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.exception.PersistenceException;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    public List<WorkOrderRecord> findByMechanicId(String idMechanic) throws PersistenceException;

    public class WorkOrderRecord extends Gateway.BaseRecord {
        public String description;
        public String date;
        public String state;
        public String mechanic_Id;
    }

}
