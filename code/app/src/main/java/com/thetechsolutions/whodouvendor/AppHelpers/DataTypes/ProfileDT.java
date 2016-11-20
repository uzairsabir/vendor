package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.MyLogs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ProfileDT extends RealmObject {

    public ProfileDT() {

    }

    @PrimaryKey
    private long id;
    private String username = "";
    private String first_name = "";
    private String last_name = "";
    private String email_1 = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String mobile_number_1 = "";
    private int zip_code = 0;
    private String image_url = "";
    private String is_register_user = "";
    private int category_id = 0;
    private int sub_category_id = 0;
    private String sub_category_title = "";
    private String category_title = "";
    private String subcategory_image_url = "";


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getImage_url() {
        try {
            MyLogs.printinfo("cat_url " + this.image_url);
            if (!(image_url.contains(".jpeg") || image_url.contains(".png") || image_url.contains(".jpg"))) {
                if (!(subcategory_image_url.contains(".jpeg") || subcategory_image_url.contains(".png") || subcategory_image_url.contains(".jpg"))) {

                    return "res:///" + R.drawable.com_facebook_profile_picture_blank_square;
                } else {
                    return this.subcategory_image_url;
                }

            }
//            else if (!image_url.contains(".jpeg") || !image_url.contains(".png")) {
//                return this.subcategory_image_url;
//            }
        } catch (Exception e) {
            return "";

        }
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIs_register_user() {
        return is_register_user;
    }

    public void setIs_register_user(String is_register_user) {
        this.is_register_user = is_register_user;
    }

    public String getEmail_1() {
        return email_1;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public String getMobile_number_1() {
        return mobile_number_1;
    }

    public void setMobile_number_1(String mobile_number_1) {
        this.mobile_number_1 = mobile_number_1;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSub_category_title() {
        return sub_category_title;
    }

    public void setSub_category_title(String sub_category_title) {
        this.sub_category_title = sub_category_title;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubcategory_image_url() {
        return subcategory_image_url;
    }

    public void setSubcategory_image_url(String subcategory_image_url) {
        this.subcategory_image_url = subcategory_image_url;
    }


//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
}
