package com.slavik.tdam.data.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.slavik.tdam.util.Response;

public class ImageService {
    private static final String BASE_URL = "https://www.flickr.com/services/rest/?method=";
    private static final String BASE_IMAGE_URL = "https://live.staticflickr.com/";

    private final RequestQueue queue;

    public ImageService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getComments(String photoID, Response<String> onResponse) {
        String path = BASE_URL + "flickr.photos.comments.getList&api_key=ff01e4bd798ab53efcf4d02337f12d5b&photo_id=" + photoID + "&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> onResponse.onResponse(response, true),
                error -> onResponse.onResponse(error.toString(), false));
        queue.add(stringRequest);
    }

    public void getInfo(String photoID, Response<String> onResponse) {
        String path = BASE_URL + "flickr.photos.comments.getInfo&api_key=ff01e4bd798ab53efcf4d02337f12d5b&photo_id=" + photoID + "&format=json&nojsoncallback=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path,
                response -> onResponse.onResponse(response, true),
                error -> onResponse.onResponse(error.toString(), false));
        queue.add(stringRequest);
    }

    public void getImage(String photoID, String serverID, String secret, ImageSize size, Response<Bitmap> onResponse) {
        String path = BASE_IMAGE_URL
                + serverID + "/" + photoID + "_" + secret + "_" + size.toString() + ".jpg";

        ImageRequest stringRequest = new ImageRequest(path,
                response -> onResponse.onResponse(response, true),
                0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                error -> onResponse.onResponse(null, false));
        queue.add(stringRequest);
    }

    public enum ImageSize {
        s, q, t, m, n, w, mid, z, c, b, h, k, _3k, _4k, _5k, _6k, o
    }
}
