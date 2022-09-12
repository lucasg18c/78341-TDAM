package com.slavik.tdam.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageStore implements IStoreImage {

    @Override
    public void saveBitmap(Context context, Bitmap bitmap, String path) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Pictures/TDAM");
        myDir.mkdirs();

        String fname = path + ".png";
        File file = new File(myDir, fname);
        if (file.exists())
            return;
        //file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bitmap getBitmap(String path) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Pictures/TDAM");
        if (!myDir.exists()) return null;


        File file = new File(myDir, path + ".png");
        if (!file.exists()) return null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
