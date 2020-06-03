package com.cstup.e_mandi.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.cstup.e_mandi.views.farmer.activites.HomeActivity;
import com.cstup.e_mandi.views.user.activites.BottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Runnable{
    private Button user_btn;
    private Button vendor_btn;
    private LinearLayout btn_container;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setCredentials();

        if(isLoggedIn()){
        Thread main = new Thread(this);
        main.start();
        }
        else{
            btn_container.setVisibility(View.VISIBLE);
            user_btn.setOnClickListener(v -> {
                user_btn.setBackground(ContextCompat.getDrawable(this , R.drawable.cust_veg_back));
                user_btn.setTextColor(ContextCompat.getColor(this , R.color.white));
                TempCache.USER_TYPE = constants.USER;
                startActivity(new Intent(MainActivity.this , LoginSignUp.class));
                finish();
            });
            vendor_btn.setOnClickListener(v -> {
                vendor_btn.setBackground(ContextCompat.getDrawable(this , R.drawable.cust_veg_back));
                vendor_btn.setTextColor(ContextCompat.getColor(this , R.color.white));
                TempCache.USER_TYPE = constants.VENDOR;
                startActivity(new Intent(MainActivity.this , LoginSignUp.class));
                finish();
            });
        }
    }

    @Override
    public void run() {
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String user_type = sp.getString(constants.USER_TYPE , null);
        if(user_type != null) {
            if (user_type.equals(constants.USER)) {
                startActivity(new Intent(MainActivity.this, BottomNavigation.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        }
    }
    private void initViews(){
        user_btn = findViewById(R.id.user_btn);
        vendor_btn = findViewById(R.id.vendor_btn);
        btn_container = findViewById(R.id.btn_container);
        sp = getSharedPreferences(constants.SHARED_PREF_NAME , MODE_PRIVATE);
    }
    private boolean isLoggedIn(){
        return sp.getBoolean(constants.IS_USER_LOGGED_IN , false);
    }

    private void setCredentials(){
        TempCache.USER_TYPE = sp.getString(constants.USER_TYPE , null);
        TempCache.IS_USER = sp.getBoolean(constants.USER , false);
        TempCache.IS_VENDOR = sp.getBoolean(constants.USER , false);
        TempCache.USER_ACCESS_TOKEN = sp.getString(constants.USER_KEY , null);
        TempCache.VENDOR_ACCESS_TOKEN = sp.getString(constants.VENDOR_KEY , null);

        if(TempCache.USER_ACCESS_TOKEN != null)
            Log.v("user_access_token -> " , TempCache.USER_ACCESS_TOKEN);
        if(TempCache.VENDOR_ACCESS_TOKEN != null)
            Log.v("vendor_access_token -> " , TempCache.VENDOR_ACCESS_TOKEN);
    }
}
