package com.example.art.theme.museumTheme;

public
class MuseumTagItem {
    private String museumName;
    private String museumId;

    public MuseumTagItem() {
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public MuseumTagItem(String museumName, String museumId) {
        this.museumName = museumName;
        this.museumId = museumId;
    }
}
