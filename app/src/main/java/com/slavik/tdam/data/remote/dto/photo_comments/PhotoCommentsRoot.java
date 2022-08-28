package com.slavik.tdam.data.remote.dto.photo_comments;


import com.slavik.tdam.model.Comment;

import java.util.List;

public class PhotoCommentsRoot {

    public CommentsDTO comments;
    public String stat;

    public List<Comment> toModel() {
        return comments.toModel();
    }
}
