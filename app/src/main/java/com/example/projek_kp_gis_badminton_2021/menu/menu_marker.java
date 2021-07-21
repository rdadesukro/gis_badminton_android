package com.example.projek_kp_gis_badminton_2021.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;
import com.example.projek_kp_gis_badminton_2021.presenter.lapangan;
import com.example.projek_kp_gis_badminton_2021.view.lapangan_view;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class menu_marker extends AppCompatActivity implements lapangan_view,OnMapReadyCallback {
    static final LatLng AWAL = new LatLng(-1.642717, 103.573723);

    MapFragment mapFragment;
    GoogleMap gMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;
    Double latti,longi;
    LocationManager locationManager;
    String tag_json_obj = "json_obj_req";
    static final int REQUEST_LOCATION = 1;
    com.example.projek_kp_gis_badminton_2021.presenter.lapangan lapangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_marker);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        lapangan = new lapangan(this,menu_marker.this);
        lapangan.get_lapangan("1");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        //gMap = gMapFragment.getMap();

        gMap.setMyLocationEnabled(true);

        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        gMap.getUiSettings().setAllGesturesEnabled(true);

        gMap.setTrafficEnabled(true);

        //  gMap.setOnMapLongClickListener(this);
        //  gMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        // gMap.setOnInfoWindowClickListener(this);

        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom(AWAL, 16));

        gMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);

        lapangan.get_lapangan("1");
        //getMarkers2();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }
    private void addMarker(double lat,double lng , final String title) {

        LatLng latLng= new LatLng(lat,lng);
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        gMap.addMarker(markerOptions);

        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
               // Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLocation() {
        if( ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latti = location.getLatitude();
                longi = location.getLongitude();


               // latti= Double.parseDouble(v_lat.getText().toString());
                //longi= Double.parseDouble(v_lng.getText().toString());
            } else {
              //  ((TextView)findViewById(R.id.txt_lat1)).setText("Unable to find correct location.");
               // ((TextView)findViewById(R.id.txt_lng1)).setText("Unable to find correct location. ");
            }
        }

    }
    @Override
    public void lapangan(List<IsiItem_lapangan> lapangan) {
        try {
            Log.i("lapangan", "lapangan: "+lapangan);
            //  progKes.setVisibility(View.VISIBLE);
            double titk;
            for (int i = 0; i < lapangan.size(); i++) {

                addMarker(lapangan.get(i).getLat(),lapangan.get(i).getLng(),lapangan.get(i).getNama());

            }


        } catch (Exception e) {

        }
    }

    @Override
    public void status(String status, String pesan) {

    }
}