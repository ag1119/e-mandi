package com.cstup.e_mandi.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;

import java.util.ArrayList;

public class addCropAdapter extends RecyclerView.Adapter<addCropAdapter.ViewHolder>{
    public interface EventListener{
        void setProgressBar(ProgressBar progressBar);
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView crop_image;
        private ImageView delete;
        private ViewHolder(View v){
            super(v);
            crop_image = v.findViewById(R.id.crop_image);
            delete = v.findViewById(R.id.delete); }
    }

    private ArrayList<Uri> list;

    public addCropAdapter(ArrayList<Uri> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_image , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(list.get(position)).centerCrop().into(holder.crop_image);

        holder.delete.setOnClickListener(v -> {
            list.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}


