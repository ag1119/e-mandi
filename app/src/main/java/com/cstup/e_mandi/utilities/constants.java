package com.cstup.e_mandi.utilities;

import androidx.fragment.app.Fragment;

import com.cstup.e_mandi.views.farmer.fragments.farmer_my_crops;
import com.cstup.e_mandi.views.farmer.fragments.farmer_notifications;
import com.cstup.e_mandi.views.farmer.fragments.farmer_orders;
import com.cstup.e_mandi.views.farmer.fragments.farmer_profile;
import com.cstup.e_mandi.views.user.fragments.cart;
import com.cstup.e_mandi.views.user.fragments.cropDetails;
import com.cstup.e_mandi.views.user.fragments.cropVendors;
import com.cstup.e_mandi.views.user.fragments.notifications;
import com.cstup.e_mandi.views.user.fragments.home;
import com.cstup.e_mandi.views.user.fragments.orders;
import com.cstup.e_mandi.views.user.fragments.profile;

import java.util.HashMap;

public class constants {
    public static final String SHARED_PREF_NAME = "MY_PREF";
    public static final String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public static final String USER_KEY = "USER_ACCESS_TOKEN";
    public static final String VENDOR_KEY = "VENDOR_ACCESS_TOKEN";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_CONTACT = "USER_CONTACT";
    public static final String VENDOR_CONTACT = "VENDOR_CONTACT";

    public static final int STATE_SEND_OTP = 0;
    public static final int STATE_VERIFY_OTP = 1;
    public static final String LOGIN = "LOGIN";
    public static final String SIGN_UP = "SIGN_UP";

    public static final int VEGETABLES_CLICKED = 0;
    public static final int GRAINS_CLICKED = 1;
    public static final int FRUITS_CLICKED = 2;

    public static final int HOME_INDEX = 0;
    public static final int CART_INDEX = 1;
    public static final int ORDERS_INDEX = 2;
    public static final int NOTIFICATION_INDEX = 3;
    public static final int PROFILE_INDEX = 4;

    public static Fragment HOME_FRAGMENT = new home();
    public static Fragment CART_FRAGMENT = new cart();
    public static Fragment NOTIFICATIONS_FRAGMENT = new notifications();
    public static Fragment PROFILE_FRAGMENT = new profile();
    public static Fragment CROP_DETAILS_FRAGMENT = new cropDetails();
    public static Fragment CROP_VENDORS_FRAGMENT = new cropVendors();
    public static Fragment ORDERS_FRAGMENT = new orders();

    public static final String HOME_TAG = "HOME";
    public static final String CART_TAG = "CART";
    public static final String NOTIFICATION_TAG = "NOTIFICATIONS";
    public static final String PROFILE_TAG = "PROFILE";
    public static final String CROP_DETAILS_TAG = "CROP_DETAILS";
    public static final String CROP_VENDORS_TAG = "CROP_VENDORS";
    public static final String ORDERS_TAG = "ORDERS";

    public static final int LOGIN_CLICKED = 0;
    public static final int SIGN_UP_CLICKED = 1;
    public static final String USER = "user";
    public static final String VENDOR = "vendor";
    public static final String ADMIN = "admin";

    public static final String TYPE_KG = "KG";
    public static final String TYPE_QUINTOL = "QUINTOL";
    public static final String REMAINING_STOCK = "Remaining Stock: ";


    public static final HashMap<String , Integer> map;
    static {
        map = new HashMap<>();
        //user corner...
        map.put("HOME" , 0);
        map.put("CART" , 1);
        map.put("ORDERS" , 2);
        map.put("NOTIFICATIONS" , 3);
        map.put("PROFILE" , 4);
        map.put("CROP_DETAILS" , 0);
        map.put("CROP_VENDORS" , 0);

        //farmer corner...
        map.put("FARMER_ORDERS" , 0);
        map.put("FARMER_MY_CROP" , 1);
        map.put("FARMER_ADD_CROP" , 2);
        map.put("FARMER_NOTIFICATIONS" , 3);
        map.put("FARMER_PROFILE" , 4);
    }

    public static class CropCategory{
        public static final int VEGETABLE = 0;
        public static final int GRAIN = 1;
        public static final int FRUIT = 2;
    }
    public static class CropClass{
        public static final String VEGETABLES = "VEGETABLES";
        public static final String FRUITS = "FRUITS";
        public static final String GRAINS = "GRAINS";
    }

    //farmer corner....
    public static final Fragment FRAMER_ORDER_FRAGMENT = new farmer_orders();
    public static final Fragment FARMER_MY_CROP_FRAGMENT = new farmer_my_crops();
    public static final Fragment FARMER_ADD_CROP_FRAGMENT = new home();
    public static final Fragment FARMER_NOTIFICATION_FRAGMENT = new farmer_notifications();
    public static final Fragment FARMER_PROFILE_FRAGMENT = new farmer_profile();

    public static final String FRAMER_ORDER_FRAGMENT_TAG = "FARMER_ORDERS";
    public static final String FARMER_MY_CROP_FRAGMENT_TAG = "FARMER_MY_CROP";
    public static final String FARMER_ADD_CROP_FRAGMENT_TAG = "FARMER_ADD_CROP";
    public static final String FARMER_NOTIFICATION_FRAGMENT_TAG = "FARMER_NOTIFICATIONS";
    public static final String FARMER_PROFILE_FRAGMENT_TAG = "FARMER_PROFILE";

    public static final int FARMER_ORDER_INDEX = 0;
    public static final int FARMER_MY_CROP_INDEX = 1;
    public static final int FARMER_ADD_CROP_INDEX = 2;
    public static final int FARMER_NOTIFICATIONS_INDEX = 3;
    public static final int FARMER_PROFILE_INDEX = 4;

}
