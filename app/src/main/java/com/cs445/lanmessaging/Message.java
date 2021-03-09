/*
Alex Mak
*/

package com.cs445.lanmessaging;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int ID;

    @ColumnInfo(name = "senderIP")
    private String senderIP;

    @ColumnInfo(name = "recipientIP")
    private String recipientIP;

    //Unix time in milliseconds
    @ColumnInfo(name = "date")
    private long date;

    //Text message
    @ColumnInfo(name = "text")
    private String text;

    public Message(String senderIP, String recipientIP, long date, String text) {
        this.senderIP = senderIP;
        this.recipientIP = recipientIP;
        this.date = date;
        this.text = text;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSenderIP() {
        return senderIP;
    }

    public String getRecipientIP() {
        return recipientIP;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

}
