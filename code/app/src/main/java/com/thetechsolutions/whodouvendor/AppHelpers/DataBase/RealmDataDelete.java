package com.thetechsolutions.whodouvendor.AppHelpers.DataBase;

import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ColleagesDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;

import org.vanguardmatrix.engine.utils.MyLogs;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Uzair on 8/6/2016.
 */
public class RealmDataDelete {

//    public static void deleteConsumerProviderLink(String providerName, int is_provider) {
//        final Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        try {
//            ConsumerProviderDT result = realm.where(ConsumerProviderDT.class).equalTo("provider_friend_number", providerName).equalTo("is_provider", is_provider).findFirst();
//
//            result.deleteFromRealm();
//
//        } catch (Exception e) {
//
//        }
//        realm.commitTransaction();
//
//
//    }

    public static void deleteHomeDTByPos(int tab_pos) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            RealmResults<CustomersDT> result = realm.where(CustomersDT.class).equalTo("tab_pos", tab_pos).findAll();
            result.deleteAllFromRealm();
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            realm.cancelTransaction();

        }

        MyLogs.printinfo("cancel_tran ");


    }

    public static void deleteCompleteTableRecord(Class requestedClass) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {


            realm.delete(requestedClass);

        } catch (Exception e) {

        }
        realm.commitTransaction();


    }


    public static void deleteContactOldRecord(String firstName, String lastName, String providerName) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            ContactDT result = realm.where(ContactDT.class).equalTo("Firstname", firstName).equalTo("Lastname", lastName).contains("Numbers", providerName).findFirst();

            result.deleteFromRealm();

        } catch (Exception e) {

        }
        realm.commitTransaction();

        try{


        }catch(Exception e){

        }


    }

    public static void deleteHomeItem(String providerName, int pos) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            //  MyLogs.printinfo("id  "+id+" pos "+pos);

            if (pos == 0) {
                CustomersDT result = realm.where(CustomersDT.class).equalTo("username", providerName).findFirst();


                result.deleteFromRealm();
            } else if (pos == 1) {
                ColleagesDT result = realm.where(ColleagesDT.class).equalTo("username", providerName).findFirst();


                result.deleteFromRealm();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        realm.commitTransaction();
    }
}
