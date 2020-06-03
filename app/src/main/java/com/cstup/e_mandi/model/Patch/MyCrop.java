package com.cstup.e_mandi.model.Patch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCrop {
    @SerializedName("changeInQty")
    @Expose
    private Double changeInQty;

    @SerializedName("crop_name")
    @Expose
    private String cropName;

    @SerializedName("crop_type_id")
    @Expose
    private Integer cropTypeId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("crop_price")
    @Expose
    private Double cropPrice;

    public Double getChangeInQty() {
        return changeInQty;
    }

    public void setChangeInQty(Double changeInQty) {
        this.changeInQty = changeInQty;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public Integer getCropTypeId() {
        return cropTypeId;
    }

    public void setCropTypeId(Integer cropTypeId) {
        this.cropTypeId = cropTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(Double cropPrice) {
        this.cropPrice = cropPrice;
    }
}
