package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city_id")
    @Expose
    private Integer city_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state_id")
    @Expose
    private Integer state_id;

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

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
}
