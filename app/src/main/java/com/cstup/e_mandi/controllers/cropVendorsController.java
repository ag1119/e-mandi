package com.cstup.e_mandi.controllers;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.Crop;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.utilities.TempCache;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cropVendorsController {
    public interface EventListener{
        void showToast(String msg);
        void showErrorPage();
        void hideErrorPage();
        void showProgressBar();
        void hideProgressBar();
        void notifyChanges();
        void showNoVendorPage();
    }

    private final String error_msg = "some error occurred";
    private ArrayList<Crop> list = new ArrayList<>();
    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private EventListener listener;

    public cropVendorsController(EventListener listener){
        this.listener = listener;
    }
    public void getCropVendors(){
        listener.showProgressBar();
        Call<List<Crop>> call = service.getCropVendors(TempCache.CROP_TYPE_ID);
        call.enqueue(new Callback<List<Crop>>() {
            @Override
            public void onResponse(@NonNull Call<List<Crop>> call,@NonNull Response<List<Crop>> response) {
                if(response.isSuccessful()){
                    listener.hideProgressBar();
                    listener.hideErrorPage();
                    assert response.body() != null;
                    list.addAll(response.body());
                    if(list.size() > 0)
                        listener.notifyChanges();
                    else{
                        listener.showNoVendorPage();
                    }

                }
                else{
                    listener.showToast(error_msg);
                    listener.hideProgressBar();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Crop>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
                listener.showErrorPage();
                listener.hideProgressBar();
            }
        });
    }

    public ArrayList<Crop> getList() {
        return list;
    }
}
