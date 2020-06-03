package com.cstup.e_mandi.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.CropType;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.ViewHolder> {

    public interface EventListener{
        void startActivityAddCropCredentials();
    }

    private EventListener listener;
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private ViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.crop_name);
            imageView = v.findViewById(R.id.crop_image);
        }
    }

    public homeAdapter(EventListener listener){this.listener = listener;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homepage_list_item , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CropType cropType = TempCache.HOMEPAGE_LIST_ITEMS.get(position);

        if(cropType != null){
            holder.textView.setText(cropType.getCropTypeName());
            Glide.with(holder.itemView.getContext())
            .load(Uri.parse(cropType.getCropTypeImage())).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(v -> {
            TempCache.CROP_TYPE_ITEM = TempCache.HOMEPAGE_LIST_ITEMS.get(position);

            assert cropType != null;
            TempCache.CROP_TYPE_ID = cropType.getCropTypeId();

            String user_type = holder.itemView.getContext()
                    .getSharedPreferences(constants.SHARED_PREF_NAME , Context.MODE_PRIVATE)
                    .getString(constants.USER_TYPE , null);

            if(user_type != null){
                if(user_type.equals(constants.USER))
                    TempCache.genericMethods.setFragment(constants.CROP_VENDORS_FRAGMENT , holder.itemView.getContext() ,
                            constants.CROP_VENDORS_TAG , R.id.frame);
                else{
                    listener.startActivityAddCropCredentials();
                }

            }

        });
    }

    @Override
    public int getItemCount() {
        return TempCache.HOMEPAGE_LIST_ITEMS.size();
    }

}
