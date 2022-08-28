package com.slavik.tdam.data.remote.dto.photosets;

import com.slavik.tdam.model.Photoset;

import java.util.ArrayList;
import java.util.List;

public class PhotosetsDTO {
    public int page;
    public int pages;
    public int perpage;
    public int total;
    public ArrayList<PhotosetDTO> photoset;

    public List<Photoset> toModel() {
        List<Photoset> ps = new ArrayList<>();

        for (PhotosetDTO p : photoset) {
            ps.add(p.toModel());
        }
        return ps;
    }
}
