package org.vanguardmatrix.engine.xpath.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import org.vanguardmatrix.engine.xpath.model.datatypes.OperationType;
import org.vanguardmatrix.engine.xpath.parser.DBHelper;
import org.vanguardmatrix.engine.xpath.parser.Database;

import java.util.ArrayList;


public class Tweet {
    public static DBHelper dbHelper;
    static Context context;
    static Database dbObject;
    private static String tableName = "Tweet";

    public Tweet(float db_id, long tweet_id, String text, String time,
                 boolean favourite) {
        // TODO Auto-generated constructor stub
        dbHelper = DBHelper.getInstance(context);
        dbObject = Database.getInstance(context);
    }

    /************************************** CREATE - ADD **************************************/
    /**
     * *********************************** Tweet *************************************
     */

    // Insert Values in table Tweet
    public static void addTweet() {
        ContentValues fieldNameTweet = new ContentValues();
        fieldNameTweet.put("tweet_id", "123456709860417537");
        fieldNameTweet.put("text", "hahah... http://t.co/MEjk5VV37J");
        fieldNameTweet.put("timestamp", "Mon Aug 12 04:39:20 GMT+05:00 2013");
        fieldNameTweet.put("user_id", "1");
        fieldNameTweet.put("type", "0");
        fieldNameTweet.put("keyword", " ");
        fieldNameTweet.put("favorite", "0");
        fieldNameTweet.put("u_screen_name", "EphluxNQ_Numair");

        dbHelper.opendb();
        DBHelper.runQuery(OperationType.Insert, tableName,
                dbObject.getTablesAllColumn(0), fieldNameTweet);
        dbHelper.closedb();
    }

    // Get Tweet
    public static Tweet getTweet(Context context, ContentValues tweetCV) {

        dbHelper = DBHelper.getInstance(context);
        dbObject = Database.getInstance(context);

        dbHelper.opendb();
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(0), tweetCV);

        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long tweet_id = cursor.getLong(1);
        String text = cursor.getString(2);
        cursor.getString(3);
        boolean favourite = cursor.getInt(7) == 0 ? false : true;

        Tweet tweet = new Tweet(db_id, tweet_id, text, null, favourite);
        cursor.close();

        return tweet;
    }

    // Get All Tweets
    public static ArrayList<Tweet> getAllTweets(Context context,
                                                ContentValues tweetCV) {
        ArrayList<Tweet> tweetz = new ArrayList<Tweet>();
        dbHelper = DBHelper.getInstance(context);
        dbObject = Database.getInstance(context);

        dbHelper.opendb();
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(0), tweetCV);

        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        dbHelper.closedb();

        cursor.moveToFirst();
        do {
            float db_id = cursor.getFloat(0);
            long tweet_id = cursor.getLong(1);
            String text = cursor.getString(2);
            String time = cursor.getString(3);
            boolean favourite = cursor.getInt(7) == 0 ? false : true;

            tweetz.add(new Tweet(db_id, tweet_id, text, time, favourite));

        } while (cursor.moveToNext());

        cursor.close();

        return tweetz;
    }

    public static ArrayList<Tweet> getAllTweetsWithScreenName(Context context,
                                                              ContentValues tweetCV) {
        // Log.e("Tweet", "type name: " + type);
        ArrayList<Tweet> tweetz = new ArrayList<Tweet>();
        DBHelper.getInstance(context).opendb();

        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                Database.getInstance(context).getTablesAllColumn(0), tweetCV);

        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }

        DBHelper.getInstance(context).closedb();
        cursor.moveToFirst();
        do {
            float db_id = cursor.getFloat(0);
            long tweet_id = cursor.getLong(1);
            String text = cursor.getString(2);
            String time = cursor.getString(3);
            boolean favourite = cursor.getInt(7) == 0 ? false : true;
            tweetz.add(new Tweet(db_id, tweet_id, text, time, favourite));

        } while (cursor.moveToNext());

        cursor.close();
        return tweetz;
    }

    // Get User via ID
    public int getTweetCount(Context context, ContentValues tweetCV) {

        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(0), tweetCV);
        if (cursor == null) {
            return 0;
        }

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Get User via ID
    public Tweet getLastTweet(String id, int key, String type, String keyword,
                              String screenName) {
        String query = "SELECT * FROM " + tableName + " WHERE " + "type"
                + " = " + type + " AND " + "keyword" + " = '" + keyword + "'"
                + " AND " + "u_screen_name" + " = '" + screenName
                + "' ORDER BY " + "tweet_id" + " DESC LIMIT 1 ";

        Log.e("aw", "recent tweet query " + query);
        // Cursor cursor = DBHelper.db.rawQuery(query, null);
        dbHelper = DBHelper.getInstance(context);
        dbObject = Database.getInstance(context);

        dbHelper.opendb();
        Cursor cursor = DBHelper.runRawQuery(query, null); // ////////////////////
        // Raw Query

        if (cursor == null || cursor.getCount() <= 0) {
            Log.e("aw", "most recent tweet is null");
            return null;
        }
        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long tweet_id = cursor.getLong(1);
        String text = cursor.getString(2);
        cursor.getString(3);
        boolean favourite = cursor.getInt(7) == 0 ? false : true;

        Log.e("Tweet", "TWEET ID: " + tweet_id);
        Tweet tweet = new Tweet(db_id, tweet_id, text, type, favourite);
        cursor.close();

        return tweet;
    }

    // Get Tweet With TweetID
    public Tweet getTweetWithTweetID(Context context, ContentValues tweetCV) {

        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                Database.getInstance(context).getTablesAllColumn(0), tweetCV);

        if (cursor == null || cursor.getCount() <= 0) {
            // Log.d(TAG, "getTweetWithTweetID returns null");
            return null;
        }
        Log.d("Tweet", "getTweetWithTweetID returns something");
        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long tweet_id = cursor.getLong(1);
        String text = cursor.getString(2);
        cursor.getString(3);
        boolean favourite = cursor.getInt(7) == 0 ? false : true;

        Tweet tweet = new Tweet(db_id, tweet_id, text, null, favourite);
        cursor.close();
        return tweet;
    }

    // Get Tweet between IDs
    public ArrayList<Tweet> getTweetBetweenIDs(long max_id, long since_id,
                                               String id, int key, String type, String keyword, String screenName) {
        ArrayList<Tweet> tweetz = new ArrayList<Tweet>();

        String query = "SELECT * FROM " + tableName + " WHERE " + "type"
                + " = " + type + " AND " + "keyword" + " = '" + keyword
                + "' AND " + "tweet_id" + " BETWEEN '" + since_id + "' AND '"
                + max_id + "' AND " + "u_screen_name" + " = '" + screenName
                + "'" + " ORDER BY " + "tweet_id" + " DESC";

        // Log.e(TAG, "getTweetBetweenIDs: " + query);
        dbHelper = DBHelper.getInstance(context);
        dbObject = Database.getInstance(context);

        dbHelper.opendb();
        Cursor cursor = DBHelper.runRawQuery(query, null); // ////////////////////
        // Raw Query

        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();

        do {
            float db_id = cursor.getFloat(0);
            long tweet_id = cursor.getLong(1);
            String text = cursor.getString(2);
            cursor.getString(3);
            boolean favourite = cursor.getInt(7) == 0 ? false : true;

            tweetz.add(new Tweet(db_id, tweet_id, text, type, favourite));

        } while (cursor.moveToNext());

        cursor.close();

        return tweetz;
    }

    public int getNumberOfTweetbyScreenName(Context context,
                                            ContentValues tweetCV) {
        DBHelper.getInstance(context).opendb();

        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                Database.getInstance(context).getTablesAllColumn(0), tweetCV);

        if (cursor == null || cursor.getCount() <= 0) {
            return 0;
        } else {
            return cursor.getCount();
        }
    }

    /************************************** Delete **************************************/
    /**
     * *********************************** Tweet *************************************
     */
    // Delete Tweet With Tweet ID
    public void deleteTweetWithTweetID(Context context, ContentValues tweetCV)
            throws SQLException {
        // TODO Delete user if not in used by other tweets
        // deleteImagesWithTweetID(tweetID);

        // String[] whereArgs = new String[] { tweetID, screenName };
        // DBHelper.db.delete(tableName, tweetID + "=?", whereArgs);
        DBHelper.getInstance(context).opendb();
        Database.getInstance(context);

        DBHelper.runQuery(OperationType.Delete, tableName, Database
                .getInstance(context).getTablesAllColumn(0), tweetCV);

        // if(getNumberOfTweetbyScreenName(screenName)>0)
        // deleteUserWithScreenName(screenName);
        // if (!(getTweetCount(TweetType.HomeTimeline, "", screenName) > 0))
        // deleteUserWithScreenName(screenName);

    }

    // Delete Tweet
    public void deleteTweet(Context context, ContentValues tweetCV)
            throws SQLException {
        // TODO Delete user if not in used by other tweets

        // Tweet tweet = getTweet(id, type, keyword, screenName);
        // deleteImagesWithTweetID(String.valueOf(tweet.getId()));

        // String[] whereArgs = new String[] { "" + id, screenName };
        // DBHelper.db.delete(tableName, id + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName, Database
                .getInstance(context).getTablesAllColumn(0), tweetCV);

        // if(getNumberOfTweetbyScreenName(screenName)>0)
        // deleteUserWithScreenName(screenName);
        // if (!(getTweetCount(TweetType.HomeTimeline, "", screenName) > 0))
        // deleteUserWithScreenName(screenName);
    }

    // Delete Tweet With Screen Name
    public void deleteTweetWithScreenName(Context context, ContentValues tweetCV)
            throws SQLException {
        // TODO Delete user if not in used by other tweets

        // Tweet tweet = getTweet(id, type, keyword, screenName);
        // deleteImagesWithTweetID(String.valueOf(tweet.getId()));

        // String[] whereArgs = new String[] { "" + id, screenName };
        // DBHelper.db.delete(tableName, id + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName, Database
                .getInstance(context).getTablesAllColumn(0), tweetCV);

        // if(getNumberOfTweetbyScreenName(screenName)>0)
        // deleteUserWithScreenName(screenName);
        // if (!(getTweetCount(TweetType.HomeTimeline, "", screenName) > 0))
        // deleteUserWithScreenName(screenName);

    }
}