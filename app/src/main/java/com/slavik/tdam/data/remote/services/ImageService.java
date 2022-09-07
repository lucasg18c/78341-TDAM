package com.slavik.tdam.data.remote.services;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.slavik.tdam.data.remote.dto.photo_comments.PhotoCommentsRoot;
import com.slavik.tdam.data.remote.dto.photo_info.PhotoInfoRoot;
import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.PhotoSize;
import com.slavik.tdam.util.Response;

import java.util.List;

public class ImageService {
    private static final String BASE_URL = "https://www.flickr.com/services/rest/?method=";
    private static final String BASE_IMAGE_URL = "https://live.staticflickr.com/";

    private final RequestQueue mQueue;
    private final Gson gson = new Gson();

    public ImageService(RequestQueue queue) {
        this.mQueue = queue;
    }

    public void getComments(String photoID, Response<List<Comment>> onResponse) {
        String path = BASE_URL + "flickr.photos.comments.getList&api_key=18a488f30edea9957660c8e5293af56d&photo_id=" + photoID + "&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> {
                    PhotoCommentsRoot res = gson.fromJson(response, PhotoCommentsRoot.class);
                    onResponse.onResponse(res.toModel(), true);
                },
                error -> onResponse.onResponse(null, false));
        mQueue.add(stringRequest);
    }

    public void getInfo(String photoID, Response<Photo> onResponse) {
        String path = BASE_URL + "flickr.photos.getInfo&api_key=18a488f30edea9957660c8e5293af56d&photo_id=" + photoID + "&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> {
                    PhotoInfoRoot res = gson.fromJson(response, PhotoInfoRoot.class);
                    onResponse.onResponse(res.toModel(), true);
                },
                error -> onResponse.onResponse(null, false));
        mQueue.add(stringRequest);
    }

    public void getImage(String photoID, String serverID, String secret, PhotoSize size, Response<Bitmap> onResponse) {
        String path = BASE_IMAGE_URL
                + serverID + "/" + photoID + "_" + secret + "_" + size.toString() + ".jpg";

        ImageRequest stringRequest = new ImageRequest(path,
                response -> onResponse.onResponse(response, true),
                0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                error -> onResponse.onResponse(null, false));
        mQueue.add(stringRequest);
    }
}
