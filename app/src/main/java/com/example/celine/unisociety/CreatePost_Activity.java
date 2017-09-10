package com.example.celine.unisociety;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;

import Model.Post;
import dbhelper.dbhelper;

public class CreatePost_Activity extends AppCompatActivity {
    private int societyID;

    private EditText et_eventTitle;
    private EditText et_eventDes;
    private Spinner sp_eventCategory;
    private EditText et_eventDate;
    private EditText et_startingTime;
    private EditText et_endingTime;
    private EditText et_eventLocation;

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
        et_eventTitle = (EditText) findViewById(R.id.title);
        et_eventDes = (EditText) findViewById(R.id.description);
        sp_eventCategory = (Spinner) findViewById(R.id.category);
        et_eventDate = (EditText) findViewById(R.id.date);
        et_startingTime = (EditText) findViewById(R.id.et_startTime);
        et_endingTime = (EditText) findViewById(R.id.et_endTime);
        et_eventLocation = (EditText) findViewById(R.id.location);

        //set category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_eventCategory.setAdapter(adapter);

        //set submit button
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a post
                Post newPost = new Post();
                newPost.setAvailable(true);


                //get all the input
                // TODO: 9/09/2017  if the editText is empty, notify user
                if (et_eventTitle.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setPostTitle(et_eventTitle.getText().toString());
                }

                if (et_eventDes.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setPostDescription(et_eventDes.getText().toString());
                }

                if (et_eventLocation.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setLocation(et_eventLocation.getText().toString());
                }

                if (et_eventDate.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    // TODO: 9/09/2017 deal with the data and time
                }
                if (et_startingTime.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    // TODO: 9/09/2017 deal with the data and time
                }
                if (et_endingTime.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    // TODO: 9/09/2017 deal with the data and time
                }

                if (sp_eventCategory.getSelectedItemPosition() == -1) {
                    Toast.makeText(CreatePost_Activity.this, "Please enter all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setEventCategory(sp_eventCategory.getSelectedItemPosition());
                }

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
