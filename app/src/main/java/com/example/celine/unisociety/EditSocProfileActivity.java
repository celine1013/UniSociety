package com.example.celine.unisociety;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import Model.Post;
import Model.Society;

public class EditSocProfileActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT = 1;

    private int socId;
    private Society soc;
    private String socKey;

    private ImageView socIcon;
    private TextView socName;
    private Spinner socCate;
    private TextView socDesc;
    private TextView socFb;
    private TextView contactPerson;
    private TextView socMail;
    private Button btn_sumbit;
    private ImageButton bt_upload;

    private StorageReference mStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_soc_profile);

        socIcon = findViewById(R.id.epf_socIcon);
        socName = findViewById(R.id.epf_soc_name);
        socCate = findViewById(R.id.epf_category);
        socDesc = findViewById(R.id.epf_soc_dec);
        socFb = findViewById(R.id.epf_soc_fb);
        contactPerson = findViewById(R.id.epf_contact_person);
        socMail = findViewById(R.id.epf_contactContent);
        btn_sumbit = findViewById(R.id.epf_btnSubmit);
        bt_upload = findViewById(R.id.epf_ib_upload);

        mStorage = FirebaseStorage.getInstance().getReference();

        // 24/10/2017 get society
        socId = getIntent().getIntExtra(Society.SOCIETY_ID, -1);
        if(socId == -1)Log.e("EDIT PROFILE", "UNKNOWN USER");
        final  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child(Society.SOCIETY).orderByChild(Society.SOCIETY_ID).equalTo(socId);
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                soc = dataSnapshot.getValue(Society.class);
                socKey = dataSnapshot.getKey();

                // 24/10/2017 get soc detail on layout
                socName.setText(soc.getSocietyName());
                String[] str = getResources().getStringArray(R.array.soc_cate);

                socDesc.setText(soc.getSocietyDescription());
                String fbLink = soc.getFacebook();
                socFb.setText(fbLink);
                contactPerson.setText(soc.getContactPerson());
                socMail.setText(soc.getEmailAddress());
                // 24/10/2017 load icon
                Glide.with(EditSocProfileActivity.this).load(soc.getLogo()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(socIcon);

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



        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });

        btn_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soc.setSocietyName(socName.getText().toString());
                soc.setSocietyCategory(socCate.getSelectedItemPosition());
                soc.setSocietyDescription(socDesc.getText().toString());
                soc.setFacebook(socFb.getText().toString());
                soc.setContactPerson(contactPerson.getText().toString());
                soc.setEmailAddress(socMail.getText().toString());

                DatabaseReference ref_e = FirebaseDatabase.getInstance().getReference(Society.SOCIETY);
                Map<String, Object> socUpdates = new HashMap<String, Object>();
                socUpdates.put(socKey, soc);
                ref_e.updateChildren(socUpdates);
                Log.v("EDIT SOC PROFILE", "PROFILE UPDATED");
                Toast.makeText(EditSocProfileActivity.this, "Profile Updated.", Toast.LENGTH_LONG).show();
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
                    Glide.with(EditSocProfileActivity.this).load(downloadUrl).listener(new RequestListener<Uri, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                            socIcon.setImageResource(android.R.drawable.ic_menu_gallery);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    }).into(socIcon);
                    // Glide.with(CreatePost_Activity.this).using(new FirebaseImageLoader()).load(filepath).into(bt_selectPicture);
                    soc.setLogo(downloadUrl.toString());
                    Toast.makeText(EditSocProfileActivity.this, "Upload Done.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
