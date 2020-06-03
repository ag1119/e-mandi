package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("crop_id")
    @Expose
    private Integer cropId;
    @SerializedName("crop_type_id")
    @Expose
    private Integer cropTypeId;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("item_qty")
    @Expose
    private Double itemQty;
    @SerializedName("item_cost")
    @Expose
    private Double itemCost;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("crop_qty")
    @Expose
    private Integer cropQty;
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
    @SerializedName("item_price")
    @Expose
    private Double itemPrice;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getItemQty() {
        return itemQty;
    }

    public void setItemQty(Double itemQty) {
        this.itemQty = itemQty;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getCropQty() {
        return cropQty;
    }

    public void setCropQty(Integer cropQty) {
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

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
