package com.codehasy.chatg.Database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo( name="username")
    public String username;

    @ColumnInfo( name="password")
    public String password;

    @ColumnInfo(name="image")
    public String image;






}
