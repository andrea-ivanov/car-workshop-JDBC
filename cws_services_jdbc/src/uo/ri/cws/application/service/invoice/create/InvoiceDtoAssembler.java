package uo.ri.cws.application.service.invoice.create;

import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.crud.MechanicDtoAssembler;

public class InvoiceDtoAssembler {

	public static InvoiceRecord toRecord(InvoiceDto dto) {
		InvoiceRecord record = new InvoiceRecord();

		// TODO : cambiar parámetros
		
		return record;
	}

	public static MechanicDto toDto(MechanicRecord record) {
		MechanicDto dto = new MechanicDto();
		dto.id = record.id;
		dto.version = record.version;
		dto.nif = record.nif;
		dto.name = record.name;
		dto.surname = record.surname;
		return dto;
	}

	public static Optional<MechanicDto> toDto(Optional<MechanicRecord> record) {
		return record.map(MechanicDtoAssembler::toDto);
	}
	
}
