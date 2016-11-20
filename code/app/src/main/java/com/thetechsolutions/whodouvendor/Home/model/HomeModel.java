package com.thetechsolutions.whodouvendor.Home.model;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataDelete;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.ServiceUrl;
import com.thetechsolutions.whodouvendor.Home.activities.HomeCreateNewContactActivity;
import com.thetechsolutions.whodouvendor.Home.fragments.HomeMainFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.webservice.WebService;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;

/**
 * Created by Uzair on 8/26/2016.
 */
public class HomeModel {


    public static boolean createProvider(String providerName, String first_name, String last_name,
                                         String user_address, String user_city, String user_state, String user_country,
                                         String email_address, String zip_code, String subcategory_id,
                                         String is_registered_user, String imageUrl
    ) {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("username", providerName));
        //params.add(new BasicNameValuePair("user_type", AppConstants.CONSUMER_TYPE));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        params.add(new BasicNameValuePair("user_address", user_address));
        params.add(new BasicNameValuePair("user_city", user_city));
        params.add(new BasicNameValuePair("user_state", user_state));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("email_address", email_address));
        params.add(new BasicNameValuePair("zip_code", zip_code));
        String type = "";
        if (HomeCreateNewContactActivity.tab_pos == 0) {
            params.add(new BasicNameValuePair("subcategory_id", ""));
            params.add(new BasicNameValuePair("user_type", AppConstants.CONSUMER_TYPE));
            type = AppConstants.CONSUMER_TYPE;
        } else {
            params.add(new BasicNameValuePair("subcategory_id", subcategory_id));
            params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
            type = AppConstants.APP_TYPE;
        }

        params.add(new BasicNameValuePair("created_by", AppPreferences.getString(AppPreferences.PREF_USER_NUMBER)));
        params.add(new BasicNameValuePair("is_registered_user", is_registered_user));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_provider, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                if (!UtilityFunctions.isEmpty(imageUrl)) {

                    try {
                        MyLogs.printinfo("calling_image " + type);
                        String friendId = resultJson.getJSONArray("body").getJSONObject(0).getString("id");
                        return uploadImage(friendId, type, imageUrl, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    return true;
                }

//                try {
//                    RealmDataInsert.insertConsumerProviders(providerName, HomeCreateNewContactActivity.tab_pos);
//                } catch (Exception e) {
//
//                }
//
//
//                try {
//                    RealmDataInsert.insertVendorProfile(resultJson.getJSONArray(AppConstants.BODY), HomeCreateNewContactActivity.tab_pos);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    AppController.insertIntoContact(first_name, last_name, user_city, zip_code, user_country, providerName, email_address);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean createConsumerProviderLink(String providerId, String first_name, String last_name, String providerName, int tab_pos

    ) {
        String id =AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("consumer_id", providerId));
        params.add(new BasicNameValuePair("vendor_id", id));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_consumer_provider_link, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

//                try {
//                    RealmDataInsert.insertConsumerProviders(providerName, tab_pos);
//                } catch (Exception e) {
//
//                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


    public static boolean updateProvider(String providerName, String first_name, String last_name,
                                         String user_address, String user_city, String user_state, String user_country,
                                         String email_address, String zip_code, String subcategory_id, int pos,String imageUrl
    ) {
        String id = String.valueOf(RealmDataRetrive.getProfile().getUsername());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("myuserName", id));
        params.add(new BasicNameValuePair("username", providerName));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        params.add(new BasicNameValuePair("user_address", user_address));
        params.add(new BasicNameValuePair("user_city", user_city));
        params.add(new BasicNameValuePair("user_state", user_state));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("email_address", email_address));
        params.add(new BasicNameValuePair("zip_code", zip_code));
        String type="";
        if (HomeMainFragment.pos == 0) {
            params.add(new BasicNameValuePair("subcategory_id", ""));
            params.add(new BasicNameValuePair("user_type", AppConstants.CONSUMER_TYPE));
            type=AppConstants.CONSUMER_TYPE;
        } else {
            params.add(new BasicNameValuePair("subcategory_id", subcategory_id));
            params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
            type=AppConstants.APP_TYPE;
        }
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_provider, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    MyLogs.printinfo("calling_image " + type);
                    String friendId = resultJson.getJSONArray("body").getJSONObject(0).getString("id");
                    if(!UtilityFunctions.isEmpty(imageUrl)) {
                        return uploadImage(friendId, type, imageUrl, false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


    public static boolean getMyProviders() {

       // String id =;

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", AppPreferences.getString(AppPreferences.PREF_USER_ID)));
        params.add(new BasicNameValuePair("user_type", AppConstants.CONSUMER_TYPE));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_my_providers, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
//                    try {
//                        RealmDataDelete.deleteHomeDTByPos(0);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }

                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {


                        try {

                            RealmDataInsert.insertCustomers(resultJson.getJSONArray(AppConstants.BODY));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean createConsumerFriendLink(String friendId, String first_name, String last_name, String providerName

    ) {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("vendor_id", id));
        params.add(new BasicNameValuePair("friend_id", friendId));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_consumer_friend_link, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

//                try {
//                    RealmDataInsert.insertConsumerProviders(providerName, HomeCreateNewContactActivity.tab_pos);
//                } catch (Exception e) {
//
//                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean getMyFriends() {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_my_friends, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {
//                        try {
//                            RealmDataDelete.deleteHomeDTByPos(1);
//                        } catch (Exception e) {
//
//                        }

                        try {

                            RealmDataInsert.insertColleages(resultJson.getJSONArray(AppConstants.BODY));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        try {
//                            JSONArray jsonArray = new JSONArray();
//                            jsonArray = resultJson.getJSONArray(AppConstants.BODY);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                try {
//                                    AppController.insertIntoContact(jsonArray.getJSONObject(i).getString("first_name"), jsonArray.getJSONObject(i).getString("last_name"),
//                                            jsonArray.getJSONObject(i).getString("city"), jsonArray.getJSONObject(i).getString("zip_code"),
//                                            jsonArray.getJSONObject(i).getString("country"), jsonArray.getJSONObject(i).getString("mobile_number_1"), jsonArray.getJSONObject(i).getString("email_1"));
//
//                                } catch (Exception e) {
//
//                                }
//
//
////                                try {
////                                    RealmDataInsert.insertConsumerProviders(jsonArray.getJSONObject(i).getString("mobile_number_1"), 1);
////                                } catch (Exception e) {
////
////                                }
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean getMyFriendsProviders() {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_my_friends_providers, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {
                        try {
                            RealmDataDelete.deleteHomeDTByPos(1);
                        } catch (Exception e) {

                        }

//                        try {
//
//                           // RealmDataInsert.insertCustomers(resultJson.getJSONArray(AppConstants.BODY), 1);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

//                        try {
//                            RealmDataInsert.insertVendorProfile(resultJson.getJSONArray(AppConstants.BODY), 2);
//
//                        } catch (JSONException e) {
//                            //   e.printStackTrace();
//                        }

//                        try {
//                            JSONArray jsonArray = new JSONArray();
//                            jsonArray = resultJson.getJSONArray(AppConstants.BODY);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                AppController.insertIntoContact(jsonArray.getJSONObject(i).getString("first_name"), jsonArray.getJSONObject(i).getString("last_name"),
//                                        jsonArray.getJSONObject(i).getString("city"), jsonArray.getJSONObject(i).getString("zip_code"),
//                                        jsonArray.getJSONObject(i).getString("country"), jsonArray.getJSONObject(i).getString("mobile_number_1"), jsonArray.getJSONObject(i).getString("email_1"));
//
//                                try {
//                                    RealmDataInsert.insertConsumerProviders(jsonArray.getJSONObject(i).getString("mobile_number_1"), 2);
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean uploadImage(String user_id, String user_type, String image_url, boolean isUpdateProfile) {

//        MyLogs.printinfo("image_url  "+"data:image/jpeg;base64," + UtilityFunctions.converStringToBase64(image_url));
        //String id = String.valueOf(RealmDataRetrive.getProfile().getId());
        //MyLogs.printinfo("user_id "+user_id);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("base64_img", "data:image/jpeg;base64," + UtilityFunctions.converStringToBase64(image_url)));
        params.add(new BasicNameValuePair("user_id", "" + user_id));
        params.add(new BasicNameValuePair("user_type", user_type));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_profile_image, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {
                try {
                    if (isUpdateProfile)
                        RealmDataInsert.insertProfile(resultJson.getJSONArray(AppConstants.BODY));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


}
