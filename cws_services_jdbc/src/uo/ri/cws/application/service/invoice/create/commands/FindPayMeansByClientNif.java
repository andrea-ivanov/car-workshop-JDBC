package uo.ri.cws.application.service.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentMean.PaymentMeanGateway.PaymentMeanRecord;
import uo.ri.cws.application.persistence.util.command.Command;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.create.PaymentMeanDtoAssembler;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;

public class FindPayMeansByClientNif implements Command<List<PaymentMeanDto>> {

    private String nif;
    private PaymentMeanGateway pmg = Factories.persistence.forPaymentMean();
    private static final String LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    public FindPayMeansByClientNif(String nif) {
        ArgumentChecks.isNotNull(nif, "El nif no puede ser nulo");
        ArgumentChecks.isNotBlank(nif, "El nif no puede estar en blanco");
        checkValidNif(nif);
        this.nif = nif;
    }

    /**
     * Comprueba primero que son 8 número seguidos de una letra y luego comprueba
     * que los cálculos con los números dan la letra
     * 
     * @param nif
     */
    private void checkValidNif(String nif) {
        ArgumentChecks.isTrue(nif.length() == 9, "El nif debe tener 9 caracteres");

        for (int i = 0; i < 8; i++) {
            ArgumentChecks.isTrue(Character.isDigit(nif.charAt(i)),
                    "Los 8 primeros caracteres del nif deben ser números");
        }

        int number = Integer.parseInt(nif.substring(0, 8));
        char expectedLetter = LETTERS.charAt(number % 23);
        char actualLetter = nif.charAt(8);

        ArgumentChecks.isTrue(expectedLetter == actualLetter, "La letra del nif no es correcta");
    }

    @Override
    public List<PaymentMeanDto> execute() throws BusinessException {
        List<PaymentMeanRecord> paymentsMeansRecords = pmg.findPayMeansByClientNif(nif);
        List<PaymentMeanDto> paymentsMeansDtos = new ArrayList<PaymentMeanDto>();

        for (PaymentMeanRecord record : paymentsMeansRecords) {
            PaymentMeanDto dto = PaymentMeanDtoAssembler.toDto(record);
            paymentsMeansDtos.add(dto);
        }

        return paymentsMeansDtos;
    }

}
