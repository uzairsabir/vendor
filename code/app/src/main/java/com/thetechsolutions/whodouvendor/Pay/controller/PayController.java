package com.thetechsolutions.whodouvendor.Pay.controller;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.WebserviceModel;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
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
    public void createPaypalId(Activity activity, String paypal) {
        new getSavePayPalIdToServer(paypal).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                UtilityFunctions.showToast_onCenter("Payment Request has been sent",activity);
                // PayMainActivity.
            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
            }


        }


    }
    private class getSavePayPalIdToServer extends AsyncTask<String, Void, Boolean> {

        String id="";
        getSavePayPalIdToServer(String _id) {
            id = _id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {


                return WebserviceModel.savePayPal(id);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {

            }

        }
    }
}
