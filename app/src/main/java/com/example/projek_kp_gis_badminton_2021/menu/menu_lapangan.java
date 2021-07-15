package com.example.projek_kp_gis_badminton_2021.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.adapter.adapter_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;
import com.example.projek_kp_gis_badminton_2021.presenter.lapangan;
import com.example.projek_kp_gis_badminton_2021.view.lapangan_view;

import java.util.List;

public class menu_lapangan extends AppCompatActivity implements lapangan_view, adapter_lapangan.OnImageClickListener {

    private TextView txtTdkAda;
    private ImageView imgData2;
    private SwipeRefreshLayout swifeRefresh;
    private RecyclerView rvAku;
    private ProgressBar progressBar;
    private adapter_lapangan adapter_lapangan;
    com.example.projek_kp_gis_badminton_2021.presenter.lapangan lapangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lapangan);
        lapangan = new lapangan(this,menu_lapangan.this);
        lapangan.get_lapangan("user");
        initView();

    }

    @Override
    public void onImageClick(int id, String nama, String alamat, String phone, String foto, double lat, double lng, int status) {

    }

    @Override
    public void lapangan(List<IsiItem_lapangan> lapangan) {
        try {
            //  progKes.setVisibility(View.VISIBLE);
            Log.i("isi_event", "event: " + lapangan.size());
            adapter_lapangan = new adapter_lapangan(menu_lapangan.this,  lapangan, 1,this::onImageClick);
            rvAku.setLayoutManager(new LinearLayoutManager(menu_lapangan.this,  LinearLayoutManager.VERTICAL, false));
            rvAku.setAdapter(adapter_lapangan);
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

    private void initView() {
        txtTdkAda = (TextView) findViewById(R.id.txt_tdk_ada);
        imgData2 = (ImageView) findViewById(R.id.img_data2);
        swifeRefresh = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        rvAku = (RecyclerView) findViewById(R.id.rv_aku);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}