package com.slavik.tdam.ui.home;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Photoset>> _photosets =
            new MutableLiveData<>(new ArrayList<>());

    private IRepository repository;

    public void Init(Fragment fragment) {
        MainActivity activity = (MainActivity) fragment.requireActivity();
        repository = activity.getRepository();
    }

    public LiveData<List<Photoset>> photosets() {
        return _photosets;
    }

    public void fetchPhotosets() {
        _photosets.postValue(repository.getPhotosets());
    }


}