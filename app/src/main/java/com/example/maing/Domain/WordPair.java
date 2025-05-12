package com.example.maing.Domain;

public class WordPair {
    private String original;
    private String translation;
    private boolean isMatched;
    private boolean isSelected;

    public WordPair(String original, String translation) {
        this.original = original;
        this.translation = translation;
    }

    // Getters and setters
    public boolean isMatched() { return isMatched; }
    public void setMatched(boolean matched) { isMatched = matched; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
    public String getOriginal() { return original; }
    public String getTranslation() { return translation; }
}
