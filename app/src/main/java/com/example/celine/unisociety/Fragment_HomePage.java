package com.example.celine.unisociety;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_HomePage extends Fragment {
    private CardView cv_newEvents;

    private RecyclerView recentEvent;
    private List<Post> recentEvents;

    private TextView tv_notification;
    private ProgressBar pb_circle;



    public Fragment_HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        cv_newEvents = v.findViewById(R.id.cv_home_events);
        tv_notification = v.findViewById(R.id.tv_home_notification);
        recentEvent = (RecyclerView) v.findViewById(R.id.rv_recentEvent);

        // TODO: 4/10/2017 retrieve events
        /*PostAdapter postAdapter = new PostAdapter(Fragment_HomePage.this.getContext(), recentEvents);
        recentEvent.setAdapter(postAdapter);

        // TODO: 4/10/2017 if no records, set notification into visible;
        //tv_notification.setVisibility(View.VISIBLE);

        // TODO: 10/09/2017 sorting
        Collections.sort(recentEvents, new Comparator<Post>() {
                    @Override
                    public int compare(Post p1, Post p2) {
                        StringBuilder sb1 = new StringBuilder(p1.getPostDate());
                        sb1.append(p1.getBeginTime());
                        StringBuilder sb2 = new StringBuilder(p2.getPostDate());
                        sb2.append(p2.getBeginTime());
                        String str1 = sb1.toString();
                        String str2 = sb2.toString();
                        return str1.compareToIgnoreCase(str2);
                    }
                });
*/

        return v;
    }
}
