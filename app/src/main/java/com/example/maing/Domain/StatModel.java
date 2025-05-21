package com.example.maing.Domain;

public class StatModel {
    String setName;
    int id_stat, correct, incorrect, skipped;

    public StatModel(String setName, int id_stat, int correct, int incorrect, int skipped) {
        this.setName = setName;
        this.id_stat = id_stat;
        this.correct = correct;
        this.incorrect = incorrect;
        this.skipped = skipped;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public int getId_stat() {
        return id_stat;
    }

    public void setId_stat(int id_stat) {
        this.id_stat = id_stat;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }
}
