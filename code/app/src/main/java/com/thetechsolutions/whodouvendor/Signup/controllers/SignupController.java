package com.thetechsolutions.whodouvendor.Signup.controllers;

import android.app.Activity;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.SyncFriendsController;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

/**
 * Created by Uzair on 7/12/2016.
 */
public class SignupController {

    public static int getIntroLayout(int position) {
        if (position == 0) {
            return R.layout.fragment_intro_one;
        } else if (position == 1) {
            return R.layout.fragment_intro_second;
        } else if (position == 2) {
            return R.layout.fragment_intro_third;
        } else {
            return R.layout.fragment_intro_fourth;
        }

    }

    public static boolean contactHandler(Activity activity) {
        String result = "";
        try {
            result = SyncFriendsController.excuteContactListFromPhoneAsyn(activity);
            if (!UtilityFunctions.isEmpty(result)) {
//                if (HomeModel.sendContactsToServer(result, activity)) {
//                    if (HomeModel.getGroupsListFromServer(activity, "0", ""))
//                        return true;
//                }
            }

            //return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
