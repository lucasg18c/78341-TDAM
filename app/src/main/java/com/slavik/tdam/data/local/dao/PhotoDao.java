package com.slavik.tdam.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.slavik.tdam.data.local.entities.PhotoEntity;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoto(PhotoEntity photos);

    @Query("SELECT * FROM PhotoEntity WHERE id = :id")
    PhotoEntity getPhotoById(String id);
}
