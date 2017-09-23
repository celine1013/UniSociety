package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.Post;
import Model.Society;

/**
 * Created by Celine on 23/09/2017.
 */

// TODO: 23/09/2017 history_item.xml + widget id

public class HistoryAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    public static final String POST_KEY = "post_key";

    private List<Post> mItems;
    private Context mContext;

    public HistoryAdapter(Context context, List<Post> posts) {
        this.mContext = context;
        this.mItems = posts;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.history_item, parent, false);
        PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
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
                Intent intent = new Intent(mContext, CreatePost_Activity.class);
                // TODO: 13/09/2017 change model into parcelable
                //intent.putExtra(POST_KEY, post);
                intent.putExtra("POST_TYPE", PostHistoryActivity.EDIT_POST);
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
            tv_EventTitle = (TextView) itemView.findViewById(R.id.tv_postTitle);
            tv_EventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
            tv_EventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
            tv_EventLocation = (TextView) itemView.findViewById(R.id.tv_eventLocation);
        }
    }
}
