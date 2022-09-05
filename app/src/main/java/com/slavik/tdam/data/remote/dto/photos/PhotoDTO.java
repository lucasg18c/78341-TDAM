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

        Photo p = new Photo();
        p.setId(id);
        p.setSecret(secret);
        p.setServer(server);
        p.setTitle(title);
        p.setPrimary(isprimary.equals("1"));

        return p;
    }
}
