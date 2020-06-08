package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("crop_id")
    @Expose
    private Integer cropId;
    @SerializedName("crop_price")
    @Expose
    private Double cropPrice;
    @SerializedName("crop_name")
    @Expose
    private String cropName;
    @SerializedName("crop_image")
    @Expose
    private String cropImage;
    @SerializedName("crop_type_image")
    @Expose
    private String cropTypeImage;
    @SerializedName("item_qty")
    @Expose
    private Double itemQty;
    @SerializedName("item_freezed_cost")
    @Expose
    private Double itemFreezedCost;
    @SerializedName("issue_timestamp")
    @Expose
    private String issueTimestamp;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
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

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public String getCropTypeImage() {
        return cropTypeImage;
    }

    public void setCropTypeImage(String cropTypeImage) {
        this.cropTypeImage = cropTypeImage;
    }

    public Double getItemQty() {
        return itemQty;
    }

    public void setItemQty(Double itemQty) {
        this.itemQty = itemQty;
    }

    public Double getItemFreezedCost() {
        return itemFreezedCost;
    }

    public void setItemFreezedCost(Double itemFreezedCost) {
        this.itemFreezedCost = itemFreezedCost;
    }

    public String getIssueTimestamp() {
        return issueTimestamp;
    }

    public void setIssueTimestamp(String issueTimestamp) {
        this.issueTimestamp = issueTimestamp;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
