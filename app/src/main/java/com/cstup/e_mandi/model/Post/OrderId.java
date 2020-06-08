package com.cstup.e_mandi.model.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderId {
    @SerializedName("order_id")
    @Expose
    private Integer order_id;

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }
}
