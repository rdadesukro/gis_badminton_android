package com.example.projek_kp_gis_badminton_2021.server;


import com.example.projek_kp_gis_badminton_2021.model.action.Response_action;
import com.example.projek_kp_gis_badminton_2021.model.jenis.Response_jenis;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.Response_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.login.Response_login;
import com.example.projek_kp_gis_badminton_2021.model.user.Response_user;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiRequest {


    @FormUrlEncoded
    @POST("send_email")
    Call<Response_action> send_email(
            @Field("email") String email);


    @Multipart
    @POST("edit_foto_profil")
    Call<Response_login> edit_foto(@Part MultipartBody.Part foto);

    @FormUrlEncoded
    @POST("register")
    Call<Response_login> register(
            @Field("nama") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("telpon") String telpon,
            @Field("role") int role,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation);

    @Multipart
    @POST("add_lapangan")
    Call<Response_login> simpan_lapangan(
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("phone") RequestBody phone,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part foto);



    @Multipart
    @POST("add_jenis")
    Call<Response_login> add_jenis(
            @Part("nama") RequestBody nama,
            @Part("informasi") RequestBody informasi,
            @Part("harga") RequestBody harga,
            @Part("lapangan_id") RequestBody lapangan_id,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part foto);


    @Multipart
    @POST("edit_jenis")
    Call<Response_login> edit_jenis(
            @Part("nama") RequestBody nama,
            @Part("informasi") RequestBody informasi,
            @Part("harga") RequestBody harga,
            @Part("lapangan_id") RequestBody lapangan_id,
            @Part("id") RequestBody id,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part foto);


    @Multipart
    @POST("edit_lapangan")
    Call<Response_login> edit_lapangan(
            @Part("id") RequestBody id,
            @Part("nama") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("phone") RequestBody phone,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part foto);



    @FormUrlEncoded
    @POST("edit_pass")
    Call<Response_login> edit_pass(
            @Field("password") String password,
            @Field("password_baru") String password_baru);



    @FormUrlEncoded
    @POST("login")
    Call<Response_login> login(
            @Field("username") String username,
            @Field("password") String password);


    @POST("get_data_lapangan")
    Call<Response_lapangan> get_lapangan();


    @FormUrlEncoded
    @POST("get_data_jenis")
    Call<Response_jenis> get_data_jenis(@Field("id") String id);

    @POST("get_data_lapangan_pemilik")
    Call<Response_lapangan> get_data_lapangan_pemilik();




    @FormUrlEncoded
    @POST("simpan_pertanyaan")
    Call<Response_action> simpan_curhatan(@Field("isi_pertanyaan") String isi_pertanyaan);

    @FormUrlEncoded
    @POST("cek_data")
    Call<Response_login> cek_data(
            @Field("email") String username,
              @Field("password") String password);



    @POST("get_data_user")
    Call<Response_user> get_data_user();


    @GET("logout")
    Call<Response_login> logout();

    @FormUrlEncoded
    @POST("hapus_token")
    Call<Response_login> hapus_token(
            @Field("token") String token);


    @FormUrlEncoded
    @POST("edit_no_hp")
    Call<Response_login> edit_no_hp(@Field("phone") String no_hp);





}


