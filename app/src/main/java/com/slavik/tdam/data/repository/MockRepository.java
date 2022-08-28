package com.slavik.tdam.data.repository;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MockRepository implements IRepository {
    @Override
    public List<Photoset> getPhotosets() {
        List<Photoset> p = new ArrayList<>();
        p.add(new Photoset("123",
                "lucas",
                "lucasg18c",
                "123",
                "123",
                "123",
                123,
                "123",
                "123",
                123,
                123,
                "Galería de aves",
                "Fotos sobre pájaros en general",
                true,
                Calendar.getInstance(),
                Calendar.getInstance(),
                3,
                0,
                true,
                false
        ));
        p.add(new Photoset("123",
                "lucas",
                "lucasg18c",
                "123",
                "123",
                "123",
                123,
                "123",
                "123",
                123,
                123,
                "Perritos",
                "Fotos sobre perros xd",
                true,
                Calendar.getInstance(),
                Calendar.getInstance(),
                3,
                0,
                true,
                false
        ));
        return p;
    }

    @Override
    public List<Photo> getPhotos() {
        return null;
    }
}
