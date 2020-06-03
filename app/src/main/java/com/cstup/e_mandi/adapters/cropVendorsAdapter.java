package com.cstup.e_mandi.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.Crop;
import com.cstup.e_mandi.model.Get.MyCrops;
import com.cstup.e_mandi.utilities.QtyAndPrice;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class cropVendorsAdapter extends RecyclerView.Adapter<cropVendorsAdapter.ViewHolder>{

    public interface MyCropsEventListener{
        void showEditContainer();
        void showBtnContainer();
        void hideRecyclerView();
    }
    private ArrayList<Crop> list;
    private ArrayList<MyCrops> myCrops;
    private MyCropsEventListener listener;
    public cropVendorsAdapter(ArrayList<Crop> list){this.list = list;}

    public cropVendorsAdapter(ArrayList<MyCrops> myCrops , MyCropsEventListener listener){
        this.myCrops = myCrops;
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView crop_image;
        private TextView price;
        private TextView govt_price;
        private TextView crop_name;
        private TextView crop_desc;
        private TextView remaining_stock;
        private ImageView editMyCrop;
        private ViewHolder(View v){
            super(v);
            crop_image = v.findViewById(R.id.crop_image);
            price = v.findViewById(R.id.price);
            govt_price = v.findViewById(R.id.govt_price);
            crop_name = v.findViewById(R.id.crop_name);
            crop_desc = v.findViewById(R.id.crop_desc);
            remaining_stock = v.findViewById(R.id.remaining_stock);
            editMyCrop = v.findViewById(R.id.edit_my_crop);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_vendors_list_item, parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boolean isUser = isUser();
        if(!isUser)
            holder.editMyCrop.setVisibility(View.VISIBLE);

        String url = getUrl(position , isUser);
        String name = getName(position , isUser);
        String price = getPrice(position , isUser);
        String desc = getDesc(position , isUser);
        String stock = getStock(position , isUser);

        QtyAndPrice qtyAndPrice = TempCache.genericMethods.getQtyAndPrice(stock , price);
        if(url != null)
            Glide.with(holder.itemView.getContext()).load(Uri.parse(url))
                .centerCrop().into(holder.crop_image);

        assert name != null;
        holder.crop_name.setText(name);

        String price_text = qtyAndPrice.getPrice() + "/" + qtyAndPrice.getType();
        holder.price.setText(price_text);
        holder.crop_desc.setText(desc);

        String stock_text = qtyAndPrice.getQty()  + qtyAndPrice.getType();
        holder.remaining_stock.setText(constants.REMAINING_STOCK + stock_text);

        //holder.govt_price.setText();
        setViewsColor(TempCache.CROP_CATEGORY , holder);

        holder.itemView.setOnClickListener(v -> {
            if(isUser){
            TempCache.crop = list.get(position);
            TempCache.genericMethods.setFragment(constants.CROP_DETAILS_FRAGMENT , holder.itemView.getContext() ,
                    constants.CROP_DETAILS_TAG , R.id.frame);
            }
        });

        holder.editMyCrop.setOnClickListener(v -> {
            TempCache.SELECTED_MY_CROP_ITEM = myCrops.get(position);
            listener.hideRecyclerView();
            listener.showEditContainer();
            listener.showBtnContainer();
        });
    }

    @Override
    public int getItemCount() {
        if(TempCache.USER_TYPE.equals(constants.USER))
            return list.size();
        else
            return myCrops.size();
    }



    private void setViewsColor(int crop_category , ViewHolder holder){
        switch (crop_category){
            case constants.CropCategory.VEGETABLE:
                holder.govt_price.setTextColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.green));
                break;
            case constants.CropCategory.GRAIN:
                holder.govt_price.setTextColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.wheat));
                break;
            case constants.CropCategory.FRUIT:
                holder.govt_price.setTextColor(ContextCompat.getColor(holder.itemView.getContext() , R.color.apple));
                break;
        }
    }

    private String getUrl(int pos , boolean isUser){
        if(isUser)
            return list.get(pos).getCropImage();
        else
            return myCrops.get(pos).getCropImage();
    }

    private String getName(int pos , boolean isUser){
        if(isUser)
            return list.get(pos).getCropName();
        else
            return myCrops.get(pos).getCropName();
    }

    private String getPrice(int pos , boolean isUser){
        if(isUser)
            return list.get(pos).getCropPrice()+"";
        else
            return myCrops.get(pos).getCropPrice()+"";
    }

    private String getDesc(int pos , boolean isUser){
        if(isUser)
            return list.get(pos).getDescription();
        else
            return myCrops.get(pos).getDescription();
    }

    private String getStock(int pos , boolean isUser){
        if(isUser)
            return list.get(pos).getCropQty()+"";
        else
            return myCrops.get(pos).getCropQty()+"";
    }

    private boolean isUser(){
        return TempCache.USER_TYPE.equals(constants.USER);
    }

}
