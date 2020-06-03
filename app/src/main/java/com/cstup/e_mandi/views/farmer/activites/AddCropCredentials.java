package com.cstup.e_mandi.views.farmer.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.adapters.addCropAdapter;
import com.cstup.e_mandi.controllers.addCropController;
import com.cstup.e_mandi.utilities.TempCache;
import com.cstup.e_mandi.utilities.constants;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;


public class AddCropCredentials extends AppCompatActivity implements addCropController.EventListener {

    private static final int CHOOSE_IMAGE_REQUEST_CODE = 19;
    private TextView add_image;
    private TextView crop_name;
    private TextView price;
    private EditText set_quantity;
    private RadioButton kg;
    private RadioButton quintol;
    private EditText set_price;
    private RadioButton perKg;
    private RadioButton perQuintol;
    private EditText description;
    private Button add_crop;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private addCropController controller;
    private CardView loading_image;
    private ProgressBar image_progressBar;
    private ArrayList<Uri> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop_credentials);
        initViews();
        setRecyclerView();


        crop_name.setText(TempCache.CROP_TYPE_ITEM.getCropTypeName());

        set_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = "\u20B9" + s;
                if(perQuintol.isChecked())
                    txt += "/" + constants.TYPE_QUINTOL;
                else
                    txt += "/"+ constants.TYPE_KG;
                setPrice(txt);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kg.setOnClickListener(v -> {
            String txt = set_price.getText().toString() + "/" + constants.TYPE_KG;
            setPrice(txt);
        });

        quintol.setOnClickListener(v -> {
            String txt = set_price.getText().toString() + "/" + constants.TYPE_QUINTOL;
            setPrice(txt);
        });

        add_image.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent ,CHOOSE_IMAGE_REQUEST_CODE);
        });

        add_crop.setOnClickListener(v -> {
            if(validate()){
                controller.addCrop();
            }
        });

    }

    private void initViews(){
        add_image = findViewById(R.id.add_images);
        crop_name = findViewById(R.id.crop_name);
        price = findViewById(R.id.price);
        set_quantity = findViewById(R.id.set_quantity);
        set_price = findViewById(R.id.set_price);
        description = findViewById(R.id.description);
        add_crop = findViewById(R.id.add_crop);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        loading_image = findViewById(R.id.loading_image);
        image_progressBar = findViewById(R.id.image_progressBar);
        add_crop = findViewById(R.id.add_crop_btn);
        kg = findViewById(R.id.kg);
        quintol = findViewById(R.id.quintol);
        perKg = findViewById(R.id.per_kg);
        perQuintol = findViewById(R.id.per_quintol);

        controller = new addCropController(this);
    }

    private boolean validate(){
        String qty = set_quantity.getText().toString();
        String price = set_price.getText().toString();
        if(TextUtils.isEmpty(qty)){
            set_quantity.setError("Please set quantity");
            return false;
        }
        if(TextUtils.isEmpty(price)){
            set_price.setError("Please set price");
            return false;
        }
        return true;
    }

    private void setRecyclerView(){
        list.clear();
        list.add(Uri.parse(TempCache.CROP_TYPE_ITEM.getCropTypeImage()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        adapter = new addCropAdapter(list);
        recyclerView.setAdapter(adapter);
        updateList();
    }

    private void setPrice(String txt){
        price.setText(txt);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            //starting image crop activity
            CropImage.activity(data.getData())
                    .start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                assert result != null;
                Uri resultUri = result.getUri();
                list.add(resultUri);
                updateList();
                controller.uploadPhoto(resultUri);


            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Log.v("image cropping error -> " , error.toString());
            }
        }
    }

    @Override
    public String getQtyInKg() {
        if(quintol.isChecked()){
            double qty = Double.parseDouble(set_quantity.getText().toString());
            qty *= 100;
            return String.format("%.2f" , qty);
        }
        return set_quantity.getText().toString();
    }

    @Override
    public String getPricePerKg() {
        if(perQuintol.isChecked()){
            double price = Double.parseDouble(set_price.getText().toString());
            price /= 100;
            return String.format("%.2f" , price);
        }
        return set_price.getText().toString();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
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
    public void showAddCropButton() {
        add_crop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddCropButton() {
        add_crop.setVisibility(View.GONE);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void disableAddImageBtn() {
        add_image.setEnabled(false);
    }

    @Override
    public void enableAddImageBtn() {
        add_image.setEnabled(true);
    }

    @Override
    public void showLoadingImage() {
        loading_image.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingImage() {
        loading_image.setVisibility(View.GONE);
    }

    @Override
    public void showProgress(int progress) {
        image_progressBar.setProgress(progress , true);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showImageProgressBar() {
        image_progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImageProgressBar() {
        image_progressBar.setVisibility(View.GONE);
    }

}
