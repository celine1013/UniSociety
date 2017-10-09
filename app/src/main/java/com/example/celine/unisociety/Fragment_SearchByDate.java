package com.example.celine.unisociety;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    private RecyclerView eventRecycler;;
    private TextView tv_date;
    private Button bt_chooseDate;
    private Button bt_searchEvDate;
    private TextView tv_note;

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
        eventRecycler = v.findViewById(R.id.rv_eventByDate);
        tv_note = v.findViewById(R.id.notification_pageDate);
        bt_chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        bt_searchEvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(Post.POST);
                Query q = postRef.orderByChild(Post.POST_DATE).equalTo(tv_date.getText().toString());
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            tv_note.setVisibility(View.VISIBLE);
                        }else{
                            tv_note.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                FirebaseRecyclerAdapter adapter2 = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,
                        R.layout.postlist_item, PostViewHolder.class, q) {
                    @Override
                    protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, int position) {

                        viewHolder.postView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Fragment_SearchByDate.this.getActivity(), PostDetail_Activity.class);
                                intent.putExtra(MainActivity.CURRENT_USER, MainActivity.currentUser);
                                intent.putExtra(Post.POST, model);
                                startActivity(intent);
                            }
                        });

                        viewHolder.setPost(model);

                    }
                };

                eventRecycler.setAdapter(adapter2);
            }
        });


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
