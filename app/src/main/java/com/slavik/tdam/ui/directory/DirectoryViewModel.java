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
import java.util.Locale;

public class DirectoryViewModel extends ViewModel {

    private final MutableLiveData<List<Photo>> _photos = new MutableLiveData<>(new ArrayList<>());
    private IRepository repository;
    private Photoset currentPhotoset;

    private boolean orderByName = false;
    private boolean orderAsc = true;

    public void setOrderByName(boolean orderByName) {
        this.orderByName = orderByName;
        order();
    }

    public void setOrderAsc(boolean orderAsc) {
        this.orderAsc = orderAsc;
        order();
    }

    public void order() {
        List<Photo> temp = _photos.getValue();
        if (temp == null || temp.size() < 2) {
            return;
        }

        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = i + 1; j < temp.size(); j++) {
                Photo a = temp.get(i);
                Photo b = temp.get(j);

                if (a == null || b == null) return;

                // Order por nombre
                if (orderByName) {
                    int aa = (int) (a.getTitle().toLowerCase(Locale.ROOT).charAt(0));
                    int bb = (int) (b.getTitle().toLowerCase(Locale.ROOT).charAt(0));

                    // Order ascendente
                    if (orderAsc) {
                        if (aa > bb) {
                            temp.set(i, b);
                            temp.set(j, a);
                        }
                        continue;
                    }

                    if (aa < bb) {
                        temp.set(i, b);
                        temp.set(j, a);
                    }
                    continue;
                }

                // Ordenar por fecha
                long aa = a.getPosted().getTimeInMillis();
                long bb = b.getPosted().getTimeInMillis();

                // Order ascendente
                if (orderAsc) {
                    if (aa > bb) {
                        temp.set(i, b);
                        temp.set(j, a);
                    }
                    continue;
                }

                if (aa < bb) {
                    temp.set(i, b);
                    temp.set(j, a);
                }
            }
        }
        _photos.postValue(temp);
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
            repository.getPhoto(true, p, PhotoSize.n, (res, success) -> {
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