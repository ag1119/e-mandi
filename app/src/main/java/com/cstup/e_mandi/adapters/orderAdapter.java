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
import com.cstup.e_mandi.model.Get.PartialOrder;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ViewHolder>{
    public interface EventListener{
        void showOrderDetails(PartialOrder partialOrder);
    }
    private ArrayList<PartialOrder> partialOrders;
    private EventListener listener;
    public orderAdapter(ArrayList<PartialOrder> partialOrders , EventListener listener){
        this.partialOrders = partialOrders;
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView orderId;
        private TextView trackOrder;
        private ViewHolder(View view){
            super(view);
            orderId = view.findViewById(R.id.order_id);
            trackOrder = view.findViewById(R.id.track_order);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setOrderId(holder , position);
        holder.itemView.setOnClickListener(v -> {
            listener.showOrderDetails(partialOrders.get(position));
            OrdersCache.selectedOrderId = partialOrders.get(position).getOrderId();
        });
    }

    @Override
    public int getItemCount() {
        return partialOrders.size();
    }

    private void setOrderId(ViewHolder holder , int pos){
        holder.orderId.setText(partialOrders.get(pos).getOrderId() + "");
    }

}
