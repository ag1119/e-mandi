package com.cstup.e_mandi.views.farmer.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import com.cstup.e_mandi.Cache.MyCropsCache;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.FragmentParams;
import com.cstup.e_mandi.utilities.GenericMethods;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    public interface MyCropsEventListener{
        void hideEditContainer();
        void hideBtnContainer();
        void showRecyclerView();
    }
    public static MyCropsEventListener listener;
    private BottomNavigationView bottomNavigationView;
    private Menu menu;
    GenericMethods genericMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        TempCache.setFarmerDefaults();

        genericMethods.setFragment(getFragmentParams(TempCache.FARMER_SELECTED_TAG));

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.orders:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.FARMER_ORDER_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.FRAMER_ORDER_FRAGMENT_TAG));
                    break;
                case R.id.my_crops:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.FARMER_MY_CROP_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.FARMER_MY_CROP_FRAGMENT_TAG));
                    break;
                case R.id.add_crop:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.FARMER_ADD_CROP_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.FARMER_ADD_CROP_FRAGMENT_TAG));
                    break;
                case R.id.notifications:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.FARMER_NOTIFICATIONS_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.FARMER_NOTIFICATION_FRAGMENT_TAG));
                    break;
                case R.id.profile:
                    TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.FARMER_PROFILE_INDEX;
                    TempCache.genericMethods.setFragment(getFragmentParams(constants.FARMER_PROFILE_FRAGMENT_TAG));
                    break;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if(MyCropsCache.IS_EDIT_CONTAINER_VISIBLE && MyCropsCache.IS_MY_CROP_CONTEXT){
            listener.hideBtnContainer();
            listener.hideEditContainer();
            listener.showRecyclerView();
            return;
        }
        if(getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else{
            super.onBackPressed();
            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();
            TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM = constants.map.get(tag);
            TempCache.FARMER_SELECTED_TAG = tag;
            TempCache.FARMER_SELECTED_FRAGMENT = getSupportFragmentManager().findFragmentByTag(tag);
            setChecked();
        }
    }

    private void initViews(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        menu = bottomNavigationView.getMenu();
        genericMethods = new GenericMethods();
    }

    private FragmentParams getFragmentParams(String tag){
        FragmentParams params = new FragmentParams();
        switch (tag){
            case constants.FRAMER_ORDER_FRAGMENT_TAG:
                params.setFragment(constants.FRAMER_ORDER_FRAGMENT);
                params.setTag(constants.FRAMER_ORDER_FRAGMENT_TAG);
                break;
            case constants.FARMER_MY_CROP_FRAGMENT_TAG:
                params.setFragment(constants.FARMER_MY_CROP_FRAGMENT);
                params.setTag(constants.FARMER_MY_CROP_FRAGMENT_TAG);
                break;
            case constants.FARMER_ADD_CROP_FRAGMENT_TAG:
                params.setFragment(constants.FARMER_ADD_CROP_FRAGMENT);
                params.setTag(constants.FARMER_ADD_CROP_FRAGMENT_TAG);
                break;
            case constants.FARMER_NOTIFICATION_FRAGMENT_TAG:
                params.setFragment(constants.FARMER_NOTIFICATION_FRAGMENT);
                params.setTag(constants.FARMER_NOTIFICATION_FRAGMENT_TAG);
                break;
            case constants.FARMER_PROFILE_FRAGMENT_TAG:
                params.setFragment(constants.FARMER_PROFILE_FRAGMENT);
                params.setTag(constants.FARMER_PROFILE_FRAGMENT_TAG);
                break;
        }
        params.setContext(HomeActivity.this);
        params.setFrame_id(R.id.container);
        return params;
    }

    private void setChecked(){
        switch (TempCache.BOTTOM_NAVIGATION_ENABLED_ITEM){
            case constants.FARMER_ORDER_INDEX:
                menu.getItem(constants.FARMER_ORDER_INDEX).setChecked(true);
                break;
            case constants.FARMER_MY_CROP_INDEX:
                menu.getItem(constants.FARMER_MY_CROP_INDEX).setChecked(true);
                break;
            case constants.FARMER_ADD_CROP_INDEX:
                menu.getItem(constants.FARMER_ADD_CROP_INDEX).setChecked(true);
                break;
            case constants.FARMER_NOTIFICATIONS_INDEX:
                menu.getItem(constants.FARMER_NOTIFICATIONS_INDEX).setChecked(true);
                break;
            case constants.FARMER_PROFILE_INDEX:
                menu.getItem(constants.FARMER_PROFILE_INDEX).setChecked(true);
                break;
        }
    }
}
