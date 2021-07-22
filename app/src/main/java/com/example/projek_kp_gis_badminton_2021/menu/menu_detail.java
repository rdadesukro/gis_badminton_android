package com.example.projek_kp_gis_badminton_2021.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.adapter.SliderAdapterExample;
import com.example.projek_kp_gis_badminton_2021.adapter.adapter_komentar;
import com.example.projek_kp_gis_badminton_2021.model.foto_slider.IsiItem_slider;
import com.example.projek_kp_gis_badminton_2021.model.jenis.IsiItem_jenis;
import com.example.projek_kp_gis_badminton_2021.model.jenis_detail.IsiItem_jenis_detail;
import com.example.projek_kp_gis_badminton_2021.model.komentar.IsiItem_komentar;
import com.example.projek_kp_gis_badminton_2021.presenter.jenis;
import com.example.projek_kp_gis_badminton_2021.presenter.komentar;
import com.example.projek_kp_gis_badminton_2021.presenter.slider;
import com.example.projek_kp_gis_badminton_2021.view.jenis_view;
import com.example.projek_kp_gis_badminton_2021.view.komentar_view;
import com.example.projek_kp_gis_badminton_2021.view.slider_view;
import com.github.squti.guru.Guru;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class menu_detail extends AppCompatActivity implements jenis_view, slider_view, komentar_view {

    private TextView txtNama;
    private TextView txtAlamat;
    private ImageView imgFoto;
    private FloatingActionButton floatingActionButton;
    private TextView btnTelpon;
    private TextView btnCaht;
    private TextView txtInformasi;
    private TextView txtJumlah;
    private Button arrowBtn;
    private ConstraintLayout conBerita;
    private TextView txtKomen;
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton btnSend;
    private adapter_komentar adapter_komentar;
    BottomSheetDialog bittom_dialog;
    ProgressDialog progressDialog;
    com.example.projek_kp_gis_badminton_2021.presenter.komentar komentar;

    com.example.projek_kp_gis_badminton_2021.presenter.jenis jenis;
    com.example.projek_kp_gis_badminton_2021.presenter.slider slider;
    String id_lapangan, role, id_jenis;
    private RatingBar ratingBar2;
    private TextView txtTgl;
    String no_telpon, status_login;
    private EditText textContent;
    private Button button;
    double lat, lng;
    private com.example.projek_kp_gis_badminton_2021.adapter.SliderAdapterExample SliderAdapterExample;
    private SliderView bener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        initView();
        id_lapangan = Guru.getString("id_lapangan", "false");
        id_jenis = Guru.getString("id_jenis", "false");

        Log.i("id_lapangan", "onCreate: " + id_lapangan);
        status_login = Guru.getString("status_loign", "false");
        role = Guru.getString("role", "1");
        jenis = new jenis(this, menu_detail.this);
        komentar = new komentar(this, menu_detail.this);
        slider = new slider(this, menu_detail.this);
        slider.get_slider(id_jenis);
        jenis.get_jenis_detail(role, id_jenis);
        komentar.get_komentar(id_jenis);

        if (role.equals("1")) {
            floatingActionButton.setVisibility(View.GONE);
        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        btnTelpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + no_telpon));
                startActivity(callIntent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guru.putString("id_jenis", id_jenis);
                Intent goInput = new Intent(menu_detail.this, menu_add_foto.class);
                startActivity(goInput);
                CustomIntent.customType(menu_detail.this, "fadein-to-fadeout");
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status_login.equals("false")) {

                    Toast.makeText(menu_detail.this, "Maaf Login Terlebih Daulu", Toast.LENGTH_SHORT).show();


                } else {
                    if (textContent.getText().toString().equals("")){
                        Toast.makeText(menu_detail.this, "Maaf Isi dahulu", Toast.LENGTH_SHORT).show();

                    }else {
                        komentar.kirim_komentar(id_jenis, textContent.getText().toString(), progressDialog);
                    }

                }
            }
        });

        ratingBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = null;
                Intent mapIntent;
                String goolgeMap = "com.google.android.apps.maps";


                gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);

                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage(goolgeMap);

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(menu_detail.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCaht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("+62" + no_telpon);
            }
        });

    }
    private void openWhatsApp(String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();
        slider.get_slider(id_jenis);
        jenis.get_jenis_detail(role, id_jenis);
        komentar.get_komentar(id_jenis);

    }

    private void initView() {
        txtNama = (TextView) findViewById(R.id.txt_nama);
        txtAlamat = (TextView) findViewById(R.id.txt_alamat);
        imgFoto = (ImageView) findViewById(R.id.img_foto);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        btnTelpon = (TextView) findViewById(R.id.btn_telpon);
        btnCaht = (TextView) findViewById(R.id.btn_caht);
        txtInformasi = (TextView) findViewById(R.id.txt_informasi);
        txtJumlah = (TextView) findViewById(R.id.txt_jumlah);
        arrowBtn = (Button) findViewById(R.id.arrowBtn);
        conBerita = (ConstraintLayout) findViewById(R.id.con_berita);
        txtKomen = (TextView) findViewById(R.id.txt_komen);
        RecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSend = (FloatingActionButton) findViewById(R.id.btn_send);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        txtTgl = (TextView) findViewById(R.id.txt_tgl);
        textContent = (EditText) findViewById(R.id.text_content);
        button = (Button) findViewById(R.id.button);
        bener = (SliderView) findViewById(R.id.bener);
    }

    @Override
    public void jenis(List<IsiItem_jenis> jenis) {

    }

    @Override
    public void jenis_detail(List<IsiItem_jenis_detail> jenis_detail) {
        try {
            //  progKes.setVisibility(View.VISIBLE);

            for (int i = 0; i < jenis_detail.size(); i++) {

                txtAlamat.setText(jenis_detail.get(i).getLapangan().getAlamat());
                txtNama.setText(jenis_detail.get(i).getNama());
                //  ratingBar2.setRating(Float.parseFloat(String.valueOf(jenis_detail.get(i).getRaiting())));
                txtInformasi.setText(jenis_detail.get(i).getInformasi());
                txtTgl.setText(jenis_detail.get(i).getCreatedAt());

                no_telpon = jenis_detail.get(i).getLapangan().getPhone();
                Log.i("isi_foto", "jenis_detail: " + jenis_detail.get(i).getFoto());


                Glide.with(this)
                        .load("http://192.168.43.48/gis_badminton/public/foto_jenis/" + jenis_detail.get(i).getFoto())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .error(R.drawable.ic_baseline_speaker_notes_off_24)
                        .centerCrop()
                        .into(imgFoto);
                ratingBar2.setRating(jenis_detail.get(i).getRaiting());
                lat = Double.parseDouble(jenis_detail.get(i).getLapangan().getLat());
                lng = Double.parseDouble(jenis_detail.get(i).getLapangan().getLng());

            }


        } catch (Exception e) {

        }
    }

    @Override
    public void status(String status, String pesan) {

    }

    @Override
    public void slider(List<IsiItem_slider> slider) {
       // swifeRefresh.setRefreshing(false);
        try {
            Log.i("data_slider", "slider: " + slider);
            SliderAdapterExample = new SliderAdapterExample(menu_detail.this, slider);
            bener.setSliderAdapter(SliderAdapterExample);
            bener.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
            bener.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
            bener.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            bener.setIndicatorSelectedColor(Color.WHITE);
            bener.setIndicatorUnselectedColor(Color.RED);
            bener.setScrollTimeInSec(4);
            bener.setAutoCycle(true);
            bener.startAutoCycle();
            //mRecycler.setAdapter(adapter);
            SliderAdapterExample.notifyDataSetChanged();
        }catch (Exception e){
            Log.i("cek_error_slider", "slider: "+e);

        }


    }

    @Override
    public void komentar(List<IsiItem_komentar> komentar) {
        try {
            //  progKes.setVisibility(View.VISIBLE);
            Log.i("isi_event", "event: " + komentar.size());
            adapter_komentar = new adapter_komentar(menu_detail.this, komentar, 1);
            RecyclerView.setLayoutManager(new LinearLayoutManager(menu_detail.this, LinearLayoutManager.VERTICAL, false));
            RecyclerView.setAdapter(adapter_komentar);
            // swifeRefresh.setRefreshing(false);
            if (komentar.size() == 0) {
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
    public void status(String status) {
        if (status.equals("1")) {
            komentar.get_komentar(id_jenis);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rating, menu);
        MenuItem tanya = menu.findItem(R.id.rating);

        tanya.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bittom_dialog = new BottomSheetDialog(menu_detail.this);
                bittom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                bittom_dialog.setContentView(R.layout.dialog_raiting);
                bittom_dialog.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp = new WindowManager.LayoutParams();
                lp.copyFrom(bittom_dialog.getWindow().getAttributes());
                bittom_dialog.getWindow().setAttributes(lp);
                bittom_dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                bittom_dialog.getWindow().setDimAmount(0.5f);


                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                ImageView btn_close = (ImageView) bittom_dialog.findViewById(R.id.btn_close);
                RatingBar ratingBar = (RatingBar) bittom_dialog.findViewById(R.id.ratingBar4);
                Button btn_edit = bittom_dialog.findViewById(R.id.btn_edit);

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (status_login.equals("false")) {

                            Toast.makeText(menu_detail.this, "Maaf Login Terlebih Daulu", Toast.LENGTH_SHORT).show();


                        } else {
                            double rating = ratingBar2.getRating();
                            jenis.edit_rating(id_jenis, ratingBar.getRating(), id_lapangan, progressDialog);
                        }

                    }
                });

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bittom_dialog.dismiss();

                    }
                });
                bittom_dialog.show();


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}