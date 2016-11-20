package com.thetechsolutions.whodouvendor.Home.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorProfileDT;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouvendor.Home.adapters.CategoryListAdapter;
import com.thetechsolutions.whodouvendor.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.PermissionHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.File;

import io.realm.RealmResults;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeCreateNewContactActivity extends FragmentActivityController implements MethodGenerator {

    public static Activity activity;
    SimpleDraweeView fresco_view;

    public static int tab_pos, id;
    static String provider_name;

    FormEditText first_name, last_name, cell_no,
            city_state, zip_codes, category, sub_category, email;

    String text_first_name, text_last_name,
            text_city_state, text_zip_codes, text_category, text_sub_category, text_email, provider_user_name;
    EditText edit_phone;

    TextView cat_text, sub_cat_text, country_code, cat_line, sub_cat_line;

    int catId = 0, subCatId = 0, provider_id = 0;

    VendorProfileDT profileDT;
    LinearLayout country_container;
    String imageUrl = "";
    MaskedEditText country_number;

    public static Intent createIntent(Activity _activity, int _tab_pos, String _provider_name) {
        activity = _activity;
        tab_pos = _tab_pos;
        provider_name = _provider_name;
        return new Intent(activity, HomeCreateNewContactActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, "Create New Contact", true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

        MyLogs.printinfo("tab_pos  " + tab_pos);

    }

    @Override
    public void viewInitialize() {
        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);
        first_name = (FormEditText) findViewById(R.id.first_name);
        last_name = (FormEditText) findViewById(R.id.last_name);
        cell_no = (FormEditText) findViewById(R.id.cell_no);
        email = (FormEditText) findViewById(R.id.email);
        city_state = (FormEditText) findViewById(R.id.city_state);
        zip_codes = (FormEditText) findViewById(R.id.zip_codes);
        category = (FormEditText) findViewById(R.id.category);
        sub_category = (FormEditText) findViewById(R.id.sub_category);
        country_container = (LinearLayout) findViewById(R.id.country_container);
        edit_phone = (EditText) findViewById(R.id.phone);

        country_number = (MaskedEditText) findViewById(R.id.country_number);
        country_code = (TextView) findViewById(R.id.country_code);

        cat_text = (TextView) findViewById(R.id.cat_text);
        sub_cat_text = (TextView) findViewById(R.id.sub_cat_text);

        cat_line = (TextView) findViewById(R.id.cat_line);
        sub_cat_line = (TextView) findViewById(R.id.sub_cat_line);


        if (tab_pos == 0) {
            sub_category.setVisibility(View.GONE);
            category.setVisibility(View.GONE);
            cat_text.setVisibility(View.GONE);
            sub_cat_text.setVisibility(View.GONE);
            cat_line.setVisibility(View.GONE);
            sub_cat_line.setVisibility(View.GONE);

        }
        country_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        country_code.setText(dialCode);
                        //country_name.setText(name);
                        picker.dismiss();

                    }
                });
            }
        });
        // descripton_service = (FormEditText) findViewById(R.id.descripton_service);
        // website = (FormEditText) findViewById(R.id.website);
    }

    @Override
    public void viewUpdate() {
        fresco_view.setImageURI("test.jpg");

        fresco_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_NEW)) {

                    if (PermissionHandler.isStoragePermissionGranted(activity)) {

                        EasyImage.openChooserWithGallery(activity, "Profile Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                    }
                } else if (profileDT.getCreated_by() != null) {

                    if (profileDT.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                        if (PermissionHandler.isStoragePermissionGranted(activity)) {

                            EasyImage.openChooserWithGallery(activity, "Profile Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                        }
                    }
                }

            }
        });
        if (!UtilityFunctions.isEmpty(provider_name))
            setProfile();
        else {
            cell_no.setVisibility(View.GONE);
            country_container.setVisibility(View.VISIBLE);
//            try {
//                BaseFlagFragment.initUI(activity);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                BaseFlagFragment.initCodes(activity);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }
        setListner();


    }

    private void setProfile() {

        profileDT = RealmDataRetrive.getVendorProfileTemp(provider_name, ContactsMainActivity.tab_pos);
        if (profileDT != null) {
            if (!profileDT.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {

                setDiableState();

            }
            try {
                first_name.setText(profileDT.getFirst_name());
                last_name.setText(profileDT.getLast_name());
            } catch (Exception e) {

            }

            cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, profileDT.getMobile_number_1()));

            try {

                zip_codes.setText("" + profileDT.getZip_code());
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
                city_state.setText(profileDT.getCity() + " " + profileDT.getState());
            } catch (Exception e) {

            }

            try {
                category.setText(profileDT.getCategory_title());
            } catch (Exception e) {

            }
            try {
                sub_category.setText(profileDT.getSub_category_title());
            } catch (Exception e) {

            }
            try {
                fresco_view.setImageURI("" + profileDT.getImage_url());
            } catch (Exception e) {

            }
            try {
                subCatId = profileDT.getSub_category_id();
            } catch (Exception e) {

            }
            try {
                email.setText(profileDT.getEmail_1());
            } catch (Exception e) {

            }

            try {
                provider_id = profileDT.getId();

            } catch (Exception e) {

            }

            try {
                provider_user_name = profileDT.getUsername();
            } catch (Exception e) {

            }


        } else {
            ContactDT contactDT = RealmDataRetrive.getContactDetail(provider_name);

            try {
                first_name.setText(contactDT.getFirstname());
                last_name.setText(contactDT.getLastname());
            } catch (Exception e) {

            }
            cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, provider_name));
        }
    }

    private void setDiableState() {

        email.setEnabled(false);
        city_state.setEnabled(false);
        zip_codes.setEnabled(false);
        category.setEnabled(false);
        sub_category.setEnabled(false);


        email.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        city_state.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        zip_codes.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        category.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        sub_category.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));

    }

    private void setListner() {
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryListDialogue(activity, false, category, sub_category);
            }
        });

        sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryListDialogue(activity, true, category, sub_category);
            }
        });

    }

    public void callCategoryListDialogue(Activity activity, final boolean isSubCategory,
                                         final FormEditText cat_view, final FormEditText sub_cat_view) {


        final Dialog categoryDialoge = new Dialog(activity);
        categoryDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialoge.setContentView(R.layout.dialoge_listview);
        categoryDialoge.show();
        Window window = categoryDialoge.getWindow();
        window.setLayout(AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) - 100, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 1000);
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

                    sub_cat_view.setText(subcatList.get(position).getSubcategory());
                    subCatId = (subcatList.get(position).getSubcategory_id());

                    if (catId == 0) {
                        catId = (RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory_id());
                        cat_view.setText(RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory());
                    }
                } else {
                    catId = (catList.get(position).getCategory_id());
                    cat_view.setText(catList.get(position).getCategory());

                    sub_cat_view.setText("");
                    subCatId = 0;
                }


            }
        });


    }

    public void validatorCode() {
        if (UtilityFunctions.isEmpty(provider_name)) {
            if (country_number.getUnmaskedText().length() == 10) {
                // String finalNumber = UtilityFunctions.getstandarizeNumber(country_code.getText().toString() + country_number.getUnmaskedText(),activity);

                String finalNumber = country_code.getText().toString() + country_number.getUnmaskedText();
                cell_no.setText(finalNumber);

            } else {
                UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_format_number), activity);

            }

        }
        boolean allValid = true;
        if (tab_pos == 0) {

        } else {
            FormEditText[] allFields = {first_name, cell_no};

            for (FormEditText field : allFields) {
                allValid = field.testValidity() && allValid;
            }
        }


        if (allValid) {

            if (subCatId == 0 && tab_pos == 1) {
                AppController.showToast(activity, "Sub Category is empty");

            } else {
                text_first_name = first_name.getText().toString();
                text_last_name = last_name.getText().toString();
                text_zip_codes = zip_codes.getText().toString();
                text_category = category.getText().toString();
                text_sub_category = sub_category.getText().toString();
                text_email = email.getText().toString();
                text_city_state = city_state.getText().toString();

                if (UtilityFunctions.isEmpty(provider_name)) {

                    //  provider_name = UtilityFunctions.getstandarizeNumber(edit_phone.getText().toString(), activity);
                    //  new createVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    if (country_number.getUnmaskedText().length() == 10) {
                        String finalNumber = UtilityFunctions.getstandarizeNumber(country_code.getText().toString() + country_number.getUnmaskedText(), activity);
                        cell_no.setText(finalNumber);
                        provider_name = finalNumber;
                    } else {
                        UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_format_number), activity);

                        return;
                    }


                    // provider_name = UtilityFunctions.getstandarizeNumber(edit_phone.getText().toString(), activity);
                    new createVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {


                    if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_NEW)) {

                        new createVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_REGISTERED)) {

                        new createLink().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_NOT_ON_APP)) {

                        try {
                            if (profileDT.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {

                                new updateVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            } else {

                                new createLink().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            }
                        } catch (Exception e) {

                        }

                    }
                }
            }


        }


    }


    public class createVendor extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                HomeMainController.createProvider(provider_name, text_first_name, text_last_name, "", text_city_state, "", "", text_email,
                        text_zip_codes, subCatId + "", AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED), imageUrl


                );

                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {

//                MyLogs.printinfo("success");

                onSuccessHandling();

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    public class createLink extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                HomeMainController.createLink("" + provider_id, text_first_name, text_last_name, provider_user_name


                );

                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {

                onSuccessHandling();

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    public class updateVendor extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                HomeMainController.updateProvider(provider_name, text_first_name, text_last_name, "", text_city_state, "", "", text_email,
                        text_zip_codes, subCatId + "", tab_pos, imageUrl);


                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {
                onSuccessHandling();

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    private void onSuccessHandling() {
        try {
            AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, tab_pos, true);
        } catch (Exception e) {

        }


//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", "ok");
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                try {
                    fresco_view.setImageURI(Uri.fromFile(new File(imageFile.getPath())));
                } catch (Exception e) {

                }

                imageUrl = imageFile.getAbsolutePath();
                //onPhotoReturned(imageFile);
            }
        });
    }


}
