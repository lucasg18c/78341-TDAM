package com.slavik.tdam.data.remote;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.slavik.tdam.util.Response;

public class DirectoryService {

    private static final String url = "https://www.flickr.com/services/rest/?method=";

    private final RequestQueue queue;

    public DirectoryService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getDirectories(Response<String> onResponse) {
        String path = url + "flickr.photosets.getList&api_key=ff01e4bd798ab53efcf4d02337f12d5b&user_id=196361992%40N04&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> onResponse.onResponse(response, true),
                error -> onResponse.onResponse(error.toString(), false));
        queue.add(stringRequest);
    }

    public void getImages(String photosetID, Response<String> onResponse) {
        String path = url + "flickr.photosets.getPhotos&api_key=ff01e4bd798ab53efcf4d02337f12d5b&photoset_id=" + photosetID + "&user_id=196361992%40N04&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> onResponse.onResponse(response, true),
                error -> onResponse.onResponse(error.toString(), false));
        queue.add(stringRequest);
    }

    public void getInfo(String photosetID, Response<String> onResponse) {
        String path = url + "flickr.photosets.getInfo&api_key=ff01e4bd798ab53efcf4d02337f12d5b&photoset_id=" + photosetID + "&user_id=196361992%40N04&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> onResponse.onResponse(response, true),
                error -> onResponse.onResponse(error.toString(), false));
        queue.add(stringRequest);
    }
}
