package com.slavik.tdam.data.repository;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;

import java.util.List;

public interface IRepository {

    List<Photoset> getPhotosets();

    List<Photo> getPhotos();
}
