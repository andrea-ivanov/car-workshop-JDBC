package uo.ri.cws.application.persistence.paymentMean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.util.exception.PersistenceException;

public interface PaymentMeanGateway extends Gateway<PaymentMeanRecord> {

    public List<PaymentMeanRecord> findPayMeansByClientNif(String nif) throws PersistenceException;

    /**
     * Aquellos atributos que no pertenezcan a la entidad del tipo de pago que se
     * está utilizando quedan a null
     * PaymentMeanRecord
     */
    public class PaymentMeanRecord extends Gateway.BaseRecord {
        public String dtype;
        public BigDecimal accumulated;
        public String client_id;

        // Para el caso de creditCard
        public String cardNumber;
        public String cardType;
        public Date cardValidThru;

        // Para el caso de bono
        public String code;
        public String description;
        public BigDecimal available;
    }
}
