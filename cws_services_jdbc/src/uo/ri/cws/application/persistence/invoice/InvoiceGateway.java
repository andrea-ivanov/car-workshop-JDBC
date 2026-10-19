package uo.ri.cws.application.persistence.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public interface InvoiceGateway extends Gateway<InvoiceRecord>{

	public class InvoiceRecord extends Gateway.BaseRecord {
	    public BigDecimal total;   		 // total amount, VAT included
	    public BigDecimal subtotal;      // amount before VAT
	    public BigDecimal vatAmount;     // VAT amount
	    public BigDecimal vatPercentage; // VAT percentage (e.g. 21 means 21%)

	    public long number;            // invoice sequential number
	    public LocalDate date;         // invoice date
	    public String state;           // as in InvoiceState
	}
	
}
