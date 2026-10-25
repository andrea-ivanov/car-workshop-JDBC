package uo.ri.cws.application.service.util;

import uo.ri.util.assertion.ArgumentChecks;

public class NifChecks {
    private static final String LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    /*
     * Comprueba primero que son 8 número seguidos de una letra y luego comprueba
     * que los cálculos con los números dan la letra
     * 
     * @param nif
     */
    public static void checkValidNif(String nif) {
        ArgumentChecks.isNotNull(nif, "El nif no puede ser nulo");
        ArgumentChecks.isNotBlank(nif, "El nif no puede estar en blanco");
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
}
