package com.slavik.tdam.ui.image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends ViewModel {

    private final MutableLiveData<List<Comment>> _comments =
            new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Photo> _photo = new MutableLiveData<>();
    private IRepository repository;
    private Photo currentPhoto;

    public LiveData<List<Comment>> comments() {
        return _comments;
    }

    public LiveData<Photo> photo() {
        return _photo;
    }

    public void init(ImageFragment imageFragment) {
        MainActivity activity = (MainActivity) imageFragment.requireActivity();
        repository = activity.getRepository();
        currentPhoto = activity.getCurrentPhoto();
        fetchInfo();
    }

    public void fetchInfo() {
        repository.getPhoto(currentPhoto, (data, isSuccess) -> {
            if (!isSuccess) return;
            _photo.postValue(data);
        });

        repository.getComments(currentPhoto, (data, isSuccess) -> {
            if (!isSuccess) return;
            _comments.postValue(data);
        });
    }
}