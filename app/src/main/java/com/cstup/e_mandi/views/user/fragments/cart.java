package com.cstup.e_mandi.views.user.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.cartAdapter;
import com.cstup.e_mandi.controllers.cartController;
import com.cstup.e_mandi.utilities.TempCache;

public class cart extends Fragment implements cartController.CartListener , cartAdapter.cartEventListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private FrameLayout listContainer;
    private RecyclerView recyclerView;
    private View emptyPage;
    private View errorView;
    private TextView tryAgain;
    private LinearLayout checkOutContainer;
    private TextView totalCartValue;
    private TextView checkOut;
    private ProgressBar progressBar;

    RecyclerView.Adapter adapter;
    cartController controller;
    public cart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(v);
        setRecyclerView();
        if(TempCache.CART_FIRST_CALL){
            controller.fetchCartItems();
            TempCache.CART_FIRST_CALL = false;
        }
        else{
            if(TempCache.cartItems != null && TempCache.cartItems.size() > 0){
                showCheckOutContainer();
                setTotalCartValue();
            }

        }

        tryAgain.setOnClickListener(v1 -> {
            controller.fetchCartItems();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if(TempCache.cartItems.size() == 0)
                controller.fetchCartItems();
            swipeRefreshLayout.setRefreshing(false);
        });
        return v;
    }

    private void initViews(View v){
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        listContainer = v.findViewById(R.id.frame);
        recyclerView = v.findViewById(R.id.recyclerView);
        emptyPage = v.findViewById(R.id.empty_page);
        errorView = v.findViewById(R.id.error_view);
        tryAgain = v.findViewById(R.id.try_again_tv);
        checkOutContainer = v.findViewById(R.id.checkout_container);
        totalCartValue = v.findViewById(R.id.total_cart_value);
        checkOut = v.findViewById(R.id.checkout_tv);
        progressBar = v.findViewById(R.id.progressBar);

        controller = new cartController(this);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new cartAdapter(controller.getCartItems() , this);
        recyclerView.setAdapter(adapter);
        notifyChanges();
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
    public void showEmptyPage() {
        emptyPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyPage() {
        emptyPage.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetPage() {
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoInternetPage() {
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showCheckOutContainer() {
        checkOutContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCheckOutContainer() {
        checkOutContainer.setVisibility(View.GONE);
    }

    @Override
    public void showListContainer() {
        listContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListContainer() {
        listContainer.setVisibility(View.GONE);
    }

    @Override
    public void setTotalCartValue() {
        totalCartValue.setText("\u20B9" + TempCache.TOTAL_CART_VALUE + "");
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyChanges() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTotalCost() {
        if(TempCache.TOTAL_CART_VALUE <= 0){
            TempCache.TOTAL_CART_VALUE = 0;
            hideCheckOutContainer();
            showEmptyPage();
        }
        setTotalCartValue();
    }
}
