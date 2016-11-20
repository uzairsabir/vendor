package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by Uzair on 8/6/2016.
 */
public class VendorCategoryDT extends RealmObject {


    public VendorCategoryDT() {

    }

    private int category_id = 0;
    @Index
    private String category = "";
    private String category_image_url = "";
    private String category_description = "";
    private int subcategory_id = 0;
    @Index
    private String subcategory = "";
    private String subcategory_image_url = "";
    private String subcategory_description = "";


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }


    public String getSubcategory_image_url() {
        return subcategory_image_url;
    }

    public void setSubcategory_image_url(String subcategory_image_url) {
        this.subcategory_image_url = subcategory_image_url;
    }

    public String getSubcategory_description() {
        return subcategory_description;
    }

    public void setSubcategory_description(String subcategory_description) {
        this.subcategory_description = subcategory_description;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
