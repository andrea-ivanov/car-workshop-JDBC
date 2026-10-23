package uo.ri.cws.application.persistence.charge;

import java.math.BigDecimal;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.charge.ChargeGateway.ChargeRecord;

public interface ChargeGateway extends Gateway<ChargeRecord> {

    public class ChargeRecord extends Gateway.BaseRecord {
        public String invoice_id;
        public String paymentMean_id;
        public BigDecimal amount;
    }
}
