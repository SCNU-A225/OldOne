package com.campus.oldone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.campus.oldone.R;
import com.campus.oldone.activity.ReleasedGoodsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {

    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_center, container, false);
    }

}
