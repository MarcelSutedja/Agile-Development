package com.example.schoolapp.Adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.schoolapp.Body.Chat;
import com.example.schoolapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.ViewHolder> {

    public static final int msg_TYPE_LEFT = 0;
    public static final int msg_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fbUSER;

    public MessageAdaptor(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }
    @NonNull
    @Override
    public MessageAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == msg_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_element_right, viewGroup, false);
            return new MessageAdaptor.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_element_left, viewGroup, false);
            return new MessageAdaptor.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdaptor.ViewHolder viewHolder, int i) {

        Chat rChat = mChat.get(i);

        viewHolder.show_Message.setText(rChat.getMessage());

        if (imageurl.equals("default")){
            viewHolder.profile_Image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(imageurl).into(viewHolder.profile_Image);
        }
        //Check for the Last Message
        if(i == mChat.size()-1){
            if (rChat.isIsseen()){
                viewHolder.txt_Seen.setText("Seen");
            }else {
                viewHolder.txt_Seen.setText("Delivered");
            }
        }else {
            viewHolder.txt_Seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_Message;
        public ImageView profile_Image;

        public TextView txt_Seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_Message = itemView.findViewById(R.id.show_message);
            profile_Image = itemView.findViewById(R.id.profile_image);
            txt_Seen = itemView.findViewById(R.id.txt_seen);
        }
    }
    @Override
    public int getItemViewType(int i){

        fbUSER = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(i).getSender().equals(fbUSER.getUid())){
            return msg_TYPE_RIGHT;
        }else{
            return msg_TYPE_LEFT;
        }
    }
}
