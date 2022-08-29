package com.slavik.tdam.data.remote.dto.photo_info;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.util.Convert;

public class PhotoInfoRoot {
    public PhotoDTO photo;
    public String stat;

    public Photo toModel() {
        return new Photo(
                photo.id,
                photo.secret,
                photo.server,
                photo.farm,
                photo.title._content,
                photo.visibility.ispublic == 1,
                photo.visibility.isfriend == 1,
                photo.visibility.isfamily == 1,
                Long.parseLong(photo.views),
                photo.description._content,
                Convert.unixToCalendar(Long.parseLong(photo.dateuploaded)),
                Long.parseLong(photo.comments._content),
                photo.owner.username
        );
    }
}
