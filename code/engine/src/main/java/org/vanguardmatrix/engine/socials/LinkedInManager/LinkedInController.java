package org.vanguardmatrix.engine.socials.LinkedInManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;
import com.initializer.engine.R;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.vanguardmatrix.engine.datatypes.SocailSignedUserObject;
import org.vanguardmatrix.engine.socials.SocialConstants;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.EnumSet;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Uzair on 12/11/2015.
 */
public class LinkedInController extends Activity {


    EditText et;
    TextView name;
    static Activity activity;

    final LinkedInOAuthService oAuthService = LinkedInOAuthServiceFactory
            .getInstance().createLinkedInOAuthService(
                    SocialConstants.LINKEDIN_CONSUMER_KEY, SocialConstants.LINKEDIN_CONSUMER_SECRET);
    final LinkedInApiClientFactory factory = LinkedInApiClientFactory
            .newInstance(SocialConstants.LINKEDIN_CONSUMER_KEY, SocialConstants.LINKEDIN_CONSUMER_SECRET);
    LinkedInRequestToken liToken;
    LinkedInApiClient client;
    LinkedInAccessToken accessToken = null;

    static int type_id;
    RelativeLayout no_data_layout;
    TextView problem_text1, problem_text2;
    ProgressBar loader;

    public static Intent createIntent(Activity _activity, int _type_id) {
        activity = _activity;
        type_id = _type_id;
        return new Intent(activity, LinkedInController.class);

    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fbmanager_layout);
        activity = this;
        loaderHandling();
        AsyncOnPreHandling();
        if (Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (type_id == 0) {
            try {
                linkedInLogin();
            } catch (Exception e) {

            }
        }


    }

    private void loaderHandling() {

        no_data_layout = (RelativeLayout) findViewById(R.id.no_data_lay);
        problem_text1 = (TextView) findViewById(R.id.problemtext);
        //problem_text2 = (TextView) findViewById(R.id.problemtext2);
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

    private void linkedInLogin() {
        ProgressDialog progressDialog = new ProgressDialog(
                activity);


        LinkedinDialog d = new LinkedinDialog(activity,
                progressDialog);
        Window window = d.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        d.show();

        // set call back listener to get oauth_verifier value
        d.setVerifierListener(new LinkedinDialog.OnVerifyListener() {
            @SuppressLint({"NewApi", "LongLogTag"})
            public void onVerify(String verifier) {
                try {
                    Log.i("LinkedinSample", "verifier: " + verifier);

                    accessToken = LinkedinDialog.oAuthService
                            .getOAuthAccessToken(LinkedinDialog.liToken,
                                    verifier);
                    LinkedinDialog.factory.createLinkedInApiClient(accessToken);
                    client = factory.createLinkedInApiClient(accessToken);
                    // client.postNetworkUpdate("Testing by Mukesh!!! LinkedIn wall post from Android app");
                    Log.i("LinkedinSample",
                            "ln_access_token: " + accessToken.getToken());
                    Log.i("LinkedinSample",
                            "ln_access_token: " + accessToken.getTokenSecret());
                    //Person p = client.getProfileForCurrentUser();
                    Person p = client.getProfileForCurrentUser(EnumSet.of(
                            ProfileField.ID, ProfileField.FIRST_NAME,
                            ProfileField.PHONE_NUMBERS, ProfileField.LAST_NAME,
                            ProfileField.HEADLINE, ProfileField.INDUSTRY,
                            ProfileField.PICTURE_URL, ProfileField.DATE_OF_BIRTH,
                            ProfileField.LOCATION_NAME, ProfileField.MAIN_ADDRESS,
                            ProfileField.LOCATION_COUNTRY));
                    Log.e("create access token secret", client.getAccessToken()
                            .getTokenSecret());

                    if (p != null) {
                        MyLogs.printerror("" + "Welcome " + p.getFirstName() + " "
                                + p.getLastName());
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", "done");
                        setResult(Activity.RESULT_OK, returnIntent);
                        activity.finish();
                       // SocailSignedUserObject.linkedInImporter(p,"LinkedIn");


//                        name.setText("Welcome " + p.getFirstName() + " "
//                                + p.getLastName());
//                        name.setVisibility(0);
//                        profile.setText("Profile:"+p.getHeadline());
//                        profile.setVisibility(0);
//                        String id = p.getId();
//                        String url = p.getPictureUrl();
//                        if(url != null && !url.isEmpty()) {
//                            //Picasso.with(LinkedInSampleActivity.this).load(url).into(photo);
//                            photo.setVisibility(0);
//                        }
//                        login.setVisibility(4);
//                        share.setVisibility(0);
//                        et.setVisibility(0);
                    }

                } catch (Exception e) {
                    Log.i("LinkedinSample", "error to get verifier");
                    e.printStackTrace();
                    activity.finish();
                }
            }
        });

        // set progress dialog
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void share() {

        String share = et.getText().toString();
        if (null != share && !share.equalsIgnoreCase("")) {
            OAuthConsumer consumer = new DefaultOAuthConsumer(SocialConstants.LINKEDIN_CONSUMER_KEY, SocialConstants.LINKEDIN_CONSUMER_SECRET);
            consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://api.linkedin.com/v1/people/~/shares");
            try {
                consumer.sign(post);
            } catch (OAuthMessageSignerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // here need the consumer for sign in for post the share
            post.setHeader("content-type", "text/XML");
            String myEntity = "<share><comment>" + share + "</comment><visibility><code>anyone</code></visibility></share>";
            try {
                post.setEntity(new StringEntity(myEntity));
                org.apache.http.HttpResponse response = httpclient.execute(post);
                Toast.makeText(activity,
                        "Shared sucessfully", Toast.LENGTH_SHORT).show();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity,
                    "Please enter the text to share",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activity.finish();
    }
}


