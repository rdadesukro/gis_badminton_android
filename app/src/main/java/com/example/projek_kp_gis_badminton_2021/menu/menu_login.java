package com.example.projek_kp_gis_badminton_2021.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.model.user.IsiItem_user;
import com.example.projek_kp_gis_badminton_2021.presenter.aksi;
import com.example.projek_kp_gis_badminton_2021.view.coba_view;
import com.github.squti.guru.Guru;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maes.tech.intentanim.CustomIntent;

public class menu_login extends AppCompatActivity implements Validator.ValidationListener, coba_view {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_lupa)
    TextView btnLupa;

    @NotEmpty
    @BindView(R.id.edit_user)
    EditText editUser;



    @NotEmpty
    @BindView(R.id.edit_pass)
    EditText editPass;
    Validator validator;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    String email,nama;
    BottomSheetDialog dialog;
    ProgressDialog pDialog;
    String  token;

    BottomSheetDialog bottom_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_login);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        editUser.requestFocus();
        Guru.getString("status_loign", "false");
        Log.i("status_loign", "onCreate: " + Guru.getString("status_loign", "default value"));
        getSupportActionBar().hide();
        pDialog = new ProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(menu_login.this, "ade", Toast.LENGTH_SHORT).show();
                validator.validate();

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public void onValidationSucceeded() {
        aksi countryPresenter = new aksi(null,menu_login.this);
        countryPresenter.login(editUser.getText().toString().trim(),editPass.getText().toString().trim(),pDialog);
    }

    @OnClick({R.id.btn_regis, R.id.btn_login, R.id.btn_lupa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_regis:
                Intent goInput = new Intent(this, menu_register.class);
                startActivity(goInput);
                CustomIntent.customType(menu_login.this, "fadein-to-fadeout");
                break;
            case R.id.btn_login:
                validator.validate();
                break;
            case R.id.btn_lupa:
//                goInput = new Intent(this, menu_lupa_password.class);
//                startActivity(goInput);
//                CustomIntent.customType(menu_login.this, "fadein-to-fadeout");
                break;
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            CustomIntent.customType(this, "fadein-to-fadeout");
            Intent intent = new Intent((Activity) this, menu_utama.class);
            intent.putExtra("Fragmentone", 0); //pass zero for Fragmentone.
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void data_user(List<IsiItem_user> data_user) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}