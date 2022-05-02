package com.example.art.info;

public class ScanItem {
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScanItem(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
