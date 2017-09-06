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
import dbhelper.dbhelper;

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

    private dbhelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_society);

        //set the spinner
        societyList = (Spinner)findViewById(R.id.society_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.societies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        societyList.setAdapter(adapter);

        et_userName = (EditText)findViewById(R.id.ET_user_name);
        et_password = (EditText)findViewById(R.id.ET_password);
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

                } else if (selectedID == R.id.RB_student) {
                    //hide societyList, securityCode field if it's student
                    societyList.setVisibility(View.GONE);
                    et_securityCode.setVisibility(View.GONE);
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
                if (db.hasExisted(userName)) {
                    Toast.makeText(Sign_Up_Activity.this, "This account has already existed.", Toast.LENGTH_LONG).show();
                    et_userName.setText("");
                    et_password.setText("");
                    et_passwordConfirm.setText("");
                    return;
                }
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
                if (userSelected.getId() == R.id.RB_student) {
                    //register normal user
                    db.registerNormalUser(0, newUser); //student id == 0
                    // TODO: 5/09/2017 auto generate userID????
                } else if (userSelected.getId() == R.id.RB_society) {
                    //register soc user
                    int societyID = societyList.getSelectedItemPosition() + 1; //society id start from 1
                    String verificationCode = et_securityCode.getText().toString();
                    if (!db.verifySocIdentity(societyID, verificationCode)) {
                        et_securityCode.setText("");
                        Toast.makeText(Sign_Up_Activity.this, "The verification code is incorrect!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    db.registerNewSocUser(societyID, newUser);

                } else {
                    Log.e("ERROR", "USER SELECTED FAILED");
                }

                //show success notification
                Toast.makeText(Sign_Up_Activity.this, "Sign Up Succeed", Toast.LENGTH_LONG);
                //go back to login page
                finish();
            }
        });

    }

}
