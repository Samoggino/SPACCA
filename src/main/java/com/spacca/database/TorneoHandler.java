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
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;

public class TorneoHandler implements Handler {

    /**
     * salva la partita in un file JSON
     * 
     * @param torneo       l'istanza del torneo da salvare
     * @param codiceTorneo il codice del torneo con cui verrà nominato il
     *                     salvataggio
     */

    @Override // passare il codice per esteso
    public boolean salva(Object torneoObject, String codiceTorneo) {
        try {
            Torneo torneo = (Torneo) torneoObject;

            if (torneoObject == null || codiceTorneo == null) {
                throw new NullPointerException("L'oggetto torneo o il codice del torneo non possono essere nulli.");
            }

            codiceTorneo = "src/main/resources/com/spacca/database/tornei/" + codiceTorneo + "/" + codiceTorneo
                    + ".json";

            /* Implementare creazione partite anche? */

            try (JsonWriter writer = new JsonWriter(new FileWriter(codiceTorneo))) {
                Gson gson = new Gson();
                gson.toJson(torneo, Torneo.class, writer);
                return true;
            } catch (JsonIOException e) {
                System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
                        this.getClass().getName() + "\n" + e.getMessage());
            } catch (FileNotFoundException e) {
                System.err.println("ERRORE: File non trovato in\n" + "\n" + e.getMessage());
            } catch (IOException e) {
                System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
                        this.getClass().getName() + "\n" + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERRORE: Errore generico nel salvataggio torneo in\n" + "\n" + e.getMessage());
            }
        } catch (NullPointerException e) {
            System.err.println("L'oggetto torneo o il codice del torneo sono nulli");
        }
        return false;
    }

    @Override
    public Torneo carica(String codice) {
        Torneo torneo = null;
        try {
            if (codice == null) {
                throw new NullPointerException("Il codice del torneo non può essere nullo.");
            }
            String path = "src/main/resources/com/spacca/database/tornei/" + codice + "/" + codice + ".json";

            Reader fileReader = new FileReader(path);
            Gson gson = new Gson();

            torneo = gson.fromJson(fileReader, Torneo.class);

            fileReader.close();
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + e.getMessage());
            // e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: Questo codice torneo non è valido." + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + this.getClass().getName() + "\n" + e.getMessage());
        }

        return torneo;
    }

    @Override
    public boolean elimina(String codice) {

        try {
            if (codice == null) {
                throw new IllegalArgumentException("Il codice del torneo non può essere nullo.");
            }
            String path = "src/main/resources/com/spacca/database/tornei/" + codice;

            if (verificaEsistenzaFile(codice)) {

                Torneo torneo = (Torneo) carica(codice);
                for (String codicePartita : torneo.getCodiciPartite()) {
                    Partita partita = new PartitaHandler().carica(codicePartita);
                    new PartitaHandler().elimina(partita.getCodice());
                }

                // rimuovi il codice del torneo da tutti i partecipanti

                for (String giocatore : torneo.getGiocatoriRimasti()) {
                    new GiocatoreHandler().carica(giocatore).removeCodiceTorneo(codice);
                }

                rmdir(path);
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("ERRORE (elimina): Il codice del torneo non può essere nullo." + e.getLocalizedMessage()
                    + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Elemento null " + e.getLocalizedMessage() + e.getMessage());
            e.printStackTrace();
        } catch (ConcurrentModificationException e) {
            System.err.println("Problema con il prefisso della Torneo perchè file non trovato" + e.getLocalizedMessage()
                    + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRORE (elimina): " + e.getLocalizedMessage() + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean elimina(Torneo torneo) {
        return elimina(torneo.getCodice());
    }

    @Override
    public Boolean verificaEsistenzaFile(String codice) {
        String path = "src/main/resources/com/spacca/database/tornei/" + codice + "/" + codice + ".json";
        try {
            if (codice == null) {
                throw new NullPointerException("Il codice del torneo non può essere nullo.");
            }
            File userFile = new File(path);

            // Verifica se il file esiste
            if (userFile.exists() && userFile.isFile()) {
                return true;
            }
        } catch (NullPointerException e) {
            System.err.println("ERRORE: Codice nullo in VerificaEsistenzaFile.");
            return false; // O gestire diversamente l'eccezione a seconda delle esigenze
        }
        return false;
    }

    @Override
    public void modifica(String oldFileName, Object newObject) {
    }

    public void mkdir(String codice) {

        File dir = new File("src/main/resources/com/spacca/database/tornei/" + codice);
        dir.mkdir();
    }

    public void rmdir(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            Files.walk(path)
                    .sorted(Collections.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            System.out.println("Cartella eliminata con successo.");
        } catch (IOException e) {
            System.err.println("Errore durante l'eliminazione della cartella: " + e.getMessage());
        }
    }

    public List<String> mostraTuttiITornei() {
        String path = "src/main/resources/com/spacca/database/tornei/";
        List<String> listaTornei = new ArrayList<>();
        try {
            File dir = new File(path);
            String[] files = dir.list();

            for (String file : files) {
                if (file.startsWith("T")) {
                    listaTornei.add(file);
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