package com.spacca.asset.carte;

public enum Nome {
    ASSO, DUE, TRE, QUATTRO, CINQUE, SEI, SETTE, FANTE, CAVALLO, RE;

    public static Nome caseInsensitiveValueOf(String value) throws IllegalArgumentException {

        /**
         * Itera su tutti i valori dell'enumerazione
         * e restituisce il valore corrispondente al parametro
         * passato in input, ignorando la case
         */
        for (Nome nome : Nome.values()) {
            if (nome.name().equalsIgnoreCase(value)) {
                return nome;
            }
        }
        throw new IllegalArgumentException("Valore non valido per l'enumerazione Nome: " + value);
    }
}
