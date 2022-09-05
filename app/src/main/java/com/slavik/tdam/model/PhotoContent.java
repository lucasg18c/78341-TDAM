package com.slavik.tdam.model;

import android.graphics.Bitmap;

public class PhotoContent {
    private Bitmap bitmap;
    private boolean isAvailable;
    private PhotoSize size;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public PhotoSize getSize() {
        return size;
    }

    public void setSize(PhotoSize size) {
        this.size = size;
    }
}
