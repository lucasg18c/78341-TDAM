package com.slavik.tdam.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.slavik.tdam.data.local.entities.PhotoEntity;
import com.slavik.tdam.data.local.entities.PhotoWithComments;

import java.util.List;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoto(PhotoEntity photos);

    @Query("SELECT * FROM PhotoEntity WHERE id = :id")
    PhotoEntity getPhotoById(String id);

    @Transaction
    @Query("SELECT * FROM PhotoEntity WHERE id = :photoId")
    PhotoWithComments getPhotoWithComments(String photoId);

}
