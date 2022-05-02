package com.example.art.museumPlan.unUsed;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;



public class OrderInfo {
    private long id;
    private String name;
    private Status status;
    private boolean isBegin;

    public enum Status {
        UNFINISHED,
        SET_OUT,
        BLANK;

        private static final List<Status> VALUES = Arrays.asList(values());
//                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();
    }

    public String getGuestName() {
        return name;
    }

    public void setGuestName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isBegin() {
        return isBegin;
    }

    public void setBegin(boolean begin) {
        isBegin = begin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
