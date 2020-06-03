package com.cstup.e_mandi.views.farmer.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cstup.e_mandi.Cache.MyCropsCache;
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.cropVendorsAdapter;
import com.cstup.e_mandi.controllers.myCropsController;
import com.cstup.e_mandi.model.Get.MyCrops;
import com.cstup.e_mandi.model.Patch.MyCrop;
import com.cstup.e_mandi.utilities.QtyAndPrice;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.views.MainActivity;
import com.cstup.e_mandi.views.farmer.activites.HomeActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class farmer_my_crops extends Fragment implements
        myCropsController.EventListener ,
        cropVendorsAdapter.MyCropsEventListener,
        HomeActivity.MyCropsEventListener {
    private LinearLayout editContainer;

    private RecyclerView recyclerView;
    private ProgressBar fetchingCropsPB;

    private CircleImageView cropImage;
    private TextView cropName;
    private TextView cropPrice;
    private TextView cropDesc;
    private TextView offer;
    private TextView govtPrice;
    private TextView remainingStock;

    private TextView addQty;
    private TextView removeQty;
    private EditText qtyEt;
    private RadioButton kg;
    private RadioButton quintol;
    private EditText priceEt;
    private RadioButton perKg;
    private RadioButton perQuintol;

    private LinearLayout btnContainer;
    private Button saveChanges;
    private Button cancel;
    private ProgressBar progressBar;
    private View errorView;
    private View emptyPage;
    private TextView emptyPageTv;
    private TextView tryAgain;

    private RecyclerView.Adapter adapter;
    private myCropsController controller;

    private boolean ADD_QUANTITY_PRESSED;

    public farmer_my_crops() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_farmer_my_crops, container, false);
         initViews(view);
         setRecyclerView();

         if(TempCache.IS_FETCH_MY_CROPS_FIRST_CALL ||  TempCache.IS_MY_CROPS_UPDATED){
             hideRecyclerView();
             controller.fetchMyCrops();
         }


         addQty.setOnClickListener(v -> {
             changeTvToGreen(addQty);
             changeTvToWhite(removeQty);
             ADD_QUANTITY_PRESSED = true;
         });

         removeQty.setOnClickListener(v -> {
             changeTvToGreen(removeQty);
             changeTvToWhite(addQty);
             ADD_QUANTITY_PRESSED = false;
         });

         qtyEt.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 setCropQtyAndPrice();
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

         priceEt.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 setCropQtyAndPrice();
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

         cancel.setOnClickListener(v -> {
             hideEditContainer();
             hideBtnContainer();
             showRecyclerView();
         });

         saveChanges.setOnClickListener(v -> {
             if(validate()){
                 MyCrop myCrop = new MyCrop();
                 myCrop.setChangeInQty(getQty());
                 myCrop.setCropPrice(getPrice());
                 myCrop.setCropName(TempCache.SELECTED_MY_CROP_ITEM.getCropName());
                 myCrop.setCropTypeId(TempCache.SELECTED_MY_CROP_ITEM.getCropTypeId());
                 myCrop.setDescription(TempCache.SELECTED_MY_CROP_ITEM.getDescription());
                 controller.updateMyCrop(TempCache.SELECTED_MY_CROP_ITEM.getCropId() , myCrop);
             }
         });

         tryAgain.setOnClickListener(v -> {
             hideErrorPage();
             controller.fetchMyCrops();
         });

         kg.setOnClickListener(v -> {
             setCropQtyAndPrice();
         });

         quintol.setOnClickListener(v -> {
             setCropQtyAndPrice();
         });

         perQuintol.setOnClickListener(v -> {
             setCropQtyAndPrice();
         });
         return view;
    }

    private void initViews(View v) {
        editContainer = v.findViewById(R.id.edit_container);
        cropImage = v.findViewById(R.id.crop_image);
        cropName = v.findViewById(R.id.crop_name);
        cropPrice = v.findViewById(R.id.price);
        cropDesc = v.findViewById(R.id.crop_desc);
        offer = v.findViewById(R.id.offer);
        govtPrice = v.findViewById(R.id.govt_price);
        remainingStock = v.findViewById(R.id.remaining_stock);
        addQty = v.findViewById(R.id.add_qty);
        removeQty = v.findViewById(R.id.remove_qty);
        qtyEt = v.findViewById(R.id.qty_et);
        kg = v.findViewById(R.id.kg);
        quintol = v.findViewById(R.id.quintol);
        priceEt = v.findViewById(R.id.price_et);
        perKg = v.findViewById(R.id.per_kg);
        perQuintol = v.findViewById(R.id.per_quintol);
        btnContainer = v.findViewById(R.id.btn_container);
        saveChanges = v.findViewById(R.id.save_changes_btn);
        cancel = v.findViewById(R.id.cancel_btn);
        progressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.recyclerView);
        fetchingCropsPB = v.findViewById(R.id.fetchingCropsPB);
        errorView = v.findViewById(R.id.error_view);
        emptyPage = v.findViewById(R.id.empty_page);
        emptyPageTv = v.findViewById(R.id.empty_page_tv);
        tryAgain = v.findViewById(R.id.try_again_tv);

        controller = new myCropsController(this);
        HomeActivity.listener = this;
    }

    private void setViews(){
        MyCrops item = TempCache.SELECTED_MY_CROP_ITEM;
        setCropImage(item.getCropImage());
        setCropName(item.getCropName());
        setCropQtyAndPrice();
        setCropDesc(item.getDescription());
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new cropVendorsAdapter(controller.getMyCrops() , this);
        recyclerView.setAdapter(adapter);
        notifyChanges();
    }

    private void setCropImage(String url){
        if(url != null)
            Glide.with(Objects.requireNonNull(getContext())).load(Uri.parse(url))
            .centerCrop().into(cropImage);
    }

    private void setCropName(String name){
        if(!TextUtils.isEmpty(name))
            cropName.setText(name);
    }

    private void setCropQtyAndPrice(){
        if(TempCache.SELECTED_MY_CROP_ITEM == null)
            return;
        String q = (TempCache.SELECTED_MY_CROP_ITEM.getCropQty() + getQty() ) + "";
        String p = (getPrice() ) + "";
        QtyAndPrice qtyAndPrice = TempCache.genericMethods.getQtyAndPrice(q , p);
        String price_txt = qtyAndPrice.getPrice() + "/" + qtyAndPrice.getType();
        String qty_txt = qtyAndPrice.getQty() + qtyAndPrice.getType();
        cropPrice.setText(price_txt);
        remainingStock.setText(qty_txt);
    }

    private void setCropDesc(String desc){
        if(desc != null){
            cropDesc.setText(desc);
            cropDesc.setVisibility(View.VISIBLE);
        }
        else
            cropDesc.setVisibility(View.GONE);
    }

    private boolean validate(){
        boolean cond_1 = getPrice() == 0;
        if(cond_1){
            priceEt.setError("Please set your selling price");
            return false;
        }
        else
            return true;
    }

    private void setPriceEt(double price){
        priceEt.setText(String.format("%.2f" , price));
    }

    private void hideErrorPage(){
        errorView.setVisibility(View.GONE);
    }

    private double getQty(){
        String q = qtyEt.getText().toString();
        if(TextUtils.isEmpty(q))
            return 0;
        double quantity = Double.parseDouble(q);
        if(!ADD_QUANTITY_PRESSED)
            quantity = -quantity;
        if(kg.isChecked())
            return quantity;
        else
            return quantity * 100;
    }

    private double getPrice(){
        String p = priceEt.getText().toString();
        if(TextUtils.isEmpty(p))
            return 0;
        double price = Double.parseDouble(p);
        if(perKg.isChecked())
            return price;
        else
            return price * 100;
    }

    public void hideEditContainer(){
        editContainer.setVisibility(View.GONE);
        MyCropsCache.IS_EDIT_CONTAINER_VISIBLE = false;
    }

    private void changeTvToGreen(TextView tv){
        tv.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_veg_back));
        tv.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.white));
    }

    private void changeTvToWhite(TextView tv){
        tv.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.cust_veg_white_back));
        tv.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.green));
    }

    @Override
    public void showEditContainer(){
        editContainer.setVisibility(View.VISIBLE);
        setPriceEt(TempCache.SELECTED_MY_CROP_ITEM.getCropPrice());
        changeTvToGreen(addQty);
        changeTvToWhite(removeQty);
        setViews();
        ADD_QUANTITY_PRESSED = true;
        qtyEt.setText("");
        MyCropsCache.IS_EDIT_CONTAINER_VISIBLE = true;
    }

    @Override
    public void showBtnContainer(){
        btnContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnContainer(){
        btnContainer.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFetchingCropsPB(){
        fetchingCropsPB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFetchingCropsPB(){
        fetchingCropsPB.setVisibility(View.GONE);
    }

    @Override
    public void notifyChanges() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRecyclerView(){
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView(){
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage(){
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyPage(){
        emptyPage.setVisibility(View.VISIBLE);
        emptyPageTv.setText(getString(R.string.empty_my_crop_txt));
    }

}
