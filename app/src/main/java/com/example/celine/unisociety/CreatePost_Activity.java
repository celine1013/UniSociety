package com.example.celine.unisociety;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;

import Model.Post;
import dbhelper.dbhelper;

public class CreatePost_Activity extends AppCompatActivity {
    private int societyID;
    private String eventTitle;
    private String eventDes;
    private int eventCategory;
    private Date eventDate;
    private Timestamp startingTime;
    private Timestamp endingTime;
    private String eventLocation;

    private Button bt_submit;
    private dbhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        societyID = getIntent().getIntExtra("USER_ID", -1);
        if (societyID == -1) {
            Toast.makeText(CreatePost_Activity.this, "unknown error", Toast.LENGTH_LONG);
            finish();
        }

        //binding
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a post
                Post newPost = new Post();
                //get all the input
                // TODO: 9/09/2017 deal with the data and time
                //upload the new post to the database
                db.createPost(societyID, newPost);
                //show success message
                Snackbar.make(view, "Post Created!", Snackbar.LENGTH_LONG).show();
                // TODO: 9/09/2017 implement undo function using snackbar
                //finish the activity
                finish();
            }
        });


    }
}
