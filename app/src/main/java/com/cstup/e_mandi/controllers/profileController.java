package com.cstup.e_mandi.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.cstup.e_mandi.Cache.ProfileCache;
import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Get.City;
import com.cstup.e_mandi.model.Get.State;
import com.cstup.e_mandi.model.Get.User;
import com.cstup.e_mandi.model.Get.Vendor;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.model.Post.UserProfile;
import com.cstup.e_mandi.model.Post.VendorProfile;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profileController {
    public interface EventListener{
        void showProgressBar();
        void hideProgressBar();
        void showSwitchAccountBtn();
        void hideSwitchAccountBtn();
        void finishActivity();
        void startUserAccount();
        void startVendorAccount();
        void setTextSwitchToVendor();
        void setTextSwitchToUser();
        void disableEditing();
        void hideSaveChangesBtn();
        void showSaveChangesBtn();
        void hideSaveChangesProgressBar();
        void showSaveChangesProgressBar();
        void setName(String name);
        void setContact(String contact);
        void setState(String state);
        void setCity(String city);
        void setAddress(String address);
        void setProfileViews();
        void showToast(String msg);
        void showProfileProgress();
        void hideProfileProgress();
        void hideCancelBtn();
        void showCancelBtn();
        void updateStates();
        void updateCities();
        void showProgress(int progress);
        void showLoadingPanel();
        void hideLoadingPanel();
        void hideInfo();
        void showInfo();
    }
    private Context context;
    private EventListener listener;
    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri downloadImageUri;
    private final String error_msg = "some error occurred";

    public profileController(Context context , EventListener listener){
        this.context = context;
        this.listener = listener;
    }

    public void switchAccount(){
        SharedPreferences sp = Objects.requireNonNull(context)
                .getSharedPreferences(constants.SHARED_PREF_NAME , Context.MODE_PRIVATE);
        String user_type = sp.getString(constants.USER_TYPE , null);
        SharedPreferences.Editor editor= sp.edit();
        listener.showProgressBar();
        listener.hideSwitchAccountBtn();
        if(user_type != null){
            if(user_type.equals(constants.USER)){
                editor.putString(constants.USER_TYPE , constants.VENDOR);
                editor.apply();
                TempCache.USER_TYPE = constants.VENDOR;
                listener.startVendorAccount();
            }
            else{
                editor.putString(constants.USER_TYPE , constants.USER);
                editor.apply();
                TempCache.USER_TYPE = constants.USER;
                listener.startUserAccount();
            }
        }
        listener.finishActivity();
        listener.hideProgressBar();
        listener.showSwitchAccountBtn();
    }

    public void setSwitchAccountBtn(){
        SharedPreferences sp = context.getSharedPreferences(constants.SHARED_PREF_NAME , Context.MODE_PRIVATE);
        String user_type = sp.getString(constants.USER_TYPE , null);
        String user_contact = sp.getString(constants.USER_CONTACT , null);
        String vendor_contact = sp.getString(constants.VENDOR_CONTACT , null);
        if(user_contact != null  && user_contact.equals(vendor_contact)){
            if(isUserPresent(constants.USER) && isUserPresent(constants.VENDOR)){
                listener.showSwitchAccountBtn();
                if(user_type != null)
                    switch (user_type){
                        case constants.USER:
                            listener.setTextSwitchToVendor();
                            break;
                        case constants.VENDOR:
                            listener.setTextSwitchToUser();
                            break;
                    }
            }
        }
    }

    private boolean isUserPresent(String user_type){
        return Objects.requireNonNull(context).getSharedPreferences(constants.SHARED_PREF_NAME , Context.MODE_PRIVATE)
                .getBoolean(user_type , false);
    }

    private void fetchStates(){
        Call<List<State>> call = service.getStates();
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(@NonNull Call<List<State>> call,@NonNull Response<List<State>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    ProfileCache.states.addAll(response.body());
                    listener.updateStates();
                    if(TempCache.USER_TYPE.equals(constants.VENDOR)){
                        Integer id = TempCache.vendor.getStateId();
                        if(id != null)
                            listener.setState(getStateById(id));
                    }
                    else{
                        Integer id = TempCache.user.getStateId();
                        if(id != null)
                            listener.setState(getStateById(id));
                    }
                }
                else
                    listener.showToast("some error occurred while fetching states");
            }

            @Override
            public void onFailure(@NonNull Call<List<State>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
            }
        });
    }

    private void fetchCities(){
        Call<List<City>> call = service.getCities();
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(@NonNull Call<List<City>> call,@NonNull Response<List<City>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    ProfileCache.allCities.addAll(response.body());
                    listener.updateCities();
                    if(TempCache.USER_TYPE.equals(constants.USER)){
                        Integer id = TempCache.user.getCityId();
                        if(id != null)
                            listener.setCity(getCityById(id));
                    }
                    else{
                        Integer id = TempCache.vendor.getCityId();
                        if(id != null)
                            listener.setCity(getCityById(id));
                    }
                }
                else
                    listener.showToast("some error occurred while fetching cities");
            }

            @Override
            public void onFailure(@NonNull Call<List<City>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
            }
        });
    }

    public ArrayList<City> getCities() {
        return ProfileCache.cities;
    }

    public ArrayList<State> getStates() {
        return ProfileCache.states;
    }

    public void filterCityListByState(int state_id){
        ProfileCache.cities.clear();
        for(City city: ProfileCache.allCities){
            if(city.getState_id() == state_id)
                ProfileCache.cities.add(city);
        }
        listener.updateCities();
    }

    public void uploadPhoto(Uri fileUri){
        listener.showProfileProgress();
        StorageReference imageRef = storageReference.child("images/"+fileUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(fileUri);
        uploadTask.addOnFailureListener(e -> {
            listener.hideProfileProgress();
            listener.showToast(e.getMessage());
        }).addOnSuccessListener(taskSnapshot -> {
            listener.showToast("image uploaded successfully");
            listener.hideProfileProgress();
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> downloadImageUri = uri);
        }).addOnProgressListener(taskSnapshot -> {
            double d = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
            listener.showProgress((int) d);
        });
    }

    public void saveUserProfile(UserProfile userProfile){
        if(downloadImageUri != null)
            userProfile.setProfile_picture(downloadImageUri + "");
        else
            userProfile.setProfile_picture(TempCache.user.getProfilePicture());
        listener.showSaveChangesProgressBar();
        listener.hideSaveChangesBtn();
        listener.hideCancelBtn();
        Call<List<MyResponse>> call = service.patchUserProfile(TempCache.USER_ACCESS_TOKEN , userProfile);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    listener.showToast("updated successfully");
                    listener.disableEditing();
                    listener.hideSaveChangesProgressBar();
                    updateUser(userProfile);
                    listener.setProfileViews();
                }
                else{
                    listener.showToast(error_msg);
                    listener.showToast(error_msg);
                    listener.hideSaveChangesProgressBar();
                    listener.showSaveChangesBtn();
                    listener.showCancelBtn();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
                listener.showToast(t.getMessage());
                listener.hideProgressBar();
                listener.showSaveChangesBtn();
                listener.showCancelBtn();
            }
        });
    }

    public void saveVendorProfile(VendorProfile vendorProfile){
        if(downloadImageUri != null)
            vendorProfile.setProfile_picture(downloadImageUri+"");
        listener.showSaveChangesProgressBar();
        listener.hideSaveChangesBtn();
        listener.hideCancelBtn();
        Call<List<MyResponse>>  call = service.patchVendorProfile(TempCache.VENDOR_ACCESS_TOKEN ,vendorProfile);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,@NonNull Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    listener.showToast("updated successfully");
                    listener.disableEditing();
                    listener.hideSaveChangesProgressBar();
                    updateVendor(vendorProfile);
                    listener.setProfileViews();
                }
                else{
                    listener.showToast(error_msg);
                    listener.showToast(error_msg);
                    listener.hideSaveChangesProgressBar();
                    listener.showSaveChangesBtn();
                    listener.showCancelBtn();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
                listener.showToast(t.getMessage());
                listener.hideProgressBar();
                listener.showSaveChangesBtn();
                listener.showCancelBtn();
            }
        });
    }

    public void getUser(){
        listener.hideInfo();
        listener.showLoadingPanel();
        Call<List<User>> call = service.getUser(TempCache.USER_ACCESS_TOKEN);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call,@NonNull Response<List<User>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    TempCache.user = response.body().get(0);
                    listener.setProfileViews();
                    listener.hideLoadingPanel();
                    listener.showInfo();
                    fetchStates();
                    fetchCities();
                }
                else{
                    listener.showToast(error_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
            }
        });
    }

    public void getVendor(){
        listener.showLoadingPanel();
        listener.hideInfo();
        Call<List<Vendor>> call = service.getVendor(TempCache.VENDOR_ACCESS_TOKEN);
        call.enqueue(new Callback<List<Vendor>>() {
            @Override
            public void onResponse(@NonNull Call<List<Vendor>> call,@NonNull Response<List<Vendor>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    TempCache.vendor = response.body().get(0);
                    fetchCities();
                    fetchStates();
                    listener.setProfileViews();
                    listener.hideLoadingPanel();
                    listener.showInfo();
                }
                else{
                    listener.showToast(error_msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Vendor>> call,@NonNull Throwable t) {
                listener.showToast(t.getMessage());
            }
        });
    }

    public String getStateById(int id){
        for(State state: ProfileCache.states){
            if(state.getState_id() == id)
                return state.getName();
        }
        return null;
    }

    public String getCityById(int id){
        for(City city: ProfileCache.allCities){
            if(city.getCity_id() == id)
                return city.getName();
        }
        return null;
    }

    private void updateUser(UserProfile userProfile){
        TempCache.user.setName(userProfile.getName());
        TempCache.user.setStateId(userProfile.getState_id());
        TempCache.user.setCityId(userProfile.getCity_id());
        TempCache.user.setProfilePicture(userProfile.getProfile_picture());
        TempCache.user.setAddress(userProfile.getAddress());
        TempCache.user.setPinCode(userProfile.getPin_code());
    }
    private void updateVendor(VendorProfile vendorProfile){
        TempCache.vendor.setName(vendorProfile.getName());
        TempCache.vendor.setStateId(vendorProfile.getState_id());
        TempCache.vendor.setCityId(vendorProfile.getCity_id());
        TempCache.vendor.setProfilePicture(vendorProfile.getProfile_picture());
        TempCache.vendor.setAddress(vendorProfile.getAddress());
        TempCache.vendor.setPinCode(vendorProfile.getPin_code());
    }
}
