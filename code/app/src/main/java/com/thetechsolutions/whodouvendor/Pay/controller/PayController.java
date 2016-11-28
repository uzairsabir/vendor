package com.thetechsolutions.whodouvendor.Pay.controller;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.WebserviceModel;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

/**
 * Created by Uzair on 7/16/2016.
 */
public class PayController {

    private static PayController ourInstance = new PayController();


    public static PayController getInstance() {
        return ourInstance;
    }

    private PayController() {
    }
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
    public void createPayment(Activity activity, String vendor_id,
                              String payment_amount,
                              String payment_description,
                              String service_date,
                              String payment_status,
                              String request_recipet) {
        new createPayment(activity, vendor_id, payment_amount, payment_description, service_date, payment_status, request_recipet).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }





    private class createPayment extends AsyncTask<String, Void, Integer> {


        Activity activity;
        String vendor_id, payment_amount, payment_description, service_date, payment_status, request_recipet;

        public createPayment(Activity _activity, String _vendor_id,
                             String _payment_amount,
                             String _payment_description,
                             String _service_date,
                             String _payment_status,
                             String _request_recipet) {
            activity = _activity;
            vendor_id = _vendor_id;
            payment_amount = _payment_amount;
            payment_description = _payment_description;
            service_date = _service_date;
            payment_status = _payment_status;
            request_recipet = _request_recipet;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                if (WebserviceModel.createPayment(vendor_id, payment_amount, payment_description, service_date, payment_status, request_recipet))

                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            AppController.hideDialoge();

            if (result == 0) {
                AppController.returnIntent(activity, "done");
                UtilityFunctions.showToast_onCenter("Payment has been sent.",activity);
                // PayMainActivity.
            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
            }


        }


    }
}
