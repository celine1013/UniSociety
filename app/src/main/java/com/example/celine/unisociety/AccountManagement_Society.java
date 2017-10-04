package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import Model.Society;


public class AccountManagement_Society extends Fragment {

    private int societyID;
    private ImageButton profileButton;
    private Button bt_changePassword;
    private Button bt_managePosts;
    private Button bt_editProfile;

    public AccountManagement_Society() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        societyID = this.getArguments().getInt(Society.SOCIETY_ID, -1);
        View view = inflater.inflate(R.layout.fragment_account_management__society, container, false);
        profileButton = (ImageButton)view.findViewById(R.id.IB_SocietyProfilePic);
        bt_editProfile = view.findViewById(R.id.btn_edit_profile);
        bt_changePassword = view.findViewById(R.id.btn_change_password);
        bt_managePosts = view.findViewById(R.id.btn_manage_history);

        setProfileButtonOnClick(view);
        setButtonChangePassword(view);
        setButtonManagePosts(view, this.societyID);
        setButtonEditProfile(view);
        return view;
    }

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

    private void setButtonManagePosts(View view, final int societyID) {
        bt_managePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostHistoryActivity.class);
                intent.putExtra(Society.SOCIETY_ID, societyID);
                startActivity(intent);
            }
        });
        /**/
    }

    private void setButtonEditProfile(View view) {
        bt_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}