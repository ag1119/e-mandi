package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("issue_timestamp")
    @Expose
    private String issueTimestamp;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("crop_id")
    @Expose
    private Integer cropId;
    @SerializedName("item_qty")
    @Expose
    private Double itemQty;
    @SerializedName("item_freezed_cost")
    @Expose
    private Double itemFreezedCost;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getIssueTimestamp() {
        return issueTimestamp;
    }

    public void setIssueTimestamp(String issueTimestamp) {
        this.issueTimestamp = issueTimestamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

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

    public Double getItemFreezedCost() {
        return itemFreezedCost;
    }

    public void setItemFreezedCost(Double itemFreezedCost) {
        this.itemFreezedCost = itemFreezedCost;
    }
}
