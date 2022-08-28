package com.slavik.tdam.data.remote.dto.photos;

import com.slavik.tdam.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotosetDTO {
    public String id;
    public String primary;
    public String owner;
    public String ownername;
    public ArrayList<PhotoDTO> photo;
    public int page;
    public int per_page;
    public int perpage;
    public int pages;
    public String title;
    public int total;

    public List<Photo> toModel() {
        List<Photo> photos = new ArrayList<>();
        for (PhotoDTO p : photo) {
            photos.add(p.toModel());
        }
        return photos;
    }
}
