package com.cstup.e_mandi.services;

import com.cstup.e_mandi.model.Get.City;
import com.cstup.e_mandi.model.Get.CropType;
import com.cstup.e_mandi.model.Get.MyCrops;
import com.cstup.e_mandi.model.Get.State;
import com.cstup.e_mandi.model.Get.User;
import com.cstup.e_mandi.model.Get.Vendor;
import com.cstup.e_mandi.model.Get.VendorDetails;
import com.cstup.e_mandi.model.Login_SignUp;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.model.Patch.MyCrop;
import com.cstup.e_mandi.model.Post.CartItem;
import com.cstup.e_mandi.model.Post.Crop;
import com.cstup.e_mandi.model.Post.UserProfile;
import com.cstup.e_mandi.model.Post.VendorProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {
    // login/sign-up services...
    @GET("otp_login")
    Call<List<MyResponse>> getLoginOtp(@Query("type") String type , @Query("contact") Long contact);
    @POST("otp_login")
    Call<List<MyResponse>> postLoginOtp(@Body Login_SignUp login);
    @GET("otp_signup")
    Call<List<MyResponse>> getSignUpOtp(@Query("type") String type , @Query("contact") Long contact);
    @POST("otp_signup")
    Call<List<MyResponse>> postSignUpOtp(@Body Login_SignUp signUp);

    //profile services
    @GET("user/me")
    Call<List<User>> getUser(@Header("X-auth-token") String x_auth_token);
    @GET("vendor/me")
    Call<List<Vendor>> getVendor(@Header("X-auth-token") String x_auth_token);
    @PATCH("user/me")
    Call<List<MyResponse>> patchUserProfile(@Header("X-Auth-Token") String x_auth_token , @Body UserProfile userProfile);
    @PATCH("vendor/me")
    Call<List<MyResponse>> patchVendorProfile(@Header("X-Auth-Token") String x_auth_token , @Body VendorProfile vendorProfile);
    @GET("vendor/{id}")
    Call<List<VendorDetails>> getVendorDetails(@Path("id") Integer id);

    //Home page services
    @GET("crop_type")
    Call<List<CropType>> getCropTypes(); // to get all crop types

    //crop
    @GET("crop")
    Call<List<com.cstup.e_mandi.model.Get.Crop>> getCropVendors(@Query("crop_type_id") Integer crop_type_id);
    @GET("crop")
    Call<List<com.cstup.e_mandi.model.Get.Crop>> getVendorsCrop(@Query("vendor_id") Integer vendor_id);

    //vendor crop
    @GET("vendor/crop")
    Call<List<MyCrops>> getMyCrops(@Header("X-Auth-Token") String x_auth_token);
    @POST("vendor/crop")
    Call<List<MyResponse>> postCrops(@Header("X-Auth-Token") String x_auth_token , @Body Crop crop);
    @PATCH("vendor/crop/{id}")
    Call<List<MyResponse>> updateMyCrop(@Header("X-Auth-Token") String x_auth_token ,
                                      @Path("id") Integer crop_id ,
                                      @Body MyCrop myCrop);

    //State and City
    @GET("state")
    Call<List<State>> getStates();
    @GET("city")
    Call<List<City>> getCities();

    //cart
    @POST("user/cart")
    Call<List<MyResponse>> addToCart(@Header("X-Auth-Token") String x_auth_token , @Body CartItem cartItem);
    @GET("user/cart")
    Call<List<com.cstup.e_mandi.model.Get.CartItem>> getCartItems(@Header("X-Auth-Token") String x_auth_token);
    @DELETE("user/cart/{id}")
    Call<List<MyResponse>> deleteCartItem(@Header("X-Auth-Token") String x_auth_token , @Path("id") Integer id);
    @PATCH("user/cart/{id}")
    Call<List<MyResponse>> updateCartItem(@Header("X-Auth-Token") String x_auth_token ,
                                          @Path("id") Integer id ,
                                          @Body com.cstup.e_mandi.model.Patch.CartItem cartItem);
}
