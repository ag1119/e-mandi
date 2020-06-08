package com.cstup.e_mandi.model.Post;

import com.cstup.e_mandi.model.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Orders {
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("order")
    @Expose
    private ArrayList<Order> order;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setOrder(ArrayList<Order> order) {
        this.order = order;
    }

    public ArrayList<Order> getOrder() {
        return order;
    }
}
