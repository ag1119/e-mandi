package com.cstup.e_mandi.views.user.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.cropVendorsAdapter;
import com.cstup.e_mandi.model.FragmentParams;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigation extends AppCompatActivity implements AdapterEventListener {

    private BottomNavigationView bottomNavigationView;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        initViews();
        TempCache.setUserDefaults();

        TempCache.genericMethods.setFragment(getFragmentParams(TempCache.SELECTED_TAG));
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.HOME_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.HOME_TAG));
                    break;
                case R.id.cart:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.CART_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.CART_TAG));
                    break;
                case R.id.my_orders:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.ORDERS_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.ORDERS_TAG));
                    break;
                case R.id.notifications:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.NOTIFICATION_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.NOTIFICATION_TAG));
                    break;
                case R.id.profile:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.PROFILE_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.PROFILE_TAG));
                    break;
            }
            return true;
        });
    }

    private void initViews(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        menu = bottomNavigationView.getMenu();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else{
            super.onBackPressed();
            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();
            TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.map.get(tag);
            TempCache.SELECTED_TAG = tag;
            TempCache.SELECTED_FRAGMENT = getSupportFragmentManager().findFragmentByTag(tag);
            setChecked();
        }
    }

    public FragmentParams getFragmentParams(String tag){
        FragmentParams params = new FragmentParams();
        switch (tag){
            case constants.HOME_TAG:
                params.setFragment(constants.HOME_FRAGMENT);
                params.setTag(constants.HOME_TAG);
                break;
            case constants.CART_TAG:
                params.setFragment(constants.CART_FRAGMENT);
                params.setTag(constants.CART_TAG);
                break;
            case constants.ORDERS_TAG:
                params.setFragment(constants.ORDERS_FRAGMENT);
                params.setTag(constants.ORDERS_TAG);
                break;
            case constants.NOTIFICATION_TAG:
                params.setFragment(constants.NOTIFICATIONS_FRAGMENT);
                params.setTag(constants.NOTIFICATION_TAG);
                break;
            case constants.PROFILE_TAG:
                params.setFragment(constants.PROFILE_FRAGMENT);
                params.setTag(constants.PROFILE_TAG);
                break;
        }
        params.setContext(BottomNavigation.this);
        params.setFrame_id(R.id.frame);
        return params;
    }
    private void setChecked(){
        switch (TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM){
            case constants.HOME_INDEX:
                menu.getItem(constants.HOME_INDEX).setChecked(true);
                break;
            case constants.CART_INDEX:
                menu.getItem(constants.CART_INDEX).setChecked(true);
                break;
            case constants.ORDERS_INDEX:
                menu.getItem(constants.ORDERS_INDEX).setChecked(true);
                break;
            case constants.NOTIFICATION_INDEX:
                menu.getItem(constants.NOTIFICATION_INDEX).setChecked(true);
                break;
            case constants.PROFILE_INDEX:
                menu.getItem(constants.PROFILE_INDEX).setChecked(true);
                break;
        }
    }
}
