package com.slavik.tdam.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Photo {
    private List<PhotoContent> content = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private String description;
    private String id;
    private boolean isPrimary;
    private Calendar posted;
    private String secret;
    private String server;
    private String title;
    private long views;
    private String localPath;
    private int commentsCount;

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Bitmap getLowQuality() {
        if (content.size() == 0) return null;

        return content.get(0).getBitmap();
    }

    public Bitmap getHighQuality() {
        if (content.size() < 2) return null;

        return content.get(content.size() - 1).getBitmap();
    }

    // SETTERS & GETTERS

    public List<PhotoContent> getContent() {
        return content;
    }

    public void setContent(List<PhotoContent> content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public Calendar getPosted() {
        return posted;
    }

    public void setPosted(Calendar posted) {
        this.posted = posted;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
