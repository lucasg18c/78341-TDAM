package com.slavik.tdam.data.repository;

import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.PhotoSize;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Response;

import java.util.List;

public interface IRepository {

    void getPhotosets(boolean forceCache, Response<List<Photoset>> response);
    void getPhotos(String photosetID, Response<Photo> response);
    void getPhoto(boolean forceCache, Photo photo, PhotoSize size, Response<Photo> response);
    void getComments(Photo photo, Response<List<Comment>> response);
}
