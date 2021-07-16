package com.example.projek_kp_gis_badminton_2021.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.adapter.ViewPagerAdapter;
import com.example.projek_kp_gis_badminton_2021.menu.fragment.menu_home;
import com.example.projek_kp_gis_badminton_2021.menu.fragment.menu_profil;
import com.github.squti.guru.Guru;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import maes.tech.intentanim.CustomIntent;

public class menu_utama extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{
    menu_home home;
    menu_profil profil;
    ViewPager vg;
    int value;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNavigationView;
    public static BadgeDrawable badge;
    String status_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

        status_login = Guru.getString("status_loign", "false");
        loadFragment(new menu_home());
        getSupportActionBar().setTitle("Home");


        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        home = new menu_home();
        profil = new menu_profil();


        adapter.addFragment(home);
        adapter.addFragment(profil);

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new menu_home();
                getSupportActionBar().setTitle("Home");
                break;


            case R.id.profil:
                if (status_login.equals("false")){
                    Intent intent = new Intent(this, menu_login.class);
                    startActivity(intent);
                    CustomIntent.customType(this, "fadein-to-fadeout");

                }else {
                    fragment = new menu_profil();
                    getSupportActionBar().setTitle("Profil");
                }
                break;
        }

        return loadFragment(fragment);

    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}