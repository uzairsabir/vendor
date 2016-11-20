package org.vanguardmatrix.engine.xpath.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.vanguardmatrix.engine.xpath.model.datatypes.OperationType;
import org.vanguardmatrix.engine.xpath.parser.DBHelper;
import org.vanguardmatrix.engine.xpath.parser.Database;


public class User {
    public static Database dbObject;
    Context context;
    private String tableName = "User";

    public User(float float1, String string, String string2, String string3,
                String string4, String string5, String string6, String string7,
                String string8) {
        // TODO Auto-generated constructor stub
    }

    // Set<String> allUserColumns = dbObject.getTable(1).getColumns().keySet();

    /************************************** CREATE - ADD **************************************/
    /**
     * *********************************** User *************************************
     */

    public void addUser() {
        ContentValues fieldNameUser = new ContentValues();
        fieldNameUser.put("u_name", "Funny Illusions�");
        fieldNameUser.put("u_screen_name", "Awkward_Pics");
        fieldNameUser
                .put("u_pic_url",
                        "http://a0.twimg.com/profile_images/378800000059920516/01cbabf2fffab6e1b63c3177ed3d4bb2_normal.jpeg");
        fieldNameUser
                .put("u_bg_url",
                        "https://pbs.twimg.com/profile_banners/523512145/1372396792/web");
        fieldNameUser.put("followers", "550312");
        fieldNameUser.put("following", "139");
        fieldNameUser.put("listed", "654");
        fieldNameUser.put("tweets", "15177");
        fieldNameUser.put("u_count", "");

        DBHelper.getInstance(context).opendb();
        // dbHelper.insertValues(fieldNameUser, tableName);
        DBHelper.runQuery(OperationType.Insert, tableName,
                dbObject.getTablesAllColumn(1), fieldNameUser);
        DBHelper.getInstance(context).closedb();

    }

    /************************************** Select - Retrieve **************************************/
    /**
     * *********************************** User *************************************
     */
    // Get User via ID
    public User getUser(Context context, ContentValues userCV) {

        // Cursor cursor = database.query(tableName,
        // dbObject.getTablesAllColumn(1), "id" + " = "
        // + id, null, null, null, null);

        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(1), userCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();
        User user = CursorToUser(cursor);
        cursor.close();

        return user;
    }

    public User getUserWithScreenName(Context context, ContentValues userCV) {

        // Cursor cursor = database.query(tableName,
        // dbObject.getTablesAllColumn(1), "" + " = '"
        // + screenName + "'", null, null, null, null);
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(1), userCV);

        if (cursor == null || cursor.getCount() <= 0) {

            return null;
        }

        cursor.moveToFirst();
        User user = CursorToUser(cursor);
        cursor.close();

        return user;
    }

    public float doesUserWithScreenNameExist(Context context,
                                             ContentValues userCV) {

        // Cursor cursor = database.query(tableName,
        // dbObject.getTablesAllColumn(1),
        // "u_screen_name" + " = '" + screenName + "'", null, null, null,
        // null);
        Cursor cursor = DBHelper.runQuery(OperationType.Select, tableName,
                dbObject.getTablesAllColumn(1), userCV);

        if (cursor == null || cursor.getCount() == 0) {
            return -1;
        }

        cursor.moveToFirst();
        float user_id = cursor.getFloat(0);
        cursor.close();

        return user_id;
    }

    private User CursorToUser(Cursor cursor) {
        return new User(cursor.getFloat(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8));
    }

    /**
     * *********************************** Delete *************************************
     */
    // Delete User
    public void deleteUser(Context context, ContentValues userCV)
            throws SQLException {

        // String[] whereArgs = new String[] { "" + id };
        // database.delete(tableName, id + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName,
                dbObject.getTablesAllColumn(1), userCV);

    }

    // Delete User With Screen name
    public void deleteUserWithScreenName(Context context, ContentValues userCV)
            throws SQLException {

        // String[] whereArgs = new String[] { "" + screenName };
        // database.delete(tableName, screenName + "=?", whereArgs);
        DBHelper.runQuery(OperationType.Delete, tableName,
                dbObject.getTablesAllColumn(1), userCV);

    }

}