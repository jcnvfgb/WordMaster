package com.example.maing.Domain;

public class WordModel {
    String word, translation, wordActivity;
    int id_word, id_set;

    public WordModel(String word, String translation, String wordActivity, int id_word, int id_set) {
        this.word = word;
        this.translation = translation;
        this.wordActivity = wordActivity;
        this.id_word = id_word;
        this.id_set = id_set;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWordActivity() {
        return wordActivity;
    }

    public void setWordActivity(String wordActivity) {
        this.wordActivity = wordActivity;
    }

    public int getId_word() {
        return id_word;
    }

    public void setId_word(int id_word) {
        this.id_word = id_word;
    }

    public int getId_set() {
        return id_set;
    }

    public void setId_set(int id_set) {
        this.id_set = id_set;
    }
}
