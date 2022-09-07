package com.slavik.tdam.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Photoset {
    private Calendar created;
    private String description;
    private String id;
    private List<Photo> photos = new ArrayList<>();
    private String title;

    public long commentsCount() {
        long sum = 0;
        for (Photo p : photos) {
            if (p == null) continue;
            sum += p.commentsCount();
        }
        return sum;
    }

    public Photo getPrimary() {
        for (Photo p : photos) {
            if (p != null  && p.isPrimary())
                return p;
        }
        return null;
    }

    public long viewsCount() {
        long sum = 0;
        for (Photo p : photos) {
            if (p == null) continue;
            sum += p.getViews();
        }
        return sum;
    }

    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new ArrayList<>();
        }

        photos.add(photo);
    }

    // SETTERS & GETTERS

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
