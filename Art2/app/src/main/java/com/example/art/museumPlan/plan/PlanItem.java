package com.example.art.museumPlan.plan;

public
class PlanItem {
    private String id;
    private String museumName;
    private String museumId;
    private String time;
    private int status;
    private String note;

    public PlanItem() {
    }

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PlanItem(String id, String museumName, String time, int status, String note) {
        this.id = id;
        this.museumName = museumName;
        this.time = time;
        this.status = status;
        this.note = note;
    }
}
