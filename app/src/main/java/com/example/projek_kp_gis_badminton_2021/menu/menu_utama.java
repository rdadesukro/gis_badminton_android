package com.example.projek_kp_gis_badminton_2021.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.adapter.ViewPagerAdapter;
import com.example.projek_kp_gis_badminton_2021.menu.fragment.menu_home;
import com.example.projek_kp_gis_badminton_2021.menu.fragment.menu_profil;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class menu_utama extends AppCompatActivity {
    menu_home home;
    menu_profil profil;
    ViewPager vg;
    int value;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNavigationView;
    public static BadgeDrawable badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        vg = (ViewPager) findViewById(R.id.vg);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                vg.setCurrentItem(0);
                                getSupportActionBar().setTitle("Home");
                                break;
                            case R.id.profil:
                                vg.setCurrentItem(3);
                                getSupportActionBar().setTitle("Profil");
                                break;
                        }
                        return false;
                    }
                });

        vg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                if (position == 0) {
                    getSupportActionBar().setTitle("Home");
                } else if (position == 1) {
                    getSupportActionBar().setTitle("News");
                } else if (position == 2) {
                    getSupportActionBar().setTitle("Notifikasi");
                }else {
                    getSupportActionBar().setTitle("Profil");
                }
                Log.d("page", "onPageSelected: " + position);

                Log.i("posisi", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(vg);
        vg.setCurrentItem(0);


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        home = new menu_home();
        profil = new menu_profil();



        adapter.addFragment(home);
        adapter.addFragment(home);

        viewPager.setAdapter(adapter);
    }
}