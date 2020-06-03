package com.cstup.e_mandi.controllers;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.MyCrops;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.model.Patch.MyCrop;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.utilities.TempCache;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myCropsController {
    public interface EventListener{
        void notifyChanges();
        void showToast(String msg);
        void showRecyclerView();
        void showProgressBar();
        void hideProgressBar();
        void showBtnContainer();
        void hideBtnContainer();
        void hideEditContainer();
        void showFetchingCropsPB();
        void hideFetchingCropsPB();
        void showErrorPage();
        void showEmptyPage();
    }
    private EventListener listener;
    private ArrayList<MyCrops> myCrops = TempCache.myCrops;
    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private final String errorMsg = "some error occurred";
    private final String successMsg = "changes saved successfully";

    public myCropsController(EventListener listener){
        this.listener = listener;
    }

    public void fetchMyCrops(){
        listener.showFetchingCropsPB();
        Call<List<MyCrops>> call = service.getMyCrops(TempCache.VENDOR_ACCESS_TOKEN);
        call.enqueue(new Callback<List<MyCrops>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyCrops>> call,@NonNull Response<List<MyCrops>> response) {
                if(response.isSuccessful()){
                    myCrops.clear();
                    assert response.body() != null;
                    myCrops.addAll(response.body());
                    if(myCrops.size() > 0){
                        listener.showRecyclerView();
                        listener.notifyChanges();
                    }
                    else{
                        listener.showEmptyPage();
                    }
                    listener.hideFetchingCropsPB();
                    TempCache.IS_FETCH_MY_CROPS_FIRST_CALL = false;
                }
                else{
                    listener.showToast(errorMsg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyCrops>> call,@NonNull Throwable t) {
                listener.showErrorPage();
                listener.hideFetchingCropsPB();
                listener.showToast(t.getMessage());
            }
        });
    }

    public void updateMyCrop(Integer crop_id , MyCrop myCrop){
        listener.hideBtnContainer();
        listener.showProgressBar();
        Call<List<MyResponse>> call = service.updateMyCrop(TempCache.VENDOR_ACCESS_TOKEN , crop_id , myCrop);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    listener.showToast(successMsg);
                    listener.hideProgressBar();
                    listener.hideEditContainer();
                    listener.showRecyclerView();
                    //fetchMyCrops();
                    TempCache.SELECTED_MY_CROP_ITEM.setCropQty(
                            TempCache.SELECTED_MY_CROP_ITEM.getCropQty() +
                            myCrop.getChangeInQty());
                    TempCache.SELECTED_MY_CROP_ITEM.setCropPrice(myCrop.getCropPrice());
                    listener.notifyChanges();
                }
                else {
                    listener.showToast(errorMsg);
                    listener.hideProgressBar();
                    listener.showBtnContainer();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
                listener.hideProgressBar();
                listener.showBtnContainer();
            }
        });
    }

    public ArrayList<MyCrops> getMyCrops() {
        return myCrops;
    }
}
