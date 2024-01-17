package com.spacca.database;

public interface Handler {

    void salva(Object object, String pathOrFileName);

    void elimina(String codice);

    Object carica(String codice);

}