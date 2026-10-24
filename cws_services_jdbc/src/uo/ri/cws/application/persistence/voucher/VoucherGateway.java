package uo.ri.cws.application.persistence.voucher;

import java.math.BigDecimal;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;
import uo.ri.util.exception.PersistenceException;

public interface VoucherGateway extends Gateway<VoucherRecord> {

    public List<VoucherRecord> findVouchersByClientNif(String nif) throws PersistenceException;

    public class VoucherRecord extends PaymentMeanRecord {
        public String code;
        public String description;
        public BigDecimal available;
    }

}
