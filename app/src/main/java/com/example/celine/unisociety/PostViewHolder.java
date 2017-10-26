package com.example.celine.unisociety;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Model.Post;
import Model.Society;

/**
 * Created by Celine on 9/10/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
    public View postView;
    private Context context;
    private ImageView ib_socIcon;
    private TextView tv_EventTitle;
    private TextView tv_EventDate;
    private TextView tv_EventTime;
    private TextView tv_EventLocation;

    public PostViewHolder(View itemView) {
        super(itemView);
        postView = itemView;
        context = itemView.getContext();
        ib_socIcon = itemView.findViewById(R.id.ib_socIcon);
        tv_EventTitle = (TextView) itemView.findViewById(R.id.tv_eventTitle);
        tv_EventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
        tv_EventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
        tv_EventLocation = (TextView) itemView.findViewById(R.id.tv_eventLocation);
    }

    public void setPost(Post post){
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child(Society.SOCIETY).orderByChild(Society.SOCIETY_ID).equalTo(post.getId());
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Society soc = dataSnapshot.getValue(Society.class);
                String downloadUrl = soc.getLogo();
                Glide.with(context).load(downloadUrl).into(ib_socIcon);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tv_EventTitle.setText(post.getPostTitle());
        tv_EventDate.setText(post.getPostDate());
        tv_EventTime.setText(post.getBeginTime()+"~"+post.getEndTime());
        tv_EventLocation.setText(post.getLocation());
    }

}
