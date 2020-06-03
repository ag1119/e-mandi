package com.cstup.e_mandi.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.Orders;
import com.cstup.e_mandi.model.Order;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ViewHolder>{

    private ArrayList<Orders> orders;

    public orderAdapter(ArrayList<Orders> orders){
        this.orders = orders;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView cropImage;
        private TextView cropName;
        private TextView orderQty;
        private TextView orderCost;
        private TextView orderConfirmedLine;
        private TextView orderConfirmedCircle;
        private TextView orderDeliveredLine;
        private TextView orderDeliveredCircle;
        private TextView cancelOrderBtn;
        private ProgressBar progressBar;
        private ViewHolder(View view){
            super(view);
            cropImage = view.findViewById(R.id.crop_image);
            cropName = view.findViewById(R.id.crop_name);
            orderQty = view.findViewById(R.id.order_qty);
            orderCost = view.findViewById(R.id.order_cost);
            orderConfirmedLine = view.findViewById(R.id.order_confirmed_line);
            orderConfirmedCircle = view.findViewById(R.id.order_confirmed_circle);
            orderDeliveredLine = view.findViewById(R.id.order_delivered_line);
            orderDeliveredCircle = view.findViewById(R.id.order_delivered_circle);
            cancelOrderBtn = view.findViewById(R.id.cancel_order_btn);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private void showProgressHideBtn(ViewHolder holder){
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.cancelOrderBtn.setVisibility(View.GONE);
    }

    private void hideProgressShowBtn(ViewHolder holder){
        holder.progressBar.setVisibility(View.GONE);
        holder.cancelOrderBtn.setVisibility(View.VISIBLE);
    }

    private void setCropImage(ViewHolder holder , int pos){
        String url = "";
        Glide.with(holder.itemView.getContext()).load(Uri.parse(url)).centerCrop().into(holder.cropImage);
    }

    private void setCropName(ViewHolder holder , int pos){}

    private void setOrderQty(ViewHolder holder , int pos){
        double qty = orders.get(pos).getItemQty();
        String type = constants.TYPE_KG;
        if(qty > 100){
            qty /= 100;
            type = constants.TYPE_QUINTOL;
        }
        String qty_txt = holder.itemView.getContext().getString(R.string.order_qty) + String.format("%.2f" , qty) + type;
        holder.orderQty.setText(qty_txt);
    }

    private void setOrderCost(ViewHolder holder , int pos){
        String order_cost_txt = holder.itemView.getContext().getString(R.string.order_cost)
                                + holder.itemView.getContext().getString(R.string.rupee)
                                + orders.get(pos).getItemFreezedCost();
        holder.orderCost.setText(order_cost_txt);
    }

    private void setOrderStatus(ViewHolder holder , int pos){

    }
}
