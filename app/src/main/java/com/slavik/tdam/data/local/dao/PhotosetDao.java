package com.slavik.tdam.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.slavik.tdam.data.local.entities.PhotosetEntity;
import com.slavik.tdam.data.local.entities.PhotosetWithPhotos;

import java.util.List;

@Dao
public interface PhotosetDao {

    @Transaction
    @Query("SELECT * FROM PhotosetEntity")
    List<PhotosetWithPhotos> getPhotosets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhotoset(PhotosetEntity photosetEntities);
}
