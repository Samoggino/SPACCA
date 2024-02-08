package com.spacca.database;

import java.io.FileNotFoundException;

public interface Handler {

    boolean salva(Object object, String pathOrFileName);

    boolean elimina(String codice) throws FileNotFoundException;

    Object carica(String codice) throws FileNotFoundException;

    Boolean verificaEsistenzaFile(String codice);

    void modifica(String oldFileName, Object newObject) throws FileNotFoundException;

}