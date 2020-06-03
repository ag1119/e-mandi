package com.cstup.e_mandi.controllers;

import android.util.Log;

import com.cstup.e_mandi.environmentVariables.ev;
import com.cstup.e_mandi.model.Login_SignUp;
import com.cstup.e_mandi.model.MyResponse;
import com.cstup.e_mandi.services.RetrofitInstance;
import com.cstup.e_mandi.services.DataService;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginSignUpController {
    private DataService service = RetrofitInstance.getService(ev.BASE_URL);
    private EventListener listener;

    public loginSignUpController(EventListener listener){
        this.listener = listener;
    }

    public interface EventListener{
        void enableOtpButton();
        void disableOtpButton();
        void enableResendTextView();
        void disableResendTextView();
        void otpSuccessfullySentToast();
        void incorrectOtpToast();
        void otpVerifiedSuccessfully();
        void errorToast(String msg);
        void changeOtpButtonState();
        void setViewsColor();
        void showProgressBar();
        void hideProgressBar();
        void saveAccessToken(String token);
        void showOtpButton();
        void hideOtpButton();
        void showOtpEditText();
        void enablePhoneEt();
        void disablePhoneEt();
    }

    public void getLoginOtp(String type , Long contact){
        listener.hideOtpButton();
        listener.showProgressBar();
        listener.disableOtpButton();
        listener.disableResendTextView();
        listener.disablePhoneEt();
        Call<List<MyResponse>> call = service.getLoginOtp(type , contact);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(Call<List<MyResponse>> call, Response<List<MyResponse>> response) {

                if(response.isSuccessful()){
                    listener.otpSuccessfullySentToast();
                    listener.showOtpEditText();
                    TempCache.SEND_OTP_BUTTON_STATE = constants.STATE_VERIFY_OTP;
                    listener.changeOtpButtonState();
                }
                else{
                    String msg = "You are not registered with this number please sign up for registration.";
                    listener.errorToast(msg);
                    TempCache.LOGIN_OR_SIGN_UP = constants.SIGN_UP_CLICKED;
                    listener.setViewsColor();
                    listener.enablePhoneEt();
                }
                listener.hideProgressBar();
                listener.enableOtpButton();
                listener.showOtpButton();
                listener.enableResendTextView();
            }

            @Override
            public void onFailure(Call<List<MyResponse>> call, Throwable t) {
                listener.errorToast(t.getMessage());
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.enableOtpButton();
                listener.enablePhoneEt();
            }
        });
    }

    public void getSignUpOtp(String type , Long contact){
        listener.hideOtpButton();
        listener.showProgressBar();
        listener.disableOtpButton();
        listener.disableResendTextView();
        listener.disablePhoneEt();
        DataService service = RetrofitInstance.getService(ev.BASE_URL);
        Call<List<MyResponse>> call = service.getSignUpOtp(type , contact);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(Call<List<MyResponse>> call, Response<List<MyResponse>> response) {
                if(response.isSuccessful()) {
                    listener.showOtpEditText();
                    listener.changeOtpButtonState();
                    listener.otpSuccessfullySentToast();
                    TempCache.SEND_OTP_BUTTON_STATE = constants.STATE_VERIFY_OTP;
                }
                else{
                    String errorMsg = "Either you are already registered or you entered invalid mobile number";
                    listener.errorToast(errorMsg);
                    listener.enablePhoneEt();
                }
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.enableOtpButton();
                listener.enableResendTextView();
            }

            @Override
            public void onFailure(Call<List<MyResponse>> call, Throwable t) {
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.errorToast(t.getMessage());
                listener.enableOtpButton();
                listener.enablePhoneEt();
                Log.v("fail " , Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void postLoginOtp(Login_SignUp login){
        listener.showProgressBar();
        listener.hideOtpButton();
        listener.disableOtpButton();
        listener.disableResendTextView();
        DataService service = RetrofitInstance.getService(ev.BASE_URL);
        Call<List<MyResponse>> call = service.postLoginOtp(login);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(Call<List<MyResponse>> call, Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    String token = response.headers().get("X-Auth-Token");
                    listener.saveAccessToken(token);
                    listener.enableOtpButton();
                    listener.enableResendTextView();
                    listener.otpVerifiedSuccessfully();
                }
                else{
                    listener.incorrectOtpToast();
                }
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.enableOtpButton();
                listener.enableResendTextView();
            }

            @Override
            public void onFailure(Call<List<MyResponse>> call, Throwable t) {
                listener.errorToast(t.getMessage());
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.enableOtpButton();
                listener.enableResendTextView();
            }
        });
    }

    public void postSignUpOtp(Login_SignUp signUp){
        listener.hideOtpButton();
        listener.showProgressBar();
        listener.disableOtpButton();
        listener.disableResendTextView();
        DataService service = RetrofitInstance.getService(ev.BASE_URL);
        Call<List<MyResponse>> call = service.postSignUpOtp(signUp);
        call.enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(Call<List<MyResponse>> call, Response<List<MyResponse>> response) {
                if(response.isSuccessful()){
                    String token = response.headers().get("X-Auth-Token");
                    listener.saveAccessToken(token);
                    listener.enableOtpButton();
                    listener.enableResendTextView();
                    listener.otpVerifiedSuccessfully();
                }
                else{
                    listener.incorrectOtpToast();
                }
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.enableOtpButton();
                listener.enableResendTextView();
            }

            @Override
            public void onFailure(Call<List<MyResponse>> call, Throwable t) {
                listener.errorToast(t.getMessage());
                listener.hideProgressBar();
                listener.showOtpButton();
                listener.enableOtpButton();
                listener.enableResendTextView();
            }
        });
    }
}
