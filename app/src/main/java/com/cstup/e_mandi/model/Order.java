package com.cstup.e_mandi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("crop_id")
    @Expose
    private Integer cropId;
    @SerializedName("item_qty")
    @Expose
    private Double itemQty;

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Double getItemQty() {
        return itemQty;
    }

    public void setItemQty(Double itemQty) {
        this.itemQty = itemQty;
    }
}
