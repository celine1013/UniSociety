package com.example.celine.unisociety;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Account;



public class LogIn_Activity extends AppCompatActivity {

    private static final String CURRENT_USER = "com.example.celine.unisociety.currentUser";

    private EditText accountName_et;
    private EditText password_et;
    private Button btn_SignUp;
    private Button btn_logIn;
    private Button btn_forgetPassword;

    private DatabaseReference mRef;
    private Account currentUser;
    //private boolean logInCompleted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        mRef = FirebaseDatabase.getInstance().getReference("User");

        Log.d("NAVIGATION SETTING", "SETTING COMPLETED");
        accountName_et = (EditText)findViewById(R.id.ET_user_name);
        password_et = (EditText)findViewById(R.id.ET_password);

        btn_SignUp = (Button)findViewById(R.id.btn_sign_up);
        btn_logIn = (Button)findViewById((R.id.btn_login));
        btn_forgetPassword = (Button)findViewById(R.id.btn_forget_password);
        Log.d("LOGIN SETTING", "BINDING COMPLETED");
        setLogInBtn();
        Log.d("LOGIN SETTING", "LOG IN COMPLETED");
        setBtn_SignUp();
        Log.d("LOGIN SETTING", "SIGN UP COMPLETED");

    }


    private void setLogInResult(Account currentUser){
        Intent data = new Intent();
        data.putExtra(CURRENT_USER, currentUser);
        setResult(RESULT_OK, data);
    }

    public static Account getAccount(Intent result){
        return (Account)result.getParcelableExtra(CURRENT_USER);
    }

    private void setLogInBtn(){
        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the input
                String account = accountName_et.getText().toString();
                String password = password_et.getText().toString();
                //check the database
                checkLogIn(account, password);
            }

        });
    }

    private void setBtn_SignUp(){
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn_Activity.this, Sign_Up_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void setBtn_forgetPassword(){

    }

    private void checkLogIn(String a, String p) {

        mRef = FirebaseDatabase.getInstance().getReference();
        final String p2 = p;
        com.google.firebase.database.Query query = mRef.child(Account.ACCOUNT).orderByChild(Account.ACCOUNT_ACCOUNT_NAME).equalTo(a);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Log.d("LOG IN", "NULL USER");
                    accountName_et.setText("");
                    password_et.setText("");
                    Toast.makeText(LogIn_Activity.this, "Log In Failed, no user existed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    currentUser = dataSnapshot.getValue(Account.class);
                    Log.d("LOG IN", currentUser.getAccountName());

                    if (!p2.equals(currentUser.getPassword())) {
                        Toast.makeText(LogIn_Activity.this, "Log In Failed", Toast.LENGTH_LONG).show();

                        accountName_et.setText("");
                        password_et.setText("");
                    } else {
                        setLogInResult(currentUser);
                        //show toast of log-in success
                        Toast.makeText(LogIn_Activity.this, "Log In Succeed", Toast.LENGTH_LONG).show();
                        //go back to main page;
                        finish();
                    }

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
        }



}
