package com.codehasy.chatg.Database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chat {

    @NonNull
    @PrimaryKey
    public String id;

    @ColumnInfo( name="message")
    public String message;

    @ColumnInfo( name="sender")
    public String sender;

    @ColumnInfo(name="reciever")
    public String reciever;






}
