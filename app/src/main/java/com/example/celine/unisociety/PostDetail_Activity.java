package com.example.celine.unisociety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Model.Post;

public class PostDetail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);


        Post post = getIntent().getExtras().getParcelable(PostAdapter.POST_KEY);
        /*if (post != null) {
            //Toast.makeText(this, "Received item " + item.getItemName(),
                    //Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }*/
    }
}
