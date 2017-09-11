package com.example.celine.unisociety;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Model.Post;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mItems;
    private Context mContext;

    public PostAdapter(Context context, List<Post> posts) {
        this.mContext = context;
        this.mItems = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.postlist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mItems.get(position);

        // TODO: 10/09/2017 add the image
        //try {
        //set view
        holder.tv_EventTitle.setText(post.getPostTitle());
        holder.tv_EventDate.setText("tbc");// TODO: 10/09/2017 change date format
        holder.tv_EventTime.setText("tbc");
        holder.tv_EventLocation.setText(post.getLocation());

        /*} catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_EventTitle;
        public TextView tv_EventDate;
        public TextView tv_EventTime;
        public TextView tv_EventLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_EventTitle = (TextView) itemView.findViewById(R.id.tv_postTitle);
            tv_EventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
            tv_EventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
            tv_EventLocation = (TextView) itemView.findViewById(R.id.tv_eventLocation);
        }
    }
}