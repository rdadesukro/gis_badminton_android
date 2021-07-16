package com.example.projek_kp_gis_badminton_2021.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.projek_kp_gis_badminton_2021.model.foto_slider.Response_slider;
import com.example.projek_kp_gis_badminton_2021.model.komentar.Response_komentar;
import com.example.projek_kp_gis_badminton_2021.model.login.Response_login;
import com.example.projek_kp_gis_badminton_2021.server.ApiRequest;
import com.example.projek_kp_gis_badminton_2021.server.Retroserver_server_AUTH;
import com.example.projek_kp_gis_badminton_2021.view.komentar_view;
import com.example.projek_kp_gis_badminton_2021.view.slider_view;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class komentar {

    private Context ctx;
    private komentar_view countryView;
    private Retroserver_server_AUTH countryService;
    public komentar(komentar_view view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }

    public void get_komentar(String id){
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_komentar> call = api.get_komentar(id);
        call.enqueue(new Callback<Response_komentar>() {
            @Override
            public void onResponse(Call<Response_komentar> call, Response<Response_komentar> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_komentar data = response.body();
                        Log.i("isi_slider", "onResponse: "+data.getIsi());
                        Log.i("data_size", "onResponse: "+data.getIsi().size());
                        countryView.komentar(data.getIsi());
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
            public void onFailure(Call<Response_komentar> call, Throwable t) {
                t.printStackTrace();
                //  sliderView_bener.setBackgroundResource(R.drawable.bg_no_item_city);
                Log.i("ewkwkwkwkw", "onFailure: " + t);
                if (t instanceof IOException) {

                } else {

                }

            }
        });

    }


    public void kirim_komentar(String jenis_id, String komentar, ProgressDialog pDialog) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setTitle("Mohon Tunggu!!!");
        pDialog.setMessage("Kirim Komentar...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Call<Response_login> sendbio = api.add_komentar(jenis_id,komentar);

        ProgressDialog finalPDialog = pDialog;
        sendbio.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                String kode = response.body().getKode();
                Log.i("kode_update", "onResponse: " + kode);
                try {
                    countryView.status(kode);


                    if (kode.equals("1")) {
                        finalPDialog.dismiss();
                        new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                    } else if (kode.equals("3")){
                        finalPDialog.dismiss();
                        new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                    }else {
                        finalPDialog.dismiss();
                        new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                    }
                }catch (Exception E){

                }


            }
            @Override
            public void onFailure(Call<Response_login> call, Throwable t) {

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }

}






