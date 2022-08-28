package com.slavik.tdam.data.repository;

import com.android.volley.RequestQueue;
import com.slavik.tdam.data.remote.services.ImageService;
import com.slavik.tdam.data.remote.services.PhotosetService;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Response;

import java.util.List;

public class Repository implements IRepository {

    private final PhotosetService photosetService;
    private final ImageService imageService;

    public Repository(RequestQueue queue) {
        photosetService = new PhotosetService(queue);
        imageService = new ImageService(queue);
    }

    @Override
    public void getPhotosets(Response<Photoset> response) {
        photosetService.getPhotosets((data, isSuccess) -> {

            for (Photoset photoset : data) {
                imageService.getImage(photoset.getPrimary(), photoset.getServer(), photoset.getSecret(), ImageService.ImageSize.w, (data1, isSuccess1) -> {
                    photoset.setPrimaryPhoto(data1);
                    response.onResponse(photoset, true);
                });
            }
        });
    }
}
