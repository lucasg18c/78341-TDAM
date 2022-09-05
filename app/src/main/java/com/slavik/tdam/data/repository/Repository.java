package com.slavik.tdam.data.repository;

import com.android.volley.RequestQueue;
import com.slavik.tdam.data.local.DatabaseTDAM;
import com.slavik.tdam.data.local.entities.PhotoEntity;
import com.slavik.tdam.data.local.entities.PhotosetEntity;
import com.slavik.tdam.data.local.entities.PhotosetWithPhotos;
import com.slavik.tdam.data.remote.services.ImageService;
import com.slavik.tdam.data.remote.services.PhotosetService;
import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.PhotoContent;
import com.slavik.tdam.model.PhotoSize;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Response;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    // Services
    private final PhotosetService photosetService;
    private final ImageService imageService;

    // Data in memory
    private final List<Photoset> photosets;

    // Database
    private final DatabaseTDAM db;

    public Repository(RequestQueue queue, DatabaseTDAM db) {
        // Init services
        photosetService = new PhotosetService(queue);
        imageService = new ImageService(queue);

        // Init data
        photosets = new ArrayList<>();

        // Database
        this.db = db;
    }

    @Override
    public void getPhotosets(boolean forceCache, Response<List<Photoset>> response) {

        // Buscar en RAM
        if (photosets.size() > 0) {
            response.onResponse(photosets, true);
            if (forceCache)
                return;
        }

        // Buscar en DB Local con Room
        getSavedPhotosets();
        if (photosets.size() > 0) {
            response.onResponse(photosets, true);
            if (forceCache) {
                return;
            }
        }

        // Consultar API
        photosetService.getPhotosets((res, success) -> {
            if (!success) {
                response.onResponse(null, false);
                return;
            }

            photosets.clear();
            photosets.addAll(res);

            // Buscar photosets
            for (Photoset p : photosets) {
                photosetService.getPhotos(p.getId(), (photos, successPhotos) -> {

                    // Retorna que hubo un error, pero sigue con los demás photosets
                    if (!successPhotos) {
                        response.onResponse(null, false);
                        return;
                    }

                    // Actualiza las fotos del photoset
                    p.setPhotos(photos);

                    // Busca datos de las fotos
                    for (Photo photo : p.getPhotos()) {
                        imageService.getInfo(photo.getId(), (photoInfo, sInfo) -> {

                            // Retorna que hubo un error, pero sigue con los demás fotos
                            if (!sInfo) {
                                response.onResponse(null, false);
                                return;
                            }

                            photo.setComments(photoInfo.getComments());
                            photo.setDescription(photoInfo.getDescription());
                            photo.setPosted(photoInfo.getPosted());
                            photo.setViews(photoInfo.getViews());

                            // Envía datos al front
                            response.onResponse(photosets, true);

                            // Guarda en DB local con ROOM
                            savePhotosets();

                            // Si es primaria del photoset, descarga su preview
                            if (photo.isPrimary()) {
                                imageService.getImage(
                                        photo.getId(),
                                        photo.getServer(),
                                        photo.getSecret(),
                                        ImageService.ImageSize.w,
                                        (bitmap, sBitmap) -> {

                                            // Retorna que hubo un error
                                            if (!sBitmap) {
                                                response.onResponse(null, false);
                                                return;
                                            }

                                            PhotoContent content = new PhotoContent();
                                            content.setAvailable(true);
                                            content.setSize(PhotoSize.w);
                                            content.setBitmap(bitmap);
                                            photo.getContent().add(content);

                                            // Envía datos al front
                                            response.onResponse(photosets, true);

                                            // Guarda en caché con ROOM
                                            savePhotosets();
                                        });
                            }
                        });
                    }
                });
            }
        });

    }

    private void getSavedPhotosets() {
        List<PhotosetWithPhotos> res = db.photosetDao().getPhotosets();
        photosets.clear();

        for (PhotosetWithPhotos p : res) {
            photosets.add(p.toModel());
        }
    }

    private void savePhotosets() {
        for (Photoset ps : photosets) {
            PhotosetEntity pse = new PhotosetEntity(ps);

            db.photosetDao().insertPhotoset(pse);

            for (PhotoEntity p : pse.photos) {
                db.photoDao().insertPhoto(p);
            }
        }

    }

    @Override
    public void getPhotos(String photosetID, Response<Photo> response) {

    }

    @Override
    public void getPhoto(Photo photo, Response<Photo> response) {

    }

    @Override
    public void getComments(Photo photo, Response<List<Comment>> response) {

    }
//
//    @Override
//    public void getPhotosets(Response<Photoset> response) {
//        photosetService.getPhotosets((data, isSuccess) -> {
//            for (Photoset photoset : data) {
//                imageService.getImage(
//                        photoset.getPrimary(),
//                        photoset.getServer(),
//                        photoset.getSecret(),
//                        ImageService.ImageSize.w,
//                        (data1, isSuccess1) -> {
//                            photoset.setPrimaryPhoto(data1);
//                            response.onResponse(photoset, true);
//                        });
//            }
//        });
//    }
//
//    @Override
//    public void getPhotos(String photosetID, Response<Photo> response) {
//        photosetService.getImages(photosetID, (data, isSuccess) ->
//        {
//            if (!isSuccess) {
//                response.onResponse(null, false);
//                return;
//            }
//
//            for (Photo p : data) {
//
//                imageService.getImage(p.getId(),
//                        p.getServer(),
//                        p.getSecret(),
//                        ImageService.ImageSize.w,
//                        (data1, isSuccess1) -> {
//                            p.setImage(data1);
//                            response.onResponse(p, true);
//                        });
//            }
//        });
//    }
//
//    @Override
//    public void getPhoto(Photo photo, Response<Photo> response) {
//        imageService.getInfo(photo.getId(), (data, isSuccess) -> {
//            if (!isSuccess) return;
//
//            response.onResponse(data, true);
//
//            imageService.getImage(data.getId(),
//                    data.getServer(),
//                    data.getSecret(),
//                    ImageService.ImageSize.b,
//                    (data1, isSuccess1) -> {
//                        data.setImage(data1);
//                        response.onResponse(data, true);
//                    });
//        });
//    }
//
//    @Override
//    public void getComments(Photo photo, Response<List<Comment>> response) {
//        imageService.getComments(photo.getId(), (data, isSuccess) -> {
//            if (!isSuccess) return;
//
//            response.onResponse(data, true);
//        });
//    }
}
