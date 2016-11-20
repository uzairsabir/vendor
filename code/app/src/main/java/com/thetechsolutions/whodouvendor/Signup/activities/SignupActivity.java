package com.thetechsolutions.whodouvendor.Signup.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.andreabaccega.widget.FormEditText;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouvendor.Home.adapters.CategoryListAdapter;
import com.thetechsolutions.whodouvendor.HomeScreenActivity;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Signup.controllers.SignUpAsynController;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.PermissionHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.HashMap;
import java.util.Map;

import eu.siacs.conversation.ui.MagicCreateActivity;
import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyAdapter;


/**
 * Created by Uzair on 12/6/2015.
 */

public class SignupActivity extends MagicCreateActivity implements View.OnClickListener {
    private static final int TIME_DELAY = 2000;
    public static boolean isRegisterationScreen, isCodeInputScreen;
    private static long back_pressed;
    Activity activity;
    EditText _edit_phone;

    Dialog workDialog;

    RelativeLayout bottomLay;
    LinearLayout country_container;
    String inputPhoneNumber, fName, lName, email, code1, code2, code3, code4;
    int zipCode;
    TextView categorySelectedText, subcategorylist;
    Button signup_btn;
    int catId = 0, subCatId = 0;
    MaskedEditText country_number;
    TextView country_name, country_code, term_text;
    String finalNumber;

    MaterialDialog term_and_condition_dialoge;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SignupActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_number);
        activity = this;
        viewsInitialize();
        // addListeners();
        viewHandler();

        try {
            getActionBar().hide();
        } catch (Exception e) {

        }


    }

    private void viewHandler() {

        if (isRegisterationScreen) {
            callRegisterationDialog(activity);
        } else if (isCodeInputScreen) {
            callInputCodeDialog(activity);
        }

    }

    private void viewsInitialize() {

//        try {
//            BaseFlagFragment.initUI(activity);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            BaseFlagFragment.initCodes(activity);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        bottomLay = (RelativeLayout) findViewById(R.id.bottom_lay);
        bottomLay.setVisibility(View.GONE);
        country_container = (LinearLayout) findViewById(R.id.country_container);
        _edit_phone = (EditText) findViewById(R.id.phone);
        signup_btn = (Button) findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(this);

        try {
            country_name = (TextView) findViewById(R.id.country_name);
            country_code = (TextView) findViewById(R.id.country_code);
            term_text = (TextView) findViewById(R.id.term_text);

            //final android.app.FragmentTransaction fragmentTransaction= getFragmentManager().beginTransaction();

            term_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    term_and_condition_dialoge = new MaterialDialog.Builder(activity)
                            .title("Privacy Policy")
                            .content("This privacy policy governs your use of the software application Whodou (“Application”) for mobile\n" +
                                    "\n" +
                                    "devices that was created by Pingem, Inc.. The Application is a consumer oriented platform that allows the\n" +
                                    "\n" +
                                    "consumer to engage with existing providers via live chat, scheduling, payment and general coordination of\n" +
                                    "\n" +
                                    "the consumer/vendor relationship. Additionally, the Application allow consumers to add their friends in\n" +
                                    "\n" +
                                    "order to seamlessly share their go-to vendors.\n" +
                                    "\n" +
                                    "User Provided Information \n" +
                                    "\n" +
                                    "The Application obtains the information you provide when you download and register the Application. \n" +
                                    "\n" +
                                    "Registration with us is mandatory in order to be able to use the basic features of the Application.\n" +
                                    "\n" +
                                    "When you register with us and use the Application, you generally provide (a) your name, email address,\n" +
                                    "\n" +
                                    "age, user name, password and other registration information; (b) transaction-related information, such as\n" +
                                    "\n" +
                                    "when you make purchases, respond to any offers, or download or use applications from us; (c) information\n" +
                                    "\n" +
                                    "you provide us when you contact us for help; (d) credit card information for purchase and use of the\n" +
                                    "\n" +
                                    "Application, and; (e) information you enter into our system when using the Application, such as contact\n" +
                                    "\n" +
                                    "information and project management information.\n" +
                                    "\n" +
                                    "We may also use the information you provided us to contact your from time to time to provide you with\n" +
                                    "\n" +
                                    "important information, required notices and marketing promotions.")
                            .contentGravity(GravityEnum.CENTER)
                            .backgroundColor(activity.getResources().getColor(R.color.white))
                            .titleColor(activity.getResources().getColor(R.color.black))
                            .contentColor(activity.getResources().getColor(R.color.who_do_u_medium_grey))
                            .show();
                }
            });

            country_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CountryPicker picker = CountryPicker.newInstance("Select Country");
                    picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

                    picker.setListener(new CountryPickerListener() {
                        @Override
                        public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                            country_code.setText(dialCode);
                            country_name.setText(name);
                            picker.dismiss();

                        }
                    });
                }
            });

            try {
                country_number = (MaskedEditText) findViewById(R.id.country_number);


            } catch (Exception e) {

            }


        } catch (Exception e) {

        }

        UtilityFunctions.showKeyboard(activity);
        country_number.setFocusable(true);


    }

    @Override
    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            try {
                HomeScreenActivity.activity.finish();


            } catch (Exception e) {

            }

        } else {
            UtilityFunctions.showToast_onCenter("Press once again to exit", activity);

        }
        back_pressed = System.currentTimeMillis();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.signup_btn:

                //inputNumberValidator();
                if (country_number.getUnmaskedText().length() == 10) {
                    finalNumber = country_code.getText().toString() + country_number.getUnmaskedText();
                    AppPreferences.setString(AppPreferences.PREF_USER_COUNTRY_CODE, country_code.getText().toString().replace("+", ""));
                    inputNumberValidator(finalNumber);
                } else {
                    UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_format_number), activity);

                }


                break;


            default:
                break;
        }
    }

    public void callSignUpAsyn(String signUpType) {
        if (UtilityFunctions.checkInternet(activity)) {
            new SignUpAsync(activity, signUpType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void callRegisterationDialog(final Activity activity) {

        isRegisterationScreen = true;
        workDialog = new Dialog(activity);
        workDialog.setCancelable(false);
        workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        workDialog.setContentView(R.layout.dialoge_signup_registeration);

        final FormEditText first_Name = (FormEditText) workDialog.findViewById(R.id.first_name);
        final FormEditText last_Name = (FormEditText) workDialog.findViewById(R.id.last_name);
        final FormEditText email_address = (FormEditText) workDialog.findViewById(R.id.email_name);
        final FormEditText zip_Code = (FormEditText) workDialog.findViewById(R.id.zip_code);

        categorySelectedText = (TextView) workDialog.findViewById(R.id.categorylist);
        subcategorylist = (TextView) workDialog.findViewById(R.id.subcategorylist);


        categorySelectedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zip_Code.clearFocus();
                first_Name.clearFocus();
                last_Name.clearFocus();
                zip_Code.clearFocus();
                callCategoryListDialogue(false);
                UtilityFunctions.hideKeyboard(activity, zip_Code);

            }
        });

        subcategorylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zip_Code.clearFocus();
                first_Name.clearFocus();
                last_Name.clearFocus();
                zip_Code.clearFocus();
                callCategoryListDialogue(true);
                UtilityFunctions.hideKeyboard(activity, zip_Code);
            }
        });

//        categorySelectedText = (TextView) workDialog.findViewById(R.id.categorylist);
//        subcategorylist = (TextView) workDialog.findViewById(R.id.subcategorylist);


        Window window = workDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 300);


        workDialog.show();


        Button declineButton = (Button) workDialog.findViewById(R.id.cancel_btn);

        final Button acceptButton = (Button) workDialog.findViewById(R.id.signup_btn);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fName = first_Name.getText().toString();
                    lName = last_Name.getText().toString();
                    email = email_address.getText().toString();
                    zipCode = Integer.parseInt(zip_Code.getText().toString());
                } catch (Exception e) {

                }


                validatorReg(first_Name, email_address, zip_Code);
                isRegisterationScreen = false;


            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDialog.dismiss();
                isRegisterationScreen = false;
            }
        });

        if (AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_NOT_ON_APP)) {
            ProfileDT profileDTs = RealmDataRetrive.getProfile();
            if (profileDTs != null) {
                first_Name.setText(profileDTs.getFirst_name());
                last_Name.setText(profileDTs.getLast_name());
                email_address.setText(profileDTs.getEmail_1());
                try {
                    zip_Code.setText(profileDTs.getZip_code());
                } catch (Exception e) {

                }
                catId = profileDTs.getCategory_id();
                subCatId = profileDTs.getSub_category_id();
                categorySelectedText.setText(profileDTs.getCategory_title());
                subcategorylist.setText(profileDTs.getSub_category_title());


            }

        }

        email_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    workDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });


    }

    public void callInputCodeDialog(final Activity activity) {

        workDialog = new Dialog(activity);
        isCodeInputScreen = true;
        workDialog.setCancelable(false);

        workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        workDialog.setContentView(R.layout.dialoge_signup_input_code);


        final FormEditText code_1 = (FormEditText) workDialog.findViewById(R.id.code_one);
        final FormEditText code_2 = (FormEditText) workDialog.findViewById(R.id.code_two);
        final FormEditText code_3 = (FormEditText) workDialog.findViewById(R.id.code_three);
        final FormEditText code_4 = (FormEditText) workDialog.findViewById(R.id.code_four);

        code_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code_3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code_4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    UtilityFunctions.hideKeyboard(activity);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        workDialog.show();
        Window window = workDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button declineButton = (Button) workDialog.findViewById(R.id.cancel_btn);

        final Button acceptButton = (Button) workDialog.findViewById(R.id.signup_btn);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatorCode(code_1, code_2, code_3, code_4);
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDialog.dismiss();
                isCodeInputScreen = false;
            }
        });

        code_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    workDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

    }

    public class SignUpAsync extends AsyncTask<String, Void, Integer> {

        Activity _activity;
        String signup_type;

        public SignUpAsync(Activity activity, String _signup_type) {
            _activity = activity;
            signup_type = _signup_type;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);
            try {
                workDialog.dismiss();
            } catch (Exception e) {

            }

        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_NUMBER_VERIFICATION)) {

                    if (SignUpAsynController.getInstance().callUserExistence(activity, inputPhoneNumber)) {
                        return 0;
                    }


                } else if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_REGISTRATION)) {

                    AppPreferences.setInt(AppPreferences.PREF_VENDOR_SUB_CATEGORY_ID, subCatId);
                    AppPreferences.setInt(AppPreferences.PREF_VENDOR_CATEGORY_ID, catId);

                    if (SignUpAsynController.getInstance().callRegisteration(email, fName, lName, zipCode, "" + subCatId))
                        return 1;

                } else if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_CODE_VERIFICATION)) {
                    if (SignUpAsynController.getInstance().callCodeVerification(activity, code1 + code2 + code3 + code4))
                        return 2;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            AppController.hideDialoge();
            try {
                workDialog.dismiss();
            } catch (Exception e) {

            }

            if (!NetworkManager.isConnected(activity)) {
                AppController.showToast(activity, "No Internet Connectivity");
            } else {

                if (result == 0) {

                    if (AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_NEW) ||
                            AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_NOT_ON_APP)) {


                        callRegisterationDialog(activity);
                    } else if (AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_REGISTERED)) {
                        callInputCodeDialog(activity);
                    }

                } else if (result == 1) {

                    callInputCodeDialog(activity);

                } else if (result == 2) {
                    MyLogs.printinfo("verified " + RealmDataRetrive.getProfile().getUsername() + "_v");

                    try {
                        createXmppConnection(RealmDataRetrive.getProfile().getUsername() + "_v", "1234");
                    } catch (Exception e) {

                    }


                    if (PermissionHandler.allPermissionsHandler(activity)) {

                        AppPreferences.setBoolean(AppPreferences.PREF_REGISTERATION_DONE, true);
                        AppPreferences.setBoolean(AppPreferences.PREF_IS_SIGNUP_DONE, true);
                        ListenerController.openInitProgressActivity(activity);
                        activity.finish();

                    }
                } else {

                    if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_NUMBER_VERIFICATION) || signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_REGISTRATION)) {
                        AppController.showToast(activity, "Something went wrong!");

                    } else if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_CODE_VERIFICATION)) {
                        AppController.showToast(activity, "Please enter correct code");
                        callInputCodeDialog(activity);

                    }

                }

            }
        }


    }


    private void inputNumberValidator(String completeNumber) {
        MyLogs.printinfo("country_code " + AppPreferences.getString(AppPreferences.PREF_USER_COUNTRY_CODE));
        inputPhoneNumber = completeNumber;


        if (UtilityFunctions.isEmpty(inputPhoneNumber)) {
            UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_filled), activity);
            return;
        }
//        if (!UtilityFunctions.isContainSpaces(inputPhoneNumber)) {
//            UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_format_number), activity);
//            return;
//        }
        inputPhoneNumber = UtilityFunctions.getstandarizeNumber(inputPhoneNumber, activity);
        new SignUpAsync(activity, AppConstants.SIGNUP_FUNCTION_TYPE_NUMBER_VERIFICATION).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionHandler.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);


                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

                    try {

                        AppPreferences.setBoolean(AppPreferences.PREF_REGISTERATION_DONE, true);
                        AppPreferences.setBoolean(AppPreferences.PREF_IS_SIGNUP_DONE, true);
                        ListenerController.openInitProgressActivity(activity);
                        activity.finish();

                    } catch (Exception e) {


                    }

                } else {

                    UtilityFunctions.showToast_onCenter("Some Permission is Denied", activity);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void validatorReg(FormEditText firstName, FormEditText email, FormEditText zip_code) {
        FormEditText[] allFields;

        allFields = new FormEditText[]{firstName, email, zip_code};


        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {

            if (catId == 0) {
                AppController.showToast(activity, "Please select a category");
            } else if (subCatId == 0) {
                AppController.showToast(activity, "Please select a sub category");
            } else {
                callSignUpAsyn(AppConstants.SIGNUP_FUNCTION_TYPE_REGISTRATION);
            }


        }

    }

    private void validatorCode(FormEditText _code1, FormEditText _code2, FormEditText _code3, FormEditText _code4) {
        FormEditText[] allFields = {_code1, _code2, _code3, _code4};
        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        code1 = _code1.getText().toString();
        code2 = _code2.getText().toString();
        code3 = _code3.getText().toString();
        code4 = _code4.getText().toString();

        if (allValid) {

            callSignUpAsyn(AppConstants.SIGNUP_FUNCTION_TYPE_CODE_VERIFICATION);
        } else {

        }


    }

    public void callCategoryListDialogue(final boolean isSubCategory) {
        final Dialog categoryDialoge = new Dialog(this);
        categoryDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialoge.setContentView(R.layout.dialoge_listview);
        categoryDialoge.show();
        Window window = categoryDialoge.getWindow();
        window.setLayout(AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) - 100, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 500);
        DynamicListView listView = (DynamicListView) categoryDialoge.findViewById(R.id.listview);
        RelativeLayout loadingContainer = (RelativeLayout) categoryDialoge.findViewById(R.id.loading_container);
        TextView title = (TextView) categoryDialoge.findViewById(R.id.title_cat);

        loadingContainer.setVisibility(View.GONE);
        EasyAdapter easyAdapter;

        final RealmResults<VendorCategoryDT> subcatList = RealmDataRetrive.getSubCategoryList(catId);
        final RealmResults<VendorCategoryDT> catList = RealmDataRetrive.getCategoryList(0);
        if (isSubCategory) {
            title.setText("Select a sub category");
            easyAdapter = new EasyAdapter<>(
                    activity,
                    CategoryListAdapter.newInstance(isSubCategory),
                    subcatList);
        } else {
            title.setText("Select a category");

            easyAdapter = new EasyAdapter<>(
                    activity,
                    CategoryListAdapter.newInstance(isSubCategory),
                    catList);
        }


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                categoryDialoge.dismiss();

                if (isSubCategory) {

                    subcategorylist.setText(subcatList.get(position).getSubcategory());
                    subCatId = (subcatList.get(position).getSubcategory_id());

                    if (catId == 0) {
                        catId = (RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory_id());
                        categorySelectedText.setText(RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory());
                    }
                } else {
                    catId = (catList.get(position).getCategory_id());
                    categorySelectedText.setText(catList.get(position).getCategory());

                    subcategorylist.setText("");
                    subCatId = 0;
                    callCategoryListDialogue(true);

                }


            }
        });


    }


}
