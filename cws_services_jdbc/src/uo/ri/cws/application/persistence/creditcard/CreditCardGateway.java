package uo.ri.cws.application.persistence.creditcard;

import java.sql.Date;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway.CreditCardRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway.PaymentMeanRecord;

public interface CreditCardGateway extends Gateway<CreditCardRecord> {

    public class CreditCardRecord extends PaymentMeanRecord {
        public String type;
        public Date validThru;
        public String number;
    }
}