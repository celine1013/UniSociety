package com.example.celine.unisociety;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Model.Account;
import Model.Post;
import Model.Society;

public class PostDetail_Activity extends AppCompatActivity {

    ;
    private Post post;
    private Account currentUser;

    private Switch st_attend;
    private ImageView iv_eventImage;
    private ImageButton ib_socIcon;
    private TextView tv_title;
    private TextView tv_socName;
    private TextView tv_description;
    private TextView tv_location;
    private TextView tv_date;
    private TextView tv_time;
    private ProgressBar pa_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        //get info from the intent
        post = this.getIntent().getParcelableExtra(Post.POST);
        currentUser = this.getIntent().getParcelableExtra(MainActivity.CURRENT_USER);

        st_attend = findViewById(R.id.st_attend);
        iv_eventImage = findViewById(R.id.eventImage);
        ib_socIcon = findViewById(R.id.society_icon);
        tv_title = findViewById(R.id.tv_eventTitle);
        tv_description = findViewById(R.id.tv_desc);
        tv_location = findViewById(R.id.tv_address);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        tv_socName = findViewById(R.id.tv_societyName);
        pa_loading = findViewById(R.id.pb_imageLoading);

        pa_loading.setVisibility(View.VISIBLE);

        if(post == null){
            Log.e("POST DETAIL", "NO POST RECEIVED: UNKNOWN ERROR");
        }

        if(currentUser != null && currentUser.getId() == Account.STUDENT){
            st_attend.setVisibility(View.VISIBLE);
            // TODO: 13/10/2017 switch listener 
        }

        // TODO: 6/10/2017 imageview

        tv_title.setText(post.getPostTitle());
        tv_description.setText(post.getPostDescription());
        tv_location.setText(post.getLocation());
        tv_date.setText(post.getPostDate());
        tv_time.setText(post.getBeginTime()+ " ~ "+post.getEndTime());
        String downloadUrl = post.getImageUrl();
        Glide.with(PostDetail_Activity.this).load(downloadUrl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                pa_loading.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                pa_loading.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(iv_eventImage);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child(Society.SOCIETY).orderByChild(Society.SOCIETY_ID).equalTo(post.getId());
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Society soc = dataSnapshot.getValue(Society.class);
                // TODO: 6/10/2017 soc icon
                tv_socName.setText(soc.getSocietyName());
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
