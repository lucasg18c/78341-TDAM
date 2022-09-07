package com.slavik.tdam.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.User;
import com.slavik.tdam.util.Convert;

import java.util.Calendar;

@Entity
public class CommentEntity {
    @NonNull
    @PrimaryKey
    public String id;
    public String photoId;

    public long posted;
    public String authorName;
    public String authorId;
    public String content;


    public CommentEntity() {
    }

    public CommentEntity(Comment comment, Photo photo) {
        id = comment.getId();
        photoId = photo.getId();
        authorId = comment.getAuthor().getId();
        authorName = comment.getAuthor().getUserName();
        content = comment.getContent();

        if (comment.getPosted() == null) {
            posted = Calendar.getInstance().getTimeInMillis();

        } else {
            posted = comment.getPosted().getTimeInMillis();
        }

    }

    public Comment toModel() {
        Comment c = new Comment();
        c.setId(id);
        c.setPosted(Convert.unixToCalendar(posted));
        c.setContent(content);

        User user = new User();
        user.setId(authorId);
        user.setUserName(authorName);
        c.setAuthor(user);

        return c;
    }
}
