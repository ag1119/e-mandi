package com.cstup.e_mandi.utilities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cstup.e_mandi.Cache.MyCropsCache;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.environmentVariables.test.Crops;
import com.cstup.e_mandi.model.FragmentParams;
import com.cstup.e_mandi.model.Get.CropType;

import java.util.Objects;

public class GenericMethods {

    public void setFragment(FragmentParams params){
        FragmentManager fragmentManager = ((AppCompatActivity) params.getContext()).getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag(params.getTag()) == null)
            fragmentManager.beginTransaction().replace(params.getFrame_id() , params.getFragment() , params.getTag())
                    .addToBackStack(params.getTag()).commit();
        else{
            fragmentManager.popBackStackImmediate(params.getTag() , 0);
            fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag(params.getTag())))
                    .commit();
        }
        TempCache.SELECTED_FRAGMENT = params.getFragment();
        TempCache.SELECTED_TAG = params.getTag();

    }

    public void setFragment(Fragment fragment , Context context , String tag , int frame_id){

        MyCropsCache.IS_MY_CROP_CONTEXT = constants.FARMER_MY_CROP_FRAGMENT_TAG.equals(tag);

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag(tag) == null)
            fragmentManager.beginTransaction().replace(frame_id , fragment , tag)
                    .addToBackStack(tag).commit();
        else{
            fragmentManager.popBackStackImmediate(tag , 0);
            fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag(tag)))
                    .commit();
        }
        TempCache.SELECTED_FRAGMENT = fragment;
        TempCache.SELECTED_TAG = tag;

    }
    public void setFragment(Fragment fragment , Context context , int frame_id){
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(frame_id , fragment).commit();
    }

    public QtyAndPrice getQtyAndPrice(String qty , String price){
        double q = Double.parseDouble(qty);
        double p = Double.parseDouble(price);
        QtyAndPrice qtyAndPrice = new QtyAndPrice();
        if(q > 100){
            qtyAndPrice.setType(constants.TYPE_QUINTOL);
            q /= 100;
            qtyAndPrice.setQty(String.format("%.2f" , q));
            p *= 100;
            qtyAndPrice.setPrice(String.format("%.2f" , p));
        }
        else {
            qtyAndPrice.setType(constants.TYPE_KG);
            qtyAndPrice.setQty(String.format("%.2f" , q));
            qtyAndPrice.setPrice(String.format("%.2f" , p));
        }
        return qtyAndPrice;
    }

    public String getCropTypeImage(String crop_class , Integer crop_type_id){
        switch (crop_class){
            case constants.CropClass.VEGETABLES:
                for(CropType v : Crops.vegetables){
                    if(v.getCropTypeId().equals(crop_type_id))
                        return v.getCropTypeImage();
                }
                break;
            case constants.CropClass.GRAINS:
                for(CropType g : Crops.grains){
                    if(g.getCropTypeId().equals(crop_type_id))
                        return g.getCropTypeImage();
                }
                break;
            case constants.CropClass.FRUITS:
                for(CropType f : Crops.fruits){
                    if(f.getCropTypeId().equals(crop_type_id))
                        return f.getCropTypeImage();
                }
                break;
        }
        return null;
    }
}
