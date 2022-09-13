package com.slavik.tdam.data.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.slavik.tdam.data.local.DatabaseTDAM;
import com.slavik.tdam.data.local.entities.CommentEntity;
import com.slavik.tdam.data.local.entities.PhotoEntity;
import com.slavik.tdam.data.local.entities.PhotoWithComments;
import com.slavik.tdam.data.local.entities.PhotosetEntity;
import com.slavik.tdam.data.local.entities.PhotosetWithPhotos;
import com.slavik.tdam.data.remote.services.ImageService;
import com.slavik.tdam.data.remote.services.PhotosetService;
import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.PhotoContent;
import com.slavik.tdam.model.PhotoSize;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.ImageStore;
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
    private final ContentResolver contentResolver;

    private final Context mContext;

    public Repository(
            RequestQueue queue,
            DatabaseTDAM db,
            ContentResolver contentResolver,
            Context context) {
        // Init services
        photosetService = new PhotosetService(queue);
        imageService = new ImageService(queue);

        // Init data
        photosets = new ArrayList<>();

        // Database
        this.db = db;
        this.contentResolver = contentResolver;

        mContext = context;

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

                savePhotoset(p);

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

                            // Si no es primaria del repo, guardarla con ROOM
                            // Las primarias se guargan después con su bitmap
                            if (!photo.isPrimary()) {
                                savePhoto(photo, p, null);
                            }

                            // Si es primaria del photoset, descarga su preview
                            if (photo.isPrimary()) {
                                imageService.getImage(
                                        photo.getId(),
                                        photo.getServer(),
                                        photo.getSecret(),
                                        PhotoSize.w,
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
                                            savePhoto(photo, p, content);
                                        });
                            }
                        });
                    }
                });
            }
        });

    }

    private void savePhotoset(Photoset p) {
        db.photosetDao().insertPhotoset(new PhotosetEntity(p));
    }

    private void getSavedPhotosets() {
        List<PhotosetWithPhotos> res = db.photosetDao().getPhotosets();
        photosets.clear();

        for (PhotosetWithPhotos p : res) {
            Photoset photoset = p.toModel();

            for (Photo photo : photoset.getPhotos()) {
                try {
                    Bitmap bitmap = new ImageStore().getBitmap(photo.getId() + "_" + PhotoSize.w);

                    if (bitmap != null) {

                        PhotoContent content = new PhotoContent();
                        content.setBitmap(bitmap);
                        content.setAvailable(true);
                        content.setSize(PhotoSize.w);
                        photo.getContent().add(content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            photosets.add(photoset);
        }
    }

    private void savePhoto(Photo photo, Photoset photoset, PhotoContent content) {
        if (photo == null) {
            return;
        }

        PhotoEntity saved = db.photoDao().getPhotoById(photo.getId());
        if (saved != null) {
            photo.setLocalPath(saved.localPath);
        }

        if (content != null) {

            new ImageStore().saveBitmap(
                    mContext,
                    content.getBitmap(),
                    photo.getId() + "_" + content.getSize().toString()
            );
        }

        PhotoEntity photoEntity = new PhotoEntity(photo, photoset.getId());
        db.photoDao().insertPhoto(photoEntity);
    }

    @Override
    public void getPhotos(String photosetID, Response<Photo> response) {

    }

    @Override
    public void getPhoto(boolean forceCache, Photo photo, PhotoSize size, Response<Photo> response) {

        // En RAM
        Photoset owner = null;
        for (Photoset ps : photosets) {
            for (Photo p : ps.getPhotos()) {
                if (p == null) continue;
                if (p.getId().equals(photo.getId())) {
                    owner = ps;
                    for (PhotoContent pc : p.getContent()) {
                        if (pc.getSize() == size && pc.getBitmap() != null) {
                            response.onResponse(photo, true);
                            if (forceCache) {
                                return;
                            }
                        }
                    }
                    break;
                }
            }
        }

        // En Local
        Bitmap bm = new ImageStore().getBitmap(photo.getId() + "_" + size.toString());

        if (bm != null) {
            PhotoContent content = new PhotoContent();
            content.setBitmap(bm);
            content.setAvailable(true);
            content.setSize(size);
            photo.getContent().add(content);
            response.onResponse(photo, true);
            if (forceCache) {
                return;
            }
        }


        // En API
        Photoset finalOwner = owner;
        imageService.getImage(
                photo.getId(),
                photo.getServer(),
                photo.getSecret(),
                size,
                (data, isSuccess) -> {
                    if (isSuccess) {
                        PhotoContent content = new PhotoContent();
                        content.setSize(size);
                        content.setAvailable(true);
                        content.setBitmap(data);
                        photo.getContent().add(content);
                        response.onResponse(photo, true);

                        if (finalOwner != null) {
                            savePhoto(photo, finalOwner, content);
                        }
                    }
                });
    }

    @Override
    public void getComments(Photo photo, Response<List<Comment>> response) {

        PhotoWithComments photoWithComments = db.photoDao().getPhotoWithComments(photo.getId());
        if (photoWithComments != null) {
            Photo p = photoWithComments.toModel();
            photo.setComments(p.getComments());
            response.onResponse(p.getComments(), true);
        }

        imageService.getComments(photo.getId(), (res, success) -> {
            response.onResponse(res, success);

            if (res != null) {
                for (Comment c : res) {
                    db.commentDao().insert(new CommentEntity(c, photo));
                }
            }
        });
    }
}
