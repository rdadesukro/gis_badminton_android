package com.example.projek_kp_gis_badminton_2021.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.projek_kp_gis_badminton_2021.R;
import com.github.squti.guru.Guru;

public class menu_pembuka extends AppCompatActivity {
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pembuka);
        role = Guru.getString("role", "false");
        star();
    }

    void star(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (role.equals("1")){
                    Intent intent  = new Intent(menu_pembuka.this, menu_utama_pemilik.class);
                    startActivity(intent);
                }else{
                    Intent intent  = new Intent(menu_pembuka.this, menu_utama.class);
                    startActivity(intent);
                }



            }
        }, 3000L); //3000 L = 3 detik
    }
}