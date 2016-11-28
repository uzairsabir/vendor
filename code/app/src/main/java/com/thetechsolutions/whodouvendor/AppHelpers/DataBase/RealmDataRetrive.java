package com.thetechsolutions.whodouvendor.AppHelpers.DataBase;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
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
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainActivity;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Uzair on 8/6/2016.
 */
public class RealmDataRetrive {

    public static RealmResults<CustomersDT> getCustomerList() {

        try {
            AppController.saveChatNamesAvatar(HomeMainActivity.activity);
        } catch (Exception e) {

        }

         Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};
        return realm.where(CustomersDT.class).findAllSorted(a, b);

    }

    public static RealmResults<ColleagesDT> getColleagesList() {


         Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};
        return realm.where(ColleagesDT.class).findAllSorted(a, b);

    }


    public static RealmResults<CustomersDT> getHomeSearchList(String keyword, int tabId, int cat_id, int sub_cat_id) {

        MyLogs.printinfo("cat_id " + cat_id + ": " + sub_cat_id);

         Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};

        if (UtilityFunctions.isEmpty(keyword)) {

            if (cat_id == 0 && sub_cat_id == 0) {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).findAllSorted(a, b);

            } else if (cat_id == 0) {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            } else if (sub_cat_id == 0) {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("category_id", cat_id).findAllSorted(a, b);

            } else {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("category_id", cat_id).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            }
        } else {
            if (cat_id == 0 && sub_cat_id == 0) {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).findAllSorted(a, b);

            } else if (cat_id == 0) {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            } else if (sub_cat_id == 0) {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).equalTo("category_id", cat_id).findAllSorted(a, b);

            } else {
                return realm.where(CustomersDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).equalTo("category_id", cat_id).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            }
        }

    }

    public static CustomersDT getCustomersDetail(String provider_user_name) {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(CustomersDT.class).equalTo("username", provider_user_name).findFirst();

    }

    public static ColleagesDT getColleaguesDetail(String provider_user_name) {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(ColleagesDT.class).equalTo("username", provider_user_name).findFirst();

    }

    public static RealmResults<ScheduleDT> getScheduleList(int tabId) {


         Realm realm = Realm.getDefaultInstance();

        MyLogs.printinfo("scheduleList " + tabId + "  " + realm.where(ScheduleDT.class).findAll().size());

        if (tabId == 0) {
            return realm.where(ScheduleDT.class).greaterThan("appointment_date_time", System.currentTimeMillis()).notEqualTo("status", "rejected").findAllSorted("appointment_date_time", Sort.ASCENDING);
        } else if (tabId == 1) {
            return realm.where(ScheduleDT.class).lessThanOrEqualTo("appointment_date_time", System.currentTimeMillis()).notEqualTo("status", "rejected").findAllSorted("appointment_date_time", Sort.DESCENDING);
        }

        return null;

    }

    public static ScheduleDT getScheduleDetail(int item_id) {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(ScheduleDT.class).equalTo("appointment_id", item_id).findFirst();

    }

    public static RealmResults<PayDT> getPayList(int tabId) {

        final Realm realm = Realm.getDefaultInstance();
        if (tabId == 0) {
            return realm.where(PayDT.class).equalTo("status", "pending").findAll();
        }
        return realm.where(PayDT.class).equalTo("status", "paid").findAll();

    }

    public static PayDT getPayDetail(int item_id) {

        Realm realm = Realm.getDefaultInstance();
        return realm.where(PayDT.class).equalTo("id", item_id).findFirst();

    }

    public static RealmResults<ChatDT> getChatList() {

        Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatDT.class).findAll();

    }

    public static RealmResults<ChatDT> getChatSearch(String keyword) {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatDT.class).contains("title", keyword, Case.INSENSITIVE).findAll();

    }

    public static RealmResults<SettingsPreferenceDT> getSettingsPreferenceList() {

         Realm realm = Realm.getDefaultInstance();
        MyLogs.printinfo(" group " + realm.where(SettingsPreferenceDT.class).findAll().size());
        return realm.where(SettingsPreferenceDT.class).findAll().distinct("group");

    }

    public static RealmResults<SettingsPreferenceDT> getSettingsPreferenceItemList(String group_name) {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(SettingsPreferenceDT.class).equalTo("group", group_name).findAll();

    }


    public static RealmResults<ContactDT> getContactList() {

         Realm realm = Realm.getDefaultInstance();
        String[] a = {"Firstname", "Lastname"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};
        return realm.where(ContactDT.class).findAllSorted(a, b).distinct("Numbers");

    }

    public static RealmResults<ContactDT> getContactListToSendServer() {

        Realm realm = Realm.getDefaultInstance();
        return realm.where(ContactDT.class).equalTo("SendedToServer", 0).findAll();

    }

    public static ProfileDT getProfile() {
        ProfileDT profileDT;
        Realm realm = Realm.getDefaultInstance();
        profileDT = realm.where(ProfileDT.class).findFirst();
        MyLogs.printerror("get_profile " + profileDT);
        return profileDT;

    }

//    public static CustomersDT getHomeProfile(String username, int tab_pos) {
//
//        final Realm realm = Realm.getDefaultInstance();
//        return realm.where(CustomersDT.class).equalTo("username", username).equalTo("tab_pos", tab_pos).findFirst();
//
//    }

    public static VendorProfileDT getVendorProfileTemp(String username, int tab_pos) {

        Realm realm = Realm.getDefaultInstance();
        return realm.where(VendorProfileDT.class).equalTo("username", username).equalTo("tab_pos", tab_pos).findFirst();

    }

    public static RealmResults<VendorCategoryDT> getCategoryList(int subCatID) {

        if (subCatID == 0) {
             Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).findAll().distinct("category");
        } else {

             Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).equalTo("subcategory_id", subCatID).findAll().distinct("category");
        }


    }

    public static RealmResults<VendorCategoryDT> getSubCategoryList(int catID) {

        if (catID == 0) {

             Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).findAll().distinct("subcategory");

        } else {

             Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).equalTo("category_id", catID).findAll().distinct("subcategory");
        }


    }

    public static RealmResults<SearchInDT> getSearchInList(boolean notRequireProvider) {
         Realm realm = Realm.getDefaultInstance();
        if (notRequireProvider) {
            return realm.where(SearchInDT.class).notEqualTo("id", 3).findAll();
        } else {
            return realm.where(SearchInDT.class).findAll();
        }


    }

    public static ContactDT getContactDetail(String username) {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(ContactDT.class).contains("Numbers", username).findFirst();

    }


    public static int getContactMaxId() {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(ContactDT.class).max("id").intValue();

    }

    public static RealmResults<CustomersDT> getHomeListOfMyProviderAndMyFriends() {

         Realm realm = Realm.getDefaultInstance();
        return realm.where(CustomersDT.class).notEqualTo("tab_pos", 2).distinct("username");

    }


}
