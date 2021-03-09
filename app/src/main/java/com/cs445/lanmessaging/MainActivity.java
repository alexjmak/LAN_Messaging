/*
Alex Mak
Adapted from https://medium.com/fnplus/android-recycler-view-using-card-view-6907672d722d
*/

package com.cs445.lanmessaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.cs445.lanmessaging.database.MessageRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageRepository.OnAsyncFinishedListener, View.OnClickListener {

    public static final boolean DEBUG = true;
    public static MessageRepository messageRepository;
    RecyclerView recyclerView;
    LastMessageAdapter lastMessageAdapter;

    private void insertTestData() {
        messageRepository.insertMessage(new Message("localhost", "192.168.1.2", 0, "hello 192.168.1.2 from localhost"));
        messageRepository.insertMessage(new Message("192.168.1.2", "localhost", 100000000, "hello localhost from 192.168.1.2"));
        messageRepository.insertMessage(new Message("192.168.1.2", "localhost", 100000000, "(2) hello localhost from 192.168.1.2"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.3", 0, "hello 192.168.1.3 from localhost"));
        messageRepository.insertMessage(new Message("192.168.1.3", "localhost", 100000000, "hello localhost from 192.168.1.3"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.3", 200000000, "(2) hello 192.168.1.3 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.3", 200000000, "(3) hello 192.168.1.3 from localhost. hello 192.168.1.3 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.4", 0, "hello 192.168.1.4 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.5", 0, "hello 192.168.1.5 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.6", 0, "hello 192.168.1.6 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.7", 0, "hello 192.168.1.7 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.8", 0, "hello 192.168.1.8 from localhost"));
        messageRepository.insertMessage(new Message("localhost", "192.168.1.9", 0, "hello 192.168.1.9 from localhost"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageRepository = new MessageRepository(getApplicationContext());

        if (DEBUG) {
            //First run adapted from Stack Overflow: Suragch
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs445.lanmessaging", MODE_PRIVATE);
            if (sharedPreferences.getBoolean("testdata", true)) {
                sharedPreferences.edit().putBoolean("testdata", false).apply();
                insertTestData();
            }
        }

        //TODO: New conversation button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start NewConversationActivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Retrieve messages from the database and calls asyncFinished(List<Message> messages)
        messageRepository.getLastMessages(this);
    }

    //TODO: Handle click on a conversation
    @Override
    public void onClick(View view) {
        TextView ip = view.findViewById(R.id.ip);
        Snackbar.make(view, "Conversation with " + ip.getText(), Snackbar.LENGTH_SHORT).show();
    }

    //Reload the RecyclerView after retrieving sets from the database
    public void asyncFinished(List<Message> messages) {
        lastMessageAdapter = new LastMessageAdapter(messages, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true); --uncomment for ConversationActivity
        recyclerView = findViewById(R.id.lastMessages);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lastMessageAdapter);
    }

}