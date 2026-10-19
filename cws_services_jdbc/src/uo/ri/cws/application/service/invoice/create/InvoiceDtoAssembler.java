package uo.ri.cws.application.service.invoice.create;

import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;



public class InvoiceDtoAssembler {

	public static InvoiceRecord toRecord(InvoiceDto dto) {
		InvoiceRecord record = new InvoiceRecord();
		record.id = dto.id;              // surrogate id (UUID)
		record.version = dto.version;

		record.total = dto.total;   		 // total amount, VAT included
		record.subtotal = dto.subtotal;      // amount before VAT
		record.vatAmount = dto.vatAmount;     // VAT amount
		record.vatPercentage = dto.vatPercentage; // VAT percentage (e.g. 21 means 21%)

		record.number = dto.number;            // invoice sequential number
		record.date = dto.date;         // invoice date
		record.state = dto.state; 
		return record;
	}

	public static InvoiceDto toDto(InvoiceRecord record) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = record.id;              // surrogate id (UUID)
		dto.version = record.version;

		dto.total = record.total;   		 // total amount, VAT included
		dto.subtotal = record.subtotal;      // amount before VAT
		dto.vatAmount = record.vatAmount;     // VAT amount
		dto.vatPercentage = record.vatPercentage; // VAT percentage (e.g. 21 means 21%)

		dto.number = record.number;            // invoice sequential number
		dto.date = record.date;         // invoice date
		dto.state = record.state; 
		return dto;
	}

	public static Optional<InvoiceDto> toDto(Optional<InvoiceRecord> record) {
		return record.map(InvoiceDtoAssembler::toDto);
	}
	
}
