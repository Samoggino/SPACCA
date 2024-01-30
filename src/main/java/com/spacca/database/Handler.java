package com.spacca.database;

public interface Handler {

    void salva(Object object, String pathOrFileName);

    void elimina(String codice);

    Object carica(String codice);

    Boolean VerificaEsistenzaFile(String codice);

    void modifica(String oldFileName, Object newObject);

}