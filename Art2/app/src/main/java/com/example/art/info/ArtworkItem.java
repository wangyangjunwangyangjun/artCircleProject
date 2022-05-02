package com.example.art.info;

import androidx.annotation.Nullable;

public class ArtworkItem {
    private String id;
    private String image;
    private String title;
    private String museum;
    private String url;
    private String status;

    public ArtworkItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMuseum() {
        return museum;
    }

    public void setMuseum(String museum) {
        this.museum = museum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return image.hashCode() * title.hashCode() * museum.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(obj instanceof ArtworkItem){
            ArtworkItem i = (ArtworkItem) obj;
            return i.museum.equals(this.museum) && i.title.equals(this.title) && i.image.equals(this.image);
        }
        return false;
    }
}
