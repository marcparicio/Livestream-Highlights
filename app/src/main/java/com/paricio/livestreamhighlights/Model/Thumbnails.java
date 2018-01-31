package com.paricio.livestreamhighlights.Model;

public class Thumbnails {

    private String medium;
    private String small;
    private String tiny;

    public Thumbnails() {

    }

    public Thumbnails(String medium, String small, String tiny) {
        this.medium = medium;
        this.small = small;
        this.tiny = tiny;
    }

    public String getMedium() {
        return medium;
    }

    public String getSmall() {
        return small;
    }

    public String getTiny() {
        return tiny;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }
}
