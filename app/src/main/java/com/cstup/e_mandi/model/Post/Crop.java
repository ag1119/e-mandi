package com.cstup.e_mandi.model.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crop {
    @SerializedName("crop_qty")
    @Expose
    private Double cropQty;
    @SerializedName("crop_name")
    @Expose
    private String cropName;
    @SerializedName("crop_type_id")
    @Expose
    private Integer cropTypeId;

    @SerializedName("crop_price")
    @Expose
    private Double cropPrice;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("crop_images")
    @Expose
    private List<String> cropImages;

    public Double getCropQty() {
        return cropQty;
    }

    public void setCropQty(Double cropQty) {
        this.cropQty = cropQty;
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

    public List<String> getCropImages() {
        return cropImages;
    }

    public void setCropImages(List<String> cropImages) {
        this.cropImages = cropImages;
    }
}
