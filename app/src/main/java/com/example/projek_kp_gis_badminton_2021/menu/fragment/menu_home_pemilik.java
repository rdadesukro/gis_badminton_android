package com.example.projek_kp_gis_badminton_2021.menu.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.adapter.adapter_lapangan;
import com.example.projek_kp_gis_badminton_2021.menu.menu_lapangan;
import com.example.projek_kp_gis_badminton_2021.menu.menu_pembuka;
import com.example.projek_kp_gis_badminton_2021.menu.menu_tambah_lapngan;
import com.example.projek_kp_gis_badminton_2021.menu.menu_utama_pemilik;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;
import com.example.projek_kp_gis_badminton_2021.presenter.lapangan;
import com.example.projek_kp_gis_badminton_2021.utils.PERMISSIONS;
import com.example.projek_kp_gis_badminton_2021.view.lapangan_view;
import com.github.squti.guru.Guru;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.ButterKnife;

public class menu_home_pemilik extends Fragment implements lapangan_view, adapter_lapangan.OnImageClickListener {


    private SwipeRefreshLayout swifeRefresh;
    private CardView cardData;
    private TextView textView4;
    private ProgressBar progressBar;
    private RecyclerView rvBerita;
    private FloatingActionButton btnPanggil;
    private adapter_lapangan adapter_lapangan;
    AlertDialog.Builder acion;
    String role;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    com.example.projek_kp_gis_badminton_2021.presenter.lapangan lapangan;
    PERMISSIONS permissions;
    public menu_home_pemilik() {
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
        View view = inflater.inflate(R.layout.activity_menu_home_pemilik, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        lapangan = new lapangan(this, getActivity());
        role = Guru.getString("role", "false");
        lapangan.get_lapangan(role);

        permissions = new PERMISSIONS(getActivity());
        permissions.checkPermission();



        if (role.equals("1")){
            btnPanggil.setVisibility(View.GONE);
        }else {
            btnPanggil.setVisibility(View.VISIBLE);
        }


        btnPanggil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), menu_tambah_lapngan.class);
                Guru.putString("edit","new");
                startActivity(intent);
            }
        });

        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lapangan.get_lapangan(role);
            }
        });

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        lapangan.get_lapangan(role);

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    private void initView(View v) {

        swifeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swifeRefresh);
        cardData = (CardView) v.findViewById(R.id.card_Data);
        textView4 = (TextView) v.findViewById(R.id.textView4);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        rvBerita = (RecyclerView) v.findViewById(R.id.rv_berita);
        btnPanggil = (FloatingActionButton) v.findViewById(R.id.btn_panggil);
    }

    @Override
    public void onImageClick(int id, String nama, String alamat, String phone, String foto, double lat, double lng, int status) {
        final CharSequence[] dialogitem = {"Edit", "Delete"};
        acion = new AlertDialog.Builder(getActivity());
        acion.setCancelable(true);
        acion.setItems(dialogitem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                switch (which) {
                    case 0:
                        Intent intent  = new Intent(getActivity(), menu_tambah_lapngan.class);
                        Guru.putString("nama",nama);
                        Guru.putString("edit","edit");
                        Guru.putString("id_lapangan", String.valueOf(id));
                        Guru.putString("alamat",alamat);
                        Guru.putString("lat", String.valueOf(lat));
                        Guru.putString("lng", String.valueOf(lng));
                        Guru.putString("phone",phone);
                        Guru.putString("status", String.valueOf(status));
                        Guru.putString("foto",foto);


                        startActivity(intent);
                        break;
                    case 1:

                       hapus(id);

                        break;
                }
            }
        }).show();

    }
    void hapus(int id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Anda akan menghahpus semua data!! Yakin mau hapus??");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                lapangan.hapus_lapangan(String.valueOf(id),progressDialog);
                               
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void lapangan(List<IsiItem_lapangan> lapangan) {
        try {
            //  progKes.setVisibility(View.VISIBLE);
            Log.i("isi_event", "event: " + lapangan.size());
            adapter_lapangan = new adapter_lapangan(getActivity(),  lapangan, 1,this::onImageClick);
            rvBerita.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false));
            rvBerita.setAdapter(adapter_lapangan);
            swifeRefresh.setRefreshing(false);
            if (lapangan.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
                //  cardEvent.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                // cardEvent.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void status(String status, String pesan) {


    }
}