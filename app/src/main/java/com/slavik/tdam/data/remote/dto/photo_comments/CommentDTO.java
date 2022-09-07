package com.slavik.tdam.data.remote.dto.photo_comments;

import com.slavik.tdam.model.Comment;
import com.slavik.tdam.model.User;
import com.slavik.tdam.util.Convert;

import java.util.Calendar;

public class CommentDTO {
    public String _content;
    public String author;
    public Long author_is_deleted;
    public String authorname;
    public String datecreate;
    public Long iconfarm;
    public String iconserver;
    public String id;
    public Object path_alias;
    public String permalink;
    public String realname;

    public Comment toModel() {
        Comment c = new Comment();
        c.setId(id);
        c.setContent(_content);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(datecreate) * 1000);
        c.setPosted(cal);

        User user = new User();
        user.setId(author);
        user.setUserName(authorname);
        c.setAuthor(user);

        return c;
    }
}
