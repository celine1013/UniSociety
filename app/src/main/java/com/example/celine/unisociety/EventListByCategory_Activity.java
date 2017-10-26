package com.example.celine.unisociety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Model.Post;

public class EventListByCategory_Activity extends AppCompatActivity{
    private TextView tv_categoryTitle;
    private RecyclerView rv_eventsByCate;
    private ProgressBar pb_loading;
    private int currentCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list_by_category);
        tv_categoryTitle = findViewById(R.id.tv_catgoryTtitle);
        rv_eventsByCate = findViewById(R.id.rv_eventsByCate);
        pb_loading = findViewById(R.id.pb_category);

        String[] cats = getResources().getStringArray(R.array.categories);
        currentCat = this.getIntent().getIntExtra(Fragment_CategoryMain.TAG_CATEGORY, -1);
        if(currentCat==-1){
            Log.d("EVENT_CATEGORY", "NO CATEGORY SPECIFIED");
            finish();
        }

        tv_categoryTitle.setText(cats[currentCat]);

        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.POST);
        Query q = postRef.orderByChild(Post.POST_CATEGORY).equalTo(currentCat);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pb_loading.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerAdapter adapter2 = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,
                R.layout.postlist_item, PostViewHolder.class, q) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, int position) {

                viewHolder.postView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EventListByCategory_Activity.this, PostDetail_Activity.class);
                        intent.putExtra(MainActivity.CURRENT_USER, MainActivity.currentUser);
                        intent.putExtra(Post.POST, model);
                        startActivity(intent);
                        //Toast.makeText(Fragment_HomePage.this.getActivity(), String.valueOf(model.getId()),Toast.LENGTH_LONG).show();
                    }
                });

                viewHolder.setPost(model);

            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_eventsByCate.setLayoutManager(manager);
        rv_eventsByCate.setAdapter(adapter2);
    }

}
