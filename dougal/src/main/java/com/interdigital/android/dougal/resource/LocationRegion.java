package com.interdigital.android.dougal.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationRegion {

    @Expose
    @SerializedName("accc")
    private String[] countryCode;
    // Three floats in an array: longitude, latitude and radius in metres.
    @Expose
    @SerializedName("accr")
    private float[] circularRegion;

    public String[] getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String[] countryCode) {
        this.countryCode = countryCode;
    }

    public float[] getCircularRegion() {
        return circularRegion;
    }

    public void setCircularRegion(float[] circularRegion) {
        this.circularRegion = circularRegion;
    }
}
