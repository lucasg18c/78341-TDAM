package com.slavik.tdam.model;

import java.util.Calendar;

public class Comment {
    private User author;
    private String content;
    private String id;
    private Calendar posted;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getPosted() {
        return posted;
    }

    public void setPosted(Calendar posted) {
        this.posted = posted;
    }
}
