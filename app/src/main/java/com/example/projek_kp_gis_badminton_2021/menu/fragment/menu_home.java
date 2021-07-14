package com.example.projek_kp_gis_badminton_2021.menu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.projek_kp_gis_badminton_2021.R;

import butterknife.ButterKnife;

public class menu_home extends Fragment {



    private CardView cardPetunjuk;
    private CardView cardAbout;
    private CardView cardMaps;
    private CardView cardLapangan;

    public menu_home() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_home, container, false);
        ButterKnife.bind(this, view);
        initView(view);

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onStart() {
        super.onStart();

    }

    private void initView(View v) {
        cardPetunjuk = (CardView) v.findViewById(R.id.card_petunjuk);
        cardAbout = (CardView) v.findViewById(R.id.card_about);
        cardMaps = (CardView) v.findViewById(R.id.card_maps);
        cardLapangan = (CardView) v.findViewById(R.id.card_lapangan);

        cardLapangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
         cardMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent materi = new Intent(getActivity(), menu_tugas.class);
//                startActivity(materi);
            }
        });

    }
}