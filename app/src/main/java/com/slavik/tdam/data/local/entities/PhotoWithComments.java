package com.slavik.tdam.data.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.slavik.tdam.model.Photo;

import java.util.List;

public class PhotoWithComments {

    @Embedded
    public PhotoEntity photo;

    @Relation(parentColumn = "id", entityColumn = "photoId")
    public List<CommentEntity> comments;

    public PhotoWithComments() {
    }

    public Photo toModel() {

        Photo p = photo.toModel();

        for (CommentEntity comment : comments) {
            p.getComments().add(comment.toModel());
        }

        return p;
    }
}
