package org.vanguardmatrix.engine.socialoauth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.initializer.engine.R;

import org.brickred.socialauth.Album;
import org.brickred.socialauth.Career;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Feed;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Uzair on 12/30/2015.
 */
public class SocialOauthController extends Activity {
    private static SocialAuthAdapter adapter;

    // Android Components
    private ListView listview;
    private AlertDialog dialog;
    private ProgressDialog mDialog;

    // Variables
    private String providerName;
    public static int pos;
    private String msg_value;
    static Activity activity;
    static int type_id;
    RelativeLayout no_data_layout;
    TextView problem_text1, problem_text2;
    ProgressBar loader;
    static SocialAuthAdapter.Provider socialMediaType;
    static String consumer_key, consumer_secret, callback_url;

    public static Intent createIntent(Activity _activity, SocialAuthAdapter.Provider _socialMediaType,
                                      String _consumer_key, String _consumer_secret, String _callBackUrl,
                                      int _type_id
    ) {
        activity = _activity;
        socialMediaType = _socialMediaType;
        type_id = _type_id;
        consumer_key = _consumer_key;
        consumer_secret = _consumer_secret;
        callback_url = _callBackUrl;
        return new Intent(activity, SocialOauthController.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.fbmanager_layout);
        loaderHandling();
        AsyncOnPreHandling();
        adapter = new SocialAuthAdapter(new ResponseListener());
        try {
            adapter.addCallBack(socialMediaType, callback_url);
            adapter.addConfig(socialMediaType, consumer_key,
                    consumer_secret, null);
            adapter.authorize(activity, socialMediaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (type_id == 0) {
//            adapter.addCallBack(SocialAuthAdapter.Provider.FACEBOOK, "http://localhost/SocialLoginTesting/gauth");
//            try {
//                adapter.addConfig(SocialAuthAdapter.Provider.FACEBOOK, "852499251508833",
//                        "7f17735ab611e422c7bac265db2f7920", null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else if (type_id == 1) {
//            adapter.addCallBack(SocialAuthAdapter.Provider.GOOGLEPLUS, "http://www.nusdtech.com");
//            try {
//                adapter.addConfig(SocialAuthAdapter.Provider.GOOGLEPLUS, "324611973215-rnfaj53j9p4l17hfpe813g3p2iogk71u.apps.googleusercontent.com",
//                        "hsDfgIwl0MDtexrxu2Lf0nNg", null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }


    }

    private void loaderHandling() {

        no_data_layout = (RelativeLayout) findViewById(R.id.no_data_lay);
        problem_text1 = (TextView) findViewById(R.id.problemtext);
       // problem_text2 = (TextView) findViewById(R.id.problemtext2);
        loader = (ProgressBar) findViewById(R.id.listsviewloader1);
    }

    private void AsyncOnPreHandling() {
        loader.setVisibility(View.VISIBLE);
        problem_text1.setVisibility(View.GONE);
        problem_text2.setVisibility(View.GONE);
    }

    private void AsyncOnPostHandling() {
        loader.setVisibility(View.INVISIBLE);
        problem_text1.setVisibility(View.VISIBLE);
        problem_text2.setVisibility(View.VISIBLE);
        problem_text2.setText(activity.getResources().getString(R.string.no_event_found));
    }

    private void onGetDataHandling() {
        no_data_layout.setVisibility(View.GONE);
    }


    private final class ResponseListener implements DialogListener {

        @Override
        public void onComplete(Bundle values) {

            Log.d("Custom-UI", "Successful");
            providerName = values.getString(SocialAuthAdapter.PROVIDER);
            Log.d("Custom-UI", "providername = " + providerName);

            UtilityFunctions.showToast_onCenter(providerName + " connected", activity);
            //Toast.makeText(CustomUI.this, providerName + " connected", Toast.LENGTH_SHORT).show();

            int res = getResources().getIdentifier(providerName + "_array", "array", getApplicationContext().getPackageName());

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Select Options");
            builder.setCancelable(true);
            builder.setIcon(android.R.drawable.ic_menu_more);

            mDialog = new ProgressDialog(activity);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setMessage("Loading...");

            if (type_id == 0) {
                getUserProfile();
            } else if (type_id == 1) {
                getContactList();
            }

//            builder.setSingleChoiceItems(new DialogAdapter(activity, R.layout.provider_options, getResources()
//                    .getStringArray(res)), 0, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int item) {
//
//                    Events(item, providerName);
//                    dialog.dismiss();
//                }
//            });
//            dialog = builder.create();
//            dialog.show();

        }

        @Override
        public void onError(SocialAuthError error) {
            Log.d("Custom-UI", "Error");
            error.printStackTrace();
        }

        @Override
        public void onCancel() {
            Log.d("Custom-UI", "Cancelled");
        }

        @Override
        public void onBack() {
            Log.d("Custom-UI", "Dialog Closed by pressing Back Key");

        }
    }

    public void Events(int position, final String provider) {

        switch (position) {
            case 0: // Code to print user profile details for all providers
            {
                mDialog.show();
                adapter.getUserProfileAsync(new ProfileDataListener());
                break;
            }

            case 1: {
                // Share Update : Facebook, Twitter, Linkedin, Yahoo,
                // MySpace,Yammer

                // Get Contacts for FourSquare, Google, Google Plus,
                // Flickr, Instagram

                // Dismiss Dialog for Runkeeper and SalesForce

                if (provider.equalsIgnoreCase("foursquare") || provider.equalsIgnoreCase("google")
                        || provider.equalsIgnoreCase("flickr") || provider.equalsIgnoreCase("googleplus")
                        || provider.equalsIgnoreCase("instagram")) {
                    mDialog.show();
                    adapter.getContactListAsync(new ContactDataListener());

                } else if (provider.equalsIgnoreCase("runkeeper") || provider.equalsIgnoreCase("salesforce")) {
                    dialog.dismiss();

                } else {

                    // Code to Post Message for all providers
                    final Dialog msgDialog = new Dialog(activity);
                    msgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    msgDialog.setContentView(R.layout.dialog);

                    TextView dialogTitle = (TextView) msgDialog.findViewById(R.id.dialogTitle);
                    dialogTitle.setText("Share Update");
                    final EditText edit = (EditText) msgDialog.findViewById(R.id.editTxt);
                    Button update = (Button) msgDialog.findViewById(R.id.update);

                    msgDialog.show();

                    update.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            msgDialog.dismiss();
                            adapter.updateStatus(edit.getText().toString(), new MessageListener(), false);
                        }
                    });
                }
                break;
            }

            case 2: {

                // Get Contacts : Facebook, Twitter, Linkedin, Yahoo,
                // MySpace,Yammer

                // Get Feeds : Google Plus, Instagram
                // Dismiss Dialog for FourSquare , Google, Flickr

                if (provider.equalsIgnoreCase("foursquare") || provider.equalsIgnoreCase("google")
                        || provider.equalsIgnoreCase("flickr")) {
                    // Close Dialog
                    dialog.dismiss();
                } else if (provider.equalsIgnoreCase("instagram") || provider.equalsIgnoreCase("googleplus")) {
                    mDialog.show();
                    adapter.getFeedsAsync(new FeedDataListener());
                } else {
                    // Get Contacts for Remaining Providers
                    mDialog.show();
                    adapter.getContactListAsync(new ContactDataListener());
                }
                break;
            }

            case 3: {
                // Get Feeds : For Facebook , Twitter, Linkedin
                // Get Albums : Google Plus
                // Dismiss Dialog: Rest

                if (provider.equalsIgnoreCase("facebook") || provider.equalsIgnoreCase("twitter")
                        || provider.equalsIgnoreCase("linkedin")) {
                    mDialog.show();
                    adapter.getFeedsAsync(new FeedDataListener());
                } else if (provider.equalsIgnoreCase("googleplus")) {
                    mDialog.show();
                    adapter.getAlbumsAsync(new AlbumDataListener());
                } else {
                    dialog.dismiss();
                }
                break;
            }

            case 4: {
                // Upload Image for Facebook and Twitter

                if (provider.equalsIgnoreCase("facebook") || provider.equalsIgnoreCase("twitter")) {

                    // Code to Post Message for all providers
                    final Dialog imgDialog = new Dialog(activity);
                    imgDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    imgDialog.setContentView(R.layout.dialog);
                    imgDialog.setCancelable(true);

                    TextView dialogTitle = (TextView) imgDialog.findViewById(R.id.dialogTitle);
                    dialogTitle.setText("Share Image");
                    final EditText edit = (EditText) imgDialog.findViewById(R.id.editTxt);
                    Button update = (Button) imgDialog.findViewById(R.id.update);
                    update.setVisibility(View.INVISIBLE);
                    Button getImage = (Button) imgDialog.findViewById(R.id.loadImage);
                    getImage.setVisibility(View.VISIBLE);
                    imgDialog.show();

                    getImage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (isEmpty(edit)) {
                                UtilityFunctions.showToast_onCenter("Please fill message", activity);
                                // Toast.makeText(CustomUI.this, "Please fill message", Toast.LENGTH_SHORT).show();
                            } else {
                                imgDialog.dismiss();
                                msg_value = edit.getText().toString();
                                // Taking image from phone gallery
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                //startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                            }
                        }
                    });

                } else if (provider.equalsIgnoreCase("linkedin")) {

                    // get Job and Education information
                    mDialog.show();
                    adapter.getCareerAsync(new CareerListener());

                } else {
                    dialog.dismiss();
                }
                break;
            }

            case 5: {
                // Get Albums for Facebook and Twitter

                if (provider.equalsIgnoreCase("facebook") || provider.equalsIgnoreCase("twitter")) {
                    mDialog.show();
                    adapter.getAlbumsAsync(new AlbumDataListener());
                } else {
                    dialog.dismiss();
                }
                break;
            }

            case 6: {
                // For share text with link preview
                if (provider.equalsIgnoreCase("facebook")) {
                    try {
                        adapter.updateStory(
                                "Hello SocialAuth Android" + System.currentTimeMillis(),
                                "Google SDK for Android",
                                "Build great social apps and get more installs.",
                                "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.",
                                "https://www.facebook.com", "http://carbonfreepress.gr/images/facebook.png",
                                new MessageListener());
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    dialog.dismiss();
                }
                break;
            }

            case 7: {
                dialog.dismiss();
                break;
            }

        }

    }

    // To receive the profile response after authentication
    private final class ProfileDataListener implements SocialAuthListener<Profile> {

        @Override
        public void onExecute(String provider, Profile t) {

            Log.d("Custom-UI", "Receiving Data");
            mDialog.dismiss();
            Profile profileMap = t;

            try {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "done");
                returnIntent.putExtra("profile", (Serializable) profileMap);
                returnIntent.putExtra("media", provider);
                setResult(Activity.RESULT_OK, returnIntent);
                adapter.signOut(activity, socialMediaType.toString());
                activity.finish();

            } catch (Exception e) {

            }
        }

        @Override
        public void onError(SocialAuthError e) {
            e.printStackTrace();
            UtilityFunctions.showToast_onCenter("Unable to your profile try later", activity);
            activity.finish();

        }
    }

    // To get status of message after authentication
    private final class MessageListener implements SocialAuthListener<Integer> {
        @Override
        public void onExecute(String provider, Integer t) {
            Integer status = t;
            if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
                // Toast.makeText(CustomUI.this, "Message posted on" + provider, Toast.LENGTH_LONG).show();
                UtilityFunctions.showToast_onCenter("Message posted on" + provider, activity);
            else
                UtilityFunctions.showToast_onCenter("Message not posted" + provider, activity);
            //Toast.makeText(CustomUI.this, "Message not posted" + provider, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SocialAuthError e) {

        }
    }

    // To receive the album response after authentication
    private final class AlbumDataListener implements SocialAuthListener<List<Album>> {

        @Override
        public void onExecute(String provider, List<Album> t) {

            Log.d("Custom-UI", "Receiving Data");
            mDialog.dismiss();
            List<Album> albumList = t;

//            if (albumList != null && albumList.size() > 0) {
//                Intent intent = new Intent(CustomUI.this, AlbumActivity.class);
//                intent.putExtra("album", (Serializable) albumList);
//                startActivity(intent);
//            } else {
//                Log.d("Custom-UI", "Album List Empty");
//            }
        }

        @Override
        public void onError(SocialAuthError e) {

        }
    }

    // To receive the contacts response after authentication
    private final class ContactDataListener implements SocialAuthListener<List<Contact>> {

        @Override
        public void onExecute(String provider, List<Contact> t) {

            Log.d("Custom-UI", "Receiving Data");
            mDialog.dismiss();
            List<Contact> contactsList = t;

            if (contactsList != null && contactsList.size() > 0) {
                MyLogs.printinfo(" contactsList " + contactsList.size());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "done");
                returnIntent.putExtra("contact", (Serializable) contactsList);
                returnIntent.putExtra("media", provider);
                setResult(Activity.RESULT_OK, returnIntent);
                adapter.signOut(activity, socialMediaType.toString());
                activity.finish();
            } else {
                Log.d("Custom-UI", "Contact List Empty");
                UtilityFunctions.showToast_onCenter(" No contacts found ", activity);
                activity.finish();
            }
        }

        @Override
        public void onError(SocialAuthError e) {

        }
    }

    // To get status of image upload after authentication
    private final class UploadImageListener implements SocialAuthListener<Integer> {

        @Override
        public void onExecute(String provider, Integer t) {
            mDialog.dismiss();
            Integer status = t;
            Log.d("Custom-UI", String.valueOf(status));
            if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
                UtilityFunctions.showToast_onCenter("Image Uploaded", activity);
                //  Toast.makeText(CustomUI.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            else
                UtilityFunctions.showToast_onCenter("Image not Uploaded", activity);
            //Toast.makeText(CustomUI.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SocialAuthError e) {

        }
    }

    // To receive the feed response after authentication
    private final class FeedDataListener implements SocialAuthListener<List<Feed>> {

        @Override
        public void onExecute(String provider, List<Feed> t) {

            Log.d("Custom-UI", "Receiving Data");
            mDialog.dismiss();
            List<Feed> feedList = t;

            if (feedList != null && feedList.size() > 0) {
                MyLogs.printinfo(" feedList " + feedList.size());
//                Intent intent = new Intent(CustomUI.this, FeedActivity.class);
//                intent.putExtra("feed", (Serializable) feedList);
//                startActivity(intent);
            } else {
                Log.d("Custom-UI", "Feed List Empty");
            }
        }

        @Override
        public void onError(SocialAuthError e) {
        }
    }

    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    // To receive the feed response after authentication
    private final class CareerListener implements SocialAuthListener<Career> {

        @Override
        public void onExecute(String provider, Career t) {

            Log.d("Custom-UI", "Receiving Data");
            mDialog.dismiss();
            Career careerMap = t;
//            Intent intent = new Intent(CustomUI.this, CareerActivity.class);
//            intent.putExtra("provider", provider);
//            intent.putExtra("career", careerMap);
//            startActivity(intent);
        }

        @Override
        public void onError(SocialAuthError e) {
        }
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch (requestCode) {
//            case SELECT_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    InputStream imageStream;
//                    try {
//                        imageStream = getContentResolver().openInputStream(selectedImage);
//                        bitmap = BitmapFactory.decodeStream(imageStream);
//                        mDialog.show();
//                        adapter.uploadImageAsync(msg_value, "icon.png", bitmap, 0,
//                                new UploadImageListener());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//        }
//    }

    /**
     * CustomAdapter for showing List. On clicking any item , it calls
     * authorize() method to authenticate provider
     */

    public class DialogAdapter extends BaseAdapter {
        // Android Components
        private final LayoutInflater mInflater;
        private final Context ctx;
        private Drawable mIcon;
        String[] drawables;
        String[] options;

        public DialogAdapter(Context context, int textViewResourceId, String[] providers) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            ctx = context;
            mInflater = LayoutInflater.from(ctx);
            options = providers;
        }

        /**
         * The number of items in the list is determined by the number of
         * speeches in our array.
         */
        @Override
        public int getCount() {
            return options.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         */
        @Override
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         * android.view.ViewGroup)
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid
            // unneccessary
            // calls to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is
            // no
            // need to reinflate it. We only inflate a new View when the
            // convertView
            // supplied by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.provider_options, null);

                // Creates a ViewHolder and store references to the two children
                // views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.providerText);
                holder.icon = (ImageView) convertView.findViewById(R.id.provider);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            String drawables[] = ctx.getResources().getStringArray(R.array.drawable_array);

            mIcon = ctx.getResources().getDrawable(
                    ctx.getResources().getIdentifier(drawables[position], "drawable", ctx.getPackageName()));

            // Bind the data efficiently with the holder
            holder.text.setText(options[position]);
//            if (options[position].equalsIgnoreCase("career"))
//                holder.icon.setImageResource(R.drawable.career);
//            else
            holder.icon.setImageDrawable(mIcon);

            return convertView;
        }

        class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }

    public void getUserProfile() {
        mDialog.show();
        adapter.getUserProfileAsync(new ProfileDataListener());
    }


    public void getContactList() {
        mDialog.show();
        adapter.getContactListAsync(new ContactDataListener());
    }

}
