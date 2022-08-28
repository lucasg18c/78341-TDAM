package com.slavik.tdam.ui.home;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slavik.tdam.data.remote.services.DirectoryService;
import com.slavik.tdam.data.remote.services.ImageService;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Photoset>> mDirectories = new MutableLiveData<>();
    private final MutableLiveData<List<Photo>> mImages = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> mImage = new MutableLiveData<>();
    private DirectoryService directoryService;
    private ImageService imageService;

    public LiveData<List<Photoset>> directories() {
        return mDirectories;
    }

    public LiveData<List<Photo>> images() {
        return mImages;
    }

    public LiveData<Bitmap> image() {
        return mImage;
    }

    public void Init(Fragment fragment) {
        MainActivity activity = (MainActivity) fragment.requireActivity();
        directoryService = activity.getDirectoryService();
        imageService = activity.getImageService();
    }

    public void fetchDirectories() {
        directoryService.getDirectories((res, success) -> {
            if (success) {
                mDirectories.postValue(res);
            }
        });
    }

    public void fetchImages() {
        directoryService.getImages("72177720301592528", (res, success) -> {
            if (success) {
                mImages.postValue(res);
            }
        });
    }

    public void fetchImage() {
        imageService.getImage(
                "52308410857", "65535", "e19cb4d6f7", ImageService.ImageSize.w,
                (res, success) -> {
                    if (success) {
                        mImage.postValue(res);
                    }
                });
    }
}