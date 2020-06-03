package com.cstup.e_mandi.controllers;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.MyCrops;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.model.Patch.MyCrop;
import com.cstup.e_mandi.model.Post.Crop;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.utilities.TempCache;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addCropController {
    public interface EventListener{
        String getQtyInKg();
        String getPricePerKg();
        String getDescription();
        void showProgressBar();
        void hideProgressBar();
        void showAddCropButton();
        void hideAddCropButton();
        void finishActivity();
        void updateList();
        void disableAddImageBtn();
        void enableAddImageBtn();
        void showLoadingImage();
        void hideLoadingImage();
        void showProgress(int progress);
        void showToast(String msg);
        void showImageProgressBar();
        void hideImageProgressBar();
    }


    private EventListener listener;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private UploadTask uploadTask;
    private ArrayList<String> list = new ArrayList<>();

    public addCropController(EventListener listener){
        this.listener = listener;
    }


    public void addCrop(){
        if(uploadTask != null && uploadTask.isInProgress()){
            String msg = "Please wait photo is uploading...";
            listener.showToast(msg);
            return;
        }
        Crop crop = new Crop();
        crop.setCropQty(Double.parseDouble(listener.getQtyInKg()));
        crop.setCropName(TempCache.CROP_TYPE_ITEM.getCropTypeName());
        crop.setCropTypeId(TempCache.CROP_TYPE_ITEM.getCropTypeId());
        crop.setCropPrice(Double.parseDouble(listener.getPricePerKg()));
        crop.setDescription(listener.getDescription());
        crop.setCropImages(list);

        listener.showProgressBar();
        listener.hideAddCropButton();

        DataService service = RetrofitInstance.getService(ev.BASE_URL);
        Call<List<MyResponse>> call = service.postCrops(TempCache.VENDOR_ACCESS_TOKEN , crop);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull  Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                Log.v("onResponse -> " , "i am in response");
                if(response.isSuccessful()){
                    listener.showAddCropButton();
                    listener.hideProgressBar();
                    listener.showToast("Crop Added Successfully.");
                    listener.finishActivity();
                    TempCache.IS_MY_CROPS_UPDATED = true;
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showAddCropButton();
                listener.hideProgressBar();
                listener.showToast("Some problem occurred while adding the crop.");
            }
        });
    }

    public void uploadPhoto(Uri fileUri){

        listener.disableAddImageBtn();
        listener.showLoadingImage();
        listener.showImageProgressBar();

        StorageReference imageRef = storageReference.child("images/"+fileUri.getLastPathSegment());
        uploadTask = imageRef.putFile(fileUri);
        uploadTask.addOnFailureListener(e -> {
            Log.v("upLoadPhoto -> " , e.toString());
            listener.hideImageProgressBar();
            listener.hideLoadingImage();
            listener.enableAddImageBtn();
            listener.showToast(e.toString());
        }).addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            listener.hideImageProgressBar();
            listener.hideLoadingImage();
            listener.enableAddImageBtn();
            list.add(uri+"");
        })).addOnProgressListener(taskSnapshot -> {
            double d = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
            listener.showProgress((int)d);
        });

    }
}
