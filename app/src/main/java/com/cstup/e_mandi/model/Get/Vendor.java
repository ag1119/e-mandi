package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vendor {
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("contact")
    @Expose
    private Long contact;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("pin_code")
    @Expose
    private Integer pinCode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("reg_timestamp")
    @Expose
    private String regTimestamp;
    @SerializedName("device_fcm_token")
    @Expose
    private String deviceFcmToken;
    @SerializedName("orders_recieved")
    @Expose
    private Integer ordersRecieved;
    @SerializedName("orders_cancelled_by_user")
    @Expose
    private Integer ordersCancelledByUser;
    @SerializedName("orders_cancelled_by_vendor")
    @Expose
    private Integer ordersCancelledByVendor;
    @SerializedName("order_domino_number")
    @Expose
    private Integer orderDominoNumber;
    @SerializedName("defaulter_status")
    @Expose
    private String defaulterStatus;
    @SerializedName("defaulter_timestamp")
    @Expose
    private String defaulterTimestamp;
    @SerializedName("defaulter_period")
    @Expose
    private String defaulterPeriod;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRegTimestamp() {
        return regTimestamp;
    }

    public void setRegTimestamp(String regTimestamp) {
        this.regTimestamp = regTimestamp;
    }

    public String getDeviceFcmToken() {
        return deviceFcmToken;
    }

    public void setDeviceFcmToken(String deviceFcmToken) {
        this.deviceFcmToken = deviceFcmToken;
    }

    public Integer getOrdersRecieved() {
        return ordersRecieved;
    }

    public void setOrdersRecieved(Integer ordersRecieved) {
        this.ordersRecieved = ordersRecieved;
    }

    public Integer getOrdersCancelledByUser() {
        return ordersCancelledByUser;
    }

    public void setOrdersCancelledByUser(Integer ordersCancelledByUser) {
        this.ordersCancelledByUser = ordersCancelledByUser;
    }

    public Integer getOrdersCancelledByVendor() {
        return ordersCancelledByVendor;
    }

    public void setOrdersCancelledByVendor(Integer ordersCancelledByVendor) {
        this.ordersCancelledByVendor = ordersCancelledByVendor;
    }

    public Integer getOrderDominoNumber() {
        return orderDominoNumber;
    }

    public void setOrderDominoNumber(Integer orderDominoNumber) {
        this.orderDominoNumber = orderDominoNumber;
    }

    public String getDefaulterStatus() {
        return defaulterStatus;
    }

    public void setDefaulterStatus(String defaulterStatus) {
        this.defaulterStatus = defaulterStatus;
    }

    public String getDefaulterTimestamp() {
        return defaulterTimestamp;
    }

    public void setDefaulterTimestamp(String defaulterTimestamp) {
        this.defaulterTimestamp = defaulterTimestamp;
    }

    public String getDefaulterPeriod() {
        return defaulterPeriod;
    }

    public void setDefaulterPeriod(String defaulterPeriod) {
        this.defaulterPeriod = defaulterPeriod;
    }
}
