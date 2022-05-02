package com.example.art.info;

import java.util.Date;

public class info8 {
    int cover;
    String title;
    double grade;
    Date time;
    int people;
    String pay;
    String location;
    int distance;

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public info8(int cover, String title, double grade, Date time, int people, String pay, String location, int distance) {
        this.cover = cover;
        this.title = title;
        this.grade = grade;
        this.time = time;
        this.people = people;
        this.pay = pay;
        this.location = location;
        this.distance = distance;
    }
}
