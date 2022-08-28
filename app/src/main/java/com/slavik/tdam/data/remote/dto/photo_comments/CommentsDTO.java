package com.slavik.tdam.data.remote.dto.photo_comments;

import com.slavik.tdam.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsDTO {
    public ArrayList<CommentDTO> comment;
    public String photo_id;

    public List<Comment> toModel() {
        List<Comment> comments = new ArrayList<>();

        for (CommentDTO c : comment) {
            comments.add(c.toModel());
        }
        return comments;
    }
}
