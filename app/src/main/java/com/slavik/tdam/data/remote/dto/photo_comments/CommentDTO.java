package com.slavik.tdam.data.remote.dto.photo_comments;

import com.slavik.tdam.model.Comment;
import com.slavik.tdam.util.Convert;

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
        return new Comment(
                _content,
                author,
                author_is_deleted == 1,
                authorname,
                Convert.unixToCalendar(Long.parseLong(datecreate)),
                iconfarm,
                iconserver,
                id,
                permalink,
                realname
        );
    }
}
