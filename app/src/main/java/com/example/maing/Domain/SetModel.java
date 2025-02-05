package com.example.maing.Domain;

import java.util.Random;

public class SetModel {
    String setName;
    int id_set, id_language, back_img;

    public SetModel(String setName, int id_set, int id_language) {
        this.setName = setName;
        this.id_set = id_set;
        this.id_language = id_language;
        Random random = new Random();
        back_img = random.nextInt(3) + 1;
    }

    public int getBack_img() {
        return back_img;
    }

    public void setBack_img(int back_img) {
        this.back_img = back_img;
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
