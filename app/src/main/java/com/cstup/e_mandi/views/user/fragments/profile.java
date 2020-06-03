package com.cstup.e_mandi.views.user.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.cityAdapter;
import com.cstup.e_mandi.adapters.stateAdapter;
import com.cstup.e_mandi.controllers.profileController;
import com.cstup.e_mandi.model.Get.City;
import com.cstup.e_mandi.model.Get.State;
import com.cstup.e_mandi.model.Post.UserProfile;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.cstup.e_mandi.views.LoginSignUp;
import com.cstup.e_mandi.views.farmer.activites.HomeActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends Fragment implements profileController. EventListener {
    private CircleImageView profile_image;
    private TextView name;
    private TextView contact;
    private TextView state;
    private TextView city;
    private TextView tv_pin;
    private ImageView edit_details;
    private TextView address;
    private TextView order_history;
    private FrameLayout history_container;
    private RecyclerView history_recyclerView;
    private TextView logout;
    private ProgressBar progressBar;
    private Button switch_account;
    private EditText edit_name;
    private Spinner choose_state;
    private Spinner choose_city;
    private Button save_changes;
    private ProgressBar save_changes_progressBar;
    private EditText edit_pin;
    private EditText edit_address;
    private profileController profileController;
    private cityAdapter cityAdapter;
    private stateAdapter stateAdapter;
    private ProgressBar profileProgress;
    private Button cancel_btn;
    private View loadingProfile;
    private LinearLayout info;
    private ProgressBar pb;
    private UserProfile userProfile = new UserProfile();

    private static final int CHOOSE_IMAGE_REQUEST_CODE = 20;
    public profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(v);
        disableEditing();

        choose_city.setAdapter(cityAdapter);
        choose_state.setAdapter(stateAdapter);

        if(TempCache.isFetchUserProfileFirstCall){
            profileController.getUser();
            profileController.fetchStates();
            profileController.fetchCities();
            TempCache.isFetchUserProfileFirstCall = false;
        }
        else {
            setProfileViews();
            showInfo();
            hideLoadingPanel();
        }

        edit_details.setOnClickListener(v13 -> enableEditing());

        save_changes.setOnClickListener(v15 -> {
            if(validate()){
                userProfile.setName(edit_name.getText().toString());
                userProfile.setAddress(edit_address.getText().toString());
                userProfile.setPin_code(Integer.parseInt(edit_pin.getText().toString()));
                profileController.saveUserProfile(userProfile);
            }
        });


        choose_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose_city.setSelection(position);
                City c = null;
                if(profileController.getCities().size() > 0)
                    c = profileController.getCities().get(position);
                if(c != null){
                    if(c.getCity_id() != null)
                        userProfile.setCity_id(c.getCity_id());
                }
                Log.v("selectedCity " , profileController.getCities().get(position) + " -city");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        choose_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose_state.setSelection(position);
                State s = null;
                if(profileController.getStates().size() > 0)
                    s = profileController.getStates().get(position);
                if(s != null){
                    if(s.getState_id() != null){
                        userProfile.setState_id(s.getState_id());
                        profileController.filterCityListByState(s.getState_id());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profileController.setSwitchAccountBtn();

        logout.setOnClickListener(v1 -> {
            SharedPreferences sp = Objects.requireNonNull(getActivity())
                    .getSharedPreferences(constants.SHARED_PREF_NAME , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(constants.IS_USER_LOGGED_IN , false);
            editor.apply();
            TempCache.SELECTED_FRAGMENT = constants.HOME_FRAGMENT;
            TempCache.USER_TYPE = constants.USER;
            startActivity(new Intent(getContext() , LoginSignUp.class));
            getActivity().finish();
        });

        switch_account.setOnClickListener(v1 -> profileController.switchAccount());

        profile_image.setOnClickListener(v12 -> {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent ,CHOOSE_IMAGE_REQUEST_CODE);
        });

        cancel_btn.setOnClickListener(v14 -> disableEditing());

        return v;
    }
    private void initViews(View v){
        profile_image = v.findViewById(R.id.profile_image);
        name = v.findViewById(R.id.name);
        contact = v.findViewById(R.id.contact);
        state = v.findViewById(R.id.state);
        city = v.findViewById(R.id.city);
        tv_pin = v.findViewById(R.id.tv_pin);
        edit_details = v.findViewById(R.id.edit_details);
        address = v.findViewById(R.id.address);
        order_history = v.findViewById(R.id.order_history);
        history_container = v.findViewById(R.id.history_container);
        history_recyclerView = v.findViewById(R.id.history_recyclerView);
        logout = v.findViewById(R.id.logout_tv);
        progressBar = v.findViewById(R.id.progressBar);
        switch_account = v.findViewById(R.id.switch_account);
        edit_name = v.findViewById(R.id.edit_name);
        choose_state = v.findViewById(R.id.choose_state);
        choose_city = v.findViewById(R.id.choose_city);
        save_changes = v.findViewById(R.id.save_changes_btn);
        save_changes_progressBar = v.findViewById(R.id.save_changes_progressBar);
        edit_pin = v.findViewById(R.id.edit_pin);
        edit_address = v.findViewById(R.id.edit_address);
        profileProgress = v.findViewById(R.id.profileProgress);
        cancel_btn = v.findViewById(R.id.cancel_btn);
        loadingProfile = v.findViewById(R.id.loadingPanel);
        info = v.findViewById(R.id.info);
        pb = v.findViewById(R.id.pb);

        profileController = new profileController(getContext() , this);
        cityAdapter = new cityAdapter(getContext() , profileController.getCities());
        stateAdapter = new stateAdapter(getContext() , profileController.getStates());
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
    public void showSwitchAccountBtn() {
        switch_account.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSwitchAccountBtn() {
        switch_account.setVisibility(View.GONE);
    }

    @Override
    public void finishActivity() {
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void startUserAccount() {}

    @Override
    public void startVendorAccount() {
        startActivity(new Intent(getContext() , HomeActivity.class));
    }

    @Override
    public void setTextSwitchToVendor() {
        switch_account.setText(R.string.switch_to_vendor);
    }

    @Override
    public void setTextSwitchToUser() {}

    @Override
    public void disableEditing() {
        edit_name.setVisibility(View.GONE);
        choose_state.setVisibility(View.GONE);
        choose_city.setVisibility(View.GONE);
        edit_address.setVisibility(View.GONE);
        edit_pin.setVisibility(View.GONE);
        save_changes_progressBar.setVisibility(View.GONE);
        save_changes.setVisibility(View.GONE);
        cancel_btn.setVisibility(View.GONE);
        profileProgress.setVisibility(View.GONE);

        name.setVisibility(View.VISIBLE);
        contact.setVisibility(View.VISIBLE);
        state.setVisibility(View.VISIBLE);
        city.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        profile_image.setEnabled(false);
        tv_pin.setVisibility(View.VISIBLE);
    }

    private void enableEditing(){
        edit_name.setVisibility(View.VISIBLE);
        edit_name.setText(name.getText().toString());
        choose_state.setVisibility(View.VISIBLE);
        choose_city.setVisibility(View.VISIBLE);
        edit_address.setVisibility(View.VISIBLE);
        edit_address.setText(address.getText().toString());
        edit_pin.setVisibility(View.VISIBLE);
        edit_pin.setText(tv_pin.getText().toString());
        save_changes.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.VISIBLE);
        save_changes_progressBar.setVisibility(View.GONE);

        name.setVisibility(View.GONE);
        contact.setVisibility(View.GONE);
        state.setVisibility(View.GONE);
        city.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        profile_image.setEnabled(true);
        tv_pin.setVisibility(View.GONE);
    }

    @Override
    public void hideSaveChangesBtn() {
        save_changes.setVisibility(View.GONE);
    }

    @Override
    public void showSaveChangesBtn() {
        save_changes.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSaveChangesProgressBar() {
        save_changes_progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSaveChangesProgressBar() {
        save_changes_progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setName(String name) {
        if(name != null)
            this.name.setText(name);
        else
            this.name.setText(R.string.name);
    }

    @Override
    public void setContact(String contact) {
        if(contact != null && !isEmpty(contact))
            this.contact.setText(contact);
        else
            this.contact.setText(R.string.contact);
    }

    @Override
    public void setState(String state) {
        if(state != null)
            this.state.setText(state);
        else
            this.state.setText(R.string.state);
    }

    @Override
    public void setCity(String city) {
        if(city != null)
            this.city.setText(city);
        else
            this.city.setText(R.string.city);
    }

    @Override
    public void setAddress(String address) {
        if(address != null)
            this.address.setText(address);
        else
            this.address.setText(R.string.address);
    }

    private void setProfile_image(Uri uri){
        if(uri != null)
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(uri).centerCrop().into(profile_image);
    }

    private void setTv_pin(String pin){
        if(pin != null && !isEmpty(pin))
            tv_pin.setText(pin);
        else
            tv_pin.setVisibility(View.GONE);
    }

    @Override
    public void setProfileViews() {
        String name = TempCache.user.getName();
        String contact = TempCache.user.getContact()+"";
        String state = null;
        if(TempCache.user.getStateId() != null)
            state = profileController.getStateById(TempCache.user.getStateId());
        String city = null;
        if(TempCache.user.getCityId() != null)
            city = profileController.getCityById(TempCache.user.getCityId());
        String address = TempCache.user.getAddress();
        String pin = TempCache.user.getPinCode() + "";
        String imagePath = TempCache.user.getProfilePicture();

        setName(name);
        setContact(contact);
        setState(state);
        setCity(city);
        setAddress(address);
        setTv_pin(pin);
        if(imagePath != null)
            setProfile_image(Uri.parse(imagePath));

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProfileProgress() {
        profileProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProfileProgress() {
        profileProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideCancelBtn() {
        cancel_btn.setVisibility(View.GONE);
    }

    @Override
    public void showCancelBtn() {
        cancel_btn.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateStates() {
        stateAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCities() {
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(int progress) {
        profileProgress.setProgress(progress);
    }

    @Override
    public void showLoadingPanel() {
        loadingProfile.setVisibility(View.VISIBLE);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingPanel() {
        loadingProfile.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void hideInfo() {
        info.setVisibility(View.GONE);
    }

    @Override
    public void showInfo() {
        info.setVisibility(View.VISIBLE);
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    private boolean validate(){
        String name = edit_name.getText().toString();
        String address = edit_address.getText().toString();
        String pin = edit_pin.getText().toString();

        if(isEmpty(name)){
            edit_name.setError("please fill your name");
            return false;
        }
        if(isEmpty(address)){
            edit_address.setError("please fill your address");
            return false;
        }
        if(isEmpty(pin)){
            edit_pin.setError("please fill pin");
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null){
            //starting image crop activity
            CropImage.activity(data.getData())
                    .start(Objects.requireNonNull(getContext()), this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == Activity.RESULT_OK){
                assert result != null;
                Uri resultUri = result.getUri();
                Log.v("imageUri -> " , resultUri+"");
                setProfile_image(resultUri);
                profileController.uploadPhoto(resultUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Log.v("image cropping error -> " , error.toString());
            }
        }
    }
}
