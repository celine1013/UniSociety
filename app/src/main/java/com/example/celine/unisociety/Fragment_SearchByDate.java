package com.example.celine.unisociety;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SearchByDate extends Fragment {

    // TODO: 9/09/2017 datepicker customization
    // TODO: 9/09/2017 listview popping


    public Fragment_SearchByDate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_date, container, false);
    }

}
