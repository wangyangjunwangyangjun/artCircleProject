package com.example.art.museumDetail;

class planItem {
    private String time;
    private String note;

    public planItem(String time, String note) {
        this.time = time;
        this.note = note;
    }

    public planItem() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
