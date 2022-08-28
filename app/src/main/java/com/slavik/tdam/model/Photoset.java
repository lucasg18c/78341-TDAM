package com.slavik.tdam.model;

import java.util.Calendar;

public class Photoset {
    private String id;
    private String owner;
    private String username;
    private String primary;
    private String secret;
    private String server;
    private int farm;
    private String countViews;
    private String countComments;
    private int countPhotos;
    private int countVideos;
    private String title;
    private String description;
    private boolean canComment;
    private Calendar dateCreate;
    private Calendar dateUpdate;
    private int photos;
    private int videos;
    private boolean visibilityCanSeeSet;
    private boolean needsInterstitial;

    public Photoset() {
    }

    public Photoset(
            String id,
            String owner,
            String username,
            String primary,
            String secret,
            String server,
            int farm,
            String countViews,
            String countComments,
            int countPhotos,
            int countVideos,
            String title,
            String description,
            boolean canComment,
            Calendar dateCreate,
            Calendar date_update,
            int photos,
            int videos,
            boolean visibilityCanSeeSet,
            boolean needsInterstitial) {
        this.id = id;
        this.owner = owner;
        this.username = username;
        this.primary = primary;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.countViews = countViews;
        this.countComments = countComments;
        this.countPhotos = countPhotos;
        this.countVideos = countVideos;
        this.title = title;
        this.description = description;
        this.canComment = canComment;
        this.dateCreate = dateCreate;
        this.dateUpdate = date_update;
        this.photos = photos;
        this.videos = videos;
        this.visibilityCanSeeSet = visibilityCanSeeSet;
        this.needsInterstitial = needsInterstitial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
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

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getCountViews() {
        return countViews;
    }

    public void setCountViews(String countViews) {
        this.countViews = countViews;
    }

    public String getCountComments() {
        return countComments;
    }

    public void setCountComments(String countComments) {
        this.countComments = countComments;
    }

    public int getCountPhotos() {
        return countPhotos;
    }

    public void setCountPhotos(int countPhotos) {
        this.countPhotos = countPhotos;
    }

    public int getCountVideos() {
        return countVideos;
    }

    public void setCountVideos(int countVideos) {
        this.countVideos = countVideos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Calendar getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Calendar dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public int getPhotos() {
        return photos;
    }

    public void setPhotos(int photos) {
        this.photos = photos;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public boolean isVisibilityCanSeeSet() {
        return visibilityCanSeeSet;
    }

    public void setVisibilityCanSeeSet(boolean visibilityCanSeeSet) {
        this.visibilityCanSeeSet = visibilityCanSeeSet;
    }

    public boolean isNeedsInterstitial() {
        return needsInterstitial;
    }

    public void setNeedsInterstitial(boolean needsInterstitial) {
        this.needsInterstitial = needsInterstitial;
    }
}
