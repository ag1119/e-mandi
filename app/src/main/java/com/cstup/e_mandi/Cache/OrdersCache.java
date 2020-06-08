package com.cstup.e_mandi.Cache;

import com.cstup.e_mandi.model.Get.Order;
import com.cstup.e_mandi.model.Get.PartialOrder;

import java.util.ArrayList;

public class OrdersCache {
    public static ArrayList<Order> userOrders = new ArrayList<>();
    public static ArrayList<Order> vendorOrders = new ArrayList<>();
    public static ArrayList<PartialOrder> userPartialOrders = new ArrayList<>();
    public static ArrayList<PartialOrder> vendorPartialOrders = new ArrayList<>();

    public static Order selectedOrder;

    //order status
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_DELIVERED = "DELIVERED";

    public static boolean isUserOrderFirstCall = true;
    public static boolean isVendorOrderFirstCall = true;
    public static boolean isOrderDetailsVisible = false;
    public static boolean isOrderContext = false;
}
