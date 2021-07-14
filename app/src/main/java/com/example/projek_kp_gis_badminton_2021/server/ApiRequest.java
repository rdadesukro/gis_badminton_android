package com.example.projek_kp_gis_badminton_2021.server;


import com.example.projek_kp_gis_badminton_2021.model.action.Response_action;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.Response_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.login.Response_login;


import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiRequest {


    @FormUrlEncoded
    @POST("send_email")
    Call<Response_action> send_email(
            @Field("email") String email);


    @Multipart
    @POST("edit_foto")
    Call<Response_login> edit_foto(@Part MultipartBody.Part foto_profil);

    @FormUrlEncoded
    @POST("register")
    Call<Response_action> register(
            @Field("nik") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation);


    @FormUrlEncoded
    @POST("edit_pass")
    Call<Response_login> edit_pass(
            @Field("password") String password,
            @Field("password_baru") String password_baru);



    @POST("get_data_lapangan")
    Call<Response_lapangan> get_lapangan();




    @FormUrlEncoded
    @POST("simpan_pertanyaan")
    Call<Response_action> simpan_curhatan(@Field("isi_pertanyaan") String isi_pertanyaan);

    @FormUrlEncoded
    @POST("cek_data")
    Call<Response_login> cek_data(
            @Field("email") String username,
              @Field("password") String password);


    @GET("logout")
    Call<Response_login> logout();

    @FormUrlEncoded
    @POST("hapus_token")
    Call<Response_login> hapus_token(
            @Field("token") String token);


    @FormUrlEncoded
    @POST("edit_no_hp")
    Call<Response_login> edit_no_hp(
            @Field("dataibu_id") String dataibu_id,
            @Field("no_hp") String no_hp);





}


