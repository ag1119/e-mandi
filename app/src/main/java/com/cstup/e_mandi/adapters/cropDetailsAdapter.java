package com.cstup.e_mandi.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.Crop;
import com.cstup.e_mandi.utilities.QtyAndPrice;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class cropDetailsAdapter extends RecyclerView.Adapter<cropDetailsAdapter.ViewHolder> {
    public interface EventListener{
        void setViews_cropDetails();
        String getCropTypeImage(String crop_class , Integer crop_type_id);
    }
    private EventListener eventListener;
    private ArrayList<Crop> list;
    public cropDetailsAdapter(EventListener eventListener , ArrayList<Crop> list){
        this.eventListener = eventListener;
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView crop_image;
        private TextView price;
        //private TextView govt_price;
        private TextView crop_name;
        private TextView crop_desc;
        private TextView remaining_stock;

        private ViewHolder(View v){
            super(v);
            crop_image = v.findViewById(R.id.crop_image);
            price = v.findViewById(R.id.price);
            //govt_price = v.findViewById(R.id.govt_price);
            crop_name = v.findViewById(R.id.crop_name);
            crop_desc = v.findViewById(R.id.crop_desc);
            remaining_stock = v.findViewById(R.id.remaining_stock);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_vendors_list_item , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Crop crop = list.get(position);

        String url = crop.getCropImage();;
        if(url == null)
            url = eventListener.getCropTypeImage(crop.getCropClass() , crop.getCropTypeId());
        if(url != null)
            Glide.with(holder.itemView.getContext())
                .load(Uri.parse(url)).centerCrop().into(holder.crop_image);

        QtyAndPrice qtyAndPrice = TempCache.genericMethods.getQtyAndPrice(crop.getCropQty()+"" , crop.getCropPrice()+"");
        holder.price.setText(qtyAndPrice.getPrice() + "/" + qtyAndPrice.getType());
        holder.remaining_stock.setText(constants.REMAINING_STOCK + qtyAndPrice.getQty() + qtyAndPrice.getType());

        //holder.govt_price.setText();
        holder.crop_name.setText(crop.getCropName());

        if(crop.getDescription() != null)
            holder.crop_desc.setText(crop.getDescription());

        holder.itemView.setOnClickListener(v -> {
            TempCache.crop = crop;
            eventListener.setViews_cropDetails();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
