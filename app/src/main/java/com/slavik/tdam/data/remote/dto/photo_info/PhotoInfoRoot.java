package com.slavik.tdam.data.remote.dto.photo_info;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.util.Convert;

import java.util.ArrayList;

public class PhotoInfoRoot {
    public PhotoDTO photo;
    public String stat;

    public Photo toModel() {

        Photo p = new Photo();
        p.setId(photo.id);
        p.setSecret(photo.secret);
        p.setServer(photo.server);
        p.setTitle(photo.title._content);
        p.setPrimary(photo.visibility.isfamily == 1);
        p.setPosted(Convert.unixToCalendar(Long.parseLong(photo.dateuploaded)));
        p.setViews(Integer.parseInt(photo.views));
        p.setComments(new ArrayList<>(Integer.parseInt(photo.comments._content)));
        p.setDescription(photo.description._content);

        return p;
    }
}
