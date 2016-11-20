package com.thetechsolutions.whodouvendor.Signup.controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import de.measite.minidns.record.A;
import eu.siacs.conversation.ui.ChatPreferences;

/**
 * Created by Uzair on 8/14/2016.
 */
public class SignUpAsynController {
    private static SignUpAsynController ourInstance = new SignUpAsynController();

    public static SignUpAsynController getInstance() {
        return ourInstance;
    }

    private SignUpAsynController() {
    }

    public boolean callUserExistence(Activity activity,String username) {
        try {
            return new callUserExistence(activity,username).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean callRegisteration(String _email, String _firstName, String _lastName, int _zipCode,String subcategory_id) {
        try {
            return new callSignUp(_email, _firstName, _lastName, _zipCode,subcategory_id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean callCodeVerification(Activity activity,String _code) {
        try {
            return new callCodeVerification(activity,_code).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


    private class callUserExistence extends AsyncTask<String, Void, Boolean> {


        String userName = "";
        Activity activity;

        public callUserExistence(Activity _activity,String username) {
            this.userName = username;
            activity=_activity;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                return SignUpModel.getInstance().checkUserExistence(userName);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            try{
                ChatPreferences a=new ChatPreferences(activity);
                a.setString(ChatPreferences.USER_NAME,userName+"_v");
            }catch (Exception e){

            }

            try{
                new callVendorCategories().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }catch (Exception e){

            }


        }
    }


    private class callSignUp extends AsyncTask<String, Void, Boolean> {

        String email, firstName, lastName,subcategory_id;
        int zipCode;


        public callSignUp(String _email, String _firstName, String _lastName, int _zipCode,String subcategory_id) {
            this.email = _email;
            this.firstName = _firstName;
            this.lastName = _lastName;
            this.zipCode = _zipCode;
            this.subcategory_id=subcategory_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                return SignUpModel.getInstance().signUp(this.email, this.firstName, this.lastName, "" + this.zipCode,this.subcategory_id);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);


        }
    }

    private class callCodeVerification extends AsyncTask<String, Void, Boolean> {

        String code;
        Activity activity;


        public callCodeVerification(Activity _activity, String _code) {
            this.code = _code;
            activity=_activity;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                return SignUpModel.getInstance().codeVerification(activity,code);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);



        }
    }

    private class callVendorCategories extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                return SignUpModel.getInstance().vendorCategories();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);




        }
    }

}
