package com.spacca.asset.carte;

public enum Seme {
    BASTONI, COPPE, DENARA, SPADE;

    public static Seme caseInsensitiveValueOf(String value) {

        /**
         * Itera su tutti i valori dell'enumerazione
         * e restituisce il valore corrispondente al parametro
         * passato in input, ignorando la case
         */
        for (Seme seme : Seme.values()) {
            if (seme.name().equalsIgnoreCase(value)) {
                return seme;
            }
        }
        throw new IllegalArgumentException("Valore non valido per l'enumerazione Seme: " + value);
    }
}
