package com.example.maing.Domain;

public class DataSetPacket {
    private String languageName;
    private String setName;
    private String word;
    private String translation;

    public DataSetPacket(String languageName, String setName, String word, String translation) {
        this.languageName = languageName;
        this.setName = setName;
        this.word = word;
        this.translation = translation;
    }

    // Геттеры
    public String getLanguageName() { return languageName; }
    public String getSetName() { return setName; }
    public String getWord() { return word; }
    public String getTranslation() { return translation; }
}
