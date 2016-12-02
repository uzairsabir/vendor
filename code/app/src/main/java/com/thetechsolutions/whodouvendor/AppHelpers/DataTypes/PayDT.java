package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class PayDT extends RealmObject {

    public PayDT() {

    }

    @PrimaryKey
    private int id = 0;

    private int vendor_id = 0;
    private String vendor_username = "";
    private String vendor_name = "";
    private String vendor_image_url = "";
    private int sub_category_id = 0;
    private String sub_categor_title = "";
    private String sub_category_image_url = "";
    private int category_id = 0;
    private String category_title = "";
    private String category_image_url = "";

    private int consumer_id = 0;
    private String consumer_username = "";
    private String consumer_name = "";
    private String consumer_image_url = "";


    private String amount = "";
    private int appointment_id = 0;
    private String description = "";
    private long service_date = 0;
    private int is_paid = 0;
    private String extra = "";
    private String created_at = "";
    private String updated_at = "";
    private String status = "";
    private String request_receipt = "";
//    {
//        "id": "135",
//            "vendor_id": "1",
//            "consumer_id": "1",
//            "": "0923452144125",
//            "": "Tauqeer Ahmed",
//            "": "defaults/profile/1-consumer-1479292710.png",
//            "amount": "111.0000",
//            "appointment_id": "1",
//            "description": "sdfsdf",
//            "service_date": "1990-01-01 00:00:00",
//            "is_paid": "0",
//            "extra": "",
//            "created_at": "2016-11-22 14:03:27",
//            "updated_at": "2016-11-22 14:03:27",
//            "status": "paid",
//            "request_receipt": "1"
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getAmount() {
        Double value = Double.parseDouble(amount);
        String value1 = "0";
        if(value != null){
            if(value == (double) Math.round(value)){
                if(value/1000000000 > 1.0){
                    value1 = String.format("%.1f G", value/1000000000);
                }
                else if(value/1000000 > 1.0){
                    value1 = String.format("%.1f M", value/1000000);
                }
                else if(value/1000 > 1.0){
                    value1 = String.format("%.1f K", value/1000);
                }
                else{
                    value1 = String.format("%.1f", value);
                }
            }
            else{
                value1 = String.format("%.2f", value);
            }
        }
        return value1;

    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_receipt() {
        return request_receipt;
    }

    public void setRequest_receipt(String request_receipt) {
        this.request_receipt = request_receipt;
    }

    public long getService_date() {
        return service_date;
    }

    public void setService_date(long service_date) {
        this.service_date = service_date;
    }

    public String getDateToDisplay() {
        try {

            return UtilityFunctions.formatteSqlDate(UtilityFunctions.converMillisToDate(this.service_date, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {

        }

        return "";
    }

    public String getVendor_username() {
        return vendor_username;
    }

    public void setVendor_username(String vendor_username) {
        this.vendor_username = vendor_username;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getVendor_image_url() {
        try {
            //       MyLogs.printinfo("cat_url " + this.subcategory_image_url);
            if (!(vendor_image_url.contains(".jpeg") || vendor_image_url.contains(".png") || vendor_image_url.contains(".jpg"))) {
                if (!(sub_category_image_url.contains(".jpeg") || sub_category_image_url.contains(".png") || sub_category_image_url.contains(".jpg"))) {

                    return "res:///" + R.drawable.com_facebook_profile_picture_blank_square;
                } else {
                    return this.sub_category_image_url;
                }

            }
//            else if (!image_url.contains(".jpeg") || !image_url.contains(".png")) {
//                return this.subcategory_image_url;
//            }
        } catch (Exception e) {
            return "";

        }

        return vendor_image_url;

    }

    public void setVendor_image_url(String vendor_image_url) {
        this.vendor_image_url = vendor_image_url;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSub_categor_title() {
        return sub_categor_title;
    }

    public void setSub_categor_title(String sub_categor_title) {
        this.sub_categor_title = sub_categor_title;
    }

    public String getSub_category_image_url() {
        return sub_category_image_url;
    }

    public void setSub_category_image_url(String sub_category_image_url) {
        this.sub_category_image_url = sub_category_image_url;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public String getConsumer_username() {
        return consumer_username;
    }

    public void setConsumer_username(String consumer_username) {
        this.consumer_username = consumer_username;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public String getConsumer_image_url() {
        try {
            //       MyLogs.printinfo("cat_url " + this.subcategory_image_url);
            if (!(consumer_image_url.contains(".jpeg") || consumer_image_url.contains(".png") || consumer_image_url.contains(".jpg"))) {
                    return "res:///" + R.drawable.com_facebook_profile_picture_blank_square;

            }
//            else if (!image_url.contains(".jpeg") || !image_url.contains(".png")) {
//                return this.subcategory_image_url;
//            }
        } catch (Exception e) {
            return "";

        }

        return consumer_image_url;
    }

    public void setConsumer_image_url(String consumer_image_url) {
        this.consumer_image_url = consumer_image_url;
    }
}
