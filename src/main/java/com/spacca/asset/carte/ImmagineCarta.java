package com.spacca.asset.carte;

import com.google.gson.annotations.SerializedName;

public class ImmagineCarta {

    @SerializedName("url")
    private String url;

    @SerializedName("alt")
    private String alt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public ImmagineCarta(String nomeFile, String alt) {
        this.url = nomeFile;
        this.alt = alt;
    }

    public ImmagineCarta getImmagine() {
        return this;
    }

}
