package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartialOrder {
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
}
