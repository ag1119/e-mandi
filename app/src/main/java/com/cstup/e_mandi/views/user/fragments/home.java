package com.cstup.e_mandi.views.user.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.homeAdapter;
import com.cstup.e_mandi.controllers.homePageController;
import com.cstup.e_mandi.environmentVariables.test.Crops;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.cstup.e_mandi.views.farmer.activites.AddCropCredentials;

import java.util.Objects;

public class home extends Fragment implements homePageController.EventListener , homeAdapter.EventListener{

    private EditText et_search_box;
    private TextView tv_vegetables;
    private TextView tv_grains;
    private TextView tv_fruits;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private View error_view;
    private TextView try_again_tv;
    private homePageController controller;
    private TextView add_crop_tv;
    private SwipeRefreshLayout swipe_refresh;
    public home() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        setAddCropTv();
        setRecyclerView();

        if(TempCache.IS_HOME_CONTROLLER_FIRST_CALL){
            controller.getAllCropType();
            TempCache.IS_HOME_CONTROLLER_FIRST_CALL = false;
        }

        tv_vegetables.setOnClickListener(v -> {
            TempCache.CLICKED = constants.VEGETABLES_CLICKED;
            TempCache.HOMEPAGE_LIST_ITEMS = Crops.vegetables;
            adapter.notifyDataSetChanged();
            setView();
        });

        tv_grains.setOnClickListener(v -> {
            TempCache.CLICKED = constants.GRAINS_CLICKED;
            TempCache.HOMEPAGE_LIST_ITEMS = Crops.grains;
            adapter.notifyDataSetChanged();
            setView();
        });

        tv_fruits.setOnClickListener(v -> {
            TempCache.CLICKED = constants.FRUITS_CLICKED;
            TempCache.HOMEPAGE_LIST_ITEMS = Crops.fruits;
            adapter.notifyDataSetChanged();
            setView();
        });

        try_again_tv.setOnClickListener(v -> {
            controller.getAllCropType();
        });

        swipe_refresh.setOnRefreshListener(() -> {
            controller.getAllCropType();
        });

        return view;
    }

    @Override
    public void onResume() {
        setView();
        super.onResume();
    }

    private void initViews(View view){
        et_search_box = view.findViewById(R.id.search);
        tv_vegetables = view.findViewById(R.id.vegetables);
        tv_grains = view.findViewById(R.id.grains);
        tv_fruits = view.findViewById(R.id.fruits);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        error_view = view.findViewById(R.id.error_view);
        try_again_tv = view.findViewById(R.id.try_again_tv);
        add_crop_tv = view.findViewById(R.id.add_crop_tv);
        swipe_refresh = view.findViewById(R.id.swipe_refresh);

        controller = new homePageController(this);
    }

    private void setView(){
        switch (TempCache.CLICKED){
            case constants.VEGETABLES_CLICKED:
                tv_vegetables.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.white));
                tv_vegetables.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_veg_back));
                tv_grains.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.wheat));
                tv_grains.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_grain_white_back));
                tv_fruits.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.apple));
                tv_fruits.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_fruit_white_back));
                et_search_box.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_veg_white_back));
                et_search_box.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.green));
                break;
            case constants.GRAINS_CLICKED:
                tv_grains.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.white));
                tv_grains.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_grain_back));
                tv_vegetables.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.green));
                tv_vegetables.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_veg_white_back));
                tv_fruits.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext(), R.color.apple));
                tv_fruits.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_fruit_white_back));
                et_search_box.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_grain_white_back));
                et_search_box.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.wheat));
                break;
            case constants.FRUITS_CLICKED:
                tv_fruits.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.white));
                tv_fruits.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_fruit_back));
                tv_grains.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.wheat));
                tv_grains.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_grain_white_back));
                tv_vegetables.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.green));
                tv_vegetables.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_veg_white_back));
                et_search_box.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()).getApplicationContext() , R.drawable.cust_fruit_white_back));
                et_search_box.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getActivity()).getApplicationContext() , R.color.apple));
                break;
        }
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2 , LinearLayoutManager.VERTICAL));
        adapter = new homeAdapter(this);
        recyclerView.setAdapter(adapter);
        updateList();
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorPage() {
        try_again_tv.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_veg_back));
        try_again_tv.setTextColor(ContextCompat.getColor(getContext() , R.color.white));
        error_view.setVisibility(View.GONE);
        try_again_tv.setVisibility(View.GONE);
        try_again_tv.setEnabled(false);
    }

    @Override
    public void showErrorPage() {
        try_again_tv.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_veg_white_back));
        try_again_tv.setTextColor(ContextCompat.getColor(getContext() , R.color.green));
        error_view.setVisibility(View.VISIBLE);
        try_again_tv.setVisibility(View.VISIBLE);
        try_again_tv.setEnabled(true);
    }

    @Override
    public void errorToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void setRefreshingFalse() {
        swipe_refresh.setRefreshing(false);
    }

    private void setAddCropTv(){
        String user_type = Objects.requireNonNull(getContext())
                            .getSharedPreferences(constants.SHARED_PREF_NAME , Context.MODE_PRIVATE)
                            .getString(constants.USER_TYPE , null);
        if(user_type != null){
            if(user_type.equals(constants.USER))
                add_crop_tv.setVisibility(View.GONE);
            else
                add_crop_tv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startActivityAddCropCredentials() {
        startActivity(new Intent(getContext() , AddCropCredentials.class));
    }
}
