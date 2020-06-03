package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCrops {
    @SerializedName("crop_id")
    @Expose
    private Integer cropId;
    @SerializedName("crop_type_id")
    @Expose
    private Integer cropTypeId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("crop_qty")
    @Expose
    private Double cropQty;
    @SerializedName("crop_price")
    @Expose
    private Double cropPrice;
    @SerializedName("crop_name")
    @Expose
    private String cropName;
    @SerializedName("packed_timestamp")
    @Expose
    private String packedTimestamp;
    @SerializedName("exp_timestamp")
    @Expose
    private String expTimestamp;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("crop_type_name")
    @Expose
    private String cropTypeName;
    @SerializedName("crop_class")
    @Expose
    private String cropClass;
    @SerializedName("crop_type_image")
    @Expose
    private String cropTypeImage;
    @SerializedName("crop_image_id")
    @Expose
    private Integer cropImageId;
    @SerializedName("crop_image")
    @Expose
    private String cropImage;

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Integer getCropTypeId() {
        return cropTypeId;
    }

    public void setCropTypeId(Integer cropTypeId) {
        this.cropTypeId = cropTypeId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Double getCropQty() {
        return cropQty;
    }

    public void setCropQty(Double cropQty) {
        this.cropQty = cropQty;
    }

    public Double getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(Double cropPrice) {
        this.cropPrice = cropPrice;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getPackedTimestamp() {
        return packedTimestamp;
    }

    public void setPackedTimestamp(String packedTimestamp) {
        this.packedTimestamp = packedTimestamp;
    }

    public String getExpTimestamp() {
        return expTimestamp;
    }

    public void setExpTimestamp(String expTimestamp) {
        this.expTimestamp = expTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCropTypeName() {
        return cropTypeName;
    }

    public void setCropTypeName(String cropTypeName) {
        this.cropTypeName = cropTypeName;
    }

    public String getCropClass() {
        return cropClass;
    }

    public void setCropClass(String cropClass) {
        this.cropClass = cropClass;
    }

    public String getCropTypeImage() {
        return cropTypeImage;
    }

    public void setCropTypeImage(String cropTypeImage) {
        this.cropTypeImage = cropTypeImage;
    }

    public Integer getCropImageId() {
        return cropImageId;
    }

    public void setCropImageId(Integer cropImageId) {
        this.cropImageId = cropImageId;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;

    }
}