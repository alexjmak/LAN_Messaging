/*
Alex Mak
Adapted from https://developer.android.com/training/data-storage/room
*/

package com.cs445.lanmessaging.database;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import com.cs445.lanmessaging.Message;

import java.util.List;

public class MessageRepository {

    private MessageDao messageDao;

    public MessageRepository(Context application) {
        MessageRoomDatabase messageRoomDatabase = MessageRoomDatabase.getDatabase(application);
        messageDao = messageRoomDatabase.messageDao();
    }

    //Inserts a new message into the database
    public void insertMessage(Message message) {
        InsertAsyncTask task = new InsertAsyncTask(messageDao);
        task.execute(message);
    }

    //Gets all messages in the database with the specified IP
    public void getMessages(String recipientIP, OnAsyncFinishedListener callback) {
        GetAsyncTask task = new GetAsyncTask(messageDao);
        task.messageOnAsyncFinishedListener(callback);
        task.execute(recipientIP);
    }

    //Gets last message in the database from each conversation
    public void getLastMessages(OnAsyncFinishedListener callback) {
        GetLastAsyncTask task = new GetLastAsyncTask(messageDao);
        task.messageOnAsyncFinishedListener(callback);
        task.execute();
    }

    //Deletes all messages from the database with the specified IP
    public void deleteMessages(String recipientIP) {
        DeleteAsyncTask task = new DeleteAsyncTask(messageDao);
        task.execute(recipientIP);
    }

    //Deletes all messages from the database
    public void deleteAllMessages() {
        DeleteAllAsyncTask task = new DeleteAllAsyncTask(messageDao);
        task.execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao asyncTaskDao;
        InsertAsyncTask(MessageDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            //Avoid errors from inserting an existing message
            try {
                asyncTaskDao.insertMessage(params[0]);
            } catch (SQLiteConstraintException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class GetAsyncTask extends AsyncTask<String, Void, List<Message>> {
        private MessageDao asyncTaskDao;
        private OnAsyncFinishedListener callback = null;

        GetAsyncTask(MessageDao dao) {
            asyncTaskDao = dao;
        }

        public void messageOnAsyncFinishedListener(OnAsyncFinishedListener callback) {
            this.callback = callback;
        }

        @Override
        protected List<Message> doInBackground(final String... params) {
            return asyncTaskDao.getMessages(params[0]);
        }

        @Override
        protected void onPostExecute(List<Message> result) {
            callback.asyncFinished(result);
        }
    }

    private static class GetLastAsyncTask extends AsyncTask<String, Void, List<Message>> {
        private MessageDao asyncTaskDao;
        private OnAsyncFinishedListener callback = null;

        GetLastAsyncTask(MessageDao dao) {
            asyncTaskDao = dao;
        }

        public void messageOnAsyncFinishedListener(OnAsyncFinishedListener callback) {
            this.callback = callback;
        }

        @Override
        protected List<Message> doInBackground(final String... params) {
            return asyncTaskDao.getLastMessages();
        }

        @Override
        protected void onPostExecute(List<Message> result) {
            callback.asyncFinished(result);
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        private MessageDao asyncTaskDao;
        DeleteAsyncTask(MessageDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteMessages(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<String, Void, Void> {
        private MessageDao asyncTaskDao;
        DeleteAllAsyncTask(MessageDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deleteAllMessages();
            return null;
        }
    }

    public interface OnAsyncFinishedListener {
        void asyncFinished(List<Message> result);
    }
}

