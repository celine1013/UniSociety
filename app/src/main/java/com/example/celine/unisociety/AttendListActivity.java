package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Model.Account;
import Model.Eventlist;
import Model.Post;
import Model.Society;

public class AttendListActivity extends AppCompatActivity {
    private RecyclerView rv_events;
    private ProgressBar pb_loading;

    private Account currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_list);

        currentUser = this.getIntent().getParcelableExtra(Account.ACCOUNT);

        rv_events = findViewById(R.id.rv_attendList);
        pb_loading = findViewById(R.id.pb_attend);

        final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Eventlist.EVENTLIST);
        Query q = postRef.orderByChild(Eventlist.ACCOUNT_ACCOUNT_NAME).equalTo(currentUser.getAccountName());
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Eventlist, AttendViewHolder>(Eventlist.class,
                R.layout.postlist_item, AttendViewHolder.class, q) {
            @Override
            protected void populateViewHolder(final AttendViewHolder viewHolder, final Eventlist model, int position) {
                viewHolder.setPost(model, AttendListActivity.this.currentUser);
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_events.setLayoutManager(manager);
        rv_events.setAdapter(adapter);
    }

    private static class AttendViewHolder extends RecyclerView.ViewHolder {
        public View postView;
        private Context context;
        private TextView tv_EventTitle;
        private TextView tv_EventDate;
        private TextView tv_EventTime;
        private TextView tv_EventLocation;

        public AttendViewHolder(View itemView) {
            super(itemView);
            postView = itemView;
            context = itemView.getContext();
            tv_EventTitle = (TextView) itemView.findViewById(R.id.tv_eventTitle);
            tv_EventDate = (TextView) itemView.findViewById(R.id.tv_eventDate);
            tv_EventTime = (TextView) itemView.findViewById(R.id.tv_eventTime);
            tv_EventLocation = (TextView) itemView.findViewById(R.id.tv_eventLocation);
        }

        public void setPost(final Eventlist el, final Account currentUser) {
            final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.POST);
            Query q = postRef.orderByChild(Post.POST_KEY).equalTo(el.getKey());
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // TODO: 15/10/2017 show notification on the screen
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            q.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final Post post = dataSnapshot.getValue(Post.class);
                    tv_EventTitle.setText(post.getPostTitle());
                    tv_EventDate.setText(post.getPostDate());
                    tv_EventTime.setText(post.getBeginTime()+"~"+post.getEndTime());
                    tv_EventLocation.setText(post.getLocation());
                    postView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, PostDetail_Activity.class);
                            intent.putExtra(MainActivity.CURRENT_USER, currentUser);
                            intent.putExtra(Post.POST, post);
                            context.startActivity(intent);
                        }
                    });

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

        }
    }
}

