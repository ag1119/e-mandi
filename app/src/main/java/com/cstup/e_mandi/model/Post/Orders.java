package com.cstup.e_mandi.model.Post;

import com.cstup.e_mandi.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders {
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("order")
    @Expose
    private List<Order> order = null;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
