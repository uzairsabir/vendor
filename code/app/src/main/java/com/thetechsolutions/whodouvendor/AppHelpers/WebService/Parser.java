package com.thetechsolutions.whodouvendor.AppHelpers.WebService;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Uzair on 8/20/2016.
 */
public class Parser {

    public static boolean insertCategories(JSONObject jsonObject) {
        try {
            JSONArray ListingJsonObject = new JSONArray();
            ListingJsonObject = jsonObject.getJSONArray(AppConstants.BODY);



            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean insertProfile(JSONObject jsonObject) {
//        try {
//            JSONArray ListingJsonObject = new JSONArray();
//            ListingJsonObject = jsonObject.getJSONArray(AppConstants.BODY);
//
//            for (int i = 0; i < ListingJsonObject.length(); i++) {
//
//                RealmDataInsert.insertProfile(ListingJsonObject.getJSONObject(i));
//
//            }
//            return true;
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return false;

    }
}
