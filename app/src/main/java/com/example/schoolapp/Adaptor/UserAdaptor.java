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
import com.example.schoolapp.Body.User;
import com.example.schoolapp.R;

import java.util.List;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ViewHolder>{

    private Context mContext;
    private List<User> mUsers;

    public UserAdaptor(Context mContext, List<User> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_element, viewGroup, false);
        return new UserAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User users = mUsers.get(i);
        viewHolder.student_Name.setText(users.getName());
        if (users.getImageURL().equals("default")){
            viewHolder.profile_Image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(users.getImageURL()).into(viewHolder.profile_Image);
        }
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView student_Name;
        public ImageView profile_Image;

        public ViewHolder(View itemView) {
            super(itemView);

            student_Name = itemView.findViewById(R.id.name);
            profile_Image = itemView.findViewById(R.id.profile_image);
        }
    }
}
