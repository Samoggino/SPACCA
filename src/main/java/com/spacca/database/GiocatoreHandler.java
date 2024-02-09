package com.spacca.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.SmartCPU;
import com.spacca.asset.utente.giocatore.StupidCPU;

public class GiocatoreHandler implements Handler {

    @Override
    public boolean salva(Object utenteObject, String username) {

        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

        try (JsonWriter writer = new JsonWriter(new FileWriter(path))) {

            AbstractGiocatore giocatore;
            Gson gson = new Gson();

            if (utenteObject instanceof Giocatore) {
                giocatore = (Giocatore) utenteObject;
                gson.toJson(giocatore, Giocatore.class, writer);
            } else {
                giocatore = (AbstractGiocatore) utenteObject;
                gson.toJson(giocatore, AbstractGiocatore.class, writer);
            }
            return true;
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato in\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
        }
        return false;
    }

    @Override
    public AbstractGiocatore carica(String username) {

        AbstractGiocatore giocatoreVero = null;

        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

        try {
            Reader fileReader = new FileReader(path);

            Gson gson = new Gson();

            // Lettura del file JSON
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            // Ottieni il valore del campo "type"
            String type = jsonObject.get("type").getAsString();

            if (type.equals("Giocatore")) {
                giocatoreVero = gson.fromJson(jsonObject, Giocatore.class);
            } else if (type.equals("SmartCPU")) {
                giocatoreVero = gson.fromJson(jsonObject, SmartCPU.class);
            } else if (type.equals("StupidCPU")) {
                giocatoreVero = gson.fromJson(jsonObject, StupidCPU.class);

            } else {
                System.err.println("ERRORE: Tipo di giocatore non riconosciuto");
            }

            fileReader.close();

        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in " + e.getMessage());
        }

        return giocatoreVero;

    }

    @Override
    public boolean elimina(String username) throws FileNotFoundException {
        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";
        try {

            File file = new File(path);

            AbstractGiocatore giocatoreEliminato = this.carica(username);

            if (verificaEsistenzaFile(username)) {

                if (file.delete()) {

                    if (!giocatoreEliminato.getListaCodiciPartite().isEmpty() ||
                            !giocatoreEliminato.getListaCodiciTornei().isEmpty()) {

                        // Creo un giocatore SmartCPU e trasferisco le partite e i tornei
                        AbstractGiocatore giocatoreSostituto = new AbstractGiocatore(username, "SmartCPU");
                        giocatoreSostituto.setListaCodiciPartite(giocatoreEliminato.getListaCodiciPartite());
                        giocatoreSostituto.setListaCodiciTornei(giocatoreEliminato.getListaCodiciTornei());
                        new GiocatoreHandler().salva(giocatoreSostituto, username);

                    }

                    return true;
                }
            }

        } catch (

        NullPointerException e) {
            System.err.println("Eccezione di puntatore nullo durante l'operazione di eliminazione per il giocatore "
                    + username + ": " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("Errore durante il cast dell'oggetto caricato: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.err.println("Operazione non supportata: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Indice fuori dai limiti: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore sconosciuto durante l'operazione di eliminazione per il giocatore " + username
                    + ": " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean verificaEsistenzaFile(String username) {
        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";
        try {
            File userFile = new File(path);

            // Verifica se il file esiste
            if (userFile.exists() && userFile.isFile()) {
                return true;
            } else {
                return false;
            }

        } catch (NullPointerException e) {
            System.err
                    .println("Eccezione durante l'operazione di ottenimento del percorso della cartella delle risorse: "
                            + e.getMessage());
        } catch (Exception e) {
            // Gestione di altre eccezioni non previste
            System.err.println(
                    "Errore generico durante l'operazione di ottenimento dei nomi dei file: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void modifica(String oldGiocatore, Object newObject) throws FileNotFoundException {
        Giocatore newGiocatore = (Giocatore) newObject;
        // Percorso del file JSON dell'oldGiocatore
        try {

            String path = "src/main/resources/com/spacca/database/giocatori/user-" + oldGiocatore + ".json";

            // se è stato modificato lo username creo il nuovo file ed elimino il vecchio
            if (!oldGiocatore.equals(newGiocatore.getUsername())) {
                salva(newGiocatore, newGiocatore.getUsername());
                elimina(oldGiocatore);
            } else { // se non è stato modificato lo username ricarico il file

                Path playerFilePath = Paths.get(path);
                // Leggi il contenuto del file JSON e deserializza in un oggetto Giocatore
                Gson gson = new Gson();

                // Sovrascrivi il contenuto del file JSON con il nuovo JSON
                String updatedJsonContent = gson.toJson(newGiocatore);
                Files.write(playerFilePath, updatedJsonContent.getBytes());

            }
        } catch (NullPointerException | ClassCastException e) {
            System.err.println("Eccezione durante l'operazione di modifica del giocatore: " + e.getMessage());
        } catch (IOException e) {
            System.err
                    .println("Eccezione di I/O durante l'operazione di modifica del giocatore: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Eccezione durante la deserializzazione del file JSON: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Eccezione di stato illegale durante l'operazione di modifica del giocatore: "
                    + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore generico durante l'operazione di modifica del giocatore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // trasformo la lista di username in giocatori per filtrare in base al tipo
    // ritorno la lista di stringhe filtrate
    public List<String> filtraListaGiocatori(String typeString) {
        List<String> giocatori = new ArrayList<>();
        List<String> appoggio = new ArrayList<>();
        try {
            appoggio = mostraTutteGliUtenti();
            if (appoggio != null) {
                for (String username : appoggio) {
                    try {
                        AbstractGiocatore user = carica(username);

                        if (user.getType().equals(typeString)) {
                            giocatori.add(username);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Giocatore caricato contiente elementi null");

                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("La lista dei giocatori è null (getallgiocatori)");
            }

        } catch (ConcurrentModificationException e) {
            System.err.println("Problema con l'iterazione della lista nel ciclo " + e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("NullPointerException : " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("NullPointerException : " + e);
            e.printStackTrace();
        }
        return giocatori;
    }

    public List<String> mostraTutteGliUtenti() {
        String path = "src/main/resources/com/spacca/database/giocatori/";
        List<String> listaTornei = new ArrayList<>();
        try {
            File dir = new File(path);
            String[] files = dir.list();

            for (String file : files) {
                if (file.startsWith("user-")) {
                    file = file.replace("user-", "");
                    file = file.replace(".json", "");
                    listaTornei.add(file);
                    System.out.println("Giocatore: " + file);
                }
            }

        } catch (NullPointerException e) {
            System.err.println("Errore: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Argomento non valido: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore generico: " + e.getMessage());
        }
        return listaTornei;
    }
}