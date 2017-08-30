package com.example.celine.unisociety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_society);

        //binding
        btn_SignUp = (Button)findViewById(R.id.btn_sign_up);
        //change layout based on usertype
        //hide societyList, securityCode field if it's student

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sign up
                //get the input
                //check if the society match the securityCode
                //check if the account exists
                //check if password confirmed
                //sign up

                //show success notification
                Toast.makeText(Sign_Up_Activity.this, "Sign Up Succeed", Toast.LENGTH_LONG);
                //go back to login page
                finish();
            }
        });

    }
}
