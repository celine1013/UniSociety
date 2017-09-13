package com.example.celine.unisociety;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Model.Post;

import dbhelper.dbhelper;
/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_HomePage extends Fragment {

    private RecyclerView recentEvent;
    private List<Post> recentEvents;
    private dbhelper db;

    public Fragment_HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);

        // TODO: 10/09/2017 db.getRecentEvent(); 
        recentEvents = new ArrayList<Post>();

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
        PostAdapter postAdapter = new PostAdapter(this.getActivity(), recentEvents);
        recentEvent = (RecyclerView) v.findViewById(R.id.rv_recentEvent);
        recentEvent.setAdapter(postAdapter);
        return v;
    }

}
