package com.ctbarbanza.gupyou.menu;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ctbarbanza.gupyou.R;

public class MenuFragment extends Fragment {
    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_menu, container, false);
        initButtons(view);
        return view;
    }

    private void initButtons(View view) {
        ImageButton btnProfile = view.findViewById(R.id.menu_profile_btn);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Y", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
