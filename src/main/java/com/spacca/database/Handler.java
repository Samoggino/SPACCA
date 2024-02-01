package com.spacca.database;

public interface Handler {

    boolean salva(Object object, String pathOrFileName);

    boolean elimina(String codice);

    Object carica(String codice);

    Boolean VerificaEsistenzaFile(String codice);

    void modifica(String oldFileName, Object newObject);

}