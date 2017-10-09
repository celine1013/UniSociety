package com.example.celine.unisociety;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Post;
import Model.Society;

public class PostHistoryActivity extends AppCompatActivity {
    public static final String POST_TYPE = "post_type";
    public static final String EDIT_POST = "edit_post";
    public static final String NEW_POST = "new_post";

    private RecyclerView history;
    private List<Post> pastEvents;
    private FloatingActionButton fab_createPost;
    private Button btn_back;

    private Account currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);

        currentUser = this.getIntent().getParcelableExtra(MainActivity.CURRENT_USER);
        if (currentUser == null) Log.e("ERROR", "HISTORY ACTIVITY FAILED");

        //create new post
        fab_createPost = (FloatingActionButton) findViewById(R.id.fab_newPost);
        fab_createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HISTORY", "CLIKED");
                Intent intent = new Intent(PostHistoryActivity.this, CreatePost_Activity.class);
                intent.putExtra(MainActivity.CURRENT_USER, currentUser);
                intent.putExtra(POST_TYPE, NEW_POST);
                startActivity(intent);
            }
        });
        // TODO: 23/09/2017 get history using id
        history = (RecyclerView) findViewById(R.id.rv_history);
        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.POST);
        Query q = postRef.orderByChild(Post.POST_SOCIETY_ID).equalTo(currentUser.getId());
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Post, HistoryViewHolder>(Post.class,
                R.layout.postlist_item, HistoryViewHolder.class, q) {
            @Override
            protected void populateViewHolder(final HistoryViewHolder viewHolder, final Post model, int position) {

                viewHolder.postView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PostHistoryActivity.this, PostDetail_Activity.class);
                        intent.putExtra(POST_TYPE, EDIT_POST);
                        intent.putExtra(MainActivity.CURRENT_USER, currentUser);
                        intent.putExtra(Post.POST, model);
                        startActivity(intent);
                        //Toast.makeText(Fragment_HomePage.this.getActivity(), String.valueOf(model.getId()),Toast.LENGTH_LONG).show();
                    }
                });

                viewHolder.setPost(model);

            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        history.setLayoutManager(manager);
        history.setAdapter(adapter);
    }

    // TODO: 23/09/2017 editing finished, will the  userID still be remained

    // TODO: 23/09/2017 back functionality (buggy, to be improved)

    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        private View postView;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            postView = itemView;

        }

        public void setPost(Post post) {

        }


    }
}


