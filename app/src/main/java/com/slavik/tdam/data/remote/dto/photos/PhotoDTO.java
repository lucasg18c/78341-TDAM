package com.slavik.tdam.data.remote.dto.photos;

import com.slavik.tdam.model.Photo;

public class PhotoDTO {
    public String id;
    public String secret;
    public String server;
    public int farm;
    public String title;
    public String isprimary;
    public int ispublic;
    public int isfriend;
    public int isfamily;

    public Photo toModel() {
        return new Photo(
                id,
                secret,
                server,
                farm,
                title,
                isprimary.equals("1"),
                ispublic == 1,
                isfriend == 1,
                isfamily == 1
        );
    }
}
