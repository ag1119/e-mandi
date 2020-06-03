package com.cstup.e_mandi.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.controllers.cartController;
import com.cstup.e_mandi.model.Get.CartItem;
import com.cstup.e_mandi.utilities.QtyAndPrice;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> implements cartController.CartAdapterListener{

    @Override
    public void showToast(String msg) {
        listener.showToast(msg);
    }

    public interface cartEventListener{
        void updateTotalCost();
        void showToast(String msg);
    }

    private ArrayList<CartItem> cartItems;
    private cartEventListener listener;
    private cartController controller = new cartController(this);
    public cartAdapter(ArrayList<CartItem> cartItems , cartEventListener listener){
        this.cartItems = cartItems;
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView cropImage;
        private TextView cropName;
        private TextView cropPrice;
        private TextView cropDesc;
        //private TextView offer;
        //private TextView govtPrice;
        private TextView remainingStock;
        private ImageView decreaseQty;
        private TextView qty;
        private TextView kgOrQuintol;
        private ImageView increaseQty;
        private RadioButton kg;
        private RadioButton quintol;
        private ViewHolder(View v){
            super(v);
            cropImage = v.findViewById(R.id.crop_image);
            cropName = v.findViewById(R.id.crop_name);
            cropPrice = v.findViewById(R.id.price);
            cropDesc = v.findViewById(R.id.crop_desc);
            //offer = v.findViewById(R.id.offer);
            //govtPrice = v.findViewById(R.id.govt_price);
            remainingStock = v.findViewById(R.id.remaining_stock);
            decreaseQty = v.findViewById(R.id.decrease_qty);
            qty = v.findViewById(R.id.qty);
            kgOrQuintol = v.findViewById(R.id.kg_or_quintol);
            increaseQty = v.findViewById(R.id.increase_qty);
            kg = v.findViewById(R.id.kg);
            quintol = v.findViewById(R.id.quintol);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        //setting crop image
        String url = item.getCropImage();
        if(url != null)
            Glide.with(holder.itemView.getContext())
                .load(Uri.parse(url)).centerCrop().into(holder.cropImage);

        //setting name of crop
        holder.cropName.setText(item.getCropName() + "");

        //setting stock and price of crop
        String price = item.getCropPrice() + "";
        String quantity = item.getCropQty() + "";
        QtyAndPrice qtyAndPrice = TempCache.genericMethods.getQtyAndPrice(quantity , price);
        price = qtyAndPrice.getPrice() + "/" + qtyAndPrice.getType();
        quantity = constants.REMAINING_STOCK+qtyAndPrice.getQty() + qtyAndPrice.getType();
        holder.cropPrice.setText(price);
        holder.remainingStock.setText(quantity);

        //setting desc of crop
        holder.cropDesc.setText(item.getDescription() + "");

        //setting qty
        if(holder.kg.isChecked())
            holder.qty.setText(item.getItemQty() + " ");
        else{
            double q = item.getItemQty();
            q /= 100;
            holder.qty.setText(String.format("%.2f" , q)+" ");
        }

        //click event on decreaseQty
        holder.decreaseQty.setOnClickListener(v -> {
            double newQty = item.getItemQty();

            if(holder.kg.isChecked()){
                if(newQty > 1)
                    newQty -= 1;
                else
                    newQty = 0;
            }
            else{
                if(newQty > 100)
                    newQty -= 100;
                else
                    newQty = 0;
            }
            item.setItemQty(newQty);
            updateTotalCartValue();
            listener.updateTotalCost();

            double new_qty_txt = Double.parseDouble(holder.qty.getText() + "") - 1;
            if(new_qty_txt > 0){
                holder.qty.setText( String.format("%.2f" , new_qty_txt));
            }
            else{
                controller.deleteCartItem(item.getItemId());
                cartItems.remove(position);
                notifyDataSetChanged();
            }



        });

        //click event on increaseQty
        holder.increaseQty.setOnClickListener(v -> {
            double newQty = item.getItemQty();
            if(holder.kg.isChecked())
                newQty += 1;
            else
                newQty += 100;
            item.setItemQty(newQty);
            updateTotalCartValue();
            listener.updateTotalCost();

            double new_qty_txt = Double.parseDouble(holder.qty.getText() + "") + 1;
            holder.qty.setText(String.format("%.2f" , new_qty_txt));
        });

        //click event on kg radio button
        holder.kg.setOnClickListener(v -> {
            holder.qty.setText(String.format("%.2f" , item.getItemQty()));
            holder.kgOrQuintol.setText(" " + constants.TYPE_KG);
        });

        //click event on quintol radio button
        holder.quintol.setOnClickListener(v -> {
            holder.qty.setText(String.format("%.2f" , item.getItemQty() / 100));
            holder.kgOrQuintol.setText(" " + constants.TYPE_QUINTOL);
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateTotalCartValue(){
        double cart_value = 0;
        for(CartItem cartItem : cartItems)
            cart_value += cartItem.getCropPrice() * cartItem.getItemQty();
        TempCache.TOTAL_CART_VALUE = cart_value;
    }
}
