package com.example.art.info;

public class LikeItem {
    private String name;
    private int status;
    private String logo;
    private String collectionId;
    private String url;

    public LikeItem() {
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

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LikeItem(String name, int status, String logo, String collectionId) {
        this.name = name;
        this.status = status;
        this.logo = logo;
        this.collectionId = collectionId;
    }
}
