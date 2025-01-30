package com.example.maing.Domain;

public class LanguageModel {
    String language;
    int id_language;

    public LanguageModel(String language, int id_language) {
        this.language = language;
        this.id_language = id_language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId_language() {
        return id_language;
    }

    public void setId_language(int id_language) {
        this.id_language = id_language;
    }
}
