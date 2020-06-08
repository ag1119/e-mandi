package com.cstup.e_mandi.views.user.fragments;

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
import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.cropDetailsAdapter;
import com.cstup.e_mandi.controllers.cartController;
import com.cstup.e_mandi.controllers.cropDetailsController;
import com.cstup.e_mandi.environmentVariables.test.Crops;
import com.cstup.e_mandi.model.Get.CropType;
import com.cstup.e_mandi.model.Get.BasicDetails;
import com.cstup.e_mandi.model.Post.CartItem;
import com.cstup.e_mandi.utilities.QtyAndPrice;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class cropDetails extends Fragment implements cropDetailsAdapter.EventListener ,
        cropDetailsController.EventListener, cartController.EventListener {

    private CircleImageView crop_image;
    private TextView price;
    private TextView govt_price;
    private TextView crop_name;
    private TextView crop_desc;
    private TextView offer;
    private EditText set_quantity;
    private RadioButton quintol;
    private RadioButton kg;
    private Button add_to_cart;
    private ProgressBar add_to_cart_pb;
    private TextView view_vendor_details;
    private View vendor_info;
    private TextView vendor_name;
    private TextView vendor_contact;
    private TextView vendor_address;
    private CircleImageView vendor_profile_image;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private View error_page;
    private TextView try_again;
    private LinearLayout info;
    private View loadingPanel;
    private TextView cost_tv;
    private TextView remaining_stock;

    private RecyclerView.Adapter adapter;
    private cropDetailsController controller;
    private cartController cartController;

    public cropDetails() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crop_details, container, false);
        initViews(v);
        setViews_cropDetails();
        setRecyclerView();
        controller.getCrops();

        try_again.setOnClickListener(v1 -> {
            hideErrorPage();
            controller.getCrops();
        });

        view_vendor_details.setOnClickListener(v1 -> {
            if(vendor_info.getVisibility() == View.VISIBLE){
                vendor_info.setVisibility(View.GONE);
                view_vendor_details.setCompoundDrawablesWithIntrinsicBounds(
                        null ,
                        null ,
                        ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_expand_more_black_24dp) ,
                        null);

            }
            else{
                vendor_info.setVisibility(View.VISIBLE);
                if(TempCache.IS_VENDOR_BASIC_INFO_FIRST_CALL){
                    controller.getVendorInfo();
                    TempCache.IS_VENDOR_BASIC_INFO_FIRST_CALL = false;
                }
                else{
                    setVendorsDetail(TempCache.basicDetails);
                }
                view_vendor_details.setCompoundDrawablesWithIntrinsicBounds(
                        null ,
                        null ,
                        ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_expand_less_black_24dp) ,
                        null);
            }

        });

        add_to_cart.setOnClickListener(v1 -> {
            if(validate()){
                CartItem cartItem = new CartItem();
                cartItem.setCrop_id(TempCache.crop.getCropId());
                cartItem.setItem_qty(getQuantityInKg(set_quantity.getText().toString()));
                cartController.addToCart(cartItem);
            }
        });

        set_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setProductCost(s+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kg.setOnClickListener(v1 -> {
            setProductCost(set_quantity.getText().toString());
        });

        quintol.setOnClickListener(v1 -> {
            setProductCost(set_quantity.getText().toString());
        });

        return v;
    }
    private void initViews(View v){
        crop_image = v.findViewById(R.id.crop_image);
        price = v.findViewById(R.id.price);
        govt_price = v.findViewById(R.id.govt_price);
        crop_name = v.findViewById(R.id.crop_name);
        crop_desc = v.findViewById(R.id.crop_desc);
        offer = v.findViewById(R.id.offer);
        set_quantity = v.findViewById(R.id.set_quantity);
        quintol = v.findViewById(R.id.quintol);
        kg = v.findViewById(R.id.kg);
        add_to_cart = v.findViewById(R.id.add_to_cart);
        view_vendor_details = v.findViewById(R.id.view_vendor_details);
        vendor_info = v.findViewById(R.id.vendor_info);
        vendor_name = v.findViewById(R.id.vendor_name);
        vendor_contact = v.findViewById(R.id.vendor_contact);
        vendor_address = v.findViewById(R.id.vendor_address);
        progressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.recyclerView);
        error_page = v.findViewById(R.id.error_view);
        try_again = v.findViewById(R.id.try_again_tv);
        info = v.findViewById(R.id.info);
        loadingPanel = v.findViewById(R.id.loadingPanel);
        vendor_profile_image = v.findViewById(R.id.profile_image);
        add_to_cart_pb = v.findViewById(R.id.add_to_cart_pb);
        cost_tv = v.findViewById(R.id.cost_tv);
        remaining_stock = v.findViewById(R.id.remaining_stock);

        controller = new cropDetailsController(this);
        cartController = new cartController(this);
    }

    public void setViews_cropDetails(){
        String url;
        if(TempCache.crop.getCropImage() != null)
            url = TempCache.crop.getCropImage();
        else{
            String crop_class = TempCache.crop.getCropClass();
            Integer crop_type_id = TempCache.crop.getCropTypeId();
            url = getCropTypeImage(crop_class , crop_type_id);
        }
        if(url != null)
            Glide.with(Objects.requireNonNull(getContext()))
                .load(Uri.parse(url))
                .centerCrop().into(crop_image);
        crop_name.setText(TempCache.crop.getCropName());
        setCropStockAndPrice();
        if(TempCache.crop.getDescription() != null)
            crop_desc.setText(TempCache.crop.getDescription());
        //govt_price.setText(TempCache.cropDetails.getGovt_price());

        TempCache.IS_VENDOR_BASIC_INFO_FIRST_CALL = true;

    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false));
        adapter = new cropDetailsAdapter(this , controller.getList());
        recyclerView.setAdapter(adapter);
    }

    private void hideErrorPage(){error_page.setVisibility(View.GONE);}

    private boolean validate(){
        String qty = set_quantity.getText().toString();
        if(TextUtils.isEmpty(qty)){
            set_quantity.setError("Invalid quantity");
            return false;
        }

        double q = Double.parseDouble(qty);
        if(quintol.isChecked())
            q *= 100;

        if(q == 0){
           set_quantity.setError("Invalid quantity");
            return false;
        }

        if(q > TempCache.crop.getCropQty()){
            set_quantity.setError("Quantity is more then stock");
            return false;
        }
        return true;
    }

    private void setProductCost(String quantity){
        if(TextUtils.isEmpty(quantity))
            cost_tv.setText("Product Cost: \u20B9" + 0);
        else{
            double crop_price = (double)TempCache.crop.getCropPrice();
            double qty = Double.parseDouble(quantity+"");
            double product_cost;
            if(kg.isChecked())
                product_cost = crop_price * qty;
            else
                product_cost = (crop_price * 100) * qty;
            cost_tv.setText("Product Cost: \u20B9" + String.format("%.2f",product_cost));
        }
    }

    private void setCropStockAndPrice(){
        String qty = TempCache.crop.getCropQty() + "";
        String prc = TempCache.crop.getCropPrice() + "";
        QtyAndPrice qtyAndPrice = TempCache.genericMethods.getQtyAndPrice(qty , prc);
        String type = qtyAndPrice.getType();
        String price_txt = "\u20B9"+qtyAndPrice.getPrice()+ "/" + type;
        String stock_txt = "Remaining Stock: " + qtyAndPrice.getQty() + type;
        setPrice(price_txt);
        setRemaining_stock(stock_txt);
    }

    private Double getQuantityInKg(String qty){
        if(kg.isChecked())
            return Double.parseDouble(qty);
        else
            return Double.parseDouble(qty) / 100;
    }

    private void setPrice(String p){
        price.setText(p);
    }

    private void setRemaining_stock(String s){
        remaining_stock.setText(s);
    }

    @Override
    public String getCropTypeImage(String crop_class , Integer crop_type_id){
        switch (crop_class){
            case constants.CropClass.VEGETABLES:
                for(CropType v : Crops.vegetables){
                    if(v.getCropTypeId().equals(crop_type_id))
                        return v.getCropTypeImage();
                }
                break;
            case constants.CropClass.GRAINS:
                for(CropType g : Crops.grains){
                    if(g.getCropTypeId().equals(crop_type_id))
                        return g.getCropTypeImage();
                }
                break;
            case constants.CropClass.FRUITS:
                for(CropType f : Crops.fruits){
                    if(f.getCropTypeId().equals(crop_type_id))
                        return f.getCropTypeImage();
                }
                break;
        }
        return null;
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingPanel() {
        loadingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingPanel() {
        loadingPanel.setVisibility(View.GONE);
    }

    @Override
    public void showInfo() {
        info.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInfo() {
        info.setVisibility(View.GONE);
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
    public void showProgressBarAddToCart() {
        add_to_cart_pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBarAddToCart() {
        add_to_cart_pb.setVisibility(View.GONE);
    }

    @Override
    public void showAddToCartBtn() {
        add_to_cart.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddToCartBtn() {
        add_to_cart.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage() {
        error_page.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext() , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void resetQuantity() {
        set_quantity.setText("");
    }

    @Override
    public void setVendorsDetail(BasicDetails vendorsDetail) {
        if(vendorsDetail.getProfile_picture() != null)
            Glide.with(Objects.requireNonNull(getContext()))
                .load(Uri.parse(vendorsDetail.getProfile_picture()))
                .override(96 , 96).into(vendor_profile_image);
        if(vendorsDetail.getName() != null)
            vendor_name.setText(vendorsDetail.getName());
        vendor_contact.setText(vendorsDetail.getContact());
        if(vendorsDetail.getAddress() != null)
            vendor_address.setText(vendorsDetail.getAddress());
    }

}
