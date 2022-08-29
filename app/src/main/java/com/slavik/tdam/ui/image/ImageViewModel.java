package com.slavik.tdam.ui.image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.ui.MainActivity;

public class ImageViewModel extends ViewModel {

    private ImageFragment fragment;
    private MutableLiveData<Photo> _photo = new MutableLiveData<>();
    private IRepository repository;
    private Photo currentPhoto;


    public LiveData<Photo> photo() {
        return _photo;
    }

    public void init(ImageFragment imageFragment) {
        fragment = imageFragment;
        MainActivity activity = (MainActivity) fragment.requireActivity();
        repository = activity.getRepository();
        currentPhoto = activity.getCurrentPhoto();
        fetchInfo();
    }

    public void fetchInfo() {
        repository.getPhoto(currentPhoto, (data, isSuccess) -> {
            if (!isSuccess) return;

            _photo.postValue(data);
        });
    }
}