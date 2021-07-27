package com.demoapp.firebaseap.ChatScreen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.firebaseap.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView  name , text , time;

    public ChatViewHolder(@NonNull  View itemView) {
        super(itemView);

        name  = itemView.findViewById(R.id.sender);
        text  = itemView.findViewById(R.id.text);
        time  = itemView.findViewById(R.id.time);
    }
}
