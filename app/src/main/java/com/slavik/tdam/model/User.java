package com.slavik.tdam.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private List<Photoset> photosets = new ArrayList<>();
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Photoset> getPhotosets() {
        return photosets;
    }

    public void setPhotosets(List<Photoset> photosets) {
        this.photosets = photosets;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
