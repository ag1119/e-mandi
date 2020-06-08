package com.cstup.e_mandi.controllers;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.Crop;
import com.cstup.e_mandi.model.Get.BasicDetails;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.utilities.TempCache;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cropDetailsController {
    public interface EventListener{
        void updateList();
        void showLoadingPanel();
        void hideLoadingPanel();
        void showInfo();
        void hideInfo();
        void showProgressBar();
        void hideProgressBar();
        void showErrorPage();
        void showToast(String msg);
        void setVendorsDetail(BasicDetails vendorsDetail);
    }
    private EventListener listener;
    private ArrayList<Crop> list =  new ArrayList<>();
    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private final String error_msg = "some error occurred while loading";

    public cropDetailsController(EventListener listener){
        this.listener = listener;
    }

    public void getCrops(){
        listener.showProgressBar();
        Call<List<Crop>> call = service.getVendorsCrop(TempCache.crop.getVendorId());
        call.enqueue(new Callback<List<Crop>>() {
            @Override
            public void onResponse(@NonNull Call<List<Crop>> call,@NonNull Response<List<Crop>> response) {
                if(response.isSuccessful()){
                    listener.hideProgressBar();
                    assert response.body() != null;
                    list.addAll(response.body());
                    listener.updateList();
                }
                else{
                    listener.showToast(error_msg);
                    listener.hideProgressBar();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Crop>> call,@NonNull Throwable t) {
                listener.hideProgressBar();
                listener.showErrorPage();
                listener.showToast(t.getMessage());
            }
        });
    }

    public void getVendorInfo(){
        listener.showLoadingPanel();
        listener.hideInfo();
        Call<List<BasicDetails>> call = service.getVendorDetails(TempCache.crop.getVendorId());
        call.enqueue(new Callback<List<BasicDetails>>() {
            @Override
            public void onResponse(@NonNull Call<List<BasicDetails>> call, @NonNull Response<List<BasicDetails>> response) {
                if(response.isSuccessful()){
                    listener.hideLoadingPanel();
                    listener.showInfo();
                    assert response.body() != null;
                    TempCache.basicDetails = response.body().get(0);
                    listener.setVendorsDetail(TempCache.basicDetails);
                }
                else {
                    listener.showToast(error_msg);
                    TempCache.IS_VENDOR_BASIC_INFO_FIRST_CALL = true;
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BasicDetails>> call, @NonNull Throwable t) {
                listener.showToast(t.getMessage());
                TempCache.IS_VENDOR_BASIC_INFO_FIRST_CALL = true;
            }
        });
    }

    public ArrayList<Crop> getList() {
        return list;
    }
}
