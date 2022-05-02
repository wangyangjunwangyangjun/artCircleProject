package com.example.art.info;

import java.util.Date;

public class ShareItem {
    private String shareId;
    private String userLogo;
    private String userId;
    private String title;
    private String contentSimple;
    private Date time;
    private int browse;
    private int favor;
    private int comment;
    private String cover;
    private String userName;

    public ShareItem() {
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentSimple() {
        return contentSimple;
    }

    public void setContentSimple(String contentSimple) {
        this.contentSimple = contentSimple;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getBrowse() {
        return browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }

    public int getFavor() {
        return favor;
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ShareItem(String userLogo, String userId, String title, String contentSimple, Date time, int browse, int favor, int comment, String cover, int attention, String userName) {
        this.userLogo = userLogo;
        this.userId = userId;
        this.title = title;
        this.contentSimple = contentSimple;
        this.time = time;
        this.browse = browse;
        this.favor = favor;
        this.comment = comment;
        this.cover = cover;
        this.userName = userName;
    }
}
