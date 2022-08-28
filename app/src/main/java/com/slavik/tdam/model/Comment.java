package com.slavik.tdam.model;

import java.util.Calendar;

public class Comment {
    public String content;
    public String author;
    public boolean isAuthorDeleted;
    public String authorName;
    public Calendar dateCreate;
    public Long iconfarm;
    public String iconServer;
    public String id;
    public String permalink;
    public String authorRealname;

    public Comment() {
    }

    public Comment(String content,
                   String author,
                   boolean isAuthorDeleted,
                   String authorName,
                   Calendar dateCreate,
                   Long iconfarm,
                   String iconServer,
                   String id,
                   String permalink,
                   String authorRealname) {
        this.content = content;
        this.author = author;
        this.isAuthorDeleted = isAuthorDeleted;
        this.authorName = authorName;
        this.dateCreate = dateCreate;
        this.iconfarm = iconfarm;
        this.iconServer = iconServer;
        this.id = id;
        this.permalink = permalink;
        this.authorRealname = authorRealname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAuthorDeleted() {
        return isAuthorDeleted;
    }

    public void setAuthorDeleted(boolean authorDeleted) {
        isAuthorDeleted = authorDeleted;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Long getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(Long iconfarm) {
        this.iconfarm = iconfarm;
    }

    public String getIconServer() {
        return iconServer;
    }

    public void setIconServer(String iconServer) {
        this.iconServer = iconServer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getAuthorRealname() {
        return authorRealname;
    }

    public void setAuthorRealname(String authorRealname) {
        this.authorRealname = authorRealname;
    }
}
