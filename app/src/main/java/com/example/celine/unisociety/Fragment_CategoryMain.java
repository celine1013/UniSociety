package com.example.celine.unisociety;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.MimeTypeFilter;
import android.util.Log;
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

    public static final String TAG_CATEGORY = "CATEGORY";
    public static final int CATEGORY_WORKSHOP = 0;
    public static final int CATEGORY_SOCIAL = 1;
    public static final int CATEGORY_PARTY = 2;
    public static final int CATEGORY_FOOD = 3;

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

        cat_workshop.setOnClickListener(this);
        cat_social.setOnClickListener(this);
        cat_party.setOnClickListener(this);
        cat_food.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d("frag_cate", "CATEGORY CLICKED");
        Intent intent = new Intent(Fragment_CategoryMain.this.getActivity(), EventListByCategory_Activity.class);
        switch (view.getId()) {
            case R.id.cat_btn_workshop:
                intent.putExtra(TAG_CATEGORY, CATEGORY_WORKSHOP);
                Log.d("frag_cate", "workshop CLICKED");
                break;
            case R.id.cat_btn_social:
                intent.putExtra(TAG_CATEGORY, CATEGORY_SOCIAL);
                break;
            case R.id.cat_btn_party:
                intent.putExtra(TAG_CATEGORY, CATEGORY_PARTY);
                break;
            case R.id.cat_btn_food:
                intent.putExtra(TAG_CATEGORY, CATEGORY_FOOD);
                break;
        }


        intent.putExtra(MainActivity.CURRENT_USER, MainActivity.currentUser);
        startActivity(intent);
        Log.d("frag_cate", "jump");

    }
}

