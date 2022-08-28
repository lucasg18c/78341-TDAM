package com.slavik.tdam.data.remote.dto.photosets;

import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.util.Convert;

import java.util.ArrayList;

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
        return new Photoset(
                id,
                owner,
                username,
                primary,
                secret,
                server,
                farm,
                count_views,
                count_comments,
                count_photos,
                count_videos,
                title._content,
                description._content,
                can_comment == 1,
                Convert.unixToCalendar(Long.parseLong(date_create)),
                Convert.unixToCalendar(Long.parseLong(date_update)),
                photos,
                videos,
                visibility_can_see_set == 1,
                needs_interstitial == 1
        );
    }
}
