package com.slavik.tdam.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.slavik.tdam.model.Photo;
import com.slavik.tdam.util.Convert;

import java.util.Calendar;

@Entity
public class PhotoEntity {
    @NonNull
    @PrimaryKey
    public String id;
    public String photosetId;

    public String description;
    public boolean isPrimary;
    public long posted;
    public String secret;
    public String server;
    public String title;
    public long views;
    public String localPath;

    public PhotoEntity() {
    }

    public PhotoEntity(Photo p, String photosetId) {
        this.photosetId = photosetId;

        id = p.getId();
        description = p.getDescription();
        isPrimary = p.isPrimary();
        if (p.getPosted() == null) {
            posted = Calendar.getInstance().getTimeInMillis();

        } else {
            posted = p.getPosted().getTimeInMillis();
        }
        secret = p.getSecret();
        server = p.getServer();
        title = p.getTitle();
        views = p.getViews();
        localPath = p.getLocalPath();
    }

    public Photo toModel() {
        Photo p = new Photo();

        p.setId(id);
        p.setSecret(secret);
        p.setServer(server);
        p.setTitle(title);
        p.setPrimary(isPrimary);
        p.setPosted(Convert.unixToCalendar(posted));
        p.setViews(views);
        //p.setComments(new ArrayList<>(Integer.parseInt(photo.comments._content)));
        p.setDescription(description);
        p.setLocalPath(localPath);

        return p;
    }
}
