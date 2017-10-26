package com.example.celine.unisociety;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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
import com.google.firebase.database.ValueEventListener;

import Model.Account;
import Model.Eventlist;
import Model.Post;
import Model.Society;

public class PostDetail_Activity extends AppCompatActivity {

    ;
    private Post post;
    private Account currentUser;

    private Switch st_attend;
    private ImageView iv_eventImage;
    private ImageView ib_socIcon;
    private TextView tv_title;
    private TextView tv_socName;
    private TextView tv_description;
    private TextView tv_location;
    private TextView tv_date;
    private TextView tv_time;
    private ProgressBar pa_loading;

    private Society soc;
    private Eventlist e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        //get info from the intent
        post = this.getIntent().getParcelableExtra(Post.POST);
        currentUser = this.getIntent().getParcelableExtra(MainActivity.CURRENT_USER);
        if(currentUser != null && currentUser.getId()==MainActivity.STUDENT) {
            // 15/10/2017 set switch status according to current user (database needed)
            st_attend = findViewById(R.id.st_attend);
            st_attend.setVisibility(View.VISIBLE);
            st_attend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        st_attend.setText("Attended");
                    } else {
                        st_attend.setText("Attend");
                    }
                }
            });
            //st_attend.setChecked(true);
            DatabaseReference atref = FirebaseDatabase.getInstance().getReference();
            // 25/10/2017 set method in eventlist class to transform string
            final String attendKey = Eventlist.toString(post.getKey(), currentUser.getAccountName());
            Query qat = atref.child(Eventlist.EVENTLIST).orderByKey().equalTo(attendKey);
            qat.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        //haven't attended
                        st_attend.setChecked(false);
                    } else {
                        //have attended
                        st_attend.setChecked(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //reset listener so to create or delete eventlist entry
            st_attend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        st_attend.setText(getResources().getString(R.string.Text_Attended));
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Eventlist.EVENTLIST);
                        //String key = ref.push().getKey();
                        e = new Eventlist(post.getKey(), currentUser.getAccountName());
                        DatabaseReference eRef = ref.child(attendKey);
                        eRef.setValue(e);
                        Log.d("ATTEND LIST POST 111", String.valueOf(e.getPostKey()));
                        //Toast.makeText(PostDetail_Activity.this, "Welcome!", Toast.LENGTH_LONG).show();
                    } else {
                        st_attend.setText(getResources().getString(R.string.Text_Attend));
                        DatabaseReference ref_d = FirebaseDatabase.getInstance().getReference(Eventlist.EVENTLIST).child(attendKey);
                        ref_d.setValue(null);
                        //Toast.makeText(PostDetail_Activity.this, "See you next time.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        iv_eventImage = findViewById(R.id.eventImage);
        ib_socIcon = (ImageView)findViewById(R.id.e_societyIcon);
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


        // 6/10/2017 imageview

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
                soc = dataSnapshot.getValue(Society.class);
                // TODO: 6/10/2017 soc icon
                String downloadUrl = soc.getLogo();
                Glide.with(PostDetail_Activity.this).load(downloadUrl).into(ib_socIcon);
                tv_socName.setText(soc.getSocietyName());
                ib_socIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PostDetail_Activity.this, SocProfileActivity.class);
                        // 24/10/2017  send soc to profile activity
                        intent.putExtra(Society.SOCIETY, soc);
                        startActivity(intent);
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
