package org.vanguardmatrix.engine.datatypes;

import android.util.Log;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.parser.ParseString;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.Serializable;

/**
 * Created by Uzair on 12/16/2015.
 */
public class PhoneContact implements Serializable {


    private int contact_id = 0;
    private String first_name = "";
    private String last_name = "";
    private String number = "";
    private String email = "";
    private String country = "";
    private String city = "";
    private String zip = "";

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
