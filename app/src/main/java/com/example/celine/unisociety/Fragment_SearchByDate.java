package com.example.celine.unisociety;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
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
    private TextView tv_date;
    private Button bt_chooseDate;
    private Button bt_searchEvDate;

    public Fragment_SearchByDate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_by_date, container, false);
        //binding
        tv_date = v.findViewById(R.id.tv_DateChose);
        bt_chooseDate = v.findViewById(R.id.bt_chooseDate);
        bt_searchEvDate = v.findViewById(R.id.bt_searchDate);

        events = new ArrayList<>();

        PostAdapter postAdapter = new PostAdapter(this.getActivity(), events);
        eventRecycler = v.findViewById(R.id.rv_eventByDate);
        eventRecycler.setAdapter(postAdapter);
        bt_chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        bt_searchEvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = tv_date.getText().toString();
                // TODO: 11/09/2017  events = getPostByDate(date)
            }
        });
        /**/
        return v;
    }
    private void showDatePickerDialog() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd;
        dpd = new DatePickerDialog(this.getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tv_date.setText(year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
}
