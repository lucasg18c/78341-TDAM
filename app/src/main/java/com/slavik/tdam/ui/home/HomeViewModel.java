package com.slavik.tdam.ui.home;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.R;
import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Photoset>> _photosets =
            new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<String> _error = new MutableLiveData<>("");
    public LiveData<String> error() {
        return _error;
    }

    private IRepository repository;
    private Fragment mFragment;

    private String getString(int resId) {
        return mFragment.getString(resId);
    }

    public void init(Fragment fragment) {
        mFragment = fragment;
        repository = ((MainActivity) fragment.requireActivity()).getRepository();
        fetchPhotosets();
    }

    public LiveData<List<Photoset>> photosets() {
        return _photosets;
    }

    public void fetchPhotosets() {
        repository.getPhotosets(false, (data, isSuccess) -> {
            if (!isSuccess) {
                _error.postValue(getString(R.string.photoset_load_error));
                return;
            }

            _photosets.postValue(data);
        });
    }
}