package com.slavik.tdam.ui.directory;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DirectoryViewModel extends ViewModel {

    private final MutableLiveData<List<Photo>> _photos = new MutableLiveData<>(new ArrayList<>());
    private IRepository repository;
    private Photoset currentPhotoset;

    public LiveData<List<Photo>> photos() {
        return _photos;
    }

    public void init(Fragment fragment) {
        MainActivity activity = (MainActivity) fragment.requireActivity();
        repository = activity.getRepository();
        currentPhotoset = activity.getCurrentPhotoset();
        fetchPhotos();
    }

    private void fetchPhotos() {
        repository.getPhotos(currentPhotoset.getId(), (data, isSuccess) -> {

            List<Photo> photosList = _photos.getValue();

            assert photosList != null;
            int i = photosList.indexOf(data);

            if (i == -1) {
                photosList.add(data);
            } else {
                photosList.set(i, data);
            }

            _photos.postValue(photosList);
        });
    }
}