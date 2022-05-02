package com.example.art.info;

import java.util.Date;

public class info6 {
    private int eassycover;
    private String eassyName;
    private String author;
    private String id;
    private String version;
    private Date date;
    private String controlledId;

    public int getEassycover() {
        return eassycover;
    }

    public void setEassycover(int eassycover) {
        this.eassycover = eassycover;
    }

    public String getEassyName() {
        return eassyName;
    }

    public void setEassyName(String eassyName) {
        this.eassyName = eassyName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getControlledId() {
        return controlledId;
    }

    public void setControlledId(String controlledId) {
        this.controlledId = controlledId;
    }

    public info6(int eassycover, String eassyName, String author, String id, String version, Date date, String controlledId) {
        this.eassycover = eassycover;
        this.eassyName = eassyName;
        this.author = author;
        this.id = id;
        this.version = version;
        this.date = date;
        this.controlledId = controlledId;
    }
}
