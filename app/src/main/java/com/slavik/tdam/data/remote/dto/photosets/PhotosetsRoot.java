package com.slavik.tdam.data.remote.dto.photosets;

import com.slavik.tdam.model.Photoset;

import java.util.List;

public class PhotosetsRoot {
    public PhotosetsDTO photosets;
    public String stat;

    public List<Photoset> toModel() {
        return photosets.toModel();
    }
}

