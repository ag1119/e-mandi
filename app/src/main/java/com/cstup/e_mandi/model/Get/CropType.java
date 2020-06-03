package com.cstup.e_mandi.model.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CropType {
    @SerializedName("crop_type_id")
    @Expose
    private Integer cropTypeId;
    @SerializedName("crop_type_name")
    @Expose
    private String cropTypeName;
    @SerializedName("crop_class")
    @Expose
    private String cropClass;
    @SerializedName("crop_type_image")
    @Expose
    private String cropTypeImage;

    public Integer getCropTypeId() {
        return cropTypeId;
    }

    public void setCropTypeId(Integer cropTypeId) {
        this.cropTypeId = cropTypeId;
    }

    public String getCropTypeName() {
        return cropTypeName;
    }

    public void setCropTypeName(String cropTypeName) {
        this.cropTypeName = cropTypeName;
    }

    public String getCropClass() {
        return cropClass;
    }

    public void setCropClass(String cropClass) {
        this.cropClass = cropClass;
    }

    public String getCropTypeImage() {
        return cropTypeImage;
    }

    public void setCropTypeImage(String cropTypeImage) {
        this.cropTypeImage = cropTypeImage;
    }
}
