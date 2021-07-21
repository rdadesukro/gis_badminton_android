package com.example.projek_kp_gis_badminton_2021.menu.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.menu.CameraCapture_new;
import com.example.projek_kp_gis_badminton_2021.model.user.IsiItem_user;
import com.example.projek_kp_gis_badminton_2021.presenter.aksi;
import com.example.projek_kp_gis_badminton_2021.utils.FileUtils;
import com.example.projek_kp_gis_badminton_2021.view.coba_view;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.squti.guru.Guru;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.jpegkit.Jpeg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import id.zelory.compressor.Compressor;
import maes.tech.intentanim.CustomIntent;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class menu_profil extends Fragment implements coba_view,CameraCapture_new.OnInputListener {

    @BindView(R.id.img_profil_2)
    ImageView imageView;

    @BindView(R.id.card)
    CardView cardView;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.img_profil)
    ImageView imgProfil;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.txt_alamat)
    TextView txtAlamat;
    @BindView(R.id.txt_nama)

    TextView txtNama;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.cardView2)
    CardView cardView2;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;


    String status_login;
    @BindView(R.id.btn_login)
    ImageView btnLogin;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.btn_keluar)
    ImageView btnKeluar;


    @BindView(R.id.btn_password)
    ImageView btnPassword;
    @BindView(R.id.txt_password)
    TextView txtPassword;
    @BindView(R.id.btn_no_hp)
    ImageView btnNoHp;
    @BindView(R.id.txt_no_hp)
    TextView txtNoHp;
    @BindView(R.id.btn_email)
    ImageView btnEmail;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    BottomSheetDialog dialog;
    EditText pass_lama,pass_baru,no_hp,email;
    ProgressDialog progressDialog;



    @BindView(R.id.progressBar4)
    android.widget.ProgressBar ProgressBar;
    //camera
    Bitmap decoded;
    int bitmap_size = 40;
    int max_resolution_image = 800;
    Uri tempUri;
    String imageTempName;
    File file,file1;
    Bitmap bitmap;
    aksi data_user;
    ProgressDialog pDialog;
    public final int SELECT_FILE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static String imageStoragePath;
    BottomSheetDialog bittom_dialog;
    String id_ibu;
    private File compressedImage;

   // PERMISSIONS permissions;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public menu_profil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_profil, container, false);
        ButterKnife.bind(this, view);
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getActivity());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
//        permissions = new PERMISSIONS(getActivity());
//        permissions.checkPermission();
        pDialog = new ProgressDialog(getActivity());
        data_user = new aksi(this, getActivity());
        data_user.get_data_user();
        Log.i("data_nik", "onCreateView: "+(Guru.getString("nik", "false")));
        status_login = Guru.getString("status_loign", "false");

        if (status_login.equals("true")) {
            nestedScrollView.setVisibility(View.VISIBLE);
            //Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
        } else {
            nestedScrollView.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), "Belum Login", Toast.LENGTH_SHORT).show();
        }
        id_ibu = Guru.getString("id_ibu", "false");
        Log.i("isi_id_user", "onCreateView: "+id_ibu);
        return view;
    }


    @OnClick(R.id.img_profil_2)
    public void onViewClicked() {

    }


    @OnClick(R.id.txt_alamat)
    public void txt_alamat() {
    }

    @OnClick({R.id.btn_keluar, R.id.btn_password, R.id.btn_no_hp, R.id.btn_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_keluar:
                SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("Apakah anda yakin ingin keluar ?");
                pDialog.setCancelable(false);
                pDialog.setConfirmText("Ya");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        aksi countryPresenter = new aksi(null,getActivity());
                        countryPresenter.logout(progressDialog);
                        Guru.putString("role", "1");
                        Log.i("isi_token", "onViewClicked: "+Guru.getString("token_login", "false"));
//                        countryPresenter.hapus_token(Guru.getString("token_login", "false"));


                    }
                });
                pDialog.setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();


                break;
            case R.id.btn_password:

                dialog = new BottomSheetDialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_password);
                dialog.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setDimAmount(0.5f);
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                pass_lama = (EditText) dialog.findViewById(R.id.edit_pw_lama);
                pass_baru = (EditText) dialog.findViewById(R.id.edit_pw_baru);
                final EditText pass_baru2 = (EditText) dialog.findViewById(R.id.edit_konfirmasi);
                pass_lama.requestFocus();
                Button btn_edit = (Button) dialog.findViewById(R.id.btn_edit_pw);
                ImageView btn_close = (ImageView) dialog.findViewById(R.id.btn_close);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pass_lama.getText().toString().equals("")) {
                            //  Toast.makeText(menu_profil_pejabat_pejabat.this, "Password lama tidak boleh kosong", Toast.LENGTH_SHORT);

                            new GlideToast.makeToast(getActivity(), "Password lama tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();
                            pass_lama.requestFocus();
                        } else if (pass_baru.getText().toString().trim().equals("")) {
                            new GlideToast.makeToast(getActivity(), "Password baru tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                            // Toast.makeText(menu_profil_pejabat_pejabat.this, "Password baru tidak boleh kosong", Toast.LENGTH_SHORT);
                            pass_baru.requestFocus();
                        } else if (pass_baru2.getText().toString().trim().equals("")) {
                            new GlideToast.makeToast(getActivity(), "Password konfirmasi tidak boleh kosong", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();

                            //Toast.makeText(menu_profil_pejabat_pejabat.this, "Password konfirmasi tidak boleh kosong", Toast.LENGTH_SHORT);
                            pass_baru2.requestFocus();
                        } else if (!pass_baru.getText().toString().equals(pass_baru2.getText().toString())) {
                            new GlideToast.makeToast(getActivity(), "pastikan password baru dan konfirmasi password sama !", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();


                            // Toast.makeText(menu_profil_pejabat_pejabat.this, "pastikan password baru dan konfirmasi password sama !", Toast.LENGTH_SHORT);
                            pass_baru2.requestFocus();
                        } else if (pass_baru.getText().toString().trim().length() < 6) {
                            //  Toast.makeText(menu_profil_pejabat_pejabat.this, "Minimal Password Baru 6 Karketr", Toast.LENGTH_SHORT);
                            new GlideToast.makeToast(getActivity(), "Minimal Password Baru 6 Karketr", GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST, GlideToast.CENTER).show();


                            pass_baru.requestFocus();
                        } else {

                            aksi countryPresenter = new aksi(null, getActivity());
                            countryPresenter.update_password(pass_lama.getText().toString().trim(), pass_baru.getText().toString().trim(), progressDialog);



                        }


                    }
                });

                dialog.show();
                break;
            case R.id.btn_no_hp:

                dialog = new BottomSheetDialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_no_hp);
                dialog.setCancelable(true);

                lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setDimAmount(0.5f);



                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                no_hp = (EditText) dialog.findViewById(R.id.edit_no_hp);

                no_hp.setText(txtNoHp.getText());



                no_hp.requestFocus();
                btn_edit = (Button) dialog.findViewById(R.id.btn_edit);
                btn_close = (ImageView) dialog.findViewById(R.id.btn_close);
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aksi countryPresenter = new aksi(null,getActivity());
                        countryPresenter.edit_nohp(progressDialog,no_hp.getText().toString().trim(),id_ibu);

                    }
                });

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
                break;
            case R.id.btn_email:
                dialog = new BottomSheetDialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_no_hp);
                dialog.setCancelable(true);

                lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setDimAmount(0.5f);



                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                no_hp = (EditText) dialog.findViewById(R.id.edit_no_hp);


                no_hp.setText(txtEmail.getText());


                no_hp.requestFocus();
                no_hp.setHint("dasdasdsadasd");
                btn_edit = (Button) dialog.findViewById(R.id.btn_edit);
                btn_close = (ImageView) dialog.findViewById(R.id.btn_close);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
                break;
        }
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Img"+ Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }
    void foto(Uri uri) {
        Glide.with(getActivity())
                .load(uri)
                .apply(new RequestOptions()
                        .fitCenter()
                        .circleCrop()
                        .error(R.drawable.ic_baseline_arrow_forward_ios_24))
                .into(imgProfil);
    }
    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imgProfil.setImageBitmap(decoded);

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Log.i(TAG, "getStringImage: "+encodedImage);
        return encodedImage;
    }
    @SuppressLint({"MissingSuperCall", "RestrictedApi"})
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {


                    BitmapFactory.Options bounds = new BitmapFactory.Options();
                    bounds.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imageStoragePath, bounds);

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(imageStoragePath, opts);
                    ExifInterface exif = new ExifInterface(imageStoragePath);
                    String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

                    int rotationAngle = 0;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);


                    setToImageView(getResizedBitmap(rotatedBitmap, max_resolution_image));
                    imgProfil.setImageBitmap(rotatedBitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
        }

        if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
            try {


                // mengambil gambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                getStringImage(decoded);
                Uri tempUri = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tempUri = getImageUri(getActivity().getApplicationContext(), bitmap);
                }
                foto(tempUri);

                file = FileUtils.getFile(getContext(), tempUri);
                int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                Log.i("isi_file", "onCaptureImageResult1: " + file.getName() + " " + file_size);
                setToImageView(getResizedBitmap(decoded, max_resolution_image));
                Log.i("isi_foto_galeri", "onActivityResult: "+file.getName());








                MultipartBody.Part body = null;
                RequestBody id_user = createPartFromString("" + Guru.getString("id_user", "false"));
                RequestBody requestFile;
                if (file == null) {

                    Toast.makeText(getActivity(), "File Kosong", Toast.LENGTH_SHORT).show();
                } else {



                    compressedImage = new Compressor(getActivity())
                            .setQuality(50)
                            .compressToFile(file);

                    int file_ke_2 = Integer.parseInt(String.valueOf(compressedImage.length() / 1024));
                    Log.i("ukuran_foto", "onActivityResult: "+file_size+" "+file_ke_2);
                    requestFile = RequestBody.create(MediaType.parse("image/*"), compressedImage);
                    body = MultipartBody.Part.createFormData("foto", compressedImage.getName(), requestFile);
                }
                aksi countryPresenter = new aksi(null,getActivity());
                countryPresenter.edit_foto(body,progressDialog);

                //  foto(tempUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
        MenuItem refres = menu.findItem(R.id.refres);
        refres.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bittom_dialog = new BottomSheetDialog(getActivity());
                bittom_dialog.setTitle("Login");
                bittom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                bittom_dialog.setContentView(R.layout.dialog_uabah_profil);
                bittom_dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(dialog.getWindow().getAttributes());
                bittom_dialog.getWindow().setAttributes(lp);
                bittom_dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                bittom_dialog.getWindow().setDimAmount(0.5f);
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                ImageView btn_close = (ImageView) bittom_dialog.findViewById(R.id.btn_close);
                ImageView btn_camera = (ImageView) bittom_dialog.findViewById(R.id.btn_camera);
                ImageView btn_galeri = (ImageView) bittom_dialog.findViewById(R.id.btn_galeri);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bittom_dialog.dismiss();

                    }
                });

                btn_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CameraCapture_new dialog1 = new CameraCapture_new();
                        dialog1.setTargetFragment(menu_profil.this, 22); // in case of fragment to activity communication we do not need this line. But must write this i case of fragment to fragment communication
                        dialog1.show(getFragmentManager(), "fragment_camera");
                        bittom_dialog.dismiss();
                    }
                });

                btn_galeri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, SELECT_FILE);
                        bittom_dialog.dismiss();

                    }
                });


                bittom_dialog.show();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSimpanClick(Jpeg data, File file) {
        try {
            file = file1;
            String filePath = file1.getPath();
            decoded = BitmapFactory.decodeFile(filePath);
            imgProfil.setImageBitmap(decoded);
            int file_size = Integer.parseInt(String.valueOf(file1.length() / 1024));
            Log.i("isi_foto", "onSimpanClick: " + file1.getName() + " " + file_size);
            Uri tempUri = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tempUri = getImageUri(getActivity().getApplicationContext(), decoded);
            }
            foto(tempUri);

            MultipartBody.Part body = null;
            RequestBody id_user = createPartFromString("" + Guru.getString("id_user", "false"));
            RequestBody requestFile;
            if (file == null) {
                Toast.makeText(getActivity(), "File Kosong", Toast.LENGTH_SHORT).show();
            } else {
                compressedImage = new Compressor(getActivity())
                        .setQuality(50)
                        .compressToFile(file);

                requestFile = RequestBody.create(MediaType.parse("image/*"), compressedImage);
                body = MultipartBody.Part.createFormData("foto", compressedImage.getName(), requestFile);
            }

            aksi countryPresenter = new aksi(null,getActivity());
            countryPresenter.edit_foto(body,progressDialog);

        } catch (Exception e) {
            Log.i("eeee", "onSimpanClick: " + e);

        }
    }

    @Override
    public void data_user(List<IsiItem_user> data_user) {
        try {
            for (int i = 0; i < data_user.size(); i++) {
                txtEmail.setText(data_user.get(i).getEmail());
                txtNama.setText(data_user.get(i).getName());
                txtNoHp.setText(data_user.get(i).getTelpon());
                txtAlamat.setText(data_user.get(i).getAlamat());
                String server = Guru.getString("data_foto", "default value");
                Glide.with(this)
                        .load("http://192.168.1.71/gis_badminton/public/foto_profil/"+ data_user.get(i).getFoto())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                ProgressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                ProgressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .circleCrop()
                        .into(imgProfil);



            }
            if (data_user.size() == 0) {
                ProgressBar.setVisibility(View.VISIBLE);
            } else {
                ProgressBar.setVisibility(View.GONE);

            }
        }catch (Exception e){

        }
    }
}