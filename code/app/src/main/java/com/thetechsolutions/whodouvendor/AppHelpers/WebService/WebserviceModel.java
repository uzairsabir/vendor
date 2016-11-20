package com.thetechsolutions.whodouvendor.AppHelpers.WebService;

import android.app.Activity;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.webservice.WebService;

import java.util.ArrayList;

/**
 * Created by Uzair on 9/18/2016.
 */
public class WebserviceModel {

    public static boolean getAppointments(Activity activity,String appointmentStatus) {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("vendor_id", id));
        params.add(new BasicNameValuePair("appointment_date_time", ""));
        params.add(new BasicNameValuePair("appointment_status", appointmentStatus));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_appointment, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {

                    if (resultJson.getJSONArray(AppConstants.BODY).length() > 0) {


                        try {

                            RealmDataInsert.insertSchedule(activity,resultJson.getJSONArray(AppConstants.BODY));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        return true;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean createAppointments(String vendorid, String appointmentdatetime, String duration, String description, String call_message, String calendarId) {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("consumer_id", id));
        params.add(new BasicNameValuePair("vendor_id", vendorid));
        params.add(new BasicNameValuePair("appointment_date_time", appointmentdatetime));
        params.add(new BasicNameValuePair("estimated_duration", duration));
        params.add(new BasicNameValuePair("description_text", description));
        params.add(new BasicNameValuePair("call_message", call_message));
        params.add(new BasicNameValuePair("consumer_device_type", "android"));
        params.add(new BasicNameValuePair("calender_guid", calendarId));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_appointment, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {

                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {

//
//                        try {
//
//                           //return RealmDataInsert.insertSchedule(resultJson.getJSONArray(AppConstants.BODY));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean updateAppointments(String appointment_id, String appointmentdatetime, String duration, String description, String appointment_status, String calendarId) {
      //  String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

//        {
//            "appointment_id": "258",
//                "appointment_date_time": "2017-05-15 16:00:00",
//                "estimated_duration": "4",
//                "appointment_status": "accepted",
//                "new_description": "testing",
//                "consumer_device_type": "android",
//                "calender_guid": "calendarGuid"
//        }
      //  params.add(new BasicNameValuePair("consumer_id", id));
        params.add(new BasicNameValuePair("appointment_id", appointment_id));
        params.add(new BasicNameValuePair("appointment_date_time", appointmentdatetime));
        params.add(new BasicNameValuePair("estimated_duration", duration));
        params.add(new BasicNameValuePair("appointment_status", appointment_status));
        params.add(new BasicNameValuePair("new_description", description));

        params.add(new BasicNameValuePair("consumer_device_type", "android"));
        params.add(new BasicNameValuePair("calender_guid", calendarId));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_appointment, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {

                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {


//                        try {
//
//                            return RealmDataInsert.insertSchedule(resultJson.getJSONArray(AppConstants.BODY));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean updateAppointmentStatus(Activity activity,String appointment_id, String updateStatus, String guid, String cancelReason) {
        //  String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//        "appointment_id":"1",
//         "updated_status":"accepted",
//         "vendor_device_type":"ios",
//         "vendor_calender_guid":"12345",
//         "vendor_cancel_reason":"testing service"
        params.add(new BasicNameValuePair("appointment_id", appointment_id));
        params.add(new BasicNameValuePair("updated_status", updateStatus));
        params.add(new BasicNameValuePair("vendor_device_type", "android"));
        params.add(new BasicNameValuePair("vendor_calender_guid", guid));
        params.add(new BasicNameValuePair("vendor_cancel_reason", cancelReason));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_vendor_appointment_status, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {

                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {


                        try {

                            return RealmDataInsert.insertSchedule(activity,resultJson.getJSONArray(AppConstants.BODY));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }
    public static boolean getPreference() {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("user_type", "vendor"));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_preference, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {

                    if (resultJson.getJSONArray(AppConstants.BODY).length() > 0) {


                        try {

                            RealmDataInsert.insertSettingsPreference(resultJson.getJSONArray(AppConstants.BODY));
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
}
