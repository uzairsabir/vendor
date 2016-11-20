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
import java.util.Date;


public class Image {
    public static Database dbObject;
    static DBHelper dbHelper;
    static Context context;
    private static String tableName = "Image";
    SQLiteDatabase database;

    // Set<String> allImageColumns =
    // DBHelper.dbObject.getTable(3).getColumns().keySet();

    public Image(float float1, String string, String string2, String string3,
                 String string4, String string5, String string6) {
        // TODO Auto-generated constructor stub
    }

    public static void addImage() {
        /************************************** Image **************************************/
        // Adding Image
        ContentValues fieldNameImage = new ContentValues();
        fieldNameImage.put("tweet_id", "359198709860417537");
        fieldNameImage.put("source", "twitter");
        fieldNameImage.put("image_url",
                "http://pbs.twimg.com/media/BPwhUtGCQAAE9tF.jpg");
        fieldNameImage.put("small", "");
        fieldNameImage.put("medium", "");
        fieldNameImage
                .put("large",
                        "/data/data/ephlux.tweetpicca.activities/cache/twitter_2_541424614.jpg");
        fieldNameImage.put("timestamp", "Mon Jul 22 12:10:11 GMT+05:00 2013");

        dbHelper = DBHelper.getInstance(context);
        dbObject = Database.getInstance(context);
        dbHelper.opendb();
        // dbHelper.insertValues(fieldNameImage, tableName);
        DBHelper.runQuery(OperationType.Insert, tableName,
                dbObject.getTablesAllColumn(3), fieldNameImage);
        dbHelper.closedb();
    }

    public static int setAllImageUriBlank(Context context, ContentValues imageCV) {

        // int status = database.update(tableName, values, null, null);

        Cursor mCursor = DBHelper.runQuery(OperationType.Update, tableName,
                Database.getInstance(context).getTablesAllColumn(3), imageCV);

        if (mCursor != null) {
            mCursor.moveToFirst();
            Log.e("Image", " cursor not null" + mCursor.getString(0));
        } else {
            Log.e("Image", "in else");
        }

        String temp = mCursor.getString(0);

        if (temp.equalsIgnoreCase("Records Updated.")) {
            return 1;
        } else {
            return 0;
        }

        // int result = mCursor.getInt(0); //
        // String(mCursor.getColumnIndex(KEY_DAY));
        // mCursor.close();
        // Log.e("Image", "All image URI set to blank");
        // return result;
        // return status;
    }

    /**
     * *********************************** Image *************************************
     */
    // Get Image via ID
    public Image getImage(Context context, ContentValues imageCV) {

        // Cursor cursor = database.query(tableName, allImageColumn(), "i_id" +
        // " = " + id, null,
        // null, null, null);
        DBHelper.getInstance(context).opendb();
        Database.getInstance(context);
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(3), imageCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();
        Image image = CursorToImage(cursor);
        cursor.close();

        return image; // return image;
    }

    public Image getImageWithURL(Context context, ContentValues imageCV) {

        // Cursor cursor = database.query(tableName, allImageColumn(),
        // "image_url" + " = "
        // + image_url, null, null, null, null);
        DBHelper.getInstance(context).opendb();
        Database.getInstance(context);
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(3), imageCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();
        Image image = CursorToImage(cursor);
        cursor.close();

        return image;
    }

    public ArrayList<Image> getImagesWithTweetID(Context context,
                                                 ContentValues imageCV) {

        // Cursor cursor = database.query(tableName, allImageColumn(),
        // "tweet_id" + " = '"
        // + tweetId + "'", null, null, null, null);
        DBHelper.getInstance(context).opendb();
        Database.getInstance(context);
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(3), imageCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        ArrayList<Image> image = new ArrayList<Image>();
        cursor.moveToFirst();
        do {
            image.add(CursorToImage(cursor));
        } while (cursor.moveToNext());
        cursor.close();

        return image;

    }

    public ArrayList<Image> getNOldImages(Context context, ContentValues imageCV) {

        // Cursor cursor = database.query(tableName, allImageColumn(), "large" +
        // " != ''", null,
        // null, null, "timestamp" + " ASC", String.valueOf(Number));
        DBHelper.getInstance(context).opendb();
        Database.getInstance(context);
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(3), imageCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        ArrayList<Image> image = new ArrayList<Image>();
        cursor.moveToFirst();
        do {
            image.add(CursorToImage(cursor));
        } while (cursor.moveToNext());
        cursor.close();

        return image;
    }

    private Image CursorToImage(Cursor cursor) {
        // TODO Auto-generated method stub
        return new Image(cursor.getFloat(0), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7));
    }

    /**
     * *********************************** Delete - Image *************************************
     */
    // Delete Image
    public void deleteImage(Context context, ContentValues imageCV)
            throws SQLException {

        // String[] whereArgs = new String[] { "" + id };
        // database.delete(tableName, id + "=?", whereArgs);
        DBHelper.getInstance(context).opendb();

        DBHelper.runQuery(OperationType.Delete, tableName, Database
                .getInstance(context).getTablesAllColumn(3), imageCV);

    }

    // Delete Image
    public void deleteImagesWithUrl(Context context, ContentValues imageCV)
            throws SQLException {

        // String[] whereArgs = new String[] { "" + image_url };
        // database.delete(tableName, image_url + "=?", whereArgs);
        DBHelper.getInstance(context).opendb();

        DBHelper.runQuery(OperationType.Delete, tableName, Database
                .getInstance(context).getTablesAllColumn(3), imageCV);

    }

    // Delete Image
    public void deleteImagesWithTweetID(Context context, ContentValues imageCV)
            throws SQLException {

        // String[] whereArgs = new String[] { "" + tweetID };
        // database.delete(tableName, tweetID + "=?", whereArgs);
        DBHelper.getInstance(context).opendb();

        DBHelper.runQuery(OperationType.Delete, tableName, Database
                .getInstance(context).getTablesAllColumn(3), imageCV);
    }

    /**
     * *********************************** Set - Image *************************************
     */
    public int setImageSmallUri(float id, String URI) {

        ContentValues values = new ContentValues();
        values.put("small", URI);
        if (!URI.equals("")) {
            values.put("timestamp", "" + new Date());
        } else {
            values.put("timestamp", "");
        }
        int status = database.update(tableName, values, "i_id" + " = " + id,
                null);

        return status;
    }

    public int setImageMediumUri(float id, String URI) {

        ContentValues values = new ContentValues();
        values.put("medium", URI);
        if (!URI.equals("")) {
            values.put("timestamp", "" + new Date());
        } else {
            values.put("timestamp", "");
        }
        int status = database.update(tableName, values, "i_id" + " = " + id,
                null);

        return status;
    }

    public int setImageLargeUri(float id, String URI) {

        ContentValues values = new ContentValues();
        values.put("large", URI);
        if (!URI.equals("")) {
            values.put("timestamp", "" + new Date());
        } else {
            values.put("timestamp", "");
        }
        int status = database.update(tableName, values, "i_id" + " = " + id,
                null);

        return status;
    }

    public int setImageSmallUriWithLink(String link, String URI) {

        ContentValues values = new ContentValues();
        values.put("small", URI);
        if (!URI.equals("")) {
            values.put("timestamp", "" + new Date());
        } else {
            values.put("timestamp", "");
        }
        int status = database.update(tableName, values, "image_url" + " = '"
                + link + "'", null);

        return status;
    }

    public int setImageMediumUriWithLink(String link, String URI) {

        ContentValues values = new ContentValues();
        values.put("medium", URI);
        if (!URI.equals("")) {
            values.put("timestamp", "" + new Date());
        } else {
            values.put("timestamp", "");
        }
        int status = database.update(tableName, values, "image_url" + " = '"
                + link + "'", null);

        return status;
    }

    public int setImageLargeUriWithLink(String link, String URI) {

        ContentValues values = new ContentValues();
        values.put("large", URI);
        if (!URI.equals("")) {
            values.put("timestamp", "" + new Date());
        } else {
            values.put("timestamp", "");
        }
        int status = database.update(tableName, values, "image_url" + " = '"
                + link + "'", null);

        return status;
    }
}