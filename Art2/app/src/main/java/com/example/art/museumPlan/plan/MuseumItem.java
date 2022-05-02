package com.example.art.museumPlan.plan;

public class MuseumItem {
    private String note;
    private String museumName;
    private int status;

    public MuseumItem() {
    }

    public MuseumItem(String note, String museumName, int status) {
        this.note = note;
        this.museumName = museumName;
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
