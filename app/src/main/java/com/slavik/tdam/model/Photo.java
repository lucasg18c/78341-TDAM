package com.slavik.tdam.model;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class Photo {
    private String id;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private boolean isPrimary;
    private boolean isPublic;
    private boolean isFriend;
    private boolean isFamily;

    private long viewsCount;
    private String description;
    private Calendar dateUploaded;
    private long commentCount;

    private Bitmap image;

    public Photo() {
    }

    public Photo(String id,
                 String secret,
                 String server,
                 int farm,
                 String title,
                 boolean isPublic,
                 boolean isFriend,
                 boolean isFamily,
                 long viewsCount,
                 String description,
                 Calendar dateUploaded,
                 long commentCount) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.isPublic = isPublic;
        this.isFriend = isFriend;
        this.isFamily = isFamily;
        this.viewsCount = viewsCount;
        this.description = description;
        this.dateUploaded = dateUploaded;
        this.commentCount = commentCount;
    }

    public Photo(String id,
                 String secret,
                 String server,
                 int farm,
                 String title,
                 boolean isPrimary,
                 boolean isPublic,
                 boolean isFriend,
                 boolean isFamily) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.isPrimary = isPrimary;
        this.isPublic = isPublic;
        this.isFriend = isFriend;
        this.isFamily = isFamily;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Photo)) return false;

        Photo o = (Photo) obj;

        return o.getId().equals(id);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Calendar dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public boolean isFamily() {
        return isFamily;
    }

    public void setFamily(boolean family) {
        isFamily = family;
    }
}
