package com.example.celine.unisociety;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Model.Account;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountManagement_Student extends Fragment {

    private Button btn_attend;
    private Button btn_changePassword;

    public AccountManagement_Student() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_account_management_student, container, false);

        btn_attend = v.findViewById(R.id.btn_Notification);
        btn_changePassword = v.findViewById(R.id.btn_forget_password);

        btn_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountManagement_Student.this.getActivity(), AttendListActivity.class);
                intent.putExtra(Account.ACCOUNT, MainActivity.currentUser);
                startActivity(intent);
            }
        });
        return v;
    }

}
