package com.thetechsolutions.whodouvendor.Pay.controller;

import com.thetechsolutions.whodouvendor.R;

/**
 * Created by Uzair on 7/16/2016.
 */
public class PayController {

    public static int getIntroLayout(int position) {
        if (position == 0) {
            return R.layout.fragment_listview_progress_activity;
        } else if (position == 1) {
            return R.layout.fragment_listview_progress_activity;
        } else if (position == 2) {
            return R.layout.fragment_payment_setup;
        }
        return 0;


    }
}
