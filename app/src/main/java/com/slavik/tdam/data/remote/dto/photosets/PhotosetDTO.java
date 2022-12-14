package com.slavik.tdam.data.remote.dto.photosets;

import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Convert;
import com.slavik.tdam.util.ListUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class PhotosetDTO {
    public String id;
    public String owner;
    public String username;
    public String primary;
    public String secret;
    public String server;
    public int farm;
    public String count_views;
    public String count_comments;
    public int count_photos;
    public int count_videos;
    public TitleDTO title;
    public DescriptionDTO description;
    public int can_comment;
    public String date_create;
    public String date_update;
    public int photos;
    public int videos;
    public int visibility_can_see_set;
    public int needs_interstitial;

    public String ownername;
    public ArrayList<PhotoDTO> photo;
    public int page;
    public int per_page;
    public int perpage;
    public int pages;
    public int total;

    public Photoset toModel() {

        Photoset ps = new Photoset();
        ps.setId(id);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(date_create) * 1000);

        ps.setCreated(cal);
        ps.setTitle(title._content);
        ps.setDescription(description._content);
        ps.setPhotos(new ListUtil<Photo>().emptyArray(count_photos));

        return ps;
    }
}
