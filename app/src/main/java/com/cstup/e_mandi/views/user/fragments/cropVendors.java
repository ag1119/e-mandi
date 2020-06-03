package com.cstup.e_mandi.views.user.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.cropVendorsAdapter;
import com.cstup.e_mandi.controllers.cropVendorsController;
import com.cstup.e_mandi.views.user.activites.AdapterEventListener;

public class cropVendors extends Fragment implements cropVendorsController.EventListener{
    private RecyclerView recyclerView;
    private cropVendorsController controller;
    private ProgressBar progressBar;
    private View errorPage;
    private View noVendorPage;
    private TextView tryAgain;
    private RecyclerView.Adapter adapter;
    public cropVendors() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crop_vendors, container, false);
        initViews(v);
        setRecyclerView();

        controller.getCropVendors();
        tryAgain.setOnClickListener(v1 -> {
            hideErrorPage();
            controller.getCropVendors();
        });

        //new cropVendorsListController().updateProductVendors(adapter);

        return v;
    }


    private void initViews(View v){
        recyclerView = v.findViewById(R.id.recyclerView);
        progressBar = v.findViewById(R.id.progressBar);
        errorPage = v.findViewById(R.id.error_view);
        tryAgain = v.findViewById(R.id.try_again_tv);
        noVendorPage = v.findViewById(R.id.no_vendor_page);

        controller = new cropVendorsController(this);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorPage() {
        errorPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorPage() {
        errorPage.setVisibility(View.GONE);
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
    public void notifyChanges() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoVendorPage() {
        noVendorPage.setVisibility(View.VISIBLE);
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new cropVendorsAdapter(controller.getList());
        recyclerView.setAdapter(adapter);
    }


}
