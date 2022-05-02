package com.example.art.info;

public class HistoryItem {
    private String id;
    private String title;
    private String time;
    private String userId;

    public HistoryItem() {
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public HistoryItem(String title, String time) {
        this.title = title;
        this.time = time;
    }
}
