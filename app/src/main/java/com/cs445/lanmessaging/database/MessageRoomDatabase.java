/*
Alex Mak
Adapted from https://developer.android.com/training/data-storage/room
*/

package com.cs445.lanmessaging.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cs445.lanmessaging.MainActivity;
import com.cs445.lanmessaging.Message;

@Database(entities = {Message.class}, version = 1, exportSchema = true)
public abstract class MessageRoomDatabase extends RoomDatabase {

    public abstract MessageDao messageDao();
    private static MessageRoomDatabase INSTANCE;

    static MessageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Message.class) {
                if (INSTANCE == null) {
                    Builder<MessageRoomDatabase> builder = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MessageRoomDatabase.class,
                            "messages.db");
                    INSTANCE = builder.build();
                }
            }
        }
        return INSTANCE;
    }


}
