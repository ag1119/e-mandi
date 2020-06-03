package com.cstup.e_mandi.model.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("crop_id")
    @Expose
    private Integer crop_id;
    @SerializedName("item_qty")
    @Expose
    private Double item_qty;

    public Integer getCrop_id() {
        return crop_id;
    }

    public void setCrop_id(Integer crop_id) {
        this.crop_id = crop_id;
    }

    public Double getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(Double item_qty) {
        this.item_qty = item_qty;
    }
}

