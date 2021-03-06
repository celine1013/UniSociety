package com.example.celine.unisociety;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import Model.Post;

/**
 * Created by Celine on 9/10/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
    public View postView;
    private TextView tv_EventTitle;
    private TextView tv_EventDate;
    private TextView tv_EventTime;
    private TextView tv_EventLocation;

    public PostViewHolder(View itemView) {
        super(itemView);
        postView = itemView;
        tv_EventTitle = (TextView) itemView.findViewById(R.id.tv_eventTitle);
        tv_EventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
        tv_EventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
        tv_EventLocation = (TextView) itemView.findViewById(R.id.tv_eventLocation);
    }

    public void setPost(Post post){
        tv_EventTitle.setText(post.getPostTitle());
        tv_EventDate.setText(post.getPostDate());
        tv_EventTime.setText(post.getBeginTime()+"~"+post.getEndTime());
        tv_EventLocation.setText(post.getLocation());
    }

}
