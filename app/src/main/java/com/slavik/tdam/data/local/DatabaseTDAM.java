package com.slavik.tdam.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.slavik.tdam.data.local.dao.CommentDao;
import com.slavik.tdam.data.local.dao.PhotoDao;
import com.slavik.tdam.data.local.dao.PhotosetDao;
import com.slavik.tdam.data.local.entities.CommentEntity;
import com.slavik.tdam.data.local.entities.PhotoEntity;
import com.slavik.tdam.data.local.entities.PhotosetEntity;

@Database(entities = {PhotosetEntity.class, PhotoEntity.class, CommentEntity.class}, version = 1)
public abstract class DatabaseTDAM extends RoomDatabase {

    public abstract PhotosetDao photosetDao();

    public abstract PhotoDao photoDao();

    public abstract CommentDao commentDao();
}
