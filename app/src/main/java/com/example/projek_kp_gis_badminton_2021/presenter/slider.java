package com.example.projek_kp_gis_badminton_2021.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.projek_kp_gis_badminton_2021.model.foto_slider.Response_slider;
import com.example.projek_kp_gis_badminton_2021.server.ApiRequest;
import com.example.projek_kp_gis_badminton_2021.server.Retroserver_server_AUTH;
import com.example.projek_kp_gis_badminton_2021.view.slider_view;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class slider {

    private Context ctx;
    private slider_view countryView;
    private Retroserver_server_AUTH countryService;
    public slider(slider_view view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }

    public void get_slider(String id){
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_slider> call = api.get_slider(id);
        call.enqueue(new Callback<Response_slider>() {
            @Override
            public void onResponse(Call<Response_slider> call, Response<Response_slider> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_slider data = response.body();
                        Log.i("isi_slider", "onResponse: "+data.getIsi());
                        Log.i("data_size", "onResponse: "+data.getIsi().size());
                        countryView.slider(data.getIsi());
//                        if (data != null && response.body().getPosts() != null) {
//                            List<PostsItem> result = data.getPosts();
//                            countryView.berita(result);
//                        }


                    } else {
                        // error case
                        switch (response.code()) {
                            case 404:
                                Toast.makeText(ctx, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(ctx,"server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(ctx, "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    //data();
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<Response_slider> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }

}






