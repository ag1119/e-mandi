package com.cstup.e_mandi.controllers;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.environmentVariables.test.Crops;
import com.cstup.e_mandi.model.Get.CropType;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homePageController {
    public interface EventListener{
        void updateList();
        void hideProgressBar();
        void showProgressBar();
        void hideErrorPage();
        void showErrorPage();
        void errorToast(String msg);
        void setRefreshingFalse();
    }

    private EventListener listener;
    public homePageController(EventListener listener){
        this.listener = listener;
    }

    public void getAllCropType(){
        Crops.vegetables.clear();
        Crops.fruits.clear();
        Crops.grains.clear();
        listener.updateList();
        listener.showProgressBar();
        listener.hideErrorPage();
        DataService service = RetrofitInstance.getService(ev.BASE_URL);
        Call<List<CropType>> call = service.getCropTypes();
        call.enqueue(new Callback<List<CropType>>() {
            @Override
            public void onResponse(@NonNull Call<List<CropType>> call,@NonNull Response<List<CropType>> response) {
                if(response.isSuccessful()){
                    List<CropType> cropTypeList = response.body();
                    assert cropTypeList != null;
                    for(CropType cropType: cropTypeList){
                        switch (cropType.getCropClass()){
                            case  constants.CropClass.VEGETABLES:
                                Crops.vegetables.add(cropType);
                                break;
                            case constants.CropClass.FRUITS:
                                Crops.fruits.add(cropType);
                                break;
                            case constants.CropClass.GRAINS:
                                Crops.grains.add(cropType);
                                break;
                        }
                    }
                    listener.updateList();
                    listener.hideProgressBar();
                    listener.setRefreshingFalse();
                }
                else {
                    listener.showErrorPage();
                    listener.hideProgressBar();
                    listener.setRefreshingFalse();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CropType>> call,@NonNull Throwable t) {
                listener.showErrorPage();
                listener.hideProgressBar();
                listener.errorToast(t.getMessage());
                TempCache.IS_HOME_CONTROLLER_FIRST_CALL = true;
                listener.setRefreshingFalse();
            }
        });
    }
}
