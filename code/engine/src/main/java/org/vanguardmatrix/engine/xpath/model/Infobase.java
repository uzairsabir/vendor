package org.vanguardmatrix.engine.xpath.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.vanguardmatrix.engine.xpath.model.datatypes.OperationType;
import org.vanguardmatrix.engine.xpath.parser.DBHelper;
import org.vanguardmatrix.engine.xpath.parser.Database;

import java.util.ArrayList;


public class Infobase {
    private static final String TAG = "Infobase";
    public static Database dbObject;
    DBHelper dbHelper;
    SQLiteDatabase database;
    Context context;
    private String tableName = "Infobase";
//	Set<String> allInfobaseColumns = dbObject.getTable(2).getColumns().keySet();

    public Infobase(float db_id, long maxi_id, long since_id, String type,
                    String keyword, String timestamp, String screenName) {
        // TODO Auto-generated constructor stub
    }

    public void addInfobase() {
        /************************************** Infobase **************************************/
        // Adding Infobase
        ContentValues fieldNameInfobase = new ContentValues();
        fieldNameInfobase.put("max_id", "359206641255727100");
        fieldNameInfobase.put("since_id", "359144438901121000");
        fieldNameInfobase.put("type", "0");
        fieldNameInfobase.put("keyword", "");
        fieldNameInfobase.put("timestamp", "Mon Jul 22 12:10:10 GMT+05:00 2013");
        fieldNameInfobase.put("user_screen_name", "EphluxNQ_Numair");

        DBHelper.getInstance(context).opendb();
//		dbHelper.insertValues(fieldNameInfobase, tableName);
        DBHelper.runQuery(OperationType.Insert, tableName, dbObject.getTablesAllColumn(2), fieldNameInfobase);
        DBHelper.getInstance(context).closedb();

        Log.e("get All column fields ", "" + DBHelper.getAllColumnFields(2));

        // Tweet tweet = new Tweet();
        // tweet.getTweets();
    }

    public int getTweetInfobaseCount(Context context, ContentValues infobaseCV) {

//		Cursor cursor = database.query(tableName,
//				allInfobaseColumn(), "type" + " = "
//						+ type + " AND " + "keyword" + " = '" + keyword + "'"
//						+ " AND " + "user_screen_name" + " = '" + screenName
//						+ "'", null, null, null, "tweet_id" + " DESC", "1");

        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(2), infobaseCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return 0;
        }

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public ArrayList<Infobase> getTweetInfobase(Context context, ContentValues infobaseCV) {

        ArrayList<Infobase> infobase = new ArrayList<Infobase>();

//		Cursor cursor = database.query(tableName,
//				allInfobaseColumn(), "type" + " = "
//						+ type + " AND " + "keyword" + " = '" + keyword + "'"
//						+ " AND " + "user_screen_name" + " = '" + screenName
//						+ "'", null, null, null, "tweet_id" + " DESC", "1");
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(0), infobaseCV);
        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();

        do {
            float db_id = cursor.getFloat(0);
            long maxi_id = cursor.getLong(1);
            long since_id = cursor.getLong(2);
            String type = cursor.getString(3);
            String keyword = cursor.getString(4);
            String timestamp = cursor.getString(5);

            infobase.add(new Infobase(db_id, maxi_id, since_id, type,
                    keyword, timestamp, timestamp));

        } while (cursor.moveToNext());

        cursor.close();

        return infobase;
    }

    public Infobase getTweetInfobase(ArrayList<Tweet> tweet, String query) {

        String[] whereIn = convertTweetListToWhereIn(tweet);
//		String placeHolder = makePlaceholders(whereIn.length);

//		String query = "SELECT * FROM " + tableName + " WHERE " + "type"
//				+ " = " + type + "max_id" + " MATCH " + placeHolder + " AND "
//				+ "user_screen_name" + " = '" + screenName + "'";
//		Log.i(TAG, query);
//		Cursor cursor = database.rawQuery(query, whereIn);
        Cursor cursor = DBHelper.runRawQuery(query, whereIn);

        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }

        Log.i(TAG, "getTweetInfobase: " + cursor.getCount());
        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long maxi_id = cursor.getLong(1);
        long since_id = cursor.getLong(2);
        // String type = cursor.getString(3);
        // String keyword = cursor.getString(4);
        String timestamp = cursor.getString(5);

        cursor.close();

        return new Infobase(db_id, maxi_id, since_id, timestamp, null, null, null);
    }

    public Infobase getTweetInfobaseWithMaxID(String query) {

//		String query = "SELECT * FROM " + tableName + " WHERE " + "max_id"
//				+ " = " + max_id + " AND " + "type" + " = " + type + " AND "
//				+ "keyword" + " = '" + keyword + "'" + " AND "
//				+ "user_screen_name" + " = '" + screenName + "'";
//		Log.i(TAG, query);
//		Cursor cursor = database.rawQuery(query, null);
        Cursor cursor = DBHelper.runRawQuery(query, null);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long maxi_id = cursor.getLong(1);
        long since_id = cursor.getLong(2);
        // String type = cursor.getString(3);
        // String keyword = cursor.getString(4);
        String timestamp = cursor.getString(5);

        cursor.close();

        return new Infobase(db_id, maxi_id, since_id, null, null, timestamp, null);
    }

    public Infobase getTweetInfobaseWithHighestMaxID(String query) {

//		String query = "SELECT * FROM " + tableName + " WHERE " + "type"
//				+ " = " + type + " AND " + "keyword" + " = '" + keyword
//				+ "' AND " + "user_screen_name" + " = '" + screenName
//				+ "' ORDER BY " + "max_id" + " DESC LIMIT 1";

        Cursor cursor = DBHelper.runRawQuery(query, null);//database.rawQuery(query, null);

        Log.i(TAG, query + " CursorCount: " + cursor.getCount());

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long maxi_id = cursor.getLong(1);
        long since_id = cursor.getLong(2);
        // String type = cursor.getString(3);
        // String keyword = cursor.getString(4);
        String timestamp = cursor.getString(5);

        cursor.close();

        return new Infobase(db_id, maxi_id, since_id, null, null,
                timestamp,
                null);
    }

    public Infobase getTweetInfobaseWithHighestMaxIDGreaterThanMaxID(String query) {

//		String query = "SELECT * FROM " + tableName + " WHERE " + "max_id"
//				+ " > " + max_id + " AND " + "type" + " = " + type + " AND "
//				+ "keyword" + " = '" + keyword + "' " + " AND "
//				+ "user_screen_name" + " = '" + screenName + "'" + " Order By "
//				+ "max_id" + " DESC Limit 1";
        Log.i(TAG, query);
        Cursor cursor = DBHelper.runRawQuery(query, null);//database.rawQuery(query, null);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long maxi_id = cursor.getLong(1);
        long since_id = cursor.getLong(2);
        // String type = cursor.getString(3);
        // String keyword = cursor.getString(4);
        String timestamp = cursor.getString(5);

        cursor.close();

        return new Infobase(db_id, maxi_id, since_id, null, null,
                timestamp,
                null);
    }

    public Infobase getTweetInfobaseWithHighestMaxIDSmallerThanMaxID(String query) {

//		String query = "SELECT * FROM " + tableName + " WHERE " + "max_id"
//				+ " < " + max_id + " AND " + "type" + " = " + type + " AND "
//				+ "keyword" + " = '" + keyword + "' " + " AND "
//				+ "user_screen_name" + " = '" + screenName + "'" + " Order By "
//				+ "max_id" + " DESC Limit 1";
        Log.i(TAG, query);
        Cursor cursor = DBHelper.runRawQuery(query, null);//database.rawQuery(query, null);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long maxi_id = cursor.getLong(1);
        long since_id = cursor.getLong(2);
        // String type = cursor.getString(3);
        // String keyword = cursor.getString(4);
        String timestamp = cursor.getString(5);

        cursor.close();

        return new Infobase(db_id, maxi_id, since_id, null, null,
                timestamp,
                null);
    }

    public Infobase getTweetInfobaseWithMaxIDEqualtoSinceID(String query) {

//		String query = "SELECT * FROM " + tableName + " WHERE " + "max_id"
//				+ " = " + since_id + " AND " + "type" + " = " + type + " AND "
//				+ "keyword" + " = '" + keyword + "' " + " AND "
//				+ "user_screen_name" + " = '" + screenName + "'" + " Limit 1";

        Cursor cursor = DBHelper.runRawQuery(query, null);//database.rawQuery(query, null);

        Log.i(TAG, query + " CursorCount:" + cursor.getCount());
        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();

        float db_id = cursor.getFloat(0);
        long maxi_id = cursor.getLong(1);
        long since_id_ = cursor.getLong(2);
        // String type = cursor.getString(3);
        // String keyword = cursor.getString(4);
        String timestamp = cursor.getString(5);

        cursor.close();

        return new Infobase(db_id, maxi_id, since_id_, null, null,
                timestamp, null);
    }

    private String[] convertTweetListToWhereIn(ArrayList<Tweet> tweet) {
        String[] ids = new String[tweet.size()];
        for (int index = 0; index < tweet.size(); index++) {
            // ids[index] = "max_id" + ":"
            // + String.valueOf(tweet.get(index).getId());
        }
        return ids;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            // throw new RuntimeException("No placeholders");
            return "";
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }


    /************************************** Delete **************************************/
    /**
     * *********************************** Tweet Infobase *************************************
     */
    // Delete InfoBase
    public void deleteInfoBaseWithID(Context context, ContentValues infobaseCV) throws SQLException {

//		String[] whereArgs = new String[] { "" + id };
//		database.delete(tableName, id + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName,
                dbObject.getTablesAllColumn(2), infobaseCV);

    }

    // Delete InfoBase
    public void deleteInfoBaseWithMaxID(Context context, ContentValues infobaseCV) throws SQLException {

//		String[] whereArgs = new String[] { "" + max_id };
//		database.delete(tableName, max_id + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName,
                dbObject.getTablesAllColumn(2), infobaseCV);
    }

    // Delete InfoBase
    public void deleteInfoBaseWithSinceID(Context context, ContentValues infobaseCV) throws SQLException {

//		String[] whereArgs = new String[] { "" + since_id };
//		database.delete(tableName, since_id + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName,
                dbObject.getTablesAllColumn(2), infobaseCV);
    }


    /**
     * *********************************** Set Infobase *************************************
     */

    public void setTweetInfobase(Infobase infobase) {

        ContentValues fieldNameInfobase = new ContentValues();
        fieldNameInfobase.put("max_id", "359206641255727100");
        fieldNameInfobase.put("since_id", "359144438901121000");
        fieldNameInfobase.put("type", "0");
        fieldNameInfobase.put("keyword", "");
        fieldNameInfobase.put("timestamp", "Mon Jul 22 12:10:10 GMT+05:00 2013");
        fieldNameInfobase.put("user_screen_name", "EphluxNQ_Numair");

//		int status = database.update(tableName, fieldNameInfobase, "id"
//				+ " = " + infobase.getDb_id(), null);

//		return status;
    }

    public void setTweetInfobaseWithMaxID(Infobase infobase) {

        ContentValues values = new ContentValues();
        // values.put(MAX_ID, infobase.getMax_id());
        values.put("since_id", "359144438901121000");//(SINCE_ID, infobase.getSince_id());
        values.put("type", "0");//(TYPE, infobase.getType().getTweetType());
        values.put("keyword", "");//(KEYWORD, infobase.getKeyword());
        values.put("timestamp", "Mon Jul 22 12:10:10 GMT+05:00 2013");//(TIMESTAMP, infobase.getTimestamp());

//		int status = database.update(TABLE_TWEET_INFOBASE, values,
//				DBHelper.MAX_ID + " = '" + infobase.getMax_id() + "'", null);

//		return status;
    }

    public void setTweetInfobaseWithMaxID(String max_id, String since_id,
                                          String type, String keyword, String timestamp) {

        ContentValues values = new ContentValues();
        // values.put(MAX_ID, infobase.getMax_id());
        values.put("since_id", "359144438901121000");//(SINCE_ID, since_id);
        values.put("type", "0");//(TYPE, type.getTweetType());
        values.put("keyword", "");//(KEYWORD, keyword);
        values.put("timestamp", "Mon Jul 22 12:10:10 GMT+05:00 2013");//(TIMESTAMP, timestamp);

//		int status = database.update(TABLE_TWEET_INFOBASE, values,
//				DBHelper.MAX_ID + " = '" + max_id + "'", null);

//		return status;
    }

    public void mergeInfobaseWithMaxID(String max_id, String since_id,
                                       String type, String keyword, String timestamp, String screenName) {

//		Infobase infobase = getTweetInfobaseWithMaxID(since_id, type, keyword,
//				screenName);
//		if (infobase == null) {
//
//			return -1;
//		}
        ContentValues values = new ContentValues();
        // values.put(MAX_ID, infobase.getMax_id());
        values.put("since_id", "359144438901121000");//(SINCE_ID, infobase.getSince_id());
        values.put("type", "0");//(TYPE, infobase.getType().getTweetType());
        values.put("keyword", "");//(KEYWORD, infobase.getKeyword());
        values.put("timestamp", "Mon Jul 22 12:10:10 GMT+05:00 2013");//(TIMESTAMP, infobase.getTimestamp());

//		int status = database.update(TABLE_TWEET_INFOBASE, values,
//				DBHelper.MAX_ID + " = '" + max_id + "'", null);
//		deleteInfoBaseWithID(infobase.getDb_id());

//		return status;
    }

}
