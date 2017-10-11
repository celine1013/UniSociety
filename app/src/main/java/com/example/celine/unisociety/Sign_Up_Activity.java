package com.example.celine.unisociety;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import Model.Account;
import Model.Society;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Sign_Up_Activity extends AppCompatActivity {
    //Radio button

    //spinner
    private Spinner societyList;
    //EditTexts
    private EditText et_userName;
    private EditText et_password;
    private EditText et_passwordConfirm;
    private EditText et_securityCode;
    private EditText et_securityQues;

    //SignUp Button
    private Button btn_SignUp;

    private RadioGroup userType;
    private RadioButton userSelected;
    private int currentChecked;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_society);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Account.ACCOUNT);


        //set the spinner
        societyList = (Spinner)findViewById(R.id.society_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.societies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        societyList.setAdapter(adapter);

        et_userName = (EditText)findViewById(R.id.signup_user_name);
        et_password = (EditText)findViewById(R.id.signup_password);
        et_passwordConfirm = (EditText)findViewById(R.id.Confirm_Password);
        et_securityCode = (EditText)findViewById(R.id.security_code);
        et_securityQues = (EditText)findViewById(R.id.Sequrity_question);

        btn_SignUp = (Button)findViewById(R.id.btn_sign_up);

        //change layout based on usertype

        userType = (RadioGroup)findViewById(R.id.RG_UserType);
        userType.clearCheck();
        userType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int selectedID) {
                Log.d("chk", "id" + selectedID);

                if (selectedID == R.id.RB_society) {
                    societyList.setVisibility(View.VISIBLE);
                    et_securityCode.setVisibility(View.VISIBLE);
                    currentChecked = R.id.RB_society;

                } else if (selectedID == R.id.RB_student) {
                    //hide societyList, securityCode field if it's student
                    societyList.setVisibility(View.GONE);
                    et_securityCode.setVisibility(View.GONE);
                    currentChecked = R.id.RB_student;
                }

            }
        });
        userSelected = (RadioButton) findViewById(R.id.RB_society);
        userSelected.setChecked(true);

        //Sign up
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get input
                Account newUser = new Account();
                String userName = et_userName.getText().toString();
                String password = et_password.getText().toString();
                String passwordComfirm = et_passwordConfirm.getText().toString();
                String secQuestion = et_securityQues.getText().toString();

                // TODO: 5/09/2017 invalid input handling e.g empty editTextfield
                //check if the account exist
                /*if (db.hasExisted(userName)) {
                    Toast.makeText(Sign_Up_Activity.this, "This account has already existed.", Toast.LENGTH_LONG).show();
                    et_userName.setText("");
                    et_password.setText("");
                    et_passwordConfirm.setText("");
                    return;
                }*/
                //confirm password
                if (!password.equals(passwordComfirm)) {
                    Toast.makeText(Sign_Up_Activity.this, "Please reconfirm password!", Toast.LENGTH_LONG).show();
                    et_password.setText("");
                    et_passwordConfirm.setText("");
                    return;
                }

                newUser.setAccountName(userName);
                newUser.setPassword(password);
                newUser.setSecurityQuestion(secQuestion);

                if (currentChecked == R.id.RB_student) {
                    //register normal user
                    newUser.setId(0);
                    registerNormalUser(newUser);
                } else if (currentChecked == R.id.RB_society) {
                    //register soc user
                    int societyID = societyList.getSelectedItemPosition() + 1; //society id start from 1
                    Log.d("SIGN UP_SOC ID" ,String.valueOf(societyID) );
                    newUser.setId(societyID);
                    registerSocietyUser(newUser);
                } else {
                    Log.e("ERROR", "USER SELECTED FAILED");
                }
                //show success notification
                //Toast.makeText(Sign_Up_Activity.this, "Sign Up Succeed", Toast.LENGTH_LONG).show();
                //go back to login page

            }
        });

    }

    private void registerSocietyUser(Account u) {
        /*final int uid_val = userID;
        final String userName_val = u.getAccountName();
        final String password_val = u.getPassword();
        final String secQuestion_val = u.getSecurityQuestion();

        DatabaseReference registerNewUser = mDatabase.push();

        registerNewUser.child("Username").setValue(userName_val);
        registerNewUser.child("Password").setValue(password_val);
        registerNewUser.child("SecQuestion").setValue(secQuestion_val);*/
        // TODO: 13/09/2017 userid push

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        final String v = et_securityCode.getText().toString();
        final Account newUser = u;
        com.google.firebase.database.Query query = mRef.child(Society.SOCIETY).orderByChild(Society.SOCIETY_ID).equalTo(u.getId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    //IF NO SOCIETY FOUND
                    Toast.makeText(Sign_Up_Activity.this, "Unknown Error", Toast.LENGTH_LONG).show();
                    Log.d("SIGN UP", "CANNOT FIND SOCIETY");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //GET SOCIETY
                    Society society = dataSnapshot.getValue(Society.class);

                    if (!v.equals(society.getVerificationCode())) {
                        //IF VERIFICATION FAILED
                        et_securityCode.setText("");
                        Toast.makeText(Sign_Up_Activity.this, "Verification Failed", Toast.LENGTH_LONG).show();
                    } else {
                        //IF VERIFICATION SUCCESS
                        Log.v("SIGN UP", "VERIFICATION SUCCEED");
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                        Query q = ref2.child(Account.ACCOUNT).orderByChild(Account.ACCOUNT_ACCOUNT_NAME).equalTo(newUser.getAccountName());
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue() == null){
                                    //IF ACCOUNT NOT YET CREATED
                                    DatabaseReference usersRef = mDatabase.child(mDatabase.push().getKey());
                                    usersRef.setValue(newUser);
                                    Toast.makeText(Sign_Up_Activity.this, "Sign Up Succeed", Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    //IF ACCOUNT ALREADY EXISTS
                                    et_userName.setText("");
                                    et_password.setText("");
                                    et_passwordConfirm.setText("");
                                    Toast.makeText(Sign_Up_Activity.this, "Account already exists.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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

    private void registerNormalUser(Account u){
        final Account newUser = u;
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
        Query q = ref2.child(Account.ACCOUNT).orderByChild(Account.ACCOUNT_ACCOUNT_NAME).equalTo(newUser.getAccountName());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    //IF ACCOUNT NOT YET CREATED
                    DatabaseReference usersRef = mDatabase.child(mDatabase.push().getKey());
                    usersRef.setValue(newUser);
                    Toast.makeText(Sign_Up_Activity.this, "Sign Up Succeed", Toast.LENGTH_LONG).show();
                    Log.v("SIGN UP", "NORMAL USER CREATED");
                    finish();
                }else{
                    //IF ACCOUNT ALREADY EXISTS
                    et_userName.setText("");
                    et_password.setText("");
                    et_passwordConfirm.setText("");
                    Toast.makeText(Sign_Up_Activity.this, "Account already exists.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}


