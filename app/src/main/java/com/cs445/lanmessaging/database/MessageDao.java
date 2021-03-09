/*
Alex Mak
Adapted from https://developer.android.com/training/data-storage/room
*/

package com.cs445.lanmessaging.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cs445.lanmessaging.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insertMessage(Message message);

    //Gets all messages from the specified conversation
    @Query("SELECT * FROM messages WHERE senderIP = :IP OR recipientIP = :IP ORDER BY date asc")
    List<Message> getMessages(String IP);

    //Gets the last message from each conversation
    @Query("SELECT m1.* FROM messages m1 WHERE m1.id = (SELECT id FROM (SELECT id, MAX(m2.date) FROM messages m2 WHERE (m1.recipientIP = m2.recipientIP AND m1.senderIP = m2.senderIP) OR (m1.recipientIP = m2.senderIP AND m1.senderIP = m2.recipientIP))) ORDER BY date desc;")
    List<Message> getLastMessages();

    //Gets all messages from the specified conversation
    @Query("DELETE FROM messages WHERE senderIP = :IP OR recipientIP = :IP")
    void deleteMessages(String IP);

    //Deletes all messages
    @Query("DELETE FROM messages")
    void deleteAllMessages();

}
