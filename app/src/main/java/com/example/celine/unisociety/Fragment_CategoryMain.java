package com.example.celine.unisociety;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_CategoryMain extends Fragment implements View.OnClickListener{

    private ImageButton cat_workshop;
    private ImageButton cat_social;
    private ImageButton cat_party;
    private ImageButton cat_food;

    public Fragment_CategoryMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_main, container, false);
        cat_workshop = view.findViewById(R.id.cat_btn_workshop);
        cat_social = view.findViewById(R.id.cat_btn_social);
        cat_party = view.findViewById(R.id.cat_btn_party);
        cat_food = view.findViewById(R.id.cat_btn_food);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cat_btn_workshop:
                break;
            case R.id.cat_btn_social:
                break;
            case R.id.cat_btn_party:
                break;
            case R.id.cat_btn_food:
                break;
            //default:
        }
    }
}
