package com.slavik.tdam.data.local.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;

import com.slavik.tdam.model.PhotoSize;

@Entity
public class PhotoContentEntity {
    private Bitmap bitmap;
    private boolean isAvailable;
    private PhotoSize size;

    public PhotoContentEntity() {
    }
}
