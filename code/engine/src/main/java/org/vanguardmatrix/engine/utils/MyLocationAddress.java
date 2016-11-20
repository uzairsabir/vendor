package org.vanguardmatrix.engine.utils;

import android.location.Address;
import android.os.Parcelable;
import android.util.Log;

import java.util.Locale;

public class MyLocationAddress extends Address implements Parcelable {

    public static String COMPLETE_DATA = "COMPLETE_DATA";
    public static String CURR_LATI = "curr_lati";
    public static String CURR_LONGI = "curr_longi";
    public static String TARG_CITY = "targ_CITY";
    public static String TARG_COUNTRY = "targ_COUNTRY";
    public static String TARG_LATI = "targ_lati";
    public static String TARG_LONGI = "targ_longi";
    public static String CURR_ADDR = "curr_addr";
    public static String TARG_ADDR = "targ_addr";
    private String address = "";
    private String locality = "";
    private String subLocality = "";
    private String city = "";
    private String country = "";
    private double latitude = 0.0;
    private double longitude = 0.0;

    /**
     *
     */
//    private static final long serialVersionUID = -9010348228147478922L;
    public MyLocationAddress(Locale locale) {
        super(locale);
    }

    public MyLocationAddress(Address address) {
        super(Locale.getDefault());

        if (!UtilityFunctions.isEmpty(address.getFeatureName()))
            this.address = address.getFeatureName();

        if (!UtilityFunctions.isEmpty(address.getSubLocality()))
            this.address = this.address + ", " + address.getSubLocality();

        if (!UtilityFunctions.isEmpty(address.getLocality()))
            this.address = this.address + ", " + address.getLocality();

        this.city = address.getLocality();
        this.country = address.getCountryName();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        this.locality = address.getFeatureName();
        this.subLocality = address.getSubLocality();

        toString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getsubLocality() {
        return subLocality;
    }

    public void setsubLocality(String subLocality) {
        this.subLocality = subLocality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        Log.e("Printing ", "MyLocationAddress : country: " + this.country
                + " city: " + this.city + " lati : " + this.latitude
                + " longi: " + this.longitude + " address : " + this.address
                + " locality : " + this.locality + " subLocality : "
                + this.subLocality);

        return "MyLocationAddress : country: " + this.country + " city: "
                + this.city + " lati : " + this.latitude + " longi: "
                + this.longitude + " address : " + this.address
                + " locality : " + this.locality + " subLocality : "
                + this.subLocality;
    }

}
