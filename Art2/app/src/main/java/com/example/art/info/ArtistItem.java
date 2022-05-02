package com.example.art.info;

import androidx.annotation.Nullable;

public class ArtistItem {
    private String ArtistId;
    private String name;
    private String cover;
    private String url;

    public ArtistItem() {
    }

    public String getArtistId() {
        return ArtistId;
    }

    public void setArtistId(String artistId) {
        ArtistId = artistId;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int hashCode() {
        return ArtistId.hashCode() * name.hashCode() * cover.hashCode() * url.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(obj instanceof ArtistItem){
            ArtistItem i = (ArtistItem) obj;
            return i.ArtistId.equals(this.ArtistId) && i.name.equals(this.name) && i.cover.equals(this.cover) && i.url.equals(this.url);
        }
        return false;
    }
}

