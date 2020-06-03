package com.cstup.e_mandi.model.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("state_id")
    @Expose
    private Integer state_id;

    @SerializedName("city_id")
    @Expose
    private Integer city_id;

    @SerializedName("pin_code")
    @Expose
    private Integer pin_code;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("profile_picture")
    @Expose
    private String profile_picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getPin_code() {
        return pin_code;
    }

    public void setPin_code(Integer pin_code) {
        this.pin_code = pin_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
