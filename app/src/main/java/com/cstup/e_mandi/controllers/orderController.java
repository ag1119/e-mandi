package com.cstup.e_mandi.controllers;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.Cache.OrdersCache;
import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.Order;
import com.cstup.e_mandi.model.Get.PartialOrder;
import com.cstup.e_mandi.model.Get.BasicDetails;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.model.Post.OrderId;
import com.cstup.e_mandi.model.Post.Orders;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderController {
    public interface EventListener{
        void showProgressBar();
        void hideProgressBar();
        void notifyChanges();
        void showEmptyPage();
        void showErrorPage();
        void hideOrderContainer();
        void showToast(String msg);
        void setProfileImage(String url);
        void setName(String n);
        void setAddress(String add);
        void setContact(String num);
        void showBtnProgressBar();
        void hideBtnProgressBar();
        void showBtnContainer();
        void hideBtnContainer();
        void hideOrderDetails();
        void showOrderContainer();
    }

    public interface cartEventListener{
        void showToast(String msg);
        void hideCheckOutProgressBar();
        void hideCheckOutContainer();
        void showCheckOutContainer();
        void showEmptyPage();
        void hideListContainer();
        void notifyChanges();
        void setOrderFragment();
    }

    private EventListener listener;
    private cartEventListener cartListener;
    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private final String error_msg = "some error occurred";
    private final String cart_error_msg = "some error occurred";
    private  String ACCESS_TOKEN;
    private ArrayList<PartialOrder> partialOrders;
    private ArrayList<Order> orders;

    public orderController(EventListener listener){
        this.listener = listener;
        if(TempCache.USER_TYPE.equals(constants.USER)){
            ACCESS_TOKEN = TempCache.USER_ACCESS_TOKEN;
            partialOrders = OrdersCache.userPartialOrders;
            orders = OrdersCache.userOrders;
        }
        else {
            ACCESS_TOKEN = TempCache.VENDOR_ACCESS_TOKEN;
            partialOrders = OrdersCache.vendorPartialOrders;
            orders = OrdersCache.vendorOrders;
        }
    }

    public orderController(cartEventListener cartListener){this.cartListener = cartListener;}

    public void fetchOrders(){
        Call<List<PartialOrder>> call = service.getPartialOrders(ACCESS_TOKEN);
        call.enqueue(new Callback<List<PartialOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<PartialOrder>> call,@NonNull Response<List<PartialOrder>> response) {
                if(response.isSuccessful()){
                    partialOrders.clear();
                    orders.clear();
                    listener.notifyChanges();
                    listener.hideProgressBar();
                    assert response.body() != null;
                    partialOrders.addAll(response.body());
                    if(partialOrders.size() > 0){
                        for(PartialOrder item : partialOrders){
                            //listener.notifyChanges();
                            fetchOrderItem(item.getOrderId());
                        }
                    }
                    else{
                        listener.hideOrderContainer();
                        listener.showEmptyPage();
                    }
                }
                else {
                    listener.hideOrderContainer();
                    listener.showErrorPage();
                    listener.showToast(error_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PartialOrder>> call,@NonNull Throwable t) {
                listener.hideProgressBar();
                listener.hideOrderContainer();
                listener.showErrorPage();
                listener.showToast(t.getMessage());
            }
        });
    }

    private void fetchOrderItem(Integer id){
        Call<List<Order>> call = service.getMyOrder(ACCESS_TOKEN , id);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call,@NonNull Response<List<Order>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    orders.addAll(response.body());
                    listener.notifyChanges();
                    if(TempCache.USER_TYPE.equals(constants.USER))
                        OrdersCache.isUserOrderFirstCall = false;
                    else
                        OrdersCache.isVendorOrderFirstCall = false;
                }
                else{
                    listener.showToast(error_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
                listener.hideOrderContainer();
                listener.showErrorPage();
            }
        });
    }

    public void getDetails(Integer id){
        Call<List<BasicDetails>> call;
        if(TempCache.USER_TYPE.equals(constants.USER))
            call = service.getVendorDetails(id);
        else
            call = service.getUserDetails(id);
        call.enqueue(new Callback<List<BasicDetails>>() {
            @Override
            public void onResponse(@NonNull Call<List<BasicDetails>> call, @NonNull Response<List<BasicDetails>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    BasicDetails basicDetails = response.body().get(0);
                    listener.setProfileImage(basicDetails.getProfile_picture());
                    listener.setName(basicDetails.getName());
                    listener.setAddress(basicDetails.getAddress());
                    listener.setContact(basicDetails.getContact());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BasicDetails>> call, @NonNull Throwable t) {
                listener.showToast(t.getMessage());
            }
        });
    }

    public void placeMyOrder(Orders orders){
        Call<List<Order>> call = service.placeMyOrder(TempCache.USER_ACCESS_TOKEN , orders);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call,@NonNull Response<List<Order>> response) {
                cartListener.hideCheckOutProgressBar();
                if(response.isSuccessful()){
                    assert response.body() != null;
//                    OrdersCache.userOrders.addAll(response.body());
//                    listener.notifyChanges();
                    deleteCartItems();
                    cartListener.hideCheckOutContainer();
                    cartListener.hideListContainer();
                    cartListener.showEmptyPage();
                    cartListener.showToast("order placed successfully");
                    cartListener.setOrderFragment();
                    OrdersCache.isUserOrderFirstCall = true;
                }
                else{
                    cartListener.showToast(cart_error_msg);
                    cartListener.showCheckOutContainer();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call,@NonNull Throwable t) {
                cartListener.hideCheckOutProgressBar();
                cartListener.showToast(t.getMessage());
                cartListener.showCheckOutContainer();
            }
        });
    }

    private void deleteCartItems(){
        cartController controller = new cartController();
        int n = TempCache.cartItems.size();
        for(int i = 0; i < n; i++){
            controller.deleteCartItem(TempCache.cartItems.get(i).getItemId());
        }
        TempCache.cartItems.clear();
        cartListener.notifyChanges();
        TempCache.CART_FIRST_CALL = false;
    }

    public void cancelOrder(OrderId orderId){
        listener.hideBtnContainer();
        listener.showBtnProgressBar();
        Call<List<MyResponse>> call = service.cancelOrder(ACCESS_TOKEN , orderId);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    listener.showToast("order is cancelled successfully");
                    listener.hideOrderDetails();
                    listener.showOrderContainer();
                    listener.showBtnContainer();
                    listener.hideBtnProgressBar();
                }
                else{
                    listener.showToast("some error occurred");
                    listener.showBtnContainer();
                    listener.hideBtnProgressBar();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
                listener.showBtnContainer();
                listener.hideBtnProgressBar();
            }
        });
    }

    public void confirmOrder(OrderId orderId){
        listener.hideBtnContainer();
        listener.showBtnProgressBar();
     Call<List<MyResponse>> call = service.confirmOrder(ACCESS_TOKEN , orderId);
     call.enqueue(new Callback<List<MyResponse>>() {
         @Override
         public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
             if(response.isSuccessful()){
                 listener.showToast("order is confirmed successfully");
                 listener.hideOrderDetails();
                 listener.showOrderContainer();
                 listener.showBtnContainer();
                 listener.hideBtnProgressBar();
             }
             else{
                 listener.showToast("some error occurred");
                 listener.showBtnContainer();
                 listener.hideBtnProgressBar();
             }
         }

         @Override
         public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
             listener.showToast(t.getMessage());
             listener.showBtnContainer();
             listener.hideBtnProgressBar();
         }
     });
    }
}
