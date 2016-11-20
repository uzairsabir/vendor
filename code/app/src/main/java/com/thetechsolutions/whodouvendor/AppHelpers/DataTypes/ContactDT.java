package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.parser.ParseString;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ContactDT extends RealmObject {

    public ContactDT() {

    }
    @PrimaryKey
    private long id = 0;
    private String Firstname = "";
    private String Lastname = "";
    @Index
    private String Numbers = "";
    private String Email = "";
    private String City = "";
    private String Zip = "";
    private String country = "";
    private int isVerified = 0;
    private int SendedToServer = 0;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return ParseString.getInitCap(Firstname);
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getNumbers() {
        try{
            return Numbers;
        }catch (Exception e){

        }
        return "";

    }

    public void setNumbers(String numbers) {
        Numbers = numbers;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public int getSendedToServer() {
        return SendedToServer;
    }

    public void setSendedToServer(int sendedToServer) {
        SendedToServer = sendedToServer;
    }

    public ArrayList<String> getNumbersToPerform() {
        ArrayList<String> number = new ArrayList<>();
        try {
            //  MyLogs.printinfo("numbers " + this.getNumbers());
            JSONObject jsonObject = new JSONObject(this.getNumbers());
            int i = 0;
            try {
                number.add(i, jsonObject.getString("primary_number"));
                i++;
            } catch (Exception e) {

            }
            try {
                number.add(i, jsonObject.getString("secondary_number"));
                i++;
            } catch (Exception e) {

            }
            try {
                number.add(i, jsonObject.getString("other_number"));

            } catch (Exception e) {

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return number;
    }


}
