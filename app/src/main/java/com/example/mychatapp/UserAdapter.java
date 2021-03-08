package com.example.mychatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context homeActivity;
    ArrayList<Users> usersArrayList;
    public UserAdapter(HomeActivity homeActivity, ArrayList<Users> usersArrayList) {
        this.homeActivity = homeActivity;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeActivity).inflate(R.layout.itemuserrow , parent , false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Users users  = usersArrayList.get(position);

        holder.userName.setText(users.name);
        holder.userStatus.setText(users.status);
        Picasso.get().load(users.imageUri).into(holder.userProfile);
    }


    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        CircularImageView userProfile;
        TextView userName ,  userStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userProfile = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userStatus = itemView.findViewById(R.id.userStatus);
        }
    }
}
