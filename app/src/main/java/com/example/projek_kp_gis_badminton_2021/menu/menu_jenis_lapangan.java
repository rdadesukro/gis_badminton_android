package com.example.projek_kp_gis_badminton_2021.menu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.adapter.adapter_jenis;
import com.example.projek_kp_gis_badminton_2021.adapter.adapter_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.jenis.IsiItem_jenis;
import com.example.projek_kp_gis_badminton_2021.model.jenis_detail.IsiItem_jenis_detail;
import com.example.projek_kp_gis_badminton_2021.presenter.jenis;
import com.example.projek_kp_gis_badminton_2021.view.jenis_view;
import com.github.squti.guru.Guru;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

public class menu_jenis_lapangan extends AppCompatActivity implements jenis_view, adapter_lapangan.OnImageClickListener {
    AlertDialog.Builder acion;
    private ConstraintLayout relativeLayout;
    private TextView txtTdkAda;
    private ImageView imgData2;
    private SwipeRefreshLayout swifeRefresh;
    private RecyclerView rvAku;
    private ProgressBar progressBar;
    private adapter_jenis adapter_jenis;
    com.example.projek_kp_gis_badminton_2021.presenter.jenis jenis;
    String id;
    private FloatingActionButton btnPanggil2;
    String  role,nama;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jenis_lapangan);
        initView();
        try {
            ProviderInstaller.installIfNeeded(this);
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        jenis = new jenis(this, menu_jenis_lapangan.this);
        id = Guru.getString("id_lapangan", "");
        nama = Guru.getString("nama_lapangan", "");
        role = Guru.getString("role", "false");
        Log.i("role_id", "onCreate: "+id+" "+role);
        jenis.get_jenis(role, id);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nama);
        if (role.equals("1")){
            btnPanggil2.setVisibility(View.GONE);
        }else {
            btnPanggil2.setVisibility(View.VISIBLE);
        }


        btnPanggil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(menu_jenis_lapangan.this, menu_tambah_jenis.class);
                Guru.putString("id_lapangan", id);
                Guru.putString("edit","new");
                startActivity(intent);
            }
        });
        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jenis.get_jenis(role, id);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        jenis.get_jenis(role, id);
    }
    @Override
    public void onImageClick(int id, String nama, String alamat, String phone, String foto, double lat, double lng, int status) {
        final CharSequence[] dialogitem = {"Edit", "Delete"};
        acion = new AlertDialog.Builder(this);
        acion.setCancelable(true);
        acion.setItems(dialogitem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                switch (which) {
                    case 0:
                        Intent intent  = new Intent(menu_jenis_lapangan.this, menu_tambah_jenis.class);
                        Guru.putString("nama",nama);
                        Guru.putString("edit","edit");
                        Guru.putString("id_jenis", String.valueOf(id));
                        Guru.putString("informasi",alamat);
                        Guru.putString("harga",phone);
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda akan menghahpus semua data!! Yakin mau hapus??");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        jenis.hapus_jenis(String.valueOf(id),progressDialog);

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
    public void jenis(List<IsiItem_jenis> jenis) {
        try {
            //  progKes.setVisibility(View.VISIBLE);
            Log.i("isi_event", "event: " + jenis);
            adapter_jenis = new adapter_jenis(menu_jenis_lapangan.this, jenis, 1, this::onImageClick);
            rvAku.setLayoutManager(new LinearLayoutManager(menu_jenis_lapangan.this, LinearLayoutManager.VERTICAL, false));
            rvAku.setAdapter(adapter_jenis);
            swifeRefresh.setRefreshing(false);
            if (jenis.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
                //  cardEvent.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                // cardEvent.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            Log.i("cek_error_jenis", "jenis: "+e);

        }
    }

    @Override
    public void jenis_detail(List<IsiItem_jenis_detail> jenis_detail) {

    }

    @Override
    public void status(String status, String pesan) {

    }

    private void initView() {
        relativeLayout = (ConstraintLayout) findViewById(R.id.relativeLayout);
        txtTdkAda = (TextView) findViewById(R.id.txt_tdk_ada);
        imgData2 = (ImageView) findViewById(R.id.img_data2);
        swifeRefresh = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        rvAku = (RecyclerView) findViewById(R.id.rv_aku);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnPanggil2 = (FloatingActionButton) findViewById(R.id.btn_panggil2);
    }
}