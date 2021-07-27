package com.demoapp.firebaseap.ChatScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demoapp.firebaseap.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    RecyclerView chatRecycler;
    EditText textMessage;
    ImageView sendButton;
    DatabaseReference userRef,chatRef;
    String username;
    FirebaseRecyclerOptions<ChatScreenModel> recyclerOptions;
    FirebaseRecyclerAdapter<ChatScreenModel, ChatViewHolder> recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecycler = findViewById(R.id.chatRecycler);
        textMessage = findViewById(R.id.chatText);
        sendButton = findViewById(R.id.send);

        chatRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        chatRecycler.hasFixedSize();


        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        chatRef = FirebaseDatabase.getInstance().getReference().child("chats");

        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if (snapshot.exists()){
                    username = (String) snapshot.child("name").getValue();
                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Check you Internet ", Toast.LENGTH_SHORT).show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textMessage.getText().toString().equals("")){
                    sendMessage();
                }else{
                    Toast.makeText(ChatActivity.this, "Please enter some text!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startChatSession();

    }

    private void startChatSession() {

        recyclerOptions = new FirebaseRecyclerOptions.Builder<ChatScreenModel>().setQuery(chatRef,ChatScreenModel.class).build();
        recyclerAdapter = new FirebaseRecyclerAdapter<ChatScreenModel, ChatViewHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull  ChatScreenModel model) {

                holder.text.setText(model.getMessage());
                holder.name.setText(model.getSender());
                holder.time.setText(model.getTime());

            }

            @NonNull

            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
                return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false));
            }
        };

        chatRecycler.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();


    }

    private void sendMessage() {
        ChatScreenModel model = new ChatScreenModel(2,textMessage.getText().toString(),username,"10:00pm");
        chatRef.child(UUID.randomUUID().toString()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                textMessage.setText("");
            }
        });
    }
}