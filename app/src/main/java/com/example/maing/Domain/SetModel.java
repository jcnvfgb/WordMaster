package com.example.maing.Domain;

public class SetModel {
    String setName;
    int id_set, id_language;

    public SetModel(String setName, int id_set, int id_language) {
        this.setName = setName;
        this.id_set = id_set;
        this.id_language = id_language;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public int getId_set() {
        return id_set;
    }

    public void setId_set(int id_set) {
        this.id_set = id_set;
    }

    public int getId_language() {
        return id_language;
    }

    public void setId_language(int id_language) {
        this.id_language = id_language;
    }
}
