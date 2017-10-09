package com.example.celine.unisociety;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_HomePage extends Fragment {
    private CardView cv_newEvents;

    private RecyclerView recentEvent;
    private List<Post> recentEvents;

    private TextView tv_notification;
    private ProgressBar pb_circle;



    public Fragment_HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        cv_newEvents = v.findViewById(R.id.cv_home_events);
        tv_notification = v.findViewById(R.id.tv_home_notification);
        recentEvent = (RecyclerView) v.findViewById(R.id.rv_recentEvent);

        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.POST);
        Query q = postRef.orderByChild(Post.POST_DATE).limitToFirst(7);
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,
        R.layout.postlist_item, PostViewHolder.class, q) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, int position) {
                viewHolder.postView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Fragment_HomePage.this.getActivity(), PostDetail_Activity.class);
                        intent.putExtra(MainActivity.CURRENT_USER, MainActivity.currentUser);
                        intent.putExtra(Post.POST, model);
                        startActivity(intent);
                        //Toast.makeText(Fragment_HomePage.this.getActivity(), String.valueOf(model.getId()),Toast.LENGTH_LONG).show();
                    }
                });
                viewHolder.setPost(model);

            }
        };

        recentEvent.setAdapter(adapter);
        return v;
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {
        public View postView;
        public TextView tv_EventTitle;
        public TextView tv_EventDate;
        public TextView tv_EventTime;
        public TextView tv_EventLocation;

        public PostViewHolder(View itemView) {
            super(itemView);
            postView = itemView;
            tv_EventTitle = (TextView) itemView.findViewById(R.id.tv_postTitle);
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

}
