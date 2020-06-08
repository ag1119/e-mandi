package com.cstup.e_mandi.controllers;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.CartItem;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.utilities.TempCache;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cartController {
    public interface EventListener{
        void showProgressBarAddToCart();
        void hideProgressBarAddToCart();
        void showAddToCartBtn();
        void hideAddToCartBtn();
        void showToast(String msg);
        void resetQuantity();
    }

    public interface CartListener{
        void showProgressBar();
        void hideProgressBar();
        void showEmptyPage();
        void hideEmptyPage();
        void showNoInternetPage();
        void hideNoInternetPage();
        void showCheckOutContainer();
        void hideCheckOutContainer();
        void showListContainer();
        void hideListContainer();
        void setTotalCartValue();
        void showToast(String msg);
        void notifyChanges();
    }

    public interface CartAdapterListener{
        void showToast(String msg);
    }

    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private EventListener listener;
    private CartListener cartListener;
    private CartAdapterListener cartAdapterListener;
    private final String error_msg = "some error occurred";



    cartController(){}

    public cartController(EventListener listener){
        this.listener = listener;
    }

    public cartController(CartListener cartListener){
        this.cartListener = cartListener;
    }

    public cartController(CartAdapterListener cartAdapterListener){
        this.cartAdapterListener = cartAdapterListener;
    }

    public void addToCart(com.cstup.e_mandi.model.Post.CartItem cartItem){
        listener.hideAddToCartBtn();
        listener.showProgressBarAddToCart();
        Call<List<MyResponse>> call = service.addToCart(TempCache.USER_ACCESS_TOKEN , cartItem);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    listener.showAddToCartBtn();
                    listener.hideProgressBarAddToCart();
                    listener.showToast("Crop added to cart successfully");
                    listener.resetQuantity();
                    TempCache.CART_FIRST_CALL = false;
                }
                else {
                    listener.showAddToCartBtn();
                    listener.hideProgressBarAddToCart();
                    listener.showToast(error_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showAddToCartBtn();
                listener.showProgressBarAddToCart();
                listener.showToast(t.getMessage());
            }
        });
    }

    public void fetchCartItems(){
        cartListener.showProgressBar();
        hideOtherViews();
        Call<List<CartItem>> call = service.getCartItems(TempCache.USER_ACCESS_TOKEN);
        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<CartItem>> call,@NonNull Response<List<CartItem>> response) {
                if(response.isSuccessful()){
                    TempCache.cartItems.clear();
                    assert response.body() != null;
                    TempCache.cartItems.addAll(response.body());
                    showHideOnSuccessfulResponse();
                    cartListener.notifyChanges();
                    TempCache.TOTAL_CART_VALUE = Double.parseDouble(getTotalCartValue());
                    cartListener.setTotalCartValue();
                }
                else
                    showHideOnNotSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call<List<CartItem>> call,@NonNull Throwable t) {
               showHideOnFailure(t.getMessage());
            }
        });
    }

    public void deleteCartItem(Integer item_id){
        Call<List<MyResponse>> call = service.deleteCartItem(TempCache.USER_ACCESS_TOKEN , item_id);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    if(cartAdapterListener != null)
                        cartAdapterListener.showToast("Item deleted successfully");
                }
                else{
                    if(cartAdapterListener != null)
                        cartAdapterListener.showToast(error_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                if(listener != null)
                    listener.showToast(t.getMessage());
            }
        });
    }

    public void updateCartItem(Integer item_id , com.cstup.e_mandi.model.Patch.CartItem cartItem){
        Call<List<MyResponse>> call = service.updateCartItem(TempCache.USER_ACCESS_TOKEN , item_id ,cartItem);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful())
                    listener.showToast("cart updated successfully");
                else
                    listener.showToast(error_msg);
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
            }
        });
    }

    public ArrayList<CartItem> getCartItems() {
        return TempCache.cartItems;
    }

    private void hideOtherViews(){
        cartListener.hideCheckOutContainer();
        cartListener.hideEmptyPage();
        cartListener.hideListContainer();
        cartListener.hideNoInternetPage();
    }

    private void showHideOnFailure(String msg){
        cartListener.showNoInternetPage();
        cartListener.hideProgressBar();
        cartListener.showToast(msg);
    }

    private void showHideOnSuccessfulResponse(){
        if(TempCache.cartItems.size() > 0){
            cartListener.hideProgressBar();
            cartListener.showCheckOutContainer();
            cartListener.showListContainer();
        }
        else {
            cartListener.showEmptyPage();
            cartListener.hideProgressBar();
        }
    }

    private void showHideOnNotSuccessful(){
        cartListener.showToast(error_msg);
        cartListener.hideProgressBar();
        cartListener.showEmptyPage();
    }

    private String getTotalCartValue(){
        double cart_value = 0;
        for(CartItem cartItem : TempCache.cartItems)
            cart_value += cartItem.getItemPrice();
        return cart_value+"";
    }
}
