package com.example.projek_kp_gis_badminton_2021.presenter;

import android.content.Context;
import android.util.Log;

import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.Response_lapangan;
import com.example.projek_kp_gis_badminton_2021.server.ApiRequest;
import com.example.projek_kp_gis_badminton_2021.server.Retroserver_server_AUTH;
import com.example.projek_kp_gis_badminton_2021.view.lapangan_view;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lapangan {

    private Context ctx;
    private lapangan_view countryView;
    private Retroserver_server_AUTH countryService;
    public lapangan(lapangan_view view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }

    public void get_lapangan() {
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Log.i("isi_server", "isi_server: "+Retroserver_server_AUTH.getClient().baseUrl());
        Call<Response_lapangan> call = api.get_lapangan();
        call.enqueue(new Callback<Response_lapangan>() {
            @Override
            public void onResponse(Call<Response_lapangan> call, Response<Response_lapangan> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_lapangan data = response.body();
                        //Toast.makeText(ctx, ""+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("isi_data", "onResponse: "+data);
                        if (data != null && data.getIsi() != null) {
                            List<IsiItem_lapangan> result = data.getIsi();
                            countryView.lapangan(result);
                        }
                    }
                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Response_lapangan> call, Throwable t) {
                t.printStackTrace();
                Log.i("cek_error", "onFailure: " + t);
                if (t instanceof IOException) {


                } else {


                }
            }
        });
    }

    }


