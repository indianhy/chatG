package com.codehasy.chatg.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,Chat.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ChatDao chatDao();
}