package com.example.art.info;

public class CollectionItem {
    private String name;
    private int status;
    private String logo;
    private String id;
    private String url;

    public CollectionItem() {
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CollectionItem(String name, int status, String logo) {
        this.name = name;
        this.status = status;
        this.logo = logo;
    }
}
