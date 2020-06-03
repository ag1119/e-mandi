package com.cstup.e_mandi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_SignUp {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("contact")
    @Expose
    private Long contact;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("device_fcm_token")
    @Expose
    private String device_fcm_token;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getDevice_fcm_token() {
        return device_fcm_token;
    }

    public void setDevice_fcm_token(String device_fcm_token) {
        this.device_fcm_token = device_fcm_token;
    }
}
