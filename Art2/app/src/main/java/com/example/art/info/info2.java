package com.example.art.info;

public class info2 {
    private String id;
    private String time;
    private int type;
    private String url;
    private String cover;
    private String keyword;
    private String title;
    private String editor;
    private String source;
    private String content;

    public info2() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public info2(String id, String time, int type, String url, String cover, String keyword, String title, String editor, String source, String content) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.url = url;
        this.cover = cover;
        this.keyword = keyword;
        this.title = title;
        this.editor = editor;
        this.source = source;
        this.content = content;
    }
}
