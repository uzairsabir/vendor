package com.thetechsolutions.whodouvendor.AppHelpers.DataBase;

import android.app.Activity;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ChatDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ColleagesDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.PayDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.SearchInDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.SettingsPreferenceDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorProfileDT;
import com.thetechsolutions.whodouvendor.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.datatypes.PhoneContact;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;

import eu.siacs.conversation.entities.Conversation;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Uzair on 8/6/2016.
 */
public class RealmDataInsert {


    public static void insertCustomers(final JSONArray jsonArray) {


        Realm realm = Realm.getDefaultInstance();

        try {


            for (int i = 0; i < jsonArray.length(); i++) {
                CustomersDT item = new CustomersDT();
                realm.beginTransaction();
                try {
                    if (jsonArray.getJSONObject(i).has("id"))
                        item.setId(jsonArray.getJSONObject(i).getInt("id"));
                    else
                        item.setId(0);
                } catch (RealmException e) {
                    item.setId(0);
                }

                try {
                    if (jsonArray.getJSONObject(i).has("country") && !jsonArray.getJSONObject(i).isNull("country"))
                        item.setCountry(jsonArray.getJSONObject(i).getString("country"));
                    else
                        item.setCountry("");
                } catch (RealmException e) {
                    item.setCountry("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("city") && !jsonArray.getJSONObject(i).isNull("city"))
                        item.setCity(jsonArray.getJSONObject(i).getString("city"));
                    else
                        item.setCity("");
                } catch (RealmException e) {
                    item.setCity("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("address") && !jsonArray.getJSONObject(i).isNull("address"))
                        item.setAddress(jsonArray.getJSONObject(i).getString("address"));
                    else
                        item.setAddress("");
                } catch (RealmException e) {
                    item.setAddress("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("category_id") && !jsonArray.getJSONObject(i).isNull("category_id"))
                        item.setCategory_id(jsonArray.getJSONObject(i).getInt("category_id"));
                    else
                        item.setCategory_id(0);
                } catch (RealmException e) {
                    item.setCategory_id(0);

                }
                try {
                    if (jsonArray.getJSONObject(i).has("category_image_url") && !jsonArray.getJSONObject(i).isNull("category_image_url"))
                        item.setCategory_image_url(jsonArray.getJSONObject(i).getString("category_image_url"));
                    else
                        item.setCategory_image_url("");
                } catch (RealmException e) {
                    item.setCategory_image_url("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("category_title") && !jsonArray.getJSONObject(i).isNull("category_title"))
                        item.setCategory_title(jsonArray.getJSONObject(i).getString("category_title"));
                    else
                        item.setCategory_title("");
                } catch (RealmException e) {
                    item.setCategory_title("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("created_by"))
                        item.setCreated_by(jsonArray.getJSONObject(i).getString("created_by"));
                    else
                        item.setCreated_by("");
                } catch (RealmException e) {
                    item.setCreated_by("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("email_1") && !jsonArray.getJSONObject(i).isNull("email_1"))
                        item.setEmail_1(jsonArray.getJSONObject(i).getString("email_1"));
                    else
                        item.setEmail_1("");
                } catch (RealmException e) {
                    item.setEmail_1("");


                }
                try {
                    if (jsonArray.getJSONObject(i).has("first_name"))
                        item.setFirst_name(jsonArray.getJSONObject(i).getString("first_name"));
                    else
                        item.setFirst_name("");
                } catch (RealmException e) {
                    item.setFirst_name("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("last_name"))
                        item.setLast_name(jsonArray.getJSONObject(i).getString("last_name"));
                    else
                        item.setLast_name("");

                } catch (RealmException e) {
                    item.setLast_name("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("image_url") && !jsonArray.getJSONObject(i).isNull("image_url"))
                        item.setImage_url(jsonArray.getJSONObject(i).getString("image_url"));
                    else
                        item.setImage_url("");

                } catch (RealmException e) {
                    item.setImage_url("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("is_register_user"))
                        item.setIs_register_user(jsonArray.getJSONObject(i).getString("is_register_user"));
                    else
                        item.setIs_register_user("");

                } catch (RealmException e) {
                    item.setIs_register_user("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("mobile_number_1"))
                        item.setMobile_number_1(jsonArray.getJSONObject(i).getString("mobile_number_1"));
                    else
                        item.setMobile_number_1("");
                } catch (RealmException e) {
                    item.setMobile_number_1("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("sub_category_id") && !jsonArray.getJSONObject(i).isNull("sub_category_id"))
                        item.setSub_category_id(jsonArray.getJSONObject(i).getInt("sub_category_id"));
                    else
                        item.setSub_category_id(0);
                } catch (RealmException e) {
                    item.setSub_category_id(0);

                }
                try {
                    if (jsonArray.getJSONObject(i).has("sub_category_title") && !jsonArray.getJSONObject(i).isNull("sub_category_title"))
                        item.setSub_category_title("" + jsonArray.getJSONObject(i).getString("sub_category_title"));
                    else
                        item.setSub_category_title("");
                } catch (RealmException e) {
                    item.setSub_category_title("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("subcategory_image_url") && !jsonArray.getJSONObject(i).isNull("subcategory_image_url"))
                        item.setSubcategory_image_url("" + jsonArray.getJSONObject(i).getString("subcategory_image_url"));
                    else
                        item.setSubcategory_image_url("");
                } catch (RealmException e) {
                    item.setSubcategory_image_url("");
                }
                try {
                    item.setTab_pos(0);
                } catch (Exception e) {
                    item.setTab_pos(0);
                }
                try {
                    if (jsonArray.getJSONObject(i).has("username"))
                        item.setUsername(jsonArray.getJSONObject(i).getString("username"));
                    else item.setUsername("");

                } catch (RealmException e) {
                    item.setUsername("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("zip_code"))
                        item.setZip_code(jsonArray.getJSONObject(i).getString("zip_code"));
                    else item.setZip_code("");

                } catch (RealmException e) {
                    item.setZip_code("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("friend_name"))
                        item.setFriend_name(jsonArray.getJSONObject(i).getString("friend_name"));
                    else item.setFriend_name("");

                } catch (RealmException e) {
                    item.setFriend_name("");
                }


                realm.copyToRealmOrUpdate(item);
                realm.commitTransaction();


            }


        } catch (Exception e) {
            e.printStackTrace();
            // realm.cancelTransaction();

        }

    }

    public static void insertColleages(final JSONArray jsonArray) {


        Realm realm = Realm.getDefaultInstance();

        try {


            for (int i = 0; i < jsonArray.length(); i++) {
                ColleagesDT item = new ColleagesDT();
                realm.beginTransaction();
                try {
                    if (jsonArray.getJSONObject(i).has("id"))
                        item.setId(jsonArray.getJSONObject(i).getInt("id"));
                    else
                        item.setId(0);
                } catch (RealmException e) {
                    item.setId(0);
                }

                try {
                    if (jsonArray.getJSONObject(i).has("country") && !jsonArray.getJSONObject(i).isNull("country"))
                        item.setCountry(jsonArray.getJSONObject(i).getString("country"));
                    else
                        item.setCountry("");
                } catch (RealmException e) {
                    item.setCountry("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("city") && !jsonArray.getJSONObject(i).isNull("city"))
                        item.setCity(jsonArray.getJSONObject(i).getString("city"));
                    else
                        item.setCity("");
                } catch (RealmException e) {
                    item.setCity("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("address") && !jsonArray.getJSONObject(i).isNull("address"))
                        item.setAddress(jsonArray.getJSONObject(i).getString("address"));
                    else
                        item.setAddress("");
                } catch (RealmException e) {
                    item.setAddress("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("category_id") && !jsonArray.getJSONObject(i).isNull("category_id"))
                        item.setCategory_id(jsonArray.getJSONObject(i).getInt("category_id"));
                    else
                        item.setCategory_id(0);
                } catch (RealmException e) {
                    item.setCategory_id(0);

                }
                try {
                    if (jsonArray.getJSONObject(i).has("category_image_url") && !jsonArray.getJSONObject(i).isNull("category_image_url"))
                        item.setCategory_image_url(jsonArray.getJSONObject(i).getString("category_image_url"));
                    else
                        item.setCategory_image_url("");
                } catch (RealmException e) {
                    item.setCategory_image_url("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("category_title") && !jsonArray.getJSONObject(i).isNull("category_title"))
                        item.setCategory_title(jsonArray.getJSONObject(i).getString("category_title"));
                    else
                        item.setCategory_title("");
                } catch (RealmException e) {
                    item.setCategory_title("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("created_by"))
                        item.setCreated_by(jsonArray.getJSONObject(i).getString("created_by"));
                    else
                        item.setCreated_by("");
                } catch (RealmException e) {
                    item.setCreated_by("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("email_1") && !jsonArray.getJSONObject(i).isNull("email_1"))
                        item.setEmail_1(jsonArray.getJSONObject(i).getString("email_1"));
                    else
                        item.setEmail_1("");
                } catch (RealmException e) {
                    item.setEmail_1("");


                }
                try {
                    if (jsonArray.getJSONObject(i).has("first_name"))
                        item.setFirst_name(jsonArray.getJSONObject(i).getString("first_name"));
                    else
                        item.setFirst_name("");
                } catch (RealmException e) {
                    item.setFirst_name("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("last_name"))
                        item.setLast_name(jsonArray.getJSONObject(i).getString("last_name"));
                    else
                        item.setLast_name("");

                } catch (RealmException e) {
                    item.setLast_name("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("image_url") && !jsonArray.getJSONObject(i).isNull("image_url"))
                        item.setImage_url(jsonArray.getJSONObject(i).getString("image_url"));
                    else
                        item.setImage_url("");

                } catch (RealmException e) {
                    item.setImage_url("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("is_register_user"))
                        item.setIs_register_user(jsonArray.getJSONObject(i).getString("is_register_user"));
                    else
                        item.setIs_register_user("");

                } catch (RealmException e) {
                    item.setIs_register_user("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("mobile_number_1"))
                        item.setMobile_number_1(jsonArray.getJSONObject(i).getString("mobile_number_1"));
                    else
                        item.setMobile_number_1("");
                } catch (RealmException e) {
                    item.setMobile_number_1("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("sub_category_id") && !jsonArray.getJSONObject(i).isNull("sub_category_id"))
                        item.setSub_category_id(jsonArray.getJSONObject(i).getInt("sub_category_id"));
                    else
                        item.setSub_category_id(0);
                } catch (RealmException e) {
                    item.setSub_category_id(0);

                }
                try {
                    if (jsonArray.getJSONObject(i).has("sub_category_title") && !jsonArray.getJSONObject(i).isNull("sub_category_title"))
                        item.setSub_category_title("" + jsonArray.getJSONObject(i).getString("sub_category_title"));
                    else
                        item.setSub_category_title("");
                } catch (RealmException e) {
                    item.setSub_category_title("");

                }
                try {
                    if (jsonArray.getJSONObject(i).has("sub_category_image_url") && !jsonArray.getJSONObject(i).isNull("sub_category_image_url"))
                        item.setSubcategory_image_url("" + jsonArray.getJSONObject(i).getString("sub_category_image_url"));
                    else
                        item.setSubcategory_image_url("");
                } catch (RealmException e) {
                    item.setSubcategory_image_url("");
                }
                try {
                    item.setTab_pos(1);
                } catch (Exception e) {
                    item.setTab_pos(0);
                }
                try {
                    if (jsonArray.getJSONObject(i).has("username"))
                        item.setUsername(jsonArray.getJSONObject(i).getString("username"));
                    else item.setUsername("");

                } catch (RealmException e) {
                    item.setUsername("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("zip_code"))
                        item.setZip_code(jsonArray.getJSONObject(i).getString("zip_code"));
                    else item.setZip_code("");

                } catch (RealmException e) {
                    item.setZip_code("");
                }
                try {
                    if (jsonArray.getJSONObject(i).has("friend_name"))
                        item.setFriend_name(jsonArray.getJSONObject(i).getString("friend_name"));
                    else item.setFriend_name("");

                } catch (RealmException e) {
                    item.setFriend_name("");
                }


                realm.copyToRealmOrUpdate(item);
                realm.commitTransaction();


            }


        } catch (Exception e) {
            e.printStackTrace();
            // realm.cancelTransaction();

        }

    }

//
//    public static boolean insertSchedule(Activity activity, final JSONArray jsonObject) {
//
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        try {
//
//
//
//            for (int i = 0; i < jsonObject.length(); i++) {
//                ScheduleDT tempItem = new ScheduleDT();
//                MyLogs.printinfo(" insertSchedule ");
//                try {
//                    try {
//                        tempItem.setAppointment_id(jsonObject.getJSONObject(i).getInt("appointment_id"));
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("calendar_guid")) {
//                            tempItem.setCalendar_guid(jsonObject.getJSONObject(i).getString("calendar_guid"));
//                        } else {
//                            tempItem.setCalendar_guid("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("sub_category_id"))
//                            tempItem.setSub_category_id(jsonObject.getJSONObject(i).getInt("sub_category_id"));
//                        else
//                            tempItem.setSub_category_id(0);
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("status"))
//                            tempItem.setStatus(jsonObject.getJSONObject(i).getString("status"));
//                        else
//                            tempItem.setStatus("");
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("consumer_name"))
//                            tempItem.setConsumer_name(jsonObject.getJSONObject(i).getString("consumer_name"));
//                        else
//                            tempItem.setConsumer_name("");
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("category_id"))
//                            tempItem.setCategory_id(jsonObject.getJSONObject(i).getInt("category_id"));
//                        else {
//                            tempItem.setCategory_id(0);
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("consumer_id"))
//                            tempItem.setConsumer_id(jsonObject.getJSONObject(i).getInt("consumer_id"));
//                        else {
//                            tempItem.setConsumer_id(0);
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("category_image_url"))
//                            tempItem.setCategory_image_url(jsonObject.getJSONObject(i).getString("category_image_url"));
//                        else {
//                            tempItem.setCategory_image_url("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("sub_categor_title"))
//                            tempItem.setSub_categor_title(jsonObject.getJSONObject(i).getString("sub_categor_title"));
//                        else {
//                            tempItem.setSub_categor_title("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("vendor_id"))
//                            tempItem.setVendor_id(jsonObject.getJSONObject(i).getInt("vendor_id"));
//                        else {
//                            tempItem.setVendor_id(0);
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("call_message"))
//                            tempItem.setCall_message(jsonObject.getJSONObject(i).getInt("call_message"));
//                        else {
//                            tempItem.setCall_message(0);
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("category_title"))
//                            tempItem.setCategory_title(jsonObject.getJSONObject(i).getString("category_title"));
//                        else {
//                            tempItem.setCategory_title("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("description"))
//                            tempItem.setDescription(jsonObject.getJSONObject(i).getString("description"));
//                        else {
//                            tempItem.setDescription("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("sub_category_image_url"))
//                            tempItem.setSub_category_image_url(jsonObject.getJSONObject(i).getString("sub_category_image_url"));
//                        else {
//                            tempItem.setSub_category_image_url("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("appointment_date_time")) {
//                            //MyLogs.printinfo("appointment_date_time " + UtilityFunctions.toDate(jsonObject.getJSONObject(i).getString("appointment_date_time")));
//                            tempItem.setAppointment_date_time(UtilityFunctions.convertDateToMillis(jsonObject.getJSONObject(i).getString("appointment_date_time")));
//                        } else {
//                            tempItem.setAppointment_date_time(UtilityFunctions.convertDateToMillis(UtilityFunctions.getcurrentDateTime()));
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("consumer_device_type"))
//                            tempItem.setConsumer_device_type(jsonObject.getJSONObject(i).getString("consumer_device_type"));
//                        else {
//                            tempItem.setConsumer_device_type("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("estimated_duration"))
//                            tempItem.setEstimated_duration(jsonObject.getJSONObject(i).getString("estimated_duration"));
//                        else {
//                            tempItem.setEstimated_duration("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                    try {
//                        if (!jsonObject.getJSONObject(i).isNull("vendor_name"))
//                            tempItem.setVendor_name(jsonObject.getJSONObject(i).getString("vendor_name"));
//                        else {
//                            tempItem.setVendor_name("");
//                        }
//                    } catch (Exception e) {
//
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//
//
//
//                realm.copyToRealmOrUpdate(tempItem);
//                realm.commitTransaction();
//                //  MyLogs.printinfo("tempItem " + tempItem.getAppointment_id());
//
//                return true;
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//           //realm.cancelTransaction();
//
//        }
//        return false;
//    }


    public static boolean insertSchedule(Activity activity, final JSONArray jsonObject) {

        Realm realm = Realm.getDefaultInstance();

        try {


            for (int i = 0; i < jsonObject.length(); i++) {
                realm.beginTransaction();
                ScheduleDT tempItem = new ScheduleDT();
                MyLogs.printinfo(" insertSchedule ");
                try {
                    try {
                        tempItem.setAppointment_id(jsonObject.getJSONObject(i).getInt("appointment_id"));
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("vendor_calendar_guid")) {
                            tempItem.setCalendar_guid(jsonObject.getJSONObject(i).getString("vendor_calendar_guid"));
                        } else {
                            tempItem.setCalendar_guid("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("consumer_image_url")) {
                            tempItem.setConsumer_image_url(jsonObject.getJSONObject(i).getString("consumer_image_url"));
                        } else {
                            tempItem.setConsumer_image_url("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("vendor_image_url")) {
                            tempItem.setVendor_image_url(jsonObject.getJSONObject(i).getString("vendor_image_url"));
                        } else {
                            tempItem.setVendor_image_url("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("consumer_address")) {
                            tempItem.setConsumer_address(jsonObject.getJSONObject(i).getString("consumer_address"));
                        } else {
                            tempItem.setConsumer_address("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("vendor_address")) {
                            tempItem.setVendor_address(jsonObject.getJSONObject(i).getString("vendor_address"));
                        } else {
                            tempItem.setVendor_address("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("consumer_zip_code")) {
                            tempItem.setConsumer_zip_code(jsonObject.getJSONObject(i).getString("consumer_zip_code"));
                        } else {
                            tempItem.setConsumer_zip_code("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("vendor_zip_code")) {
                            tempItem.setVendor_zip_code(jsonObject.getJSONObject(i).getString("vendor_zip_code"));
                        } else {
                            tempItem.setVendor_zip_code("");
                        }
                    } catch (Exception e) {

                    }







                    try {
                        if (!jsonObject.getJSONObject(i).isNull("sub_category_id"))
                            tempItem.setSub_category_id(jsonObject.getJSONObject(i).getInt("sub_category_id"));
                        else
                            tempItem.setSub_category_id(0);
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("status"))
                            tempItem.setStatus(jsonObject.getJSONObject(i).getString("status"));
                        else
                            tempItem.setStatus("");
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("consumer_name"))
                            tempItem.setConsumer_name(jsonObject.getJSONObject(i).getString("consumer_name"));
                        else
                            tempItem.setConsumer_name("");
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("category_id"))
                            tempItem.setCategory_id(jsonObject.getJSONObject(i).getInt("category_id"));
                        else {
                            tempItem.setCategory_id(0);
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("consumer_id"))
                            tempItem.setConsumer_id(jsonObject.getJSONObject(i).getInt("consumer_id"));
                        else {
                            tempItem.setConsumer_id(0);
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("category_image_url"))
                            tempItem.setCategory_image_url(jsonObject.getJSONObject(i).getString("category_image_url"));
                        else {
                            tempItem.setCategory_image_url("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("sub_categor_title"))
                            tempItem.setSub_categor_title(jsonObject.getJSONObject(i).getString("sub_categor_title"));
                        else {
                            tempItem.setSub_categor_title("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("vendor_id"))
                            tempItem.setVendor_id(jsonObject.getJSONObject(i).getInt("vendor_id"));
                        else {
                            tempItem.setVendor_id(0);
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("call_message"))
                            tempItem.setCall_message(jsonObject.getJSONObject(i).getInt("call_message"));
                        else {
                            tempItem.setCall_message(0);
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("category_title"))
                            tempItem.setCategory_title(jsonObject.getJSONObject(i).getString("category_title"));
                        else {
                            tempItem.setCategory_title("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("description"))
                            tempItem.setDescription(jsonObject.getJSONObject(i).getString("description"));
                        else {
                            tempItem.setDescription("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("sub_category_image_url"))
                            tempItem.setSub_category_image_url(jsonObject.getJSONObject(i).getString("sub_category_image_url"));
                        else {
                            tempItem.setSub_category_image_url("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("appointment_date_time")) {
                            //MyLogs.printinfo("appointment_date_time " + UtilityFunctions.toDate(jsonObject.getJSONObject(i).getString("appointment_date_time")));
                            tempItem.setAppointment_date_time(UtilityFunctions.convertDateToMillis(jsonObject.getJSONObject(i).getString("appointment_date_time")));
                        } else {
                            tempItem.setAppointment_date_time(UtilityFunctions.convertDateToMillis(UtilityFunctions.getcurrentDateTime()));
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("consumer_device_type"))
                            tempItem.setConsumer_device_type(jsonObject.getJSONObject(i).getString("consumer_device_type"));
                        else {
                            tempItem.setConsumer_device_type("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("estimated_duration"))
                            tempItem.setEstimated_duration(jsonObject.getJSONObject(i).getString("estimated_duration"));
                        else {
                            tempItem.setEstimated_duration("");
                        }
                    } catch (Exception e) {

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("vendor_name"))
                            tempItem.setVendor_name(jsonObject.getJSONObject(i).getString("vendor_name"));
                        else {
                            tempItem.setVendor_name("");
                        }
                    } catch (Exception e) {

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
//                try {
//                    MyLogs.printinfo("temp_tes 1 " + tempItem.getAppointmentDate());
//                    MyLogs.printinfo("temp_tes 2 " + tempItem.getConsumer_name());
//                    MyLogs.printinfo("temp_tes 3 " + tempItem.getDuration());
//                    //     UtilityFunctions.addEvent(activity, tempItem.getAppointment_date_time(), tempItem.getConsumer_name(), tempItem.getDuration());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }

                realm.insertOrUpdate(tempItem);
                realm.commitTransaction();


                // MyLogs.printinfo("tempItem " + tempItem.getAppointment_id());

                //  return true;


            }
        } catch (Exception e) {
            e.printStackTrace();
            //  realm.cancelTransaction();

        }
        return true;
    }

    public static void insertPay(final JSONObject jsonObject) {

        try {

            Realm realm = Realm.getDefaultInstance();

            //realm.createObjectFromJson(CustomersDT.class, jsonObject);


            for (int i = 1; i < 3; i++) {
                PayDT tempItem = new PayDT();
                realm.beginTransaction();

                if (i == 1) {
                    tempItem.setId(2);
                    tempItem.setTitle("Aubery Spencer");
                    tempItem.setService_date("Monday, july 18");
                    tempItem.setAmount("$65");
                    tempItem.setDisplay_pic("res:///" + R.drawable.auto_mechanic);
                } else if (i == 2) {
                    tempItem.setId(3);
                    tempItem.setTitle("John Walton");
                    tempItem.setService_date("Monday, july 20");
                    tempItem.setAmount("$100");
                    tempItem.setDisplay_pic("res:///" + R.drawable.babysitter);
                }
                realm.copyToRealm(tempItem);
                realm.commitTransaction();

            }


        } catch (Exception e) {

        }

    }

    public static void insertSettingsPreference(final JSONArray jsonArray) {
        Realm realm = Realm.getDefaultInstance();
        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                realm.beginTransaction();
                realm.createOrUpdateObjectFromJson(SettingsPreferenceDT.class, jsonArray.getJSONObject(i));
                realm.commitTransaction();
            }

//            realm.executeTransactionAsync(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//
//                }
//            });


            //  }


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static boolean insertContact(final ArrayList<PhoneContact> arrayList) {
        int counter = 0;
        Realm realm = Realm.getDefaultInstance();


        for (PhoneContact item : arrayList) {

            realm.beginTransaction();

            ContactDT tempItem = new ContactDT();
            //MyLogs.printerror("contact_id "+item.getContact_id());
            tempItem.setId(item.getContact_id());
            tempItem.setNumbers(item.getNumber());
            tempItem.setFirstname(item.getFirst_name());
            tempItem.setLastname(item.getLast_name());
            tempItem.setCity(item.getCity());
            tempItem.setCountry(item.getCountry());
            tempItem.setEmail(item.getEmail());
            tempItem.setZip(item.getZip());
            tempItem.setIsVerified(0);
            tempItem.setSendedToServer(0);

            realm.insertOrUpdate(tempItem);
            realm.commitTransaction();
        }
//                    realm.cancelTransaction();


//            realm.cancelTransaction();
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//
//
//
//
//                }
//            });


        //realm.close();
/*

        if (counter == arrayList.size()) {
            return true;
        }
*/
        return true;

    }

    public static void insertProfile(final JSONArray jsonObject) {
        Realm realm = Realm.getDefaultInstance();

        try {

            for (int i = 0; i < jsonObject.length(); i++) {
                ProfileDT tempItem = new ProfileDT();
                realm.beginTransaction();
                try {
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("id")) {
                            tempItem.setId(Integer.parseInt(jsonObject.getJSONObject(i).getString("id")));
                            AppPreferences.setString(AppPreferences.PREF_USER_ID, "" + tempItem.getId());

                        } else {
                            tempItem.setId(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("username")) {
                            tempItem.setUsername(jsonObject.getJSONObject(i).getString("username"));
                        } else {
                            tempItem.setUsername("");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("mobile_number_1"))
                            tempItem.setMobile_number_1(jsonObject.getJSONObject(i).getString("mobile_number_1"));
                        else
                            tempItem.setMobile_number_1("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("address"))
                            tempItem.setAddress(jsonObject.getJSONObject(i).getString("address"));
                        else
                            tempItem.setAddress("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("city"))
                            tempItem.setCity(jsonObject.getJSONObject(i).getString("city"));
                        else
                            tempItem.setCity("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("country"))
                            tempItem.setCountry(jsonObject.getJSONObject(i).getString("country"));
                        else {
                            tempItem.setCountry("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("email_1"))
                            tempItem.setEmail_1(jsonObject.getJSONObject(i).getString("email_1"));
                        else {
                            tempItem.setEmail_1("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("first_name"))
                            tempItem.setFirst_name(jsonObject.getJSONObject(i).getString("first_name"));
                        else {
                            tempItem.setFirst_name("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("last_name"))
                            tempItem.setLast_name(jsonObject.getJSONObject(i).getString("last_name"));
                        else {
                            tempItem.setLast_name("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("image_url"))
                            tempItem.setImage_url(jsonObject.getJSONObject(i).getString("image_url"));
                        else {
                            tempItem.setImage_url("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("sub_category_image_url"))
                            tempItem.setSubcategory_image_url(jsonObject.getJSONObject(i).getString("sub_category_image_url"));
                        else {
                            tempItem.setSubcategory_image_url("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("is_register_user"))
                            tempItem.setIs_register_user(jsonObject.getJSONObject(i).getString("is_register_user"));
                        else {
                            tempItem.setIs_register_user("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("zip_code"))
                            tempItem.setZip_code(jsonObject.getJSONObject(i).getInt("zip_code"));
                        else {
                            tempItem.setZip_code(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("state"))
                            tempItem.setState(jsonObject.getJSONObject(i).getString("state"));
                        else {
                            tempItem.setState("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("category_id"))
                            tempItem.setCategory_id(jsonObject.getJSONObject(i).getInt("category_id"));
                        else {
                            tempItem.setCategory_id(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("sub_category_id"))
                            tempItem.setSub_category_id(jsonObject.getJSONObject(i).getInt("sub_category_id"));
                        else {
                            tempItem.setSub_category_id(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("category_title"))
                            tempItem.setCategory_title(jsonObject.getJSONObject(i).getString("category_title"));
                        else {
                            tempItem.setCategory_title("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!jsonObject.getJSONObject(i).isNull("sub_category_title"))
                            tempItem.setSub_category_title(jsonObject.getJSONObject(i).getString("sub_category_title"));
                        else {
                            tempItem.setSub_category_title("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }

                realm.insertOrUpdate(tempItem);
                realm.commitTransaction();
                //realm.close();
                //  MyLogs.printerror("tempItem " + tempItem.getId());
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        // realm.commitTransaction();

    }

    public static void insertCategories(final JSONArray jsonArray) {

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        try {
            realm.createAllFromJson(VendorCategoryDT.class, jsonArray);


        } catch (RealmException e) {
            e.printStackTrace();

        }
        realm.commitTransaction();


    }

    public static void insertSearchIn() {
        Realm realm = Realm.getDefaultInstance();

        try {


            for (int i = 1; i < 4; i++) {
                SearchInDT tempItem = new SearchInDT();
                realm.beginTransaction();
                if (i == 1) {
                    tempItem.setId(i);
                    tempItem.setTitle(AppConstants.CUSTOMERS);
                    tempItem.setImage_url("res:///" + R.drawable.appliance_repair);
                } else if (i == 2) {
                    tempItem.setId(2);
                    tempItem.setTitle(AppConstants.COLLEAGUES);
                    tempItem.setImage_url("res:///" + R.drawable.auto_mechanic);
                } else if (i == 3) {
                    tempItem.setId(3);
                    tempItem.setTitle("Friend's Providers");
                    tempItem.setImage_url("res:///" + R.drawable.babysitter);
                }
                realm.copyToRealm(tempItem);
                realm.commitTransaction();

            }


        } catch (Exception e) {
            realm.cancelTransaction();

        }

    }

    public static void insertVendorProfile(final JSONArray jsonArray, int _tab_pos) {
        Realm realm = Realm.getDefaultInstance();

        try {
            try {


                for (int i = 0; i < jsonArray.length(); i++) {
                    VendorProfileDT item = new VendorProfileDT();
                    realm.beginTransaction();
                    try {
                        if (jsonArray.getJSONObject(i).has("id"))
                            item.setId(jsonArray.getJSONObject(i).getInt("id"));
                        else
                            item.setId(0);
                    } catch (RealmException e) {
                        item.setId(0);
                    }

                    try {
                        if (jsonArray.getJSONObject(i).has("country") && !jsonArray.getJSONObject(i).isNull("country"))
                            item.setCountry(jsonArray.getJSONObject(i).getString("country"));
                        else
                            item.setCountry("");
                    } catch (RealmException e) {
                        item.setCountry("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("city") && !jsonArray.getJSONObject(i).isNull("city"))
                            item.setCity(jsonArray.getJSONObject(i).getString("city"));
                        else
                            item.setCity("");
                    } catch (RealmException e) {
                        item.setCity("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("address") && !jsonArray.getJSONObject(i).isNull("address"))
                            item.setAddress(jsonArray.getJSONObject(i).getString("address"));
                        else
                            item.setAddress("");
                    } catch (RealmException e) {
                        item.setAddress("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("category_id") && !jsonArray.getJSONObject(i).isNull("category_id"))
                            item.setCategory_id(jsonArray.getJSONObject(i).getInt("category_id"));
                        else
                            item.setCategory_id(0);
                    } catch (RealmException e) {
                        item.setCategory_id(0);

                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("category_title") && !jsonArray.getJSONObject(i).isNull("category_title"))
                            item.setCategory_title(jsonArray.getJSONObject(i).getString("category_title"));
                        else
                            item.setCategory_title("");
                    } catch (RealmException e) {
                        item.setCategory_title("");

                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("created_by"))
                            item.setCreated_by(jsonArray.getJSONObject(i).getString("created_by"));
                        else
                            item.setCreated_by("");
                    } catch (RealmException e) {
                        item.setCreated_by("");

                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("email_1") && !jsonArray.getJSONObject(i).isNull("email_1"))
                            item.setEmail_1(jsonArray.getJSONObject(i).getString("email_1"));
                        else
                            item.setEmail_1("");
                    } catch (RealmException e) {
                        item.setEmail_1("");


                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("first_name"))
                            item.setFirst_name(jsonArray.getJSONObject(i).getString("first_name"));
                        else
                            item.setFirst_name("");
                    } catch (RealmException e) {
                        item.setFirst_name("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("last_name"))
                            item.setLast_name(jsonArray.getJSONObject(i).getString("last_name"));
                        else
                            item.setLast_name("");

                    } catch (RealmException e) {
                        item.setLast_name("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("image_url") && !jsonArray.getJSONObject(i).isNull("image_url"))
                            item.setImage_url(jsonArray.getJSONObject(i).getString("image_url"));
                        else
                            item.setImage_url("");

                    } catch (RealmException e) {
                        item.setImage_url("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("is_register_user"))
                            item.setIs_register_user(jsonArray.getJSONObject(i).getString("is_register_user"));
                        else
                            item.setIs_register_user("");

                    } catch (RealmException e) {
                        item.setIs_register_user("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("mobile_number_1"))
                            item.setMobile_number_1(jsonArray.getJSONObject(i).getString("mobile_number_1"));
                        else
                            item.setMobile_number_1("");
                    } catch (RealmException e) {
                        item.setMobile_number_1("");

                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("sub_category_id") && !jsonArray.getJSONObject(i).isNull("sub_category_id"))
                            item.setSub_category_id(jsonArray.getJSONObject(i).getInt("sub_category_id"));
                        else
                            item.setSub_category_id(0);
                    } catch (RealmException e) {
                        item.setSub_category_id(0);

                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("sub_category_title") && !jsonArray.getJSONObject(i).isNull("sub_category_title"))
                            item.setSub_category_title("" + jsonArray.getJSONObject(i).getString("sub_category_title"));
                        else
                            item.setSub_category_title("");
                    } catch (RealmException e) {
                        item.setSub_category_title("");

                    }

                    try {
                        item.setTab_pos(_tab_pos);
                    } catch (Exception e) {
                        item.setTab_pos(0);
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("username"))
                            item.setUsername(jsonArray.getJSONObject(i).getString("username"));
                        else item.setUsername("");

                    } catch (RealmException e) {
                        item.setUsername("");
                    }
                    try {
                        if (jsonArray.getJSONObject(i).has("zip_code"))
                            item.setZip_code(jsonArray.getJSONObject(i).getString("zip_code"));
                        else item.setZip_code("");

                    } catch (RealmException e) {
                        item.setZip_code("");
                    }


                    realm.copyToRealmOrUpdate(item);
                    realm.commitTransaction();


                }


            } catch (Exception e) {
                //   realm.cancelTransaction();

            }
//            for (int i = 0; i < jsonObject.length(); i++) {
//
//                jsonObject.getJSONObject(i).put("tab_pos", "" + _tab_pos);
//                jsonObject.getJSONObject(i).put("tab_primary", "" + _tab_pos + jsonObject.getJSONObject(i).getString("id"));
//
//                MyLogs.printinfo("my_new_json_object " + jsonObject);
//                realm.createOrUpdateObjectFromJson(VendorProfileDT.class, jsonObject.getJSONObject(i));
//            }
//

        } catch (Exception e) {
            e.printStackTrace();

        }
//        realm.commitTransaction();

    }

//    public static void insertConsumerProviders(String provider_number, int is_provider) {
//        Realm realm = Realm.getDefaultInstance();
//        try {
//
//            if (RealmDataRetrive.getConsumerProvider(provider_number, is_provider) == null) {
//
//                MyLogs.printinfo(" provider_number " + provider_number + " : " + is_provider);
//
//                ConsumerProviderDT tempItem = new ConsumerProviderDT();
//                realm.beginTransaction();
//
//                tempItem.setProvider_friend_number(provider_number);
//                tempItem.setIs_provider(is_provider);
//
//
//                realm.copyToRealm(tempItem);
//                realm.commitTransaction();
//
//            }
//
//        } catch (Exception e) {
//            realm.cancelTransaction();
//
//        }
//
//    }

    public static void insertMessageList(ArrayList<Conversation> list) {

        RealmDataDelete.deleteCompleteTableRecord(ChatDT.class);

        RealmResults<CustomersDT> homeList = RealmDataRetrive.getHomeListOfMyProviderAndMyFriends();
        Realm realm = Realm.getDefaultInstance();

        try {


            for (int i = 0; i < list.size(); i++) {
                for (CustomersDT item : homeList
                        ) {
                    if (item.getUsername().equals(list.get(i).getName())) {
                        ChatDT tempItem = new ChatDT();
                        realm.beginTransaction();

                        tempItem.setId(i);
                        tempItem.setTitle(item.getFirst_name() + " " + item.getLast_name());
                        //     tempItem.setMessage(list.get(i).getLatestMessage());
                        tempItem.setDisplay_pic(item.getImage_url());
                        tempItem.setUsername(item.getUsername());
                        tempItem.setRead(list.get(i).isRead());

                        realm.copyToRealm(tempItem);
                        realm.commitTransaction();
                    }

                }


            }


        } catch (Exception e) {
            realm.cancelTransaction();

        }

    }


}
