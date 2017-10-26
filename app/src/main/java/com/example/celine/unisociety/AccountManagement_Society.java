package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Model.Account;
import Model.Eventlist;
import Model.Society;


public class AccountManagement_Society extends Fragment {

    private Account currentUser = null;
    private ImageButton profileButton;
    private Button bt_changePassword;
    private Button bt_managePosts;
    private Button bt_editProfile;

    private Society soc;

    public AccountManagement_Society() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_management__society, container, false);
        currentUser = this.getArguments().getParcelable(MainActivity.CURRENT_USER);

        profileButton = (ImageButton)view.findViewById(R.id.IB_SocietyProfilePic);
        bt_editProfile = view.findViewById(R.id.btn_edit_profile);
        bt_changePassword = view.findViewById(R.id.btn_change_password);
        bt_managePosts = view.findViewById(R.id.btn_manage_history);

        final  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child(Society.SOCIETY).orderByChild(Society.SOCIETY_ID).equalTo(currentUser.getId());
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                soc = dataSnapshot.getValue(Society.class);
                String downloadUrl = soc.getLogo();
                Glide.with(AccountManagement_Society.this).load(downloadUrl).into(profileButton);
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

        setProfileButtonOnClick(view);
        setButtonChangePassword(view);
        setButtonManagePosts(view);
        setButtonEditProfile(view);
        return view;
    }

    // TODO: 24/10/2017  do we need this?
    private void setProfileButtonOnClick(View view) {
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setButtonChangePassword(View view) {
        bt_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setButtonManagePosts(View view) {
        bt_managePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountManagement_Society.this.getActivity(), PostHistoryActivity.class);
                intent.putExtra(MainActivity.CURRENT_USER, currentUser);
                startActivity(intent);
            }
        });
        /**/
    }

    private void setButtonEditProfile(View view) {
        bt_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send soc ID to editProfileActivity
            }
        });

    }
}