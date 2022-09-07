package com.slavik.tdam.ui.directory;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.PhotoSize;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DirectoryViewModel extends ViewModel {

    private final MutableLiveData<List<Photo>> _photos = new MutableLiveData<>(new ArrayList<>());
    private IRepository repository;
    private Photoset currentPhotoset;

    public void setOrderByName(boolean orderByName) {

    }

    public void setOrderAsc(boolean orderAsc) {

    }

    public LiveData<List<Photo>> photos() {
        return _photos;
    }

    public void init(Fragment fragment) {
        MainActivity activity = (MainActivity) fragment.requireActivity();
        repository = activity.getRepository();
        currentPhotoset = activity.getCurrentPhotoset();
        _photos.postValue(currentPhotoset.getPhotos());
        fetchPhotos();
    }

    public void fetchPhotos() {
        for (Photo p : currentPhotoset.getPhotos()) {
            repository.getPhoto(true, p, PhotoSize.w, (res, success) -> {
                List<Photo> photos = _photos.getValue();

                for (int i = 0; i < photos.size(); i++) {
                    if (photos.get(i).getId().equals(res.getId())) {
                        photos.set(i, res);
                        _photos.postValue(photos);
                        return;
                    }
                }
            });
        }
    }
}