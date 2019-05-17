package com.example.schoolapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolapp.Adaptor.UserAdaptor;
import com.example.schoolapp.Body.Chatlist;
import com.example.schoolapp.Body.User;
import com.example.schoolapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdaptor userAdaptor;
    private List<User> mUsers;

    FirebaseUser fbUser;
    DatabaseReference dbReference;
    private List <Chatlist> usersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fbUser = FirebaseAuth.getInstance().getCurrentUser();                   //Get the current Logged in User

        usersList = new ArrayList<>();

        dbReference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fbUser.getUid());
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    private void chatList(){
        mUsers = new ArrayList<>();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User users = snapshot.getValue(User.class);
                    for (Chatlist chatlist : usersList){
                        if (users.getId().equals(chatlist.getId())){
                            mUsers.add(users);
                        }
                    }
                }
                //Shows users that have interracted with User
                userAdaptor = new UserAdaptor(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
