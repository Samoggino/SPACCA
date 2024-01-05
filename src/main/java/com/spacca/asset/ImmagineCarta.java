package com.spacca.asset;

public class ImmagineCarta {

    public String url;
    public String alt;

    public ImmagineCarta(String nomeFile, String alt) {
        this.url = nomeFile;
        this.alt = alt;
    }

    public ImmagineCarta getImmagine() {
        return this;
    }

}
