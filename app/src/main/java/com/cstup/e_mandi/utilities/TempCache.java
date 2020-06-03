package com.cstup.e_mandi.utilities;

import androidx.fragment.app.Fragment;

import com.cstup.e_mandi.environmentVariables.test.Crops;
import com.cstup.e_mandi.model.Get.CartItem;
import com.cstup.e_mandi.model.Get.Crop;
import com.cstup.e_mandi.model.Get.CropType;
import com.cstup.e_mandi.model.Get.MyCrops;
import com.cstup.e_mandi.model.Get.User;
import com.cstup.e_mandi.model.Get.Vendor;
import com.cstup.e_mandi.model.Get.VendorDetails;

import java.util.ArrayList;

public class TempCache {
    public static String USER_TYPE;
    public static boolean IS_USER = false;
    public static boolean IS_VENDOR = false;
    public static String USER_ACCESS_TOKEN;
    public static String VENDOR_ACCESS_TOKEN;

    public static GenericMethods genericMethods = new GenericMethods();
    public static String CONTACT;
    //home fragment cache
    public static ArrayList<CropType> HOMEPAGE_LIST_ITEMS = Crops.vegetables;//default
    public static int CLICKED;
    //Bottom navigation item cache
    public static int BOTTOM_NAVIGATION_ENABLED_ITEM;
    //Bottom navigation view activity cache
    public static Fragment SELECTED_FRAGMENT;
    //Fragment tags
    public static String SELECTED_TAG;

    //cart fragment cache..
    public static boolean CART_FIRST_CALL = true;
    public static ArrayList<CartItem> cartItems = new ArrayList<>();
    public static double TOTAL_CART_VALUE;

    //crop
    public static Crop crop;

    public static Integer CROP_CATEGORY = constants.CropCategory.VEGETABLE;

    //vendor crop
    public static Integer CROP_TYPE_ID;
    public static MyCrops SELECTED_MY_CROP_ITEM;
    public static boolean IS_FETCH_MY_CROPS_FIRST_CALL = true;
    public static boolean IS_MY_CROPS_UPDATED = false;
    public static ArrayList<MyCrops> myCrops = new ArrayList<>();

    public static String CROP_NAME;
    public static String FARMER_ID;

    public static int LOGIN_OR_SIGN_UP = 0;
    public static int SEND_OTP_BUTTON_STATE = 0;

    public static boolean IS_HOME_CONTROLLER_FIRST_CALL = true;

    public static void setUserDefaults(){
        BOTTOM_NAVIGATION_ENABLED_ITEM = 0;
        SELECTED_FRAGMENT = constants.HOME_FRAGMENT;
        SELECTED_TAG = constants.HOME_TAG;
        CLICKED = 0;
        HOMEPAGE_LIST_ITEMS = Crops.vegetables;
    }

    //farmer corner....
    public static Fragment FARMER_SELECTED_FRAGMENT;
    public static String FARMER_SELECTED_TAG;

    public static void setFarmerDefaults(){
        BOTTOM_NAVIGATION_ENABLED_ITEM = 0;
        FARMER_SELECTED_FRAGMENT = constants.FRAMER_ORDER_FRAGMENT;
        FARMER_SELECTED_TAG = constants.FRAMER_ORDER_FRAGMENT_TAG;
    }

    public static CropType CROP_TYPE_ITEM;

    //profile
    public static boolean isFetchUserProfileFirstCall = true;
    public static boolean isFetchVendorProfileFirstCall = true;
    public static User user;
    public static Vendor vendor;

    //vendor basic info
    public static boolean IS_VENDOR_BASIC_INFO_FIRST_CALL = true;
    public static VendorDetails vendorDetails = new VendorDetails();
}
