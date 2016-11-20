package org.vanguardmatrix.engine.socials.GooglePlusManager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.initializer.engine.R;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Uzair on 12/12/2015.
 */
public class GooglePlusController extends Activity {

    AccountManager mAccountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    static Activity activity;
    static int type_id;
    RelativeLayout no_data_layout;
    TextView problem_text1, problem_text2;
    ProgressBar loader;
    private URL feedUrl;
    private static final String username = "yourUsername";
    private static final String pwd = "yourPassword";
//    private ContactsService service;


    public static Intent createIntent(Activity _activity, int _type_id) {
        activity = _activity;
        type_id = _type_id;
        return new Intent(activity, GooglePlusController.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fbmanager_layout);
        activity = this;
        loaderHandling();
        AsyncOnPreHandling();
        syncGoogleAccount();
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


    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    private AbstractGetNameTask getTask(String email, String scope) {
        return new GetNameInForeground(this, email, scope);
    }

    public void syncGoogleAccount() {
        if (UtilityFunctions.checkInternet(activity)) {
            String[] accountarrs = getAccountNames();
            if (accountarrs.length > 0) {
                getTask(accountarrs[0], SCOPE).execute();
                //getTask(accountarrs[0], SCOPE_FRIEND_LIST).execute();
                //onGetDataHandling();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "done");
                setResult(Activity.RESULT_OK, returnIntent);
                //activity.finish();
                String url = "https://www.google.com/m8/feeds/contacts/default/full";

//                try {
//                    this.feedUrl = new URL(url);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//
//                new GetTask().execute();
            } else {
                Toast.makeText(activity, "No Google Account Sync!",
                        Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        } else {
            Toast.makeText(activity, "No Network Service!",
                    Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

//    private class GetTask extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//            service = new  ContactsService("ContactsSample");
//            try {
//                service.setUserCredentials(username, pwd);
//            } catch (AuthenticationException e) {
//                e.printStackTrace();
//            }
//            try {
//                queryEntries();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//
//    private void queryEntries() throws IOException, ServiceException {
//        Query myQuery = new Query(feedUrl);
//        myQuery.setMaxResults(50);
//        myQuery.setStartIndex(1);
//        myQuery.setStringCustomParameter("showdeleted", "false");
//        myQuery.setStringCustomParameter("requirealldeleted", "false");
//        //        myQuery.setStringCustomParameter("sortorder", "ascending");
//        //        myQuery.setStringCustomParameter("orderby", "");
//
//
//        try {
//            ContactFeed resultFeed = (ContactFeed) this.service.query(myQuery, ContactFeed.class);
//            for (ContactEntry entry : resultFeed.getEntries()) {
//                printContact(entry);
//            }
//            System.err.println("Total: " + resultFeed.getEntries().size() + " entries found");
//
//        } catch (NoLongerAvailableException ex) {
//            System.err.println("Not all placehorders of deleted entries are available");
//        }
//
//    }
//
//    private void printContact(ContactEntry contact) throws IOException, ServiceException {
//        System.err.println("Id: " + contact.getId());
//        if (contact.getTitle() != null)
//            System.err.println("Contact name: " + contact.getTitle().getPlainText());
//        else {
//            System.err.println("Contact has no name");
//        }
//
//        System.err.println("Last updated: " + contact.getUpdated().toUiString());
////        if (contact.hasDeleted()) {
////            System.err.println("Deleted:");
////        }
//
//        //        ElementHelper.printContact(System.err, contact);
//
//        Link photoLink = contact.getLink("http://schemas.google.com/contacts/2008/rel#photo", "image/*");
//        if (photoLink.getEtag() != null) {
//            Service.GDataRequest request = service.createLinkQueryRequest(photoLink);
//
//            request.execute();
//            InputStream in = request.getResponseStream();
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            RandomAccessFile file = new RandomAccessFile("/tmp/" + contact.getSelfLink().getHref().substring(contact.getSelfLink().getHref().lastIndexOf('/') + 1), "rw");
//
//            byte[] buffer = new byte[4096];
//            for (int read = 0; (read = in.read(buffer)) != -1; )
//                out.write(buffer, 0, read);
//            file.write(out.toByteArray());
//            file.close();
//            in.close();
//            request.end();
//        }
//
//        System.err.println("Photo link: " + photoLink.getHref());
//        String photoEtag = photoLink.getEtag();
//        System.err.println("  Photo ETag: " + (photoEtag != null ? photoEtag : "(No contact photo uploaded)"));
//
//        System.err.println("Self link: " + contact.getSelfLink().getHref());
//        System.err.println("Edit link: " + contact.getEditLink().getHref());
//        System.err.println("ETag: " + contact.getEtag());
//        System.err.println("-------------------------------------------\n");
//    }




}
