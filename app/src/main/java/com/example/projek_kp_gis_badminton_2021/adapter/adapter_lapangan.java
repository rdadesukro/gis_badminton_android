package com.example.projek_kp_gis_badminton_2021.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.projek_kp_gis_badminton_2021.R;
import com.example.projek_kp_gis_badminton_2021.menu.menu_jenis_lapangan;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;
import com.github.squti.guru.Guru;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import maes.tech.intentanim.CustomIntent;


public class adapter_lapangan extends RecyclerView.Adapter<adapter_lapangan.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;
    String lat_new,lng_new;
    String lat,lng;
    String jenis;
    private int animation_type = 0;
    private List<IsiItem_lapangan> mList ;
    private Context ctx;
    private OnImageClickListener onImageClickListener;
    public adapter_lapangan(Context ctx, List<IsiItem_lapangan> mList , int animation_type, OnImageClickListener onImageClickListener) {
        this.jenis = jenis;
        this.animation_type = animation_type;
        this.mList = mList;
        this.ctx = ctx;
        this.onImageClickListener = onImageClickListener;

    }
    public interface OnImageClickListener {
        void onImageClick(int id,
                          String nama,
                          String alamat,
                          String phone,
                          String foto,
                          double lat,
                          double lng,
                          int status);
    }



    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        HolderData holder;
            layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_lapangan,parent, false);
            holder = new HolderData(layout);

            return holder;
    }


    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        final IsiItem_lapangan dm = mList.get(position);
        holder.txt_nama.setText(dm.getNama());
        holder.txt_alamat.setText(dm.getAlamat());
//        if (!dm.getRaiting()=null){
//            holder.ratingBar.setRating(0);
//
//        }else {
            holder.ratingBar.setRating(dm.getRaiting());
       // }

        if (dm.getStatus()==1&&dm.getStatus_all()>0){
            holder.txt_status.setBackgroundResource(R.drawable.bg_status_ready);
            holder.txt_status.setText("Ready");
        }else {
            holder.txt_status.setBackgroundResource(R.drawable.bg_status_penuh);
            holder.txt_status.setText("Penuh");
        }
        Glide.with(ctx)
                .load("http://192.168.1.71/gis_badminton/public/foto_lapangan/"+dm.getFoto())
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
                .into(holder.img_foto);

        holder.dm = dm;
        setAnimation(holder.itemView,position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder {





        @BindView(R.id.txt_nama)
        TextView txt_nama;

        @BindView(R.id.txt_alamat)
        TextView txt_alamat;

        @BindView(R.id.txt_status)
        TextView txt_status;

        @BindView(R.id.img_foto)
        ImageView img_foto;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;




        IsiItem_lapangan dm;


        public HolderData(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                   String role = Guru.getString("role", "false");
                    if (role.equals("1")){

                    }else {
                        onImageClickListener.onImageClick(dm.getId(),
                                dm.getNama(),
                                dm.getAlamat(),dm.getPhone(),dm.getFoto(),dm.getLat(),dm.getLng(),dm.getStatus());

                    }
                       return false;
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onImageClickListener.onImageClick(String.valueOf(dm.getId()), String.valueOf(dm.getDataibuId()));


                    String role = Guru.getString("role", "false");
                    if (role.equals("1")){
                        if (dm.getStatus()==1&&dm.getStatus_all()>0){
                            Intent goInput = new Intent(ctx, menu_jenis_lapangan.class);
                            Guru.putString("id_lapangan", String.valueOf(dm.getId()));
                            Guru.putString("nama_lapangan", String.valueOf(dm.getNama()));
                            ctx.startActivity(goInput);
                            CustomIntent.customType(ctx, "bottom-to-up");
                        }else {
                            Toast.makeText(ctx, "MAAF LAPANGAN PENUH", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Intent goInput = new Intent(ctx, menu_jenis_lapangan.class);
                        Guru.putString("id_lapangan", String.valueOf(dm.getId()));
                        ctx.startActivity(goInput);
                        CustomIntent.customType(ctx, "bottom-to-up");

                    }





                }
            });

//
        }


    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
           // ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
}
