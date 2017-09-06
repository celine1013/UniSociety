package com.example.celine.unisociety;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import Model.Account;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_LOG_IN = 0;

    private Account currentUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_category:
                    return true;
                case R.id.navigation_date:
                    return true;
                case R.id.navigation_account:
                    if (currentUser != null) {
                        Intent intent = new Intent(MainActivity.this, LogIn_Activity.class);
                        startActivityForResult(intent, REQUEST_CODE_LOG_IN);
                    } else {
                        Log.d("FRAGMENT", "GOING TO ACCOUNT MANAGEMENT");
                        AccountManagement_Society accountManagementS = new AccountManagement_Society();
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.content, accountManagementS, accountManagementS.getTag()).commit();
                        Log.d("FRAGMENT", "GONE TO ACCOUNT MANAGEMENT");
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_OK)return;
        if(requestCode == REQUEST_CODE_LOG_IN){
            if(data == null)return;
            currentUser = LogIn_Activity.getAccount(data);
        }
    }


}
