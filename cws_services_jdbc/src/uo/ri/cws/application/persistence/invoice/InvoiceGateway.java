package uo.ri.cws.application.persistence.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.util.exception.PersistenceException;

public interface InvoiceGateway extends Gateway<InvoiceRecord> {

    Optional<InvoiceRecord> findByNumber(Long number) throws PersistenceException;

    List<InvoiceRecord> findPaidAndUsedOver500ByClientId(String id) throws PersistenceException;

    Long getNextInvoiceNumber() throws PersistenceException;

    public class InvoiceRecord extends Gateway.BaseRecord {
        public BigDecimal total; // total amount, VAT included
        public BigDecimal subtotal; // amount before VAT
        public BigDecimal vatAmount; // VAT amount
        public BigDecimal vatPercentage; // VAT percentage (e.g. 21 means 21%)

        public long number; // invoice sequential number
        public LocalDate date; // invoice date
        public String state; // as in InvoiceState
        public boolean usedForVoucher;
    }

}
