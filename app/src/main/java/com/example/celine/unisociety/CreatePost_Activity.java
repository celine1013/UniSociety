package com.example.celine.unisociety;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;

public class CreatePost_Activity extends AppCompatActivity {
    private int societyID;
    private String eventTitle;
    private String eventDes;
    private int eventCategory;
    private Date eventDate;
    private Timestamp startingTime;
    private Timestamp endingTime;
    private String eventLocation;

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
        //create a post
        //get all the input
        // TODO: 9/09/2017 deal with the data and time
        //upload the new post to the database
        //show success message
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        //finish the activity
        finish();

    }
}
