package com.slavik.tdam.data.repository;

import com.android.volley.RequestQueue;
import com.slavik.tdam.data.remote.services.PhotosetService;
import com.slavik.tdam.data.remote.services.ImageService;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;

import java.util.List;

public class Repository implements IRepository {

    private final PhotosetService photosetService;
    private final ImageService imageService;

    public Repository(RequestQueue queue) {
        photosetService = new PhotosetService(queue);
        imageService = new ImageService(queue);
    }

    @Override
    public List<Photoset> getPhotosets() {
        return null;
    }

    @Override
    public List<Photo> getPhotos() {
        return null;
    }
}
