package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crop {
    @SerializedName("crop_id")
    @Expose
    private Integer cropId;
    @SerializedName("crop_qty")
    @Expose
    private Integer cropQty;
    @SerializedName("crop_name")
    @Expose
    private String cropName;
    @SerializedName("crop_type_id")
    @Expose
    private Integer cropTypeId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("crop_class")
    @Expose
    private String cropClass;
    @SerializedName("crop_type_name")
    @Expose
    private String cropTypeName;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("crop_image")
    @Expose
    private String cropImage;
    @SerializedName("crop_price")
    @Expose
    private Integer cropPrice;

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getCropQty() {
        return cropQty;
    }

    public void setCropQty(Integer cropQty) {
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

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCropClass() {
        return cropClass;
    }

    public void setCropClass(String cropClass) {
        this.cropClass = cropClass;
    }

    public String getCropTypeName() {
        return cropTypeName;
    }

    public void setCropTypeName(String cropTypeName) {
        this.cropTypeName = cropTypeName;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public Integer getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(Integer cropPrice) {
        this.cropPrice = cropPrice;
    }
}
