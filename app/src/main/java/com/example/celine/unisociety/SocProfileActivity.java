package com.example.celine.unisociety;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import Model.Society;

public class SocProfileActivity extends AppCompatActivity {

    private Society soc;

    private ImageView socIcon;
    private TextView socName;
    private TextView socCate;
    private TextView socDesc;
    private TextView socFb;
    private TextView contactPerson;
    private TextView socMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_profile);

        socIcon = findViewById(R.id.pf_soc_icon);
        socName = findViewById(R.id.pf_soc_name);
        socCate = findViewById(R.id.pf_category);
        socDesc = findViewById(R.id.pf_soc_dec);
        socFb = findViewById(R.id.pf_soc_fb);
        contactPerson = findViewById(R.id.pf_contact_person);
        socMail = findViewById(R.id.pf_contactContent);

        // 24/10/2017 get society
        soc = getIntent().getParcelableExtra(Society.SOCIETY);
        if(soc == null){
            Log.e("SOCIETY PROFILE", "NO SOCIETY FOUND");
            finish();
        }

        // 24/10/2017 get soc detail on layout
        socName.setText(soc.getSocietyName());
        String[] str = getResources().getStringArray(R.array.soc_cate);
        socCate.setText(str[soc.getSocietyCategory()]);
        socDesc.setText(soc.getSocietyDescription());
        final String fbLink = soc.getFacebook();
        //socFb.setText(fbLink);
        contactPerson.setText(soc.getContactPerson());
        socMail.setText(soc.getEmailAddress());
        // 24/10/2017 load icon
        Glide.with(SocProfileActivity.this).load(soc.getLogo()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(socIcon);
        // 24/10/2017  link the fb link to web-page done
        socFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbLink));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
