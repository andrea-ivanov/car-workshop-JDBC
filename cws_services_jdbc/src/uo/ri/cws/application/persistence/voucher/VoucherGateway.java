package uo.ri.cws.application.persistence.voucher;

import java.math.BigDecimal;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway.VoucherRecord;

public interface VoucherGateway extends Gateway<VoucherRecord> {

    public class VoucherRecord extends Gateway.BaseRecord {
        public String clientId;
        public BigDecimal accumulatedAmount;
        public String code;
        public String description;
        public BigDecimal availableAmount;
    }

    public List<VoucherRecord> findVouchersByClientNif(String nif);

}
