package com.cstup.e_mandi.model.Patch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("changeInQty")
    @Expose
    private Double changeInQty;

    public Double getChangeInQty() {
        return changeInQty;
    }

    public void setChangeInQty(Double changeInQty) {
        this.changeInQty = changeInQty;
    }
}
