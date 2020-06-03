package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("device_fcm_token")
    @Expose
    private String deviceFcmToken;
    @SerializedName("contact")
    @Expose
    private Long contact;
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
    @SerializedName("reg_timestamp")
    @Expose
    private String regTimestamp;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("orders_issued")
    @Expose
    private Integer ordersIssued;
    @SerializedName("orders_cancelled_by_user")
    @Expose
    private Integer ordersCancelledByUser;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceFcmToken() {
        return deviceFcmToken;
    }

    public void setDeviceFcmToken(String deviceFcmToken) {
        this.deviceFcmToken = deviceFcmToken;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
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

    public String getRegTimestamp() {
        return regTimestamp;
    }

    public void setRegTimestamp(String regTimestamp) {
        this.regTimestamp = regTimestamp;
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

    public Integer getOrdersIssued() {
        return ordersIssued;
    }

    public void setOrdersIssued(Integer ordersIssued) {
        this.ordersIssued = ordersIssued;
    }

    public Integer getOrdersCancelledByUser() {
        return ordersCancelledByUser;
    }

    public void setOrdersCancelledByUser(Integer ordersCancelledByUser) {
        this.ordersCancelledByUser = ordersCancelledByUser;
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
