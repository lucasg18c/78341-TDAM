package com.slavik.tdam.data.repository;

import com.android.volley.RequestQueue;
import com.slavik.tdam.data.remote.services.ImageService;
import com.slavik.tdam.data.remote.services.PhotosetService;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Response;

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
                imageService.getImage(
                        photoset.getPrimary(),
                        photoset.getServer(),
                        photoset.getSecret(),
                        ImageService.ImageSize.w,
                        (data1, isSuccess1) -> {
                            photoset.setPrimaryPhoto(data1);
                            response.onResponse(photoset, true);
                        });
            }
        });
    }

    @Override
    public void getPhotos(String photosetID, Response<Photo> response) {
        photosetService.getImages(photosetID, (data, isSuccess) ->
        {
            if (!isSuccess) {
                response.onResponse(null, false);
                return;
            }

            for (Photo p : data) {

                imageService.getImage(p.getId(),
                        p.getServer(),
                        p.getSecret(),
                        ImageService.ImageSize.w,
                        (data1, isSuccess1) -> {
                            p.setImage(data1);
                            response.onResponse(p, true);
                        });
            }
        });
    }

    @Override
    public void getPhoto(Photo photo, Response<Photo> response) {
        imageService.getInfo(photo.getId(), (data, isSuccess) -> {
            if (!isSuccess) return;

            response.onResponse(data, true);

            imageService.getImage(data.getId(),
                    data.getServer(),
                    data.getSecret(),
                    ImageService.ImageSize.b,
                    (data1, isSuccess1) -> {
                        data.setImage(data1);
                        response.onResponse(data, true);
                    });
        });
    }
}
