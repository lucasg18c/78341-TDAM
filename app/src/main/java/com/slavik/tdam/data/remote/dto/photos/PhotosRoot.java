package com.slavik.tdam.data.remote.dto.photos;

import com.slavik.tdam.model.Photo;

import java.util.List;

public class PhotosRoot {
    public PhotosetDTO photoset;
    public String stat;

    public List<Photo> toModel() {
        return photoset.toModel();
    }
}



