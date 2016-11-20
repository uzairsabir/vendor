package com.thetechsolutions.whodouvendor.AppHelpers.Controllers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.AppHelpers.BottomListAdapter;
import com.thetechsolutions.whodouvendor.AppHelpers.BottomListItemType;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatFromMainActivity;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatMainActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeCreateNewContactActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeFriendProfileActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainSearchActivity;
import com.thetechsolutions.whodouvendor.HomeScreenActivity;
import com.thetechsolutions.whodouvendor.Pay.activities.PayDetailActivity;
import com.thetechsolutions.whodouvendor.Pay.activities.PayMainActivity;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.activities.ScheduleDetailActivity;
import com.thetechsolutions.whodouvendor.Schedule.activities.ScheduleMainActivity;
import com.thetechsolutions.whodouvendor.Settings.activities.SettingsMainActivity;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.customviews.HorizontialListViewSecond;

import java.util.ArrayList;

/**
 * Created by Uzair on 7/16/2016.
 */
public class BottomMenuController {

    static int bottomOptionItemWidth;
    public static HorizontialListViewSecond bottomListView;
    static BottomListAdapter bottomListAdapter;
    static ArrayList<BottomListItemType> bottomOptions = new ArrayList<>();
    static Activity activity;
    static final int BOTTOM_OPTION_0 = 0;
    static final int BOTTOM_OPTION_1 = 1;
    static final int BOTTOM_OPTION_2 = 2;
    static final int BOTTOM_OPTION_3 = 3;
    static final int BOTTOM_OPTION_4 = 4;
    private ViewGroup mLinearLayout;


    private static BottomMenuController ourInstance = new BottomMenuController(activity);

    public static BottomMenuController getInstance(Activity _activity) {

        activity = _activity;
        return ourInstance;
    }

    private BottomMenuController(Activity activity) {


    }

    public void setBottomMenu(Activity activity) {

        if (activity instanceof HomeMainActivity ||
                activity instanceof ContactsMainActivity ||
                activity instanceof HomeCreateNewContactActivity ||
                activity instanceof HomeFriendProfileActivity ||
                activity instanceof HomeMainSearchActivity
                ) {

            createMenu(activity, BOTTOM_OPTION_0);

        } else if (activity instanceof ChatMainActivity || activity instanceof ChatFromMainActivity) {

            createMenu(activity, BOTTOM_OPTION_1);

        } else if (activity instanceof ScheduleMainActivity || activity instanceof ScheduleDetailActivity) {

            createMenu(activity, BOTTOM_OPTION_2);

        } else if (activity instanceof PayMainActivity || activity instanceof PayDetailActivity) {

            createMenu(activity, BOTTOM_OPTION_3);

        } else if (activity instanceof SettingsMainActivity) {

            createMenu(activity, BOTTOM_OPTION_4);

        }
    }

    private void createMenu(Activity activity, int id) {


        try {
            mLinearLayout = (ViewGroup) activity.findViewById(R.id.bottom_lay);
            View layout2 = LayoutInflater.from(activity).inflate(R.layout.fragment_horizontal_listview, mLinearLayout, false);


            bottomListView = (HorizontialListViewSecond) layout2.findViewById(R.id.images_horizontal_listview);
            bottomOptionItemWidth = AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) / 5;
            bottomOptions = getBottomOptions();

            bottomListAdapter = new BottomListAdapter
                    (activity, R.layout.item_home_bottom_listview, bottomOptions, bottomOptionItemWidth, id);
            bottomListView.setAdapter(bottomListAdapter);
            bottomListAdapter.notifyDataSetChanged();
            bottomListView.invalidate();

            mLinearLayout.addView(layout2);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private ArrayList<BottomListItemType> getBottomOptions() {

        ArrayList<BottomListItemType> newList = new ArrayList<>();
        BottomListItemType Home = new BottomListItemType("Home", R.drawable.bg_vendors_hover);
        BottomListItemType chat = new BottomListItemType("Chat", R.drawable.bg_friend_hover);
        BottomListItemType Schedule = new BottomListItemType("Schedule", R.drawable.bg_calendar_hover);
        BottomListItemType Pay = new BottomListItemType("Pay", R.drawable.bg_payments_hover);
        BottomListItemType Setting = new BottomListItemType("Settings", R.drawable.bg_account_hover);

        newList.add(Home);
        newList.add(chat);
        newList.add(Schedule);
        newList.add(Pay);
        newList.add(Setting);

        return newList;
    }

    public static void bottomOptionClick(int option1, Activity activity) {

        switch (option1) {
            case BOTTOM_OPTION_0:
                if (!(activity instanceof HomeMainActivity) &&
                        !(activity instanceof ContactsMainActivity) &&
                        !(activity instanceof HomeCreateNewContactActivity) &&
                        !(activity instanceof HomeFriendProfileActivity) &&
                        !(activity instanceof HomeMainSearchActivity)
                        ) {
                    ListenerController.openHomeActivity(activity, 0);
                    if (!(activity instanceof HomeScreenActivity)) {
                        ListenerController.closeActivity(activity);
                    }
                }
                break;
            case BOTTOM_OPTION_1:

                if (!(activity instanceof ChatMainActivity) &&
                        !(activity instanceof ChatFromMainActivity)) {
                    ListenerController.openChatActivity(activity);
                    if (!(activity instanceof HomeScreenActivity)) {
                        ListenerController.closeActivity(activity);
                    }
                }


                break;
            case BOTTOM_OPTION_2:

                if (!(activity instanceof ScheduleMainActivity) && !(activity instanceof ScheduleDetailActivity)) {
                    ListenerController.openScheduleActivity(activity);
                    if (!(activity instanceof HomeScreenActivity)) {
                        ListenerController.closeActivity(activity);
                    }
                }

                break;

            case BOTTOM_OPTION_3:

                if (!(activity instanceof PayMainActivity) && !(activity instanceof PayDetailActivity)) {

                    ListenerController.openPaymentActivity(activity);
                    if (!(activity instanceof HomeScreenActivity)) {
                        ListenerController.closeActivity(activity);
                    }
                }

                break;

            case BOTTOM_OPTION_4:
                if (!(activity instanceof SettingsMainActivity)) {

                    ListenerController.openSettingActivity(activity);
                    if (!(activity instanceof HomeScreenActivity)) {
                        ListenerController.closeActivity(activity);
                    }
                }


                break;

            default:
                break;
        }
    }


}
