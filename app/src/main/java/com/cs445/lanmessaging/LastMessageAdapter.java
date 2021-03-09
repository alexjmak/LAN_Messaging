/*
Alex Mak
Adapted from https://medium.com/fnplus/android-recycler-view-using-card-view-6907672d722d
*/

package com.cs445.lanmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LastMessageAdapter extends RecyclerView.Adapter<LastMessageAdapter.ViewHolder> {
    List<Message> lastMessageList;
    Context context;
    private View.OnClickListener callback;

    public LastMessageAdapter(List<Message> lastMessageList, View.OnClickListener callback) {
        this.lastMessageList = lastMessageList;
        this.callback = callback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ip;
        TextView lastMessage;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            ip = itemView.findViewById(R.id.ip);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            date = itemView.findViewById(R.id.date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Message message = lastMessageList.get(position);

        String senderIP = message.getSenderIP();
        String recipientIP = message.getRecipientIP();

        String lastMessage = message.getText();
        //Adds You: in front of messages
        if (senderIP.equals("localhost")) {
            lastMessage = "You: " + lastMessage;
        }

        String ip = recipientIP.equals("localhost") ? senderIP : recipientIP;

        Date date = new Date(message.getDate());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        String dateString = dateFormat.format(date);

        holder.lastMessage.setText(lastMessage);
        holder.ip.setText(ip);
        holder.date.setText(dateString);
        holder.itemView.setOnClickListener(callback);
    }

    @Override
    public int getItemCount() {
        return lastMessageList.size();
    }

}