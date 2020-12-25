package com.codehasy.chatg.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(List<String> userIds);

    @Query("SELECT * FROM user WHERE id IN (:userId)")
    User loadUserById(String userId);

    @Query("SELECT * FROM user WHERE username LIKE :first AND " +
            "image LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
