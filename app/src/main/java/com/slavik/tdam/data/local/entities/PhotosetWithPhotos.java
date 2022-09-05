package com.slavik.tdam.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Convert;

import java.util.List;

public class PhotosetWithPhotos {
    @Embedded
    public PhotosetEntity photoset;

    @Relation(parentColumn = "id", entityColumn = "photosetId")
    public List<PhotoEntity> photos;

    public PhotosetWithPhotos() {
    }

    public Photoset toModel() {
        Photoset ps = new Photoset();

        ps.setId(photoset.id);
        ps.setCreated(Convert.unixToCalendar(photoset.created));
        ps.setTitle(photoset.title);
        ps.setDescription(photoset.description);

        for (PhotoEntity p : photos) {
            ps.addPhoto(p.toModel());
        }

        return ps;
    }
}
