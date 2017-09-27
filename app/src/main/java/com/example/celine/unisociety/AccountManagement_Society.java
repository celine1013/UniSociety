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

        setProfileButtonOnClick(view);
        setButtonChangePassword(view);
        setButtonManagePosts(view);
        setButtonEditProfile(view);
        return view;
    }

    private void setProfileButtonOnClick(View view) {

    }

    private void setButtonChangePassword(View view) {

    }

    private void setButtonManagePosts(View view) {
        /*Intent intent = new Intent(getActivity(), PostHistoryActivity.class);
        intent.putExtra(Society.SOCIETY_ID, this.societyID);
        startActivity(intent);*/
    }

    private void setButtonEditProfile(View view) {

    }
}