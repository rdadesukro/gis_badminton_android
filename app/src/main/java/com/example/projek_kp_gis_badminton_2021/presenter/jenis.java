package com.example.projek_kp_gis_badminton_2021.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.projek_kp_gis_badminton_2021.model.jenis.IsiItem_jenis;
import com.example.projek_kp_gis_badminton_2021.model.jenis.Response_jenis;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.Response_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.login.Response_login;
import com.example.projek_kp_gis_badminton_2021.server.ApiRequest;
import com.example.projek_kp_gis_badminton_2021.server.Retroserver_server_AUTH;
import com.example.projek_kp_gis_badminton_2021.view.jenis_view;
import com.example.projek_kp_gis_badminton_2021.view.lapangan_view;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jenis {

    private Context ctx;
    private jenis_view countryView;
    private Retroserver_server_AUTH countryService;
    public jenis(jenis_view view, Context ctx) {
        this.countryView = view;
        this.ctx = ctx;

        if (this.countryService == null) {
            this.countryService = new Retroserver_server_AUTH();
        }
    }

    public void get_jenis(String role,String id) {
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);
        Log.i("isi_server", "isi_server: "+Retroserver_server_AUTH.getClient().baseUrl());
        Call<Response_jenis> call;
        if (role.equals("user")){
            call = api.get_data_jenis(id);
        }else {
            call = api.get_data_jenis(id);
        }
        call.enqueue(new Callback<Response_jenis>() {
            @Override
            public void onResponse(Call<Response_jenis> call, Response<Response_jenis> response) {

                try {

                    if (response.isSuccessful()) {
                        Response_jenis data = response.body();
                        //Toast.makeText(ctx, ""+ response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("isi_data", "onResponse: "+data);
                        if (data != null && data.getIsi() != null) {
                            List<IsiItem_jenis> result = data.getIsi();
                            countryView.jenis(result);
                        }
                    }
                } catch (Exception e) {
                    Log.e("onResponse", "There is an error" + e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Response_jenis> call, Throwable t) {
                t.printStackTrace();
                Log.i("cek_error", "onFailure: " + t);
                if (t instanceof IOException) {


                } else {


                }
            }
        });
    }

    public void simpan_jenis(SweetAlertDialog pDialog,
                                RequestBody id_jenis,
                                RequestBody id_lapangan,
                                RequestBody nama,
                                RequestBody informasi,
                                RequestBody harga,
                                RequestBody status,
                                String jenis,
                                MultipartBody.Part foto) {
        pDialog = new SweetAlertDialog((Activity) ctx, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3395ff"));
        pDialog.setTitleText("Simpan Data");
        pDialog.setContentText("Mohon tunggu sedang memproses...");
        pDialog.setCancelable(false);
        pDialog.show();



        SweetAlertDialog finalPDialog = pDialog;
        ApiRequest api = Retroserver_server_AUTH.getClient().create(ApiRequest.class);

        Call<Response_login> sendbio = null;
        if (jenis.equals("new")){
            sendbio = api.add_jenis(nama,
                    informasi,
                    harga,
                    id_lapangan,
                    status,
                    foto);
        }else {
            sendbio = api.edit_jenis(nama,
                    informasi,
                    harga,
                    id_lapangan,
                    id_jenis,
                    status,
                    foto);
        }


        sendbio.enqueue(new Callback<Response_login>() {
            @Override
            public void onResponse(Call<Response_login> call, Response<Response_login> response) {

                try {
                    String kode = response.body().getKode();
                    String role = String.valueOf(response.body().getRole());
                    Log.i("role_kode", "onResponse: " + role);

                    //role = 1 = pemilik
                    //role = 2 = usert
                    if (kode.equals("1")) {
                        finalPDialog.dismissWithAnimation();
//                    login_new(email,password,finalPDialog);
                        new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.SUCCESSTOAST, GlideToast.CENTER).show();

                    } else {
                        finalPDialog.dismissWithAnimation();
                        new GlideToast.makeToast((Activity) ctx, "" + response.body().getMessage(), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                    }
                }catch (Exception e){
                    Log.i("cek_error_login", "onResponse: "+e);
                    finalPDialog.dismiss();
                }



            }
            @Override
            public void onFailure(Call<Response_login> call, Throwable t) {
                Log.e("cek_eror_login", "onFailure: "+t);

                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
            }
        });

    }


    }


