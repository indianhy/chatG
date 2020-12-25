package com.codehasy.chatg.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat")
    List<Chat> getAll();

    @Query("SELECT * FROM chat WHERE sender IN (:userId)")
    List<Chat> loadAllChatForSender(String userId);

    @Query("SELECT reciever FROM chat WHERE sender IN (:userId) OR reciever IN (:userId)")
    List<String> loadRecieverForSender(String userId);


    @Query("SELECT * FROM chat WHERE reciever IN (:userId)")
    List<Chat> loadAllChatForReciever(String userId);

    @Query("SELECT * FROM chat WHERE sender IN (:userId) LIMIT 1")
    Chat loadUserById(String userId);

    @Insert
    void insertAll(Chat... chats);

    @Delete
    void delete(Chat chat);
}
