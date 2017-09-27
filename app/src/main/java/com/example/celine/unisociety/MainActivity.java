package com.example.celine.unisociety;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import Model.Account;

import android.widget.Button;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private Button mButton;
    private ProgressBar mProgressBar;
    public BottomNavigationView navigation;


    private static final int STUDENT = 0;
    private static final int REQUEST_CODE_LOG_IN = 0;

    private Account currentUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("FRAGMENT", "GOING TO HOME PAGE");
                    Fragment_HomePage home = new Fragment_HomePage();
                    manager.beginTransaction().replace(R.id.content, home).commit();
                    Log.d("FRAGMENT", "GONE TO HOME PAGE");
                    return true;

                case R.id.navigation_category:
                    Log.d("FRAGMENT", "GOING TO CATEGORY MAIN PAGE");
                    Fragment_CategoryMain categoryMain = new Fragment_CategoryMain();
                    manager.beginTransaction().replace(R.id.content, categoryMain).commit();
                    Log.d("FRAGMENT", "GONE TO CATEGORY MAIN PAGE");
                    return true;

                case R.id.navigation_date:
                    Log.d("FRAGMENT", "GOING TO DATE");
                    Fragment_SearchByDate date = new Fragment_SearchByDate();
                    manager.beginTransaction().replace(R.id.content, date).commit();
                    Log.d("FRAGMENT", "GONE TO DATE");
                    return true;

                case R.id.navigation_account:
                    if (currentUser == null) {
                        Log.d("NEW ACTIVITY", "LOG IN");
                        Intent intent = new Intent(MainActivity.this, LogIn_Activity.class);
                        startActivityForResult(intent, REQUEST_CODE_LOG_IN);
                    } else {
                        if (currentUser.getId() == STUDENT) {
                            Log.d("FRAGMENT", "GOING TO STUDENT ACCOUNT MANAGEMENT");

                            Log.d("FRAGMENT", "GONE TO STUDENT ACCOUNT MANAGEMENT");
                        } else {

                            Log.d("FRAGMENT", "GOING TO SOCIETY ACCOUNT MANAGEMENT");
                            AccountManagement_Society accountManagementS = new AccountManagement_Society();
                            Bundle bundle = new Bundle();
                            bundle.putInt("societyID", currentUser.getId());
                            accountManagementS.setArguments(bundle);
                            manager.beginTransaction().replace(R.id.content, accountManagementS, accountManagementS.getTag()).commit();
                            Log.d("FRAGMENT", "GONE TO SOCIETY MANAGEMENT");
                        }
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
        //currentUser = null;
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_OK)return;
        if(requestCode == REQUEST_CODE_LOG_IN){
            if(data == null)return;
            currentUser = LogIn_Activity.getAccount(data);
            //after log-in, go to personal accountmanagement page;

        }
    }

    public BottomNavigationView getNavigation() {
        return navigation;
    }
}

