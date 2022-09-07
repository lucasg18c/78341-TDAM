package com.slavik.tdam.data.remote.dto.photo_info;

import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.util.Convert;
import com.slavik.tdam.util.ListUtil;

import java.util.Calendar;

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

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(photo.dateuploaded) * 1000);

        p.setPosted(cal);
        p.setViews(Integer.parseInt(photo.views));
        p.setComments(new ListUtil<Comment>().emptyArray(Integer.parseInt(photo.comments._content)));
        p.setDescription(photo.description._content);

        return p;
    }
}
