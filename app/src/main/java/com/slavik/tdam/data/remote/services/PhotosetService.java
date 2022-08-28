package com.slavik.tdam.data.remote.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.slavik.tdam.data.remote.dto.photos.PhotosRoot;
import com.slavik.tdam.data.remote.dto.photosets.PhotosetsRoot;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Response;

import java.util.List;

public class PhotosetService {

    private static final String url = "https://www.flickr.com/services/rest/?method=";

    private final RequestQueue mQueue;

    private final Gson gson = new Gson();

    public PhotosetService(RequestQueue queue) {
        this.mQueue = queue;
    }

    public void getPhotosets(Response<List<Photoset>> onResponse) {
        String path = url + "flickr.photosets.getList&api_key=18a488f30edea9957660c8e5293af56d&user_id=196361992%40N04&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> {
                    PhotosetsRoot res = gson.fromJson(response, PhotosetsRoot.class);
                    onResponse.onResponse(res.toModel(), true);
                },
                error -> onResponse.onResponse(null, false));
        mQueue.add(stringRequest);
    }

    public void getImages(String photosetID, Response<List<Photo>> onResponse) {
        String path = url + "flickr.photosets.getPhotos&api_key=18a488f30edea9957660c8e5293af56d&photoset_id=" + photosetID + "&user_id=196361992%40N04&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> {
                    PhotosRoot res = gson.fromJson(response, PhotosRoot.class);
                    onResponse.onResponse(res.toModel(), true);
                },
                error -> onResponse.onResponse(null, false));
        mQueue.add(stringRequest);
    }
}
