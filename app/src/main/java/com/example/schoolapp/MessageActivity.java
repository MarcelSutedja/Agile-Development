package com.example.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.schoolapp.Adaptor.MessageAdaptor;
import com.example.schoolapp.Body.Chat;
import com.example.schoolapp.Body.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_Image;
    TextView name;
    ImageButton btn_Send;
    EditText text_Send;
    MessageAdaptor messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;
    FirebaseUser fbUser;
    DatabaseReference dbReference;
    Intent intent;
    ValueEventListener seenListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

                recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_Image = findViewById(R.id.profile_Image);
        name = findViewById(R.id.name);
        btn_Send = findViewById(R.id.btn_send);
        text_Send = findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        //Set what happens when you press the send button(Green Arrow)
        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = text_Send.getText().toString();        //Collect sent message as String
                if (!message.equals("")){
                    sendMessage(fbUser.getUid(), userid, message);      //Give String to method that uploads the message to the database
                }else{
                    Toast emptyMessage = Toast.makeText(MessageActivity.this, "There is nothing to send", Toast.LENGTH_LONG);
                    emptyMessage.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1250);
                    emptyMessage.show();
                }
                text_Send.setText("");
            }
        });


        dbReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User users = dataSnapshot.getValue(User.class);
                name.setText(users.getName());
                if (users.getImageURL().equals("default")){
                    profile_Image.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(profile_Image);
                }
                readMessages(fbUser.getUid(), userid, users.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        seenMessage(userid);
    }
    private void seenMessage(final String userid){
        dbReference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chats = snapshot.getValue(Chat.class);
                    if(chats.getReceiver().equals(fbUser.getUid())&&chats.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //Send Message to Database along with the relevant information (Sender, Receiver, Actual Message, Seen status)
    private void sendMessage (String sender, final String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fbUser.getUid()).child(receiver);
        chatReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatReference.child("id").setValue(receiver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference receiverRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(receiver).child(fbUser.getUid());
        chatReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatReference.child("id").setValue(fbUser.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readMessages(final String myid, final String userid, final String imageurl){
        mChat = new ArrayList<>();

        dbReference = FirebaseDatabase.getInstance().getReference("Chats");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid)&& chat.getSender().equals(userid)||
                    chat.getReceiver().equals(userid)&&chat.getSender().equals(myid) ){
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdaptor(MessageActivity.this, mChat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void status (String status){
        dbReference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.getUid());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("currentStatus", status);

        dbReference.updateChildren(hashMap);
    }
    @Override
    protected void onResume(){
        super.onResume();
        status("Online");
    }
    @Override
    protected void onPause(){
        super.onPause();
        dbReference.removeEventListener(seenListener);
        status("Offline");
    }
}
