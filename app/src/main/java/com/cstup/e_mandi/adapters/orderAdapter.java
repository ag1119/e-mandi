package com.cstup.e_mandi.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.Cache.OrdersCache;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.Order;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ViewHolder>{
    public interface EventListener{
        void showOrderDetails(Order order);
    }
    private ArrayList<Order> orders;
    private EventListener listener;
    public orderAdapter(ArrayList<Order> orders , EventListener listener){
        this.orders = orders;
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout contentContainer;
        private CircleImageView cropImage;
        private TextView cropName;
        private TextView orderQty;
        private TextView orderCost;
        private LinearLayout orderStatusContainer;
        private TextView orderConfirmedLine;
        private TextView orderConfirmedCircle;
        private TextView orderDeliveredLine;
        private TextView orderDeliveredCircle;
        private TextView cancelledOrderTv;
        private TextView deliveryAddress;

        private View loadingPanel;

        private ViewHolder(View view){
            super(view);
            contentContainer = view.findViewById(R.id.content_container);
            cropImage = view.findViewById(R.id.crop_image);
            cropName = view.findViewById(R.id.crop_name);
            orderQty = view.findViewById(R.id.order_qty);
            orderCost = view.findViewById(R.id.order_cost);
            orderStatusContainer = view.findViewById(R.id.status_container);
            orderConfirmedLine = view.findViewById(R.id.order_confirmed_line);
            orderConfirmedCircle = view.findViewById(R.id.order_confirmed_circle);
            orderDeliveredLine = view.findViewById(R.id.order_delivered_line);
            orderDeliveredCircle = view.findViewById(R.id.order_delivered_circle);
            cancelledOrderTv = view.findViewById(R.id.cancelled_order_tv);
            deliveryAddress = view.findViewById(R.id.delivery_address);

            loadingPanel = view.findViewById(R.id.loading_panel);

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
        if(orders.get(position).getCropName() != null){
            hideLoadingPanel(holder);
            showContentContainer(holder);
            setCropImage(holder , position);
            setCropName(holder , position);
            setOrderQty(holder , position);
            setOrderCost(holder , position);
            setOrderStatus(holder , position);
            setDeliveryAddress(holder , position);
            holder.itemView.setOnClickListener(v -> {
                OrdersCache.selectedOrder = orders.get(position);
                listener.showOrderDetails(orders.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
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
        String status = orders.get(pos).getOrderStatus();
        switch (status){
            case OrdersCache.STATUS_CONFIRMED:
                fillOrderedConfirmedCircle(holder);
                fillOrderedConfirmedLine(holder);
                break;

            case OrdersCache.STATUS_DELIVERED:
                fillOrderedDeliveredLine(holder);
                fillOrderedDeliveredCircle(holder);
                break;
            case OrdersCache.STATUS_CANCELLED:
                hideOrderStatusContainer(holder);
                showCancelledOrderTv(holder);
                break;
        }
    }

    private void hideLoadingPanel(ViewHolder holder){
        holder.loadingPanel.setVisibility(View.GONE);
    }

    private void showContentContainer(ViewHolder holder){
        holder.contentContainer.setVisibility(View.VISIBLE);
    }

    private void fillOrderedDeliveredLine(ViewHolder holder){
        holder.orderDeliveredLine.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.green));
    }

    private void fillOrderedDeliveredCircle(ViewHolder holder){
        holder.orderDeliveredCircle.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.green));
    }

    private void fillOrderedConfirmedLine(ViewHolder holder){
        holder.orderConfirmedLine.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.green));
    }

    private void fillOrderedConfirmedCircle(ViewHolder holder){
        holder.orderConfirmedCircle.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.green));
    }

    private void setDeliveryAddress(ViewHolder holder , int pos){
        String address = orders.get(pos).getDeliveryAddress();
        if(address != null)
            holder.deliveryAddress.setText("Delivery Address : " + address);
    }

    private void hideOrderStatusContainer(ViewHolder holder){
        holder.orderStatusContainer.setVisibility(View.GONE);
    }

    private void showCancelledOrderTv(ViewHolder holder){
        holder.cancelledOrderTv.setVisibility(View.VISIBLE);
    }


}
