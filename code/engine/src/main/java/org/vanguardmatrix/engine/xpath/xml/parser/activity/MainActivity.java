package org.vanguardmatrix.engine.xpath.xml.parser.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;

import org.vanguardmatrix.engine.xpath.model.Image;
import org.vanguardmatrix.engine.xpath.model.Tweet;
import org.vanguardmatrix.engine.xpath.parser.DBHelper;

import java.util.ArrayList;

public class MainActivity extends Activity {

    DBHelper dbHelper;
    String tableName = "";
    ArrayList<Tweet> tweetz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///setContentView(R.layout.about_viewer);

        tweetz = null;
        dbHelper = DBHelper.getInstance(MainActivity.this);
        // dbHelper = new DBHelper(context, dbName, null,
        // Integer.parseInt(dbVersion));

        /************************************** Select - Retrieve **************************************/
        /************************************** User **************************************/
        // Get User via ID
        // Log.e("Before ::", "" + tweetz.size());
        ContentValues tweetCV = new ContentValues();
        tweetCV.put("u_screen_name", "EphluxNQ_Numair");
        tweetCV.put("type", "0");
        tweetCV.put("keyword", "Instagram");

        // tweetz = Tweet.getAllTweetsWithScreenName(HomeScreenOld.this,
        // tweetCV);
        try {

            tweetz = Tweet.getAllTweets(MainActivity.this, tweetCV);

            Log.e("After ::", "" + tweetz.size());

            for (int i = 0; i < tweetz.size(); i++) {
                Log.e("HomeScreenOld", "" + tweetz.get(i));
            }

            // Image.addImage();

            ContentValues values = new ContentValues();
            values.put("small", "");
            values.put("medium", "");
            values.put("large", "");
            values.put("timestamp", "");

            if (Image.setAllImageUriBlank(MainActivity.this, values) == 1) {
                Log.e("HomeScreenOld", "Record updated");
            } else {
                Log.e("HomeScreenOld", "Record not updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tweet.addTweet();
        // Delete Values from table
        /*
         * params: table name , id/tweet_id(PK) , user screen_name
		 */
        // dbHelper.deleteField(tableName, id, screen_name);

        // Get all values from table
        /*
         * params: type(tweet type), keyword, user screen_name, key = table
		 * sequence no (0,1,2,3... n tables)
		 */
        // String type = "";
        // String keyword = "";

        // dbHelper.getAllValues(id, key, tableName, type, keyword,
        // screen_name);

    }
}