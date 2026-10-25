package uo.ri.cws.application.persistence.workorder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;
import uo.ri.util.exception.PersistenceException;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    public List<WorkOrderRecord> findByMechanicId(String idMechanic) throws PersistenceException;

    public List<WorkOrderRecord> findNotInvoicedWorkOrdersByClient(String nif) throws PersistenceException;

    public List<WorkOrderRecord> findPaidAndUnusedByClientId(String id) throws PersistenceException;

    public class WorkOrderRecord extends Gateway.BaseRecord {
        public String vehicleId;
        public String description;
        public LocalDateTime date;
        public BigDecimal amount;
        public String state;
        public boolean usedForVoucher;
        // might be null
        public String mechanicId;
        public String invoiceId;
    }

}
