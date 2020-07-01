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
import com.cstup.e_mandi.model.Get.Order;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class orderItemsAdapter extends RecyclerView.Adapter<orderItemsAdapter.ViewHolder> {
    private ArrayList<Order> orders;
    public orderItemsAdapter(ArrayList<Order> orders){
        this.orders = orders;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_list_item , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setCropImage(holder , position);
        setCropName(holder , position);
        setItemQty(holder , position);
        setItemCost(holder , position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView cropImage;
        private TextView cropName;
        private TextView itemQty;
        private TextView itemCost;
        private ViewHolder(View v){
            super(v);
            cropImage = v.findViewById(R.id.crop_image);
            cropName = v.findViewById(R.id.crop_name);
            itemQty = v.findViewById(R.id.item_qty);
            itemCost = v.findViewById(R.id.item_cost);
        }
    }

    private void setCropImage(ViewHolder holder , int pos){
        String url = orders.get(pos).getCropImage();
        if(url == null)
            url = orders.get(pos).getCropTypeImage();
        if(url != null)
            Glide.with(holder.itemView.getContext()).load(Uri.parse(url)).centerCrop().into(holder.cropImage);
    }

    private void setCropName(ViewHolder holder , int pos){
        String name = orders.get(pos).getCropName();
        if(name != null)
            holder.cropName.setText(name);
    }

    private void setItemQty(ViewHolder holder , int pos){
        double qty = orders.get(pos).getItemQty();
        String type = constants.TYPE_KG;
        if(qty > 100){
            qty /= 100;
            type = constants.TYPE_QUINTOL;
        }
        String qty_txt = holder.itemView.getContext().getString(R.string.item_qty) + String.format("%.2f" , qty) + type;
        holder.itemQty.setText(qty_txt);
    }

    private void setItemCost(ViewHolder holder , int pos){
        String item_cost_txt = holder.itemView.getContext().getString(R.string.item_cost)
                + holder.itemView.getContext().getString(R.string.rupee)
                + orders.get(pos).getItemFreezedCost();
        holder.itemCost.setText(item_cost_txt);
    }
}
