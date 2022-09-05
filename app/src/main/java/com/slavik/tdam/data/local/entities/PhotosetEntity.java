package com.slavik.tdam.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PhotosetEntity {

    @NonNull
    @PrimaryKey
    public String id;
    public long created;
    public String description;
    public String title;
    @Ignore
    public List<PhotoEntity> photos = new ArrayList<>();

    public PhotosetEntity() {
    }

    public PhotosetEntity(Photoset ps) {
        id = ps.getId();
        created = ps.getCreated().getTimeInMillis();
        description = ps.getDescription();
        title = ps.getTitle();

        for (Photo p : ps.getPhotos()) {
            if (p.getId().length() > 0) {
                photos.add(new PhotoEntity(p, ps.getId()));
            }
        }
    }
}
