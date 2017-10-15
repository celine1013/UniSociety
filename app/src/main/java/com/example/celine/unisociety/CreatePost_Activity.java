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

import javax.sql.DataSource;

import Model.Account;
import Model.Post;

import static android.R.attr.data;

public class CreatePost_Activity extends AppCompatActivity {
    public static final String USER_ID = "user_id";
    public static final String POST_CONTENT = "POST_CONTENT";
    private Account currentUser = null;
    private String postType;
    private Post newPost;

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
                pb_loading.setVisibility(View.VISIBLE);

            }
        });

        //show the data if it's editing existing post
        Intent i = this.getIntent();
        postType = i.getStringExtra(PostHistoryActivity.POST_TYPE);
        Log.d("CREATE POST", postType);
        if (postType.equals(PostHistoryActivity.EDIT_POST)) {
            //show all data;
            Post p = this.getIntent().getParcelableExtra(CreatePost_Activity.POST_CONTENT);
            Toast.makeText(CreatePost_Activity.this, postType, Toast.LENGTH_LONG).show();
            // TODO: 23/09/2017 push the info to the interface
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

                //upload the new post to the database
                // TODO: 23/09/2017 create or update the post
                switch (postType) {
                    case PostHistoryActivity.EDIT_POST:
                        //update the post
                        break;

                    case PostHistoryActivity.NEW_POST:
                        //create new post
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Post.POST);
                        newPost.setKey(ref.push().getKey());
                        DatabaseReference postRef = ref.child(ref.push().getKey());
                        postRef.setValue(newPost);
                        Toast.makeText(CreatePost_Activity.this, "New Post Created.", Toast.LENGTH_LONG).show();
                        Log.v("CREATE POST", "NEW POST CREATED");
                        finish();
                        break;

                    default:
                }

                //show success message
                Snackbar.make(view, "Post Created!", Snackbar.LENGTH_LONG).show();
                // TODO: 9/09/2017 implement undo function using snackbar
                //finish the activity
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
