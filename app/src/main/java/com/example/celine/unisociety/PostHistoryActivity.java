package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Model.Account;
import Model.Post;
import Model.Society;

public class PostHistoryActivity extends AppCompatActivity {
    public static final String POST_TYPE = "post_type";
    public static final String EDIT_POST = "edit_post";
    public static final String NEW_POST = "new_post";

    private RecyclerView history;
    private FloatingActionButton fab_createPost;
    private ProgressBar pb_loading;

    private Account currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);

        currentUser = this.getIntent().getParcelableExtra(MainActivity.CURRENT_USER);
        if (currentUser == null) {
            Log.e("POST_HISTORY", "HISTORY ACTIVITY FAILED");
            finish();
        }

        //create new post
        fab_createPost = (FloatingActionButton) findViewById(R.id.fab_newPost);
        fab_createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POST_HISTORY", "CLIKED");
                Intent intent = new Intent(PostHistoryActivity.this, CreatePost_Activity.class);
                intent.putExtra(MainActivity.CURRENT_USER, currentUser);
                intent.putExtra(POST_TYPE, NEW_POST);
                startActivity(intent);
            }
        });

        // TODO: 23/09/2017 get history using id
        history = (RecyclerView) findViewById(R.id.rv_history);
        pb_loading = findViewById(R.id.pb_history);
        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.POST);
        Query q = postRef.orderByChild(Post.POST_SOCIETY_ID).equalTo(currentUser.getId());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    pb_loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Post, HistoryViewHolder>(Post.class,
                R.layout.history_item, HistoryViewHolder.class, q) {
            @Override
            protected void populateViewHolder(final HistoryViewHolder viewHolder, final Post model, int position) {
                viewHolder.setPost(model, PostHistoryActivity.this.currentUser);
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        history.setLayoutManager(manager);
        history.setAdapter(adapter);
    }


    private static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public View postView;
        private Context context;
        private ImageView ib_socIcon;
        private ImageButton ib_editPost;
        private TextView tv_EventTitle;
        private TextView tv_EventDate;
        private TextView tv_EventTime;
        private TextView tv_EventLocation;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            postView = itemView;
            context = itemView.getContext();
            ib_socIcon = itemView.findViewById(R.id.his_socIcon);
            ib_editPost = itemView.findViewById(R.id.ib_editPost);
            tv_EventTitle = itemView.findViewById(R.id.his_postTitle);
            tv_EventLocation = itemView.findViewById(R.id.his_location);
            tv_EventDate = itemView.findViewById(R.id.his_date);
            tv_EventTime = itemView.findViewById(R.id.his_time);

        }

        public void setPost(final Post post, final Account currentUser) {
            final  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query q = ref.child(Society.SOCIETY).orderByChild(Society.SOCIETY_ID).equalTo(currentUser.getId());
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
            ib_editPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CreatePost_Activity.class);
                    intent.putExtra(MainActivity.CURRENT_USER, currentUser);
                    intent.putExtra(PostHistoryActivity.POST_TYPE, PostHistoryActivity.EDIT_POST);
                    intent.putExtra(Post.POST, post);
                    context.startActivity(intent);
                }
            });
        }
    }
}



