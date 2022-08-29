package com.slavik.tdam.data.repository;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Response;

import java.util.List;

public interface IRepository {

    void getPhotosets(Response<Photoset> response);
    void getPhotos(String photosetID, Response<Photo> response);
    void getPhoto(Photo photo, Response<Photo> response);
}
