package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public static final String POST_KEY = "post_key";
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
        final Post post = mItems.get(position);

        // TODO: 10/09/2017 add the image
        //try {
        //set view
        holder.tv_EventTitle.setText(post.getPostTitle());
        holder.tv_EventDate.setText(post.getPostDate());
        holder.tv_EventTime.setText(post.getBeginTime()+"~"+post.getEndTime());
        holder.tv_EventLocation.setText(post.getLocation());

        /*} catch (IOException e) {
            e.printStackTrace();
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostDetail_Activity.class);
                // TODO: 13/09/2017 change model into parcelable
                //intent.putExtra(POST_KEY, post);
                mContext.startActivity(intent);
            }
        });
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
            tv_EventTitle = (TextView) itemView.findViewById(R.id.his_postTitle);
            tv_EventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
            tv_EventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
            tv_EventLocation = (TextView) itemView.findViewById(R.id.tv_eventLocation);
        }
    }
}