package com.example.celine.unisociety;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import Model.Post;
import Model.Society;

import android.widget.Button;
import android.widget.ProgressBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private Button mButton;
    private ProgressBar mProgressBar;
    public BottomNavigationView navigation;
    public static final String CURRENT_USER = "CURRENT_USER";

    public static final int STUDENT = 0;
    private static final int REQUEST_CODE_LOG_IN = 0;

    public static Account currentUser;

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
                            // TODO: 4/10/2017 pass current user to new activity 
                            AccountManagement_Student accountManagementStu = new AccountManagement_Student();
                            manager.beginTransaction().replace(R.id.content, accountManagementStu, accountManagementStu.getTag()).commit();
                            Log.d("FRAGMENT", "GONE TO STUDENT ACCOUNT MANAGEMENT");
                        } else {

                            Log.d("FRAGMENT", "GOING TO SOCIETY ACCOUNT MANAGEMENT");
                            AccountManagement_Society accountManagementS = new AccountManagement_Society();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(MainActivity.CURRENT_USER, currentUser);
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


        //TEMP PRELOADING SOCIETIES
        /*Society s1 = new Society(1, "BITSA", "RANDOM SOMETHING FOR BITSA", 3,
                "bitsa@unsw.edu.au", "Emily", "0000000000", "exampleFacebook.com","logo",
                true, "bitsa");

        Society s2 = new Society(2, "WIT", "RANDOM SOMETHING FOR WIT", 3,
                "WIT@unsw.edu.au", "Silvia", "0000000002", "exampleFacebook.com2","logo2",
                true, "wit");

        Society s3 = new Society(3, "ADMIN", "RANDOM SOMETHING FOR ADMIN", 1,
                "ADMIN@unsw.edu.au", "Celine", "not going to tell you :)", "exampleFacebook.com3","logo3",
                true, "admin");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Society.SOCIETY);
        DatabaseReference ref2 = ref.child(ref.push().getKey());
        ref2.setValue(s1);
        DatabaseReference ref3 = ref.child(ref.push().getKey());
        ref3.setValue(s2);
        DatabaseReference ref4 = ref.child(ref.push().getKey());
        ref4.setValue(s3);*/


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

    public Account getCurrentUser() {
        return currentUser;
    }
}

