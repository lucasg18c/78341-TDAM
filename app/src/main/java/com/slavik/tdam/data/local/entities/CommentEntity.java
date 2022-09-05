package com.slavik.tdam.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class CommentEntity {
    @NonNull
    @PrimaryKey
    public String id;
    public Calendar posted;
    public UserEntity author;
    public String content;

    public CommentEntity() {
    }
}
