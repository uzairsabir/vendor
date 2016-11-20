package org.vanguardmatrix.engine.socials.FacebookManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialog;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareDialog;
import com.initializer.engine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.webservice.WebService;
import org.vanguardmatrix.engine.datatypes.SocailSignedUserObject;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.io.File;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by Uzair on 12/2/2015.
 */
public class FacebookController extends Activity {

    static Activity activity;
    static int type_id;
    static String image_url, title, description;
    ShareDialog shareDialog;
    LikeDialog likeDialog;
    CallbackManager callbackManager;
    RelativeLayout no_data_layout;
    TextView problem_text1, problem_text2;
    ProgressBar loader;
    String canonical_url;
    Boolean getFriendList = false;
    FacebookCallback<LoginResult> loginFacebookCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("Facebook-Login-Success", loginResult.toString());
            Profile profile = Profile.getCurrentProfile();
//            Log.e("Facebook-Login", profile.getName());
            getUserDataThroughGraphAPI();
            if (!AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions")) {
                LoginManager.getInstance().logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
            }
        }

        @Override
        public void onCancel() {
            Log.e("Facebook-Login-Cancel", "Cancelled");
            activity.finish();
        }

        @Override
        public void onError(FacebookException exception) {
            Log.e("Facebook-Login-onError", exception.toString());
            activity.finish();
        }
    };
    FacebookCallback<Sharer.Result> shareFacebookCallback = new FacebookCallback<Sharer.Result>() {

        @SuppressLint("LongLogTag")
        @Override
        public void onSuccess(Sharer.Result result) {
            Log.e("Facebook-Sharing-Success", result.toString());
        }

        @Override
        public void onCancel() {
            Log.e("Facebook-Sharing-Cancel", "Cancelled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.e("Facebook-Sharing-Error", error.toString());
        }
    };
    FacebookCallback<LikeDialog.Result> likeFacebookCallback = new FacebookCallback<LikeDialog.Result>() {

        @SuppressLint("LongLogTag")
        @Override
        public void onSuccess(com.facebook.share.internal.LikeDialog.Result result) {
            Log.e("Facebook-LikeDialog-Success", result.toString());
        }

        @SuppressLint("LongLogTag")
        @Override
        public void onError(FacebookException error) {
            Log.e("Facebook-LikeDialog-onError", error.toString());
        }

        @SuppressLint("LongLogTag")
        @Override
        public void onCancel() {
            Log.e("Facebook-LikeDialog-Cancel", "Cancelled");
        }
    };

    public static Intent createIntent
            (Activity _activity, int _type_id, String _title, String _description, String _image_url) {
        activity = _activity;
        type_id = _type_id;
        image_url = _image_url;
        title = _title;
        description = _description;
        return new Intent(activity, FacebookController.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fbmanager_layout);
        activity = this;
        loaderHandling();
        AsyncOnPreHandling();
        initComponents();


    }

    private void loaderHandling() {

        no_data_layout = (RelativeLayout) findViewById(R.id.no_data_lay);
        problem_text1 = (TextView) findViewById(R.id.problemtext);
      //  problem_text2 = (TextView) findViewById(R.id.problemtext2);
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

    private void initComponents() {
        doDefaults();
        setReferences();

        if (type_id == 0) {
            try {
                loginWithFacebook();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (type_id == 1) {
            try {
                shareLinkOnFacebook(title, description, image_url);

            } catch (Exception e) {
                e.printStackTrace();
            }
            activity.finish();

        } else if (type_id == 2) {
            try {
                sharePhotoOnFacebook(image_url);
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            activity.finish();

        } else if (type_id == 3) {
            try {
                shareVideoOnFacebook(image_url);

            } catch (Exception e) {
                e.printStackTrace();
            }
            activity.finish();

        } else if (type_id == 4) {
            try {
                inviteFriend();
            } catch (Exception e) {
                e.printStackTrace();
            }
            activity.finish();

        } else if (type_id == 5) {
            try {
                getFriendList = true;
                loginWithFacebook();

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    private void doDefaults() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk fb = new FacebookSdk();
        Log.e("SDK Version", fb.getSdkVersion());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        likeDialog = new LikeDialog(this);
//Call back for login
        LoginManager.getInstance().registerCallback(callbackManager, loginFacebookCallback);
//Call back for share
        shareDialog.registerCallback(callbackManager, shareFacebookCallback);
//Call back for like
        likeDialog.registerCallback(callbackManager, likeFacebookCallback);
    }

    private void setReferences() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "user_friends", "email",
                        "user_birthday", "user_location", "read_custom_friendlists"));
        // LoginManager.getInstance().logInWithPublishPermissions(this,Arrays.asList("publish_actions"));
    }

    private void getUserDataThroughGraphAPI() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("Facebook-GraphRequest-Response", object.toString() + "-" + response.toString());
                        try {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "done");
                            returnIntent.putExtra("profile", (Serializable) SocailSignedUserObject.facebookImporterOAuth(object));
                            returnIntent.putExtra("media", "facebook");
                            setResult(Activity.RESULT_OK, returnIntent);
                            if (getFriendList)
                                friendsList();
                            activity.finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,gender,birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void likeButton() {
/*
* LikeView likeView = (LikeView) findViewById(R.id.btnLike);
* likeView.setObjectIdAndType(“https://www.facebook.com/FacebookDevelopers&#8221;, LikeView.ObjectType.PAGE);
*/

        if (LikeDialog.canShowNativeDialog()) {
            LikeContent likeContent = new LikeContent.Builder()
                    .setObjectId("https://www.facebook.com/FacebookDevelopers&#8221;")
                    .setObjectType(String.valueOf(LikeView.ObjectType.PAGE)).build();
            likeDialog.show(likeContent);
        } else if (LikeDialog.canShowWebFallback()) {
            LikeContent likeContent = new LikeContent.Builder()
                    .setObjectId("https://www.facebook.com/FacebookDevelopers&#8221;")
                    .setObjectType(String.valueOf(LikeView.ObjectType.PAGE)).build();
            likeDialog.show(likeContent);
        }
    }

    private void sharePhotoOnFacebook(String image_url) {

        if (ShareDialog.canShow(SharePhotoContent.class)) {
            Bitmap image = BitmapFactory.decodeFile(image_url);
            SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
            SharePhotoContent photoContent = new SharePhotoContent.Builder()
                    .addPhoto(photo).build();
            shareDialog.show(photoContent);
        } else {

        }
    }

    private void shareLinkOnFacebook(String title, String description, String image_url) {
        try {
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setContentDescription(description)
                        .setContentUrl(Uri.parse(image_url)).build();
                shareDialog.show(linkContent);

            }
        } catch (Exception e) {

        }
    }

    private void shareVideoOnFacebook(String video_url) {
        try {
            if (ShareDialog.canShow(ShareVideoContent.class)) {
                File file = new File(video_url);
                Uri local = Uri.fromFile(file);
                ShareVideo video = new ShareVideo.Builder().setLocalUrl(local).build();
                ShareVideoContent videoContent = new ShareVideoContent.Builder().setVideo(video).build();
                shareDialog.show(videoContent);
            }
        } catch (Exception e) {

        }
    }

//    public static Uri getImageContentUri(Context context, File imageFile) {
//        String filePath = imageFile.getAbsolutePath();
//        Cursor cursor = context.getContentResolver().query(
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Video.Media._ID},
//                MediaStore.Video.Media.DATA + "=? ", new String[]{filePath}, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
//            return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id);
//        } else {
//            if (imageFile.exists()) {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Video.Media.DATA, filePath);
//                return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
//            } else {
//                return null;
//            }
//        }
//    }

    private void inviteFriend() {

        new callCurl(activity).execute();

        String appLinkUrl, previewImageUrl;
        appLinkUrl = "https://fb.me/653328091475189";
        previewImageUrl = "https://lh3.googleusercontent.com/aRp9HeVZ0_sbGG4sr9vqw7ZDT-h9_ocpp9zHSe1Wi5WMWdyq2u9N2msE1niS9R0P-w=w300";
        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder().setApplinkUrl(appLinkUrl).setPreviewImageUrl(previewImageUrl).build();
            AppInviteDialog appInviteDialog = new AppInviteDialog(activity);
            appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>() {
                @Override
                public void onSuccess(AppInviteDialog.Result result) {
                    Log.e("getresultt", "" + result);
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                }
            });


            AppInviteDialog.show(this, content);
        }
    }

    private void friendsList() {

        Bundle params = new Bundle();
        params.putString("limit", "5000"); // Up to 5000?
        //loginWithFacebook();

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
/* handle the result */

                        Log.e("Friends List: 1", response.toString());
                        try {

                            JSONArray rawPhotosData = response.getJSONObject().getJSONArray("data");
                            String next = response.getJSONObject().getJSONObject("paging").getString("next");

                            Log.e("next", "" + next);

                            for (int i = 0; i < rawPhotosData.length(); i++) {
                                Log.e("friends", "" + rawPhotosData.getJSONObject(i));


                                //String id=rawPhotosData.getJSONObject(i).getString("id");
                                //getFriendsNameList(id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }).executeAsync();


        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friendlists", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
/* handle the result */
                        Log.e("Friends List: 2", response.toString());
                        try {
                            JSONArray rawPhotosData = response.getJSONObject().getJSONArray("data");

                            for (int i = 0; i < rawPhotosData.length(); i++) {
                                Log.e("friendlists", "" + rawPhotosData.getJSONObject(i));

                                String id = rawPhotosData.getJSONObject(i).getString("id");
                                //getFriendsNameList(id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).executeAsync();

        GraphRequest request_ = new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/taggable_friends", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
/* handle the result */
                        Log.e("Friends List: 3", response.toString());
                        try {

                            JSONArray rawPhotosData = response.getJSONObject().getJSONArray("data");


                            for (int i = 0; i < rawPhotosData.length(); i++) {
                                Log.e("data name", "" + rawPhotosData.getJSONObject(i));


                                //String id=rawPhotosData.getJSONObject(i).getString("id");
                                //getFriendsNameList(id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request_.executeAsync();

/* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/1616708415228148/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
/* handle the result */
                        Log.e("Friends List: 4", response.toString());
                    }
                }
        ).executeAsync();

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/1616708415228148/invitable_friends", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
/* handle the result */
                        Log.e("Friends List: 5", response.toString());
                    }
                }).executeAsync();


    }


    public class callCurl extends AsyncTask<String, Void, Boolean> {
        Activity _activity;

        public callCurl(Activity activity) {
            _activity = activity;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            //viewLoader();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                String tem_app_url = "https://graph.facebook.com/app/app_link_hosts?" +
                        "access_token=" + URLEncoder.encode("649147498559915|dzt0j0nlt8jeV9UU1WvLK5mWl7U") +
                        "&name=android" +
                        "&android=" + URLEncoder.encode("[{" +
                        "\"url\":\"https://play.google.com/store/apps/details?id=com.vanguardmatrix.zaair&hl=en/?referral=123\"," +
                        "\"package\":\"com.vanguardmatrix.zaair.zaairapp\"," +
                        "\"app_name\":\"Zaair Guide\",}" + "]") +
                        "&web=" + URLEncoder.encode("{should_fallback: true}");
                Log.e("tem_app_url", "" + URLDecoder.decode(tem_app_url));
                JSONObject jsonObject = new JSONObject();
                jsonObject = WebService.callCurlRequest(tem_app_url.toString());
                JSONArray jsonArray = new JSONArray();
                jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    canonical_url = jsonArray.getJSONObject(i).getString("canonical_url");
                }

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

                String appLinkUrl, previewImageUrl;
                appLinkUrl = "http://202.142.175.163/zaair/fb-meta-tags/abc@wa.com";
                // MyLogs.printinfo("canonical_url " + canonical_url);
                previewImageUrl = "https://lh3.googleusercontent.com/aRp9HeVZ0_sbGG4sr9vqw7ZDT-h9_ocpp9zHSe1Wi5WMWdyq2u9N2msE1niS9R0P-w=w300";
                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder().setApplinkUrl(appLinkUrl).setPreviewImageUrl(previewImageUrl).build();
                    AppInviteDialog appInviteDialog = new AppInviteDialog(activity);
                    appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>() {
                        @Override
                        public void onSuccess(AppInviteDialog.Result result) {
                            Log.e("getresultt", "" + result);
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException e) {
                        }
                    });


                    AppInviteDialog.show(activity, content);
                }
            }

        }
    }

//
}
