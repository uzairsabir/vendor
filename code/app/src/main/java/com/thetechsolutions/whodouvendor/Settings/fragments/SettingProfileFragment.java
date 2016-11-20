package com.thetechsolutions.whodouvendor.Settings.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cocosw.bottomsheet.BottomSheet;
import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.PermissionHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;

/**
 * Created by Uzair on 12/23/2015.
 */
public class SettingProfileFragment extends Fragment {


    static Activity activity;
    public static SettingProfileFragment fragment;


    public Dialog dialog;
    public static SimpleDraweeView fresco_view;
    public static EditText firstName, lastName, cellno, city_state, zip_codes;
    public static String imageUrl = "", sub_cat_id = "", email = "", address = "";


    public static Fragment newInstance(Activity _activity) {
        fragment = new SettingProfileFragment();
        activity = _activity;
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        } catch (Exception e) {
            e.printStackTrace();
        }


        ViewUpdate(rootView);
        return rootView;
    }

    private void ViewUpdate(final View rootView) {

        renderProfile(rootView);
    }


    public void renderProfile(View rootView) {

        firstName = (EditText) rootView.findViewById(R.id.first_name);
        lastName = (EditText) rootView.findViewById(R.id.last_name);
        city_state = (EditText) rootView.findViewById(R.id.city_state);
        zip_codes = (EditText) rootView.findViewById(R.id.zip_codes);
        cellno = (EditText) rootView.findViewById(R.id.cell_no);


        fresco_view = (SimpleDraweeView) rootView.findViewById(R.id.fresco_view);
        fresco_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PermissionHandler.isStoragePermissionGranted(activity)) {

                    EasyImage.openChooserWithGallery(activity, "Profile Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                }

                //openDialoge();

            }
        });


        new renderData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

    public class renderData extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {

                try {
                    ProfileDT profileDT = RealmDataRetrive.getProfile();

                    firstName.setText(profileDT.getFirst_name());
                    lastName.setText(profileDT.getLast_name());
                    try {

                        zip_codes.setText("" + profileDT.getZip_code());
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    try {
                        email = profileDT.getEmail_1();

                    } catch (Exception e) {

                    }
                    try {
                        address = profileDT.getAddress();
                    } catch (Exception e) {

                    }
                    try {
                        MyLogs.printinfo("sub_cat_id " + profileDT.getSub_category_id());
                        sub_cat_id = "" + profileDT.getSub_category_id();
                    } catch (Exception e) {

                    }

//                    PhoneFormat phoneFormat = new PhoneFormat(activity);
//
//                    String numberString;
//                    if (profileDT.getMobile_number_1().startsWith("00")) {
//                        numberString = profileDT.getMobile_number_1().substring(2);
//
//                    } else {
//                        numberString = profileDT.getMobile_number_1().substring(1);
//                    }
//
//
//                    String formattedString = phoneFormat.format("+" + numberString);
                    cellno.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, profileDT.getMobile_number_1()));

                    MyLogs.printinfo("image_url " + profileDT.getImage_url());
                    fresco_view.setImageURI("" + profileDT.getImage_url());

                    city_state.setText(profileDT.getCity());

                } catch (Exception e) {

                    e.printStackTrace();
                }

            } else {

            }


        }
    }


    public class updateProfile extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                // SettingController.updateProfile(activity, data, isImageSelected, mImageCurrentPath);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);
            if (result) {
                UtilityFunctions.showToast_onCenter("profile has been updated", activity);
            } else {
                UtilityFunctions.showToast_onCenter("Something went wrong", activity);
            }


        }
    }

    private void openDialoge() {
        new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog).title("How would you like to add ?")
                .sheet(R.menu.sheet_media_menu).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.gallery:
                        EasyImage.openGallery(fragment, EasyImage.REQ_PICK_PICTURE_FROM_GALLERY);
                        break;
                    case R.id.camera:

                        EasyImage.openCamera(fragment, EasyImageConfig.REQ_TAKE_PICTURE);
                        break;
                    case R.id.cancel:

                        dialog.dismiss();
                        break;
                }
            }
        }).show();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
//            @Override
//            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//                //Some error handling
//            }
//
//            @Override
//            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
//                //Handle the image
//                MyLogs.printinfo("imageFile " + imageFile);
//                fresco_view.setImageURI(Uri.fromFile(imageFile));
//            }
//        });
//    }

}
