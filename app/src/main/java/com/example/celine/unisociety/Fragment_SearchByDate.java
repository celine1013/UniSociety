package com.example.celine.unisociety;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Model.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SearchByDate extends Fragment {

    // TODO: 9/09/2017 datepicker customization
    // TODO: 9/09/2017 listview popping
    private RecyclerView eventRecycler;
    private List<Post> events;

    public Fragment_SearchByDate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_by_date, container, false);

        /*PostAdapter postAdapter = new PostAdapter(this.getActivity(), events);
        eventRecycler = (RecyclerView) v.findViewById(R.id.rv_eventByDate);
        eventRecycler.setAdapter(postAdapter);*/
        return v;
    }

}
