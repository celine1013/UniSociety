package com.example.celine.unisociety;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import Model.Account;
import Model.Post;

import static android.R.attr.data;

public class CreatePost_Activity extends AppCompatActivity {
    private Account currentUser = null;
    private String postType;
    private Post newPost;
    private Post prevPost;

    private EditText et_eventTitle;
    private EditText et_eventDes;
    private Spinner sp_eventCategory;
    private Button bt_eventDate;
    private Button bt_startingTime;
    private Button bt_endingTime;
    private EditText et_eventLocation;
    private ImageView iv_selectPicture;
    private ImageButton bt_upload;
    private Button bt_delete;
    private ProgressBar pb_loading;

    private static final int GALLERY_INTENT = 1;

    private Button bt_submit;

    private Uri mImageUri;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        currentUser = getIntent().getParcelableExtra(MainActivity.CURRENT_USER);
        if (currentUser == null) {
            Toast.makeText(CreatePost_Activity.this, "unknown error", Toast.LENGTH_LONG);
            finish();
        }

        mStorage = FirebaseStorage.getInstance().getReference();

        //binding
        et_eventTitle = (EditText) findViewById(R.id.c_et_eventTitle);
        et_eventDes = (EditText) findViewById(R.id.c_et_desc);
        sp_eventCategory = (Spinner) findViewById(R.id.c_sp_category);
        bt_eventDate = (Button) findViewById(R.id.c_bt_date);
        bt_startingTime = (Button) findViewById(R.id.c_bt_time);
        bt_endingTime = (Button) findViewById(R.id.c_bt_time2);
        et_eventLocation = (EditText) findViewById(R.id.c_et_location);
        iv_selectPicture = findViewById(R.id.c_eventImage);
        bt_submit = (Button) findViewById(R.id.c_btn_submit);
        bt_delete = findViewById(R.id.c_btn_delete);
        bt_upload = findViewById(R.id.c_ib_upload);
        pb_loading = findViewById(R.id.c_pb_imageLoading);


        //set category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_eventCategory.setAdapter(adapter);
        //set time buttons
        setEventTimeBTS();
        newPost = new Post();
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               selectPicture();

            }
        });

        //show the data if it's editing existing post
        Intent i = this.getIntent();
        postType = i.getStringExtra(PostHistoryActivity.POST_TYPE);
        Log.d("CREATE POST", postType);

        if (postType.equals(PostHistoryActivity.EDIT_POST)) {
            //show all data;
            bt_delete.setVisibility(View.VISIBLE);
            prevPost = this.getIntent().getParcelableExtra(Post.POST);
            newPost.setKey(prevPost.getKey());
            newPost.setImageUrl(prevPost.getImageUrl());
            pb_loading.setVisibility(View.VISIBLE);


            et_eventTitle.setText(prevPost.getPostTitle());
            et_eventLocation.setText(prevPost.getLocation());
            et_eventDes.setText(prevPost.getPostDescription());
            bt_eventDate.setText(prevPost.getPostDate());
            bt_startingTime.setText(prevPost.getBeginTime());
            bt_endingTime.setText(prevPost.getEndTime());
            sp_eventCategory.setSelection(prevPost.getEventCategory());
            String downloadUrl = prevPost.getImageUrl();

            Glide.with(CreatePost_Activity.this).load(downloadUrl).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    pb_loading.setVisibility(View.INVISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    pb_loading.setVisibility(View.INVISIBLE);
                    return false;
                }
            }).into(iv_selectPicture);
        }

        //set submit button
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a post
               // Post newPost = new Post();
                newPost.setAvailable(true);
                newPost.setId(currentUser.getId());

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
                if (sp_eventCategory.getSelectedItemPosition() == -1) {
                    Toast.makeText(CreatePost_Activity.this, R.string.Text_Enter_All_Fields, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newPost.setEventCategory(sp_eventCategory.getSelectedItemPosition());
                }
                newPost.setPostDate(bt_eventDate.getText().toString());
                newPost.setBeginTime(bt_startingTime.getText().toString());
                newPost.setEndTime(bt_endingTime.getText().toString());

                //upload the new post to the database
                // 23/09/2017 create or update the post
                switch (postType) {
                    case PostHistoryActivity.EDIT_POST:
                        DatabaseReference ref_e = FirebaseDatabase.getInstance().getReference(Post.POST);
                        Map<String, Object> postUpdates = new HashMap<String, Object>();
                        postUpdates.put(newPost.getKey(), newPost);
                        ref_e.updateChildren(postUpdates);
                        Log.v("CREATE POST", "POST UPDATED");
                        Toast.makeText(CreatePost_Activity.this, "Post Updated.", Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case PostHistoryActivity.NEW_POST:
                        //create new post
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Post.POST);
                        String key = ref.push().getKey();
                        newPost.setKey(key);
                        DatabaseReference postRef = ref.child(key);
                        postRef.setValue(newPost);
                        Snackbar.make(view, "Post Created!", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(CreatePost_Activity.this, "Post Created.", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref_d = FirebaseDatabase.getInstance().getReference(Post.POST)
                        .child(prevPost.getKey());
                ref_d.setValue(null);
                Toast.makeText(CreatePost_Activity.this, "Post Deleted.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void selectPicture() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode ==RESULT_OK) {
            pb_loading.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            Log.d("Uri", uri.toString());
            final StorageReference filepath = mStorage.child("Post_Images").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.d("downloadUrl", downloadUrl.toString());
                   // bt_selectPicture.setImageURI(downloadUrl);
                    Glide.with(CreatePost_Activity.this).load(downloadUrl).listener(new RequestListener<Uri, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            iv_selectPicture.setImageResource(android.R.drawable.ic_menu_gallery);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pb_loading.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(iv_selectPicture);
                    // Glide.with(CreatePost_Activity.this).using(new FirebaseImageLoader()).load(filepath).into(bt_selectPicture);
                    newPost.setImageUrl(downloadUrl.toString());
                    Toast.makeText(CreatePost_Activity.this, "Upload Done.", Toast.LENGTH_LONG).show();
                }
            });
        }
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
                        bt_endingTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }
}
