package com.thetechsolutions.whodouvendor.AppHelpers.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.R;

import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.datatypes.PhoneContact;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import eu.siacs.conversation.entities.Conversation;
import eu.siacs.conversation.ui.ConversationActivity;
import eu.siacs.conversation.utils.FriendNames;
import eu.siacs.conversation.utils.UIHelper;

/**
 * Created by Uzair on 12/19/2015.
 */
public class AppController {

    static MaterialDialog loaderDialoge;


    public static void insertDummpyContent() {
        // RealmDataInsert.insertCustomers(null);
        // RealmDataInsert.insertSchedule(null);
     //   RealmDataInsert.insertPay(null);
        // RealmDataInsert.insertChat(null);
      //  RealmDataInsert.insertSettingsPreference(null);
        RealmDataInsert.insertSearchIn();
        AppPreferences.setBoolean(AppPreferences.PREF_IS_DUMY_CONTENT_INSERT, true);

    }

    public static void showToast(Activity activity, String text) {
        SuperActivityToast.create(activity, new Style(), Style.TYPE_STANDARD)
                .setText(text)
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(activity.getResources().getColor(R.color.app_text_blue_new_tr50))
                .setAnimations(Style.ANIMATIONS_POP).show();
    }

    public static void showDialoge(Activity activity) {

        MyLogs.printinfo("show_dialoge");
        loaderDialoge = new MaterialDialog.Builder(activity)
                .title("Loading")
                .content("Please wait...")
                .progress(true, 0)
                .widgetColor(activity.getResources().getColor(R.color.who_do_u_blue))
                .backgroundColor(activity.getResources().getColor(R.color.white))
                .titleColor(activity.getResources().getColor(R.color.black))
                .contentColor(activity.getResources().getColor(R.color.who_do_u_medium_grey))
                .show();
    }

    public static void hideDialoge() {

        try {
            loaderDialoge.dismiss();
        } catch (Exception e) {

        }

    }

    public static void insertIntoContact(String first_name, String last_name, String user_city, String zip_code, String user_country, String providerName, String email_address) {


        int id = RealmDataRetrive.getContactMaxId() + 1;
        ArrayList<PhoneContact> arrayList = new ArrayList<>();
        PhoneContact phoneContact = new PhoneContact();
        phoneContact.setFirst_name(first_name);
        phoneContact.setLast_name(last_name);
        phoneContact.setCity(user_city);
        phoneContact.setContact_id(id + 1);
        phoneContact.setZip(zip_code);
        phoneContact.setCountry(user_country);
        try {
            JSONObject numberJson = new JSONObject();
            numberJson.put("primary_number", providerName);
            phoneContact.setNumber(numberJson.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            JSONObject emailJson = new JSONObject();
            emailJson.put("primary_email", email_address);
            phoneContact.setEmail(emailJson.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
        // MyLogs.printinfo("arrayList_contact  " + phoneContact.getContact_id());
        arrayList.add(phoneContact);


        RealmDataInsert.insertContact(arrayList);
    }

    public static void saveChatList(Activity activity, List<Conversation> list) {

        // GsonBuilder gsonb = new GsonBuilder();
        try {
            UtilityFunctions.deleteFile("CalEvents");
        } catch (Exception e) {

        }
        FileOutputStream fos = null;
        try {

            fos = activity.openFileOutput("CalEvents", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList<Conversation> getChatList(Activity activity) {

        FileInputStream fis;
        ArrayList<Conversation> returnlist = null;
        try {
            fis = activity.openFileInput("CalEvents");
            ObjectInputStream ois = new ObjectInputStream(fis);
            returnlist = (ArrayList<Conversation>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnlist;

    }

    public static void openChat(final Activity activity, String contactNumber, String contactName, String contactAvatar, String isRegistered, int tab) {
        ProfileDT profileDT = RealmDataRetrive.getProfile();
        MyLogs.printinfo("chat_tab_"+tab);

        if (isRegistered.equals("0")) {

            loaderDialoge = new MaterialDialog.Builder(activity)
                    .title("Not a registered user on App")
                    .content("Would you like to sent Invitation to join App ?")
                    .contentGravity(GravityEnum.CENTER)
                    .positiveText("Yes")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            showToast(activity, "Invitation has been sent");
                        }
                    })
                    .widgetColor(activity.getResources().getColor(R.color.who_do_u_blue))
                    .backgroundColor(activity.getResources().getColor(R.color.white))
                    .titleColor(activity.getResources().getColor(R.color.black))
                    .contentColor(activity.getResources().getColor(R.color.who_do_u_medium_grey))
                    .show();
        } else {
            if (tab == 1) {

                activity.startActivity(ConversationActivity.createIntent(activity, profileDT.getUsername() + "_v", profileDT.getImage_url(), contactNumber + "_v", contactName, contactAvatar));

            } else {
              //.//  MyLogs.printinfo("chat_tab_0 ");
                activity.startActivity(ConversationActivity.createIntent(activity, profileDT.getUsername() + "_v", profileDT.getImage_url(), contactNumber + "_c", contactName, contactAvatar));

            }

        }


    }


    public static void saveChatNamesAvatar(Activity activity) {

        // GsonBuilder gsonb = new GsonBuilder();

        if (RealmDataRetrive.getHomeListOfMyProviderAndMyFriends().size() > 0) {
            List<FriendNames> list = new ArrayList<>();
            FriendNames item = new FriendNames();
            for (CustomersDT providerDT : RealmDataRetrive.getHomeListOfMyProviderAndMyFriends()) {
                item.setDisplayName(providerDT.getFirst_name() + " " + providerDT.getLast_name());
                item.setDisplayAvatar(providerDT.getImage_url());
                item.setUsername(providerDT.getUsername());
                list.add(item);
            }
            try {
                UtilityFunctions.deleteFile(UIHelper.FILE_NAME);
            } catch (Exception e) {

            }
            FileOutputStream fos = null;
            try {

                fos = activity.openFileOutput(UIHelper.FILE_NAME, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(list);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static String getCompleteAddress() {
        // MyLogs.printinfo("complete_address " + WebService.callCurlRequest("http://maps.googleapis.com/maps/api/geocode/json?address=" + 77379 + "&sensor=true").getJSONArray("results").getJSONObject(0).getString("formatted_address"));

        return "";
    }

    public static void returnIntent(Activity activity, String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", message);
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    public static boolean getActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //  String result=data.getStringExtra("result");
                return true;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                return false;
                //Write your code if there's no result
            }
        }
        return false;
    }

    public static ArrayList<Conversation> removeDuplicates(ArrayList<Conversation> l) {
        Set<Conversation> s = new TreeSet<Conversation>(new Comparator<Conversation>() {

            @Override
            public int compare(Conversation o1, Conversation o2) {
                if (o1.getJid().toBareJid().toString().equalsIgnoreCase(o2.getJid().toBareJid().toString()))
                    // ... compare the two object according to your requirements
                    return 0;
                else {
                    return 1;
                }
            }
        });
        s.addAll(l);
        final ArrayList<Conversation> newList = new ArrayList(s);
        MyLogs.printinfo("newList " + newList.size());
        return newList;
    }

    public static ArrayList<Conversation> removeNew(ArrayList<Conversation> list) {
        ArrayList<Conversation> arrayList = new ArrayList<>();
        HashSet<Conversation> hashSet = new HashSet<Conversation>();
        hashSet.addAll(list);
        arrayList.addAll(hashSet);
        return arrayList;
    }

}
