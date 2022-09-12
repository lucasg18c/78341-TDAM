package com.slavik.tdam.util;

import android.content.Context;
import android.graphics.Bitmap;

public interface IStoreImage {

    void saveBitmap(Context context, Bitmap bitmap, String path);
    Bitmap getBitmap(String path);
}
