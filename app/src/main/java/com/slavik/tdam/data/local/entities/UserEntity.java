package com.slavik.tdam.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @NonNull
    @PrimaryKey
    private String id;
    private List<PhotosetEntity> photosets = new ArrayList<>();
    private String userName;

    public UserEntity() {
    }
}
