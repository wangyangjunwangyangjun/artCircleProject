package com.example.art.info;

import androidx.annotation.Nullable;

public class MuseumItem {
    private String museumId;
    private String name;
    private String country;
    private String cover;

    public MuseumItem() {
    }

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContury() {
        return country;
    }

    public void setContury(String country) {
        this.country = country;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public MuseumItem(String museumId, String name, String country, String cover) {
        this.museumId = museumId;
        this.name = name;
        this.country = country;
        this.cover = cover;
    }

    @Override
    public int hashCode() {
        return museumId.hashCode() * name.hashCode() * country.hashCode()*cover.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(obj instanceof MuseumItem){
            MuseumItem i = (MuseumItem) obj;
            return i.museumId.equals(this.museumId) && i.name.equals(this.name) && i.country.equals(this.country) && i.cover.equals(this.cover);
        }
        return false;
    }
}
