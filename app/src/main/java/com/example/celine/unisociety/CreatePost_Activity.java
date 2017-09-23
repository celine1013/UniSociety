package com.example.celine.unisociety;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import Model.Post;
import dbhelper.dbhelper;

public class CreatePost_Activity extends AppCompatActivity {
    private int societyID;
    private String postType;

    private EditText et_eventTitle;
    private EditText et_eventDes;
    private Spinner sp_eventCategory;
    private Button bt_eventDate;
    private Button bt_startingTime;
    private Button bt_endingTime;
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
        bt_eventDate = (Button) findViewById(R.id.bt_date);
        bt_startingTime = (Button) findViewById(R.id.bt_startTime);
        bt_endingTime = (Button) findViewById(R.id.bt_endTime);
        et_eventLocation = (EditText) findViewById(R.id.location);
        bt_submit = (Button)findViewById(R.id.btn_Submit);

        //set category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_eventCategory.setAdapter(adapter);
        //set time buttons
        setEventTimeBTS();

        //show the data if it's editing existing post
        postType = this.getIntent().getStringExtra("POST_TYPE");
        if(postType.equals(PostHistoryActivity.EDIT_POST)){
            //show all data;
            Post p = this.getIntent().getParcelableExtra(HistoryAdapter.POST_KEY);
            // TODO: 23/09/2017 push the info to the interface
        }

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
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setPostTitle(et_eventTitle.getText().toString());
                }

                if (et_eventDes.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setPostDescription(et_eventDes.getText().toString());
                }

                if (et_eventLocation.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setLocation(et_eventLocation.getText().toString());
                }

                if (bt_eventDate.getText().toString().equals(R.string.Text_Choose_Date)) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setPostDate(bt_eventDate.getText().toString());
                }
                if (bt_startingTime.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setBeginTime(bt_startingTime.getText().toString());

                }
                if (bt_endingTime.getText().toString().equals("")) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setEndTime(bt_endingTime.getText().toString());
                }

                if (sp_eventCategory.getSelectedItemPosition() == -1) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setEventCategory(sp_eventCategory.getSelectedItemPosition());
                }

                //upload the new post to the database
                // TODO: 23/09/2017 create or update the post
                //if()

                //show success message
                Snackbar.make(view, "Post Created!", Snackbar.LENGTH_LONG).show();
                // TODO: 9/09/2017 implement undo function using snackbar
                //finish the activity
                finish();
            }
        });




    }

    //ref.http://terryyamg.blogspot.com.au/2015/03/android-jump-datepicker-timepicker.html
    private void setEventTimeBTS() {
        bt_eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        bt_startingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartTimePickerDialog();
            }
        });
        bt_endingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndTimePickerDialog();
            }
        });

    }

    public void showDatePickerDialog() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        bt_eventDate.setText(year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void showStartTimePickerDialog() {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);


        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        bt_startingTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }

    public void showEndTimePickerDialog() {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        bt_startingTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }
}
