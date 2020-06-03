package com.cstup.e_mandi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.controllers.loginSignUpController;
import com.cstup.e_mandi.model.Login_SignUp;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.cstup.e_mandi.views.farmer.activites.HomeActivity;
import com.cstup.e_mandi.views.user.activites.BottomNavigation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;


public class LoginSignUp extends AppCompatActivity implements loginSignUpController.EventListener {

    private Button login_btn;
    private Button sign_up_btn;
    private TextInputEditText phone_et;
    private TextInputEditText otp_et;
    private TextView resend_otp_tv;
    private TextView send_otp_btn;
    private loginSignUpController controller;
    private ProgressBar progressBar;
    private TextInputLayout otp_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        initViews();
        setViewsColor();

        login_btn.setOnClickListener(v -> {
            TempCache.LOGIN_OR_SIGN_UP = constants.LOGIN_CLICKED;
            setViewsColor();
        });

        sign_up_btn.setOnClickListener(v -> {
            TempCache.LOGIN_OR_SIGN_UP = constants.SIGN_UP_CLICKED;
            setViewsColor();
        });

        send_otp_btn.setOnClickListener(v -> {
            switch (TempCache.SEND_OTP_BUTTON_STATE){
                case constants.STATE_SEND_OTP:
                    if(!TextUtils.isEmpty(phone_et.getText()) && phone_et.getText().length() == 10){
                        Long contact = Long.parseLong(phone_et.getText().toString());
                        TempCache.CONTACT = contact + "";
                        if(TempCache.LOGIN_OR_SIGN_UP == constants.LOGIN_CLICKED)
                            controller.getLoginOtp(TempCache.USER_TYPE , contact);
                        else
                            controller.getSignUpOtp(TempCache.USER_TYPE , contact);
                    }
                    else{
                        phone_et.setError("Please enter a valid mobile number");
                    }
                    break;
                case constants.STATE_VERIFY_OTP:

                    if( !TextUtils.isEmpty(otp_et.getText())
                            && otp_et.getText().length() == 5
                            && !TextUtils.isEmpty(phone_et.getText())
                            && phone_et.getText().toString().length() == 10 ){

                        Login_SignUp login_signUp = new Login_SignUp();
                        login_signUp.setType(TempCache.USER_TYPE);
                        login_signUp.setContact(Long.parseLong(phone_et.getText().toString()));
                        login_signUp.setOtp(Integer.parseInt(otp_et.getText().toString()));
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(task -> {
                                    login_signUp.setDevice_fcm_token(Objects.requireNonNull(task.getResult()).getToken());
                                    if(TempCache.LOGIN_OR_SIGN_UP == constants.LOGIN_CLICKED){
                                        controller.postLoginOtp(login_signUp);
                                    }
                                    else{
                                        controller.postSignUpOtp(login_signUp);
                                    }
                                });
                    }
                    else{
                        otp_et.setError("Please enter a valid otp.");
                    }
                    break;
            }
        });

        resend_otp_tv.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(phone_et.getText())
                    && phone_et.getText().length() == 10){
                if(TempCache.LOGIN_OR_SIGN_UP == constants.LOGIN_CLICKED){
                    controller.getLoginOtp(TempCache.USER_TYPE , Long.parseLong(phone_et.getText().toString()));
                }
                else {
                    controller.getSignUpOtp(TempCache.USER_TYPE , Long.parseLong(phone_et.getText().toString()));
                }
            }
            else {
                phone_et.setError("Please enter valid mobile number");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TempCache.LOGIN_OR_SIGN_UP = constants.LOGIN_CLICKED;
    }

    private void initViews(){
        login_btn = findViewById(R.id.login_btn);
        sign_up_btn = findViewById(R.id.sign_up_btn);
        phone_et = findViewById(R.id.phone_et);
        otp_et = findViewById(R.id.otp_et);
        resend_otp_tv = findViewById(R.id.resend_otp_tv);
        send_otp_btn = findViewById(R.id.send_otp_btn);
        progressBar = findViewById(R.id.progressBar);
        otp_container = findViewById(R.id.otp_container);
        controller = new loginSignUpController(this);
    }
    public void setViewsColor(){
        switch (TempCache.LOGIN_OR_SIGN_UP){
            case constants.LOGIN_CLICKED:
                login_btn.setBackground(ContextCompat.getDrawable(LoginSignUp.this , R.drawable.cust_veg_back));
                login_btn.setTextColor(ContextCompat.getColor(LoginSignUp.this , R.color.white));
                sign_up_btn.setBackground(ContextCompat.getDrawable(LoginSignUp.this , R.drawable.cust_veg_white_back));
                sign_up_btn.setTextColor(ContextCompat.getColor(LoginSignUp.this , R.color.green));
                break;
            case constants.SIGN_UP_CLICKED:
                sign_up_btn.setBackground(ContextCompat.getDrawable(LoginSignUp.this , R.drawable.cust_veg_back));
                sign_up_btn.setTextColor(ContextCompat.getColor(LoginSignUp.this , R.color.white));
                login_btn.setBackground(ContextCompat.getDrawable(LoginSignUp.this , R.drawable.cust_veg_white_back));
                login_btn.setTextColor(ContextCompat.getColor(LoginSignUp.this , R.color.green));

        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void saveAccessToken(String token) {
        SharedPreferences sp = getSharedPreferences(constants.SHARED_PREF_NAME , MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(TempCache.USER_TYPE.equals(constants.USER)){
            editor.putString(constants.USER_KEY , token);
            TempCache.USER_ACCESS_TOKEN = token;
        }
        else{
            editor.putString(constants.VENDOR_KEY , token);
            TempCache.VENDOR_ACCESS_TOKEN = token;
        }
        editor.apply();
    }

    @Override
    public void showOtpButton() {
        send_otp_btn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOtpButton() {
        send_otp_btn.setVisibility(View.GONE);
    }

    @Override
    public void showOtpEditText() {
        otp_container.setVisibility(View.VISIBLE);
        resend_otp_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void enablePhoneEt() {
        phone_et.setEnabled(true);
    }

    @Override
    public void disablePhoneEt() {
        phone_et.setEnabled(false);
    }

    @Override
    public void enableOtpButton() {
        send_otp_btn.setEnabled(true);
    }

    @Override
    public void disableOtpButton() {
        send_otp_btn.setEnabled(false);
    }

    @Override
    public void enableResendTextView() {
        resend_otp_tv.setEnabled(true);
    }

    @Override
    public void disableResendTextView() {
        resend_otp_tv.setEnabled(false);
    }

    @Override
    public void otpSuccessfullySentToast() {
        Toast.makeText(this , "Otp sent successfully" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void incorrectOtpToast() {
        Toast.makeText(this , "Incorrect otp." , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void otpVerifiedSuccessfully() {
        Toast.makeText(this , "Otp verified successfully" , Toast.LENGTH_SHORT).show();
        SharedPreferences sp = getSharedPreferences(constants.SHARED_PREF_NAME , MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(constants.IS_USER_LOGGED_IN , true);
        editor.putString(constants.USER_TYPE , TempCache.USER_TYPE);
        switch (TempCache.USER_TYPE){
            case constants.USER:
                editor.putBoolean(constants.USER , true);
                editor.putString(constants.USER_CONTACT , TempCache.CONTACT);
                break;
            case constants.VENDOR:
                editor.putBoolean(constants.VENDOR , true);
                editor.putString(constants.VENDOR_CONTACT , TempCache.CONTACT);
                break;
        }
        editor.apply();

        if(TempCache.USER_TYPE.equals(constants.USER)){
            startActivity(new Intent(LoginSignUp.this , BottomNavigation.class));
            finish();
        }
        else{
            startActivity(new Intent(LoginSignUp.this , HomeActivity.class));
            finish();
        }

    }

    @Override
    public void errorToast(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeOtpButtonState() {
        send_otp_btn.setText(R.string.verify_otp);
    }

}
