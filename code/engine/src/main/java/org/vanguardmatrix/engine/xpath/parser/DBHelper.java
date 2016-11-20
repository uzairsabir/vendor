package org.vanguardmatrix.engine.xpath.parser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.datatypes.Contact;
import org.vanguardmatrix.engine.datatypes.PhoneNumber;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;
import org.vanguardmatrix.engine.xpath.model.datatypes.OperationType;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

public class DBHelper extends SQLiteOpenHelper {

    public static SQLiteDatabase db;

    public static Database dbObject;
    static Set<String> columnNames;
    private static DBHelper dbHelper;
    //    private static DatabaseSQLiteHelper myDBsqliteHelper;
    private final String TAG = DBHelper.this.getClass().getSimpleName();
    private Cursor cursor;

    private DBHelper(Context context) {

        this(context, dbObject = Database.getInstance(context));

//        try{
////            db.sqlite_create_function($sqlite_db, "sqrt", "sqrt", 1);
////            db.loa
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    private DBHelper(Context context, Database db) {
        super(context, dbObject.dbName, null, dbObject.dbVersion);
        Log.i(TAG + "- DBName::::" + dbObject.dbName, "::::Version::::"
                + dbObject.dbVersion);

        this.opendb();
        createTableQuery();
        this.closedb();
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context);

        return dbHelper;
    }

    public static Set<String> getAllColumnFields(int key) {
        columnNames = dbObject.getTable(key).getColumns().keySet();
        return columnNames;
    }


    // Close database

    /**
     * ************************** Run Query for all Table *****************************
     */
    public static Cursor runQuery(OperationType select, String tableName,
                                  String[] allColumnFields, ContentValues tweetCV) {
        // String table, String[] columns, ContentValues whereArgs, String
        // groupBy, String having, String orderBy, String limit
        Cursor cursor = null;
        switch (select.getOperationType()) {
            // Insert
            case (0):
                db.insert(tableName, "nullColumnHack", tweetCV);
                break;

            // Delete
            case (1):
                String whereClauseStr = "";
                Set<Entry<String, Object>> delset = tweetCV.valueSet();
                for (Entry<String, Object> key : delset) {

                    String value = key.getKey().toLowerCase(Locale.getDefault());// tweetCV.getAsString(key);
                    Log.i("DBHelper", "value::::" + value);

                    // String[] whereArgs = new String[] { tweetID, screenName };
                    // DBHelper.db.delete(tableName, tweetID + "=?", whereArgs);

                    whereClauseStr = value + " =? '" + key.getValue().toString();
                    Log.i("DBHelper", "query string:::" + whereClauseStr);

                }

                db.delete(tableName, whereClauseStr, allColumnFields);
                break;

            // Update
            case (2):
                // db.update(tableName, values, whereClause, whereArgs)
                int x = db.update(tableName, tweetCV, null, null);
                String[] s = new String[1];
                String[] sv = new String[1];
                sv[0] = "Update";

                MatrixCursor mc = new MatrixCursor(sv);

                if (x == 0) {
                    s[0] = "No Records Updated.";
                } else {
                    s[0] = "Records Updated.";
                }

                mc.addRow(s);

                cursor = mc;

                break;

            // Select
            case (3):

                String queryString = "";
                Set<Entry<String, Object>> set = tweetCV.valueSet();
                for (Entry<String, Object> key : set) {

                    String value = key.getKey().toLowerCase(Locale.getDefault());// tweetCV.getAsString(key);
                    Log.i("DBHelper", "value::::" + value);

                    // queryString += key + " = '" + value + "' AND ";
                    queryString += value + " = '" + key.getValue().toString()
                            + "' AND ";
                    Log.i("DBHelper", "query string:::" + queryString);

                }
                queryString = queryString.substring(0, queryString.length() - 5);
                Log.i("DBHelper", "query sub string:::" + queryString);
                cursor = DBHelper.db.query(tableName, allColumnFields, queryString,
                        null, null, null, "tweet_id" + " DESC");
                break;

            // Incorrect Operation Type
            default:
                Log.i("DBHelper",
                        "<<<<<<!!!!!!!!!!!!!!!!!! Invalid Input !!!!!!!!!!!!!!!!!!>>>>>>>");
                break;
        }

        return cursor;
    }

    public static Cursor runRawQuery(String query, String[] whereIn) {
        // String table, String[] columns, ContentValues whereArgs, String
        // groupBy, String having, String orderBy, String limit
        Cursor cursor = null;
        cursor = DBHelper.db.rawQuery(query, whereIn);

        return cursor;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

//        myDBsqliteHelper = DatabaseSQLiteHelper.getInstance(
//                ActivityManager.getInstance().getRunningActivity());
//        File dbDir = ActivityManager.getInstance().getRunningActivity().getDatabasePath(DatabaseManager.dbFileName + ".db");//Dir("tests", Context.MODE_PRIVATE);
//        db = SQLiteDatabase.openOrCreateDatabase(dbDir, null);
////        db = getWritableDatabase();
//        db.execSQL("PRAGMA key = '" + AppPreferences.getString(AppPreferences.PREF_DEVICE_DEPTH) + "'");

//        SQLiteConnection conn = new SQLiteConnection("Data Source=MyDatabase.sqlite;Version=3;");
//        conn.SetPassword("password");
//        conn.open();
    }

    public void dropDB() throws Exception {
        // Delete all tables
        onCreate(db);
    }

    // Open database
    public void opendb() {
        // Log.i("DBHElper", "Opendb called");

//        File dbDir = ActivityManager.getInstance().getRunningActivity().getDatabasePath(DatabaseManager.dbFileName + ".db");//Dir("tests", Context.MODE_PRIVATE);
//        File mDatabaseFile = new File(dbDir, DatabaseManager.dbFileName + ".db");

//        db = SQLiteDatabase.openOrCreateDatabase("zaair.db", SQLiteDatabase.CREATE_IF_NECESSARY);
//        db = SQLiteDatabase.openOrCreateDatabase(dbDir, null);

        db = this.getWritableDatabase();
//        db = new SQLiteOpenHelper(Application.getInstance(),
//                Database.getInstance(ActivityManager.getInstance().getRunningActivity()).dbName,
//                null, Database.getInstance(ActivityManager.getInstance().getRunningActivity()).dbVersion);

//        db = SQLiteDatabase.openOrCreateDatabase("zaair.db", null);

        db.enableWriteAheadLogging();

//        try {
//            db.execSQL("PRAGMA key = '" + AppPreferences.getString(AppPreferences.PREF_DEVICE_DEPTH) + "'");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA automatic_index = on;");
        }

//        sqlite3_open_v2("data.db", &db, SQLITE_OPEN_READWRITE | SQLITE_OPEN_CREATE, NULL);
//        sqlite3_stmt* stm;
//        const char *pzTail;
//        int res;
//        res = sqlite3_prepare(db, "PRAGMA key = 'ac23';", -1, &stm, &pzTail); //ac23 is database passphrase
//        res = sqlite3_step(stm);
//        res = sqlite3_prepare(db, "PRAGMA lic = '77523-009-0000007-72328';", -1, &stm, &pzTail); //software license key
//        res = sqlite3_step(stm);

    }

    public void closedb() {
        // try {
        // Log.i("DBHElper", "Closedb called");
        // if (db != null) {
        // if (db.isOpen()) {
        // db.disableWriteAheadLogging();
        // db.close();
        // }
        // }
        // } catch (Exception e) {
        // }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "on create db");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "in on upgrade");

        // for (int i = 0; i < dbObject.getTables().keySet().size(); i++) {
        //
        // Log.i(TAG, "Table Quantity" + i);
        // Log.i(TAG, "Table names::" + dbObject.mytablesNames.get(i));
        // Log.i(TAG, "onUpgrade::" + "Tables dropped");
        //
        // db.execSQL("DROP TABLE IF EXISTS " + dbObject.mytablesNames.get(i));
        // onCreate(db);
        // }
        Log.e("aw", "upgrading db");
        while (oldVersion < newVersion) {
            oldVersion += 1;
            Log.i(TAG, "Migrate to version " + oldVersion);
            migrate(db, oldVersion);
        }
    }

    /**
     * Called on database migration.
     *
     * @param db
     * @param toVersion
     */
    private void migrate(SQLiteDatabase db, int toVersion) {
        switch (toVersion) {
            case 12:
//                db.execSQL("ALTER TABLE " + GcmNotificationsModel.N_TABLE
//                        + " ADD COLUMN " + GcmNotificationsModel.N_TARGETID
//                        + " VARCHAR;");
                break;
            case 14:
                db.execSQL("CREATE TABLE IF NOT EXISTS voicereport (report_id Integer PRIMARY KEY AUTOINCREMENT,username Varchar,name Varchar,imageurl Varchar,report_type Varchar,category Varchar,target_latitude Varchar,target_longitude Varchar,target_address Varchar,target_location_name Varchar,created_at Varchar,report_detail Varchar,report_image_url Varchar,endorse_count Varchar,is_endorse Varchar,endorse_report Varchar );");
                break;
            case 15:
//                db.execSQL("ALTER TABLE " + GcmNotificationsModel.N_TABLE
//                        + " ADD COLUMN " + GcmNotificationsModel.N_EXTRA
//                        + " VARCHAR;");
                break;
            default:
                break;
        }
    }

    /**
     * *********************** CREATE TABLE *************************
     */
    private void createTableQuery() {
        Log.i(TAG + " Table list", "" + dbObject.getTables().keySet());
        Log.i(TAG + "...", "" + dbObject.getTables().keySet().size());
        Log.i(TAG + " Column list", ""
                + dbObject.getTable(0).getColumns().keySet());

        // Set<String> keys = dbObject.getTables().keySet();
        // String array[] = new String[keys.size()];
        // array = keys.toArray(array);
        String query = "";

        for (int i = 0; i < Database.mytables.size(); i++) {
            String appendedQuerry = "";
            // CREATE TABLE COMPANY(
            // ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL
            // );
            for (int j = 0; j < Database.mytables.get(i).myColumns.size(); j++) {
                if (Database.mytables.get(i).myColumns.get(j).getPK()
                        .equalsIgnoreCase("true")) {
                    appendedQuerry += ""
                            + Database.mytables.get(i).myColumns.get(j)
                            .getfieldName()
                            + " "
                            + Database.mytables.get(i).myColumns.get(j)
                            .getdataType() + " "
                            + "PRIMARY KEY AUTOINCREMENT,";
                } else {

                    appendedQuerry += ""
                            + Database.mytables.get(i).myColumns.get(j)
                            .getfieldName()
                            + " "
                            + Database.mytables.get(i).myColumns.get(j)
                            .getdataType() + ",";
                }
            }

            appendedQuerry = appendedQuerry.substring(0,
                    appendedQuerry.length() - 1);

            query = "CREATE TABLE IF NOT EXISTS "
                    + dbObject.mytablesNames.get(i) + " (" + appendedQuerry
                    + " );";
            try {
                db.execSQL(query);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Log.i(TAG + "final query for creating Table ", i + " : " + query);

        }

    }


    //-------------------------  Zaair App Queriess Start -------------------------------------------
    public int addTableRecord(JSONObject singleObject, String filter,
                              String tableName) {

        Log.i("addingRecord", " tableName : " + tableName + "--- json - "
                + " record ID : " + filter + singleObject);
        int x = dbObject._tableList.get(tableName).addRecordItem(singleObject,
                filter, dbHelper, tableName);
        return x;

    }

    public void updateTableRecordsWithoutWhereClause(String TableName, ContentValues cv, String id) {
        db.update(TableName, cv, null, null);

    }

    public void updateTableRecordsWithWhereClause(String TableName, ContentValues cv, String username) {
        db.update(TableName, cv, "username=\'" + username + "\' COLLATE NOCASE", null);

    }


    public void deleteTableRecords(String tableName) {
        Log.e("delete table record", "delete from " + tableName + ";");
        db.delete(tableName, null, null);

    }


    //--------------------------Zaair App Queriess End----------------------------------------------

    public long insertTableRecordOnly(JSONObject singleContact, String filter,
                                      String tableName) {

        // Log.i("addingRecord", " tableName : " + tableName + "--- json - "
        // + " record ID : " + filter + singleContact);
        long x = dbObject._tableList.get(tableName).addRecordOnly(
                singleContact, filter, dbHelper, tableName);
        return x;

    }

    public long insertSingleRecord(int value, int appointmentid, String last_date,
                                   String tableName) {
        ContentValues values = new ContentValues();
        values.put("id", value);
        values.put("appointment_id", appointmentid);
        values.put("last_date", last_date);
        long item = db.insert(tableName, null, values);

        return item;


    }


    public Cursor getRecordsFromJoin(String TableName1, String TableName2, String sortbyColumn) {
        Cursor cursor = db.rawQuery("select * from " + TableName1 + " LEFT JOIN " + TableName2 + " ON " +
                TableName1 + ".id = " + TableName2 + ".id " + ";", null);
        Log.e("DBHelper", " allrecords cursor count" + cursor.getCount());
        return cursor;


    }

    public void updateContact(String attribute, String value, String phoneNumber) {
        Log.i("updatingRecored", "Contact" + phoneNumber + ": " + attribute
                + "=" + value);
        try {

            dbHelper.opendb();

            ContentValues values = new ContentValues();
            values.put(attribute, value);

            String strFilter = Contact.ATTR_PHONE_NUMBER + "=\'" + phoneNumber
                    + "\'";

            int updateResult = db.update("contacts", values, strFilter, null);

            Log.e("contact update", " result : " + updateResult);

            dbHelper.closedb();
        } catch (Exception e) {
            e.printStackTrace();
            dbHelper.closedb();
        }
    }

    public void updateRecord(String columnName, String columnValue,
                             String filterColumn, String filterValue, String tableName) {

        Log.i("updatingRecored", "Record id = " + filterValue + ": "
                + columnName + "=" + columnValue);
        try {
            dbHelper.opendb();
            ContentValues values = new ContentValues();
            values.put(columnName, columnValue);
            String strFilter = filterColumn + "=\'" + filterValue + "\'";
            int updateResult = db.update(tableName, values, strFilter, null);
            Log.e("record update", " result : " + updateResult);
            dbHelper.closedb();
        } catch (Exception e) {
            e.printStackTrace();
            dbHelper.closedb();
        }
    }

    public Contact getContactRecord(String phoneNum) {

        Contact userContact = new Contact();

        // Log.i("fetching data query : ",
        // "select * from contacts where " + Contact.ATTR_PHONE_NUMBER
        // + "= \"" + Contact.getNumberOnly(phoneNum) + "\";");

        opendb();
        Cursor c = db.rawQuery(
                "select * from contacts where " + Contact.ATTR_PHONE_NUMBER
                        + " = \'" + Contact.getNumberOnly(phoneNum) + "\';",
                null);

        if (c == null) {
            Log.e("cursor", " is null");
            return null;
        }

        if (c.moveToFirst()) {
            // Log.e("cursor", " moved to first");
            ;
        } else
            return null;

        // Log.e("aw",
        // " fetch from db for contact :"
        // + c.getString(c
        // .getColumnIndex(Contact.ATTR_PHONE_NUMBER))
        // + " value firstName"
        // + c.getString(c.getColumnIndex(Contact.ATTR_FIRST_NAME))
        // + " value gender"
        // + c.getString(c.getColumnIndex(Contact.ATTR_GENDER))
        // + " value DOB"
        // + c.getString(c.getColumnIndex(Contact.ATTR_DOB))
        // + " value email"
        // + c.getString(c.getColumnIndex(Contact.ATTR_EMAIL))
        // + " value country"
        // + c.getString(c.getColumnIndex(Contact.ATTR_COUNTRY))
        // + " value city"
        // + c.getString(c.getColumnIndex(Contact.ATTR_CITY))
        // + " value profileImageURL"
        // + c.getString(c.getColumnIndex(Contact.ATTR_IMAGE_URL))
        // + " value coverImageURL"
        // + c.getString(c
        // .getColumnIndex(Contact.ATTR_COVER_IMAGE_URL)));

        try {

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_FIRST_NAME))))
                    userContact.setfirstName(c.getString(c
                            .getColumnIndex(Contact.ATTR_FIRST_NAME)));
                else
                    userContact.setfirstName("");

            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_PHONE_NUMBER))))
                    userContact.setphoneNumber(AppPreferences.getString(AppPreferences.PREF_USER_PHONE_NO));
                else
                    userContact.setphoneNumber(PhoneNumber
                            .getFormatedNumber(AppPreferences.getString(AppPreferences.PREF_USER_PHONE_NO)));

            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c.getColumnIndex(Contact.ATTR_DOB))))
                    userContact.setdob(c.getString(c
                            .getColumnIndex(Contact.ATTR_DOB)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c.getColumnIndex(Contact.ATTR_GENDER))))
                    userContact.setgender(c.getString(c
                            .getColumnIndex(Contact.ATTR_GENDER)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c.getColumnIndex(Contact.ATTR_EMAIL))))
                    userContact.setemail(c.getString(c
                            .getColumnIndex(Contact.ATTR_EMAIL)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c
                        .getString(c.getColumnIndex(Contact.ATTR_COUNTRY))))
                    userContact.setcountry(c.getString(c
                            .getColumnIndex(Contact.ATTR_COUNTRY)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {
                if (!isEmpty(c.getString(c.getColumnIndex(Contact.ATTR_CITY))))
                    userContact.setcity(c.getString(c
                            .getColumnIndex(Contact.ATTR_CITY)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_IMAGE_URL))))
                    userContact.setprofileImage(c.getString(c
                            .getColumnIndex(Contact.ATTR_IMAGE_URL)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_COVER_IMAGE_URL))))
                    userContact.setcoverImage(c.getString(c
                            .getColumnIndex(Contact.ATTR_COVER_IMAGE_URL)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_LAST_NAME))))
                    userContact.setlastName(c.getString(c
                            .getColumnIndex(Contact.ATTR_LAST_NAME)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_SOCIAL_URLS))))
                    userContact.setsocialUrls(c.getString(c
                            .getColumnIndex(Contact.ATTR_SOCIAL_URLS)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            try {

                if (!isEmpty(c.getString(c
                        .getColumnIndex(Contact.ATTR_PHONE_BOOK_NAME))))
                    userContact.setphoneBookName(c.getString(c
                            .getColumnIndex(Contact.ATTR_PHONE_BOOK_NAME)));
            } catch (Exception e) {
                // e.printStackTrace();
            }

            // try {
            //
            // if (!isEmpty(c.getString(c
            // .getColumnIndex(Contact.ATTR_PHOTO_URI))))
            // userContact.setphotoURI(c.getString(c
            // .getColumnIndex(Contact.ATTR_PHOTO_URI)));
            // } catch (Exception e) {
            // e.printStackTrace();
            // }

        } catch (Exception e) {
            e.printStackTrace();
            c.close();
            closedb();
            return null;
        }

        c.close();
        closedb();
        return userContact;
    }

//    public PhoneContact getPhoneContactRecord(String tableName, String phoneNum) {
//
//        PhoneContact userContact = new PhoneContact();
//
//        Log.i("fetching data query : ", "select * from " + tableName
//                + " where " + PhoneContact.NUMBER + "= \"" + phoneNum + "\";");
//        opendb();
//        Cursor c = db.rawQuery("select * from " + tableName + " where "
//                + PhoneContact.NUMBER + " = \'" + phoneNum + "\';", null);
//
//        if (c.moveToFirst()) {
//        }
//
//        try {
//
//            try {
//
//                if (!isEmpty(c.getString(c.getColumnIndex(PhoneContact.NUMBER))))
//                    userContact.setnumber(c.getString(c
//                            .getColumnIndex(PhoneContact.NUMBER)));
//                else
//                    userContact.setnumber(PhoneNumber
//                            .getFormatedNumber(phoneNum));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//
//            try {
//
//                if (!isEmpty(c.getString(c.getColumnIndex(PhoneContact.EMAIL))))
//                    userContact.setemail(c.getString(c
//                            .getColumnIndex(PhoneContact.EMAIL)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//
//                if (!isEmpty(c.getString(c.getColumnIndex(PhoneContact.NAME))))
//                    userContact.setname(c.getString(c
//                            .getColumnIndex(PhoneContact.NAME)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            c.close();
//            closedb();
//            return null;
//        }
//
//        c.close();
//        closedb();
//        return userContact;
//    }

    // public Feed getFeedRecord(String phoneNum) {
    //
    // Feed userFeeds = new Feed();
    // opendb();
    // Cursor c = db.rawQuery(
    // "select * from feed_categories where " + Feed.F_ID + " = \'"
    // + Contact.getNumberOnly(phoneNum) + "\';", null);
    //
    // if (c.moveToFirst()) {
    // // Log.e("cursor", " moved to first");
    // }
    //
    // try {
    //
    // try {
    //
    // if (!isEmpty(c.getString(c.getColumnIndex(Feed.F_TITLE))))
    // userFeeds.settitle(c.getString(c
    // .getColumnIndex(Feed.F_TITLE)));
    // else
    // userFeeds.settitle("");
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    //
    // }
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // return null;
    //
    // }

    private boolean isEmpty(String str) {
        if ((str == null) || str.matches("^\\s*$") || str.matches("")
                || str.matches("null")) {
            return true;
        } else {
            return false;
        }
    }


    public Cursor getallRecordsCursor(String TableName, String sortbyColumn,
                                      String asc_des) {
        Cursor cursor = db.rawQuery("select * from " + TableName + " ORDER BY "
                + sortbyColumn + " " + asc_des + ";", null);

        Log.e("Get data in " + asc_des + " ", "select * from " + TableName
                + " ORDER BY " + sortbyColumn + " " + asc_des + ";");


        return cursor;
    }

    public Cursor getTopRecordFromMaxID_Cursor(String TableName,
                                               String whereColumn) {
        Cursor cursor = db.rawQuery("select * from " + TableName + " where "
                + whereColumn + " = "
                + ("(select MAX(" + whereColumn + ")" + " from " + TableName)
                + ")" + ";", null);

        Log.e("Get data in " + " ", "select * from " + TableName + " where "
                + whereColumn + " = "
                + ("(select MAX(" + whereColumn + ")" + " from " + TableName)
                + ")" + ";");

        Log.e("DBHelper", " allrecords cursor count" + cursor.getCount());

        return cursor;
    }

    public Cursor getallRecordsCursor(String TableName) {
        Cursor cursor = db.rawQuery("select * from " + TableName + "", null);
        Log.e("DBHelper", " allrecords cursor count" + cursor.getCount());
        return cursor;
    }

    public Cursor getallRecordsCursor(String TableName, String whereColumn,
                                      String whereValue, String asc_des) {

        String rawQuery = "select * from " + TableName + " where "
                + whereColumn + " = \'" + whereValue + "\' " + " ORDER BY "
                + whereColumn + " " + asc_des + ";";
        MyLogs.printerror("rawQuery: " + rawQuery);
        Cursor cursor = db.rawQuery(rawQuery, null);
        Log.e("DBHelper", " allrecords cursor count" + cursor.getCount());
        return cursor;
    }

    public Cursor getallRecordsCursor(String TableName, String whereColumn,
                                      String whereValue, String orderbyColumn, String asc_des) {
        Cursor cursor = db.rawQuery("select * from " + TableName + " where "
                + whereColumn + " = \'" + whereValue + "\' " + " ORDER BY "
                + orderbyColumn + " " + asc_des + ";", null);
        Log.e("DBHelper", " allrecords cursor count" + cursor.getCount());
        return cursor;
    }

    public Cursor getallRecordsCursor4FeedsOnlyBigImages(String TableName,
                                                         String whereColumn0, String whereValue0, String whereColumn1,
                                                         String whereValue1, String orderbyColumn, String asc_des, int limit) {

        Cursor cursor = null;
        String query = "select * from " + TableName + " where " + whereColumn0
                + " = \'" + whereValue0 + "\' AND ";
        if (whereValue1.contains(",")) {
            query = query + "(";
            String x[] = whereValue1.split(",");
            for (int i = 0; i < x.length; i++) {
                String s = x[i];
                if (i == (x.length - 1))
                    query = query + whereColumn1 + " = \'" + s + "\'";
                else
                    query = query + whereColumn1 + " = \'" + s + "\' OR ";
            }
            query = query + ")";
        } else {
            query = query + whereColumn1 + " = \'" + whereValue1 + "\'";
        }

        query = query + " ORDER BY " + orderbyColumn + " " + asc_des
                + " LIMIT 0," + limit + ";";

        Log.e("final query", " :" + query);

        cursor = db.rawQuery(query, null);

        Log.e("DBHelper",
                " allrecords cursor count 4 feedsonlyImages"
                        + cursor.getCount());
        return cursor;
    }

    public Cursor getallRecordsCursor4FeedsOnlyNews(String TableName,
                                                    String whereColumn0, String whereValue0, String orderbyColumn,
                                                    String asc_des, int limit) {

        Cursor cursor = null;

        cursor = db.rawQuery("select * from " + TableName + " where "
                        + whereColumn0 + " = \'" + whereValue0 + "\' ORDER BY "
                        + orderbyColumn + " " + asc_des + " LIMIT 0," + limit + ";",
                null);

        Log.e("final query", " :" + "select * from " + TableName + " where "
                + whereColumn0 + " = \'" + whereValue0 + "\' ORDER BY "
                + orderbyColumn + " " + asc_des + " LIMIT 0," + limit + ";");

        Log.e("DBHelper",
                " allrecords cursor count 4 feedsonly" + cursor.getCount());
        return cursor;
    }

    public Cursor getallRecordsCursor4FeedsOnly(String TableName,
                                                String whereColumn1, String whereValue1, String whereColumn2,
                                                int whereValue2, String orderbyColumn, String asc_des) {

        Cursor cursor = null;

        if (whereValue2 < 1) { // In case of World News
            cursor = db.rawQuery("select * from " + TableName + " where "
                    + whereColumn1 + " = \'" + whereValue1 + "\' ORDER BY "
                    + orderbyColumn + " " + asc_des + ";", null);

            Log.e("final query", " :" + "select * from " + TableName
                    + " where " + whereColumn1 + " = \'" + whereValue1
                    + "\' ORDER BY " + orderbyColumn + " " + asc_des + ";");

        } else { // In case of Local News
            cursor = db.rawQuery("select * from " + TableName + " where "
                    + whereColumn1 + " = \'" + whereValue1 + "\' AND ("
                    + whereColumn2 + " = '-1\' OR " + whereColumn2 + " = '"
                    + whereValue2 + "\')" + " ORDER BY " + orderbyColumn + " "
                    + asc_des + ";", null);

            Log.e("final query", " :" + "select * from " + TableName
                    + " where " + whereColumn1 + " = \'" + whereValue1
                    + "\' AND (" + whereColumn2 + " ='-1\' OR " + whereColumn2
                    + " ='" + whereValue2 + "\')" + " ORDER BY "
                    + orderbyColumn + " " + asc_des + ";");

        }
        Log.e("DBHelper",
                " allrecords cursor count 4 feedsonly" + cursor.getCount());
        return cursor;
    }

    public Cursor getallRecordsCursorGT(String TableName, String whereColumn,
                                        String whereValue, String asc_des) {
        Cursor cursor = db.rawQuery("select * from " + TableName + " where "
                + whereColumn + " > \'" + whereValue + "\' " + " ORDER BY "
                + whereColumn + " " + asc_des + ";", null);
        Log.e("DBHelper", " allrecords cursor count" + cursor.getCount());
        return cursor;
    }

    public int deleteRecord(String tableName, String whereColumn,
                            String whereValue) {
        Log.e("deleting record query", "delete from " + tableName + " where "
                + whereColumn + " = " + whereValue + " ;");
        int x = db.delete(tableName, whereColumn + "=" + whereValue, null);
        // Cursor cursor = db.rawQuery("delete from " + tableName + " where "
        // + whereColumn + " = \'" + whereValue + "\';", null);
        Log.e("DBHelper", " deleting  " + " x:" + x);
        return x;
    }

//    public int deleteRecord(String tableName, String whereColumn,
//                            String whereValue, String secondCol, String SecondColValue) {
//        Log.e("deleting record query", "delete from " + tableName + " where "
//                + whereColumn + " = " + whereValue + " ;");
//        int x = db.rawQuery(tableName, whereColumn + " = " + whereValue + " &&" + secondCol + " = " + SecondColValue, null);
//        // Cursor cursor = db.rawQuery("delete from " + tableName + " where "
//        // + whereColumn + " = \'" + whereValue + "\';", null);
//        Log.e("DBHelper", " deleting  " + " x:" + x);
//        return x;
//    }

    public Cursor getRecordCursor(String tableName, int eventid, String date) {

        // Log.i("fetching data query NEW: ", "select * from " + tableName + ";");
        opendb();
        MyLogs.printinfo("Query " + "select * from " + tableName + " where appointment_id = " + eventid + " AND last_date = \'" +
                date+"\';");
        Cursor cursor = db.rawQuery("select * from " + tableName + " where appointment_id = " + eventid + " AND last_date = \'" +
                date+"\';", null);


        closedb();
        Log.e("getRecordCursor", "------- size : " + cursor.getCount());
        return cursor;
    }

    public Cursor getTopRecordCursorWithSortedColumn(String tableName,
                                                     String sortOrder, String sortcolumn, int limit) {

//        Log.i("getSingleRecordCursorWithSortedColumn : ", "select * from "
//                + tableName + " ORDER BY " + sortcolumn + " " + sortOrder
//                + " LIMIT " + limit + " ;");
        opendb();

        Cursor cursor = db
                .rawQuery("select * from " + tableName + " ORDER BY "
                        + sortcolumn + " " + sortOrder + " LIMIT " + limit
                        + " ;", null);

        closedb();
//        Log.e("getSingleRecordCursorWithSortedColumn : ", "------- size : "
        //  + cursor.getCount());
        return cursor;
    }

    public Cursor getRecordCursor(String tableName, String sortOrder,
                                  String sortcolumn, boolean forSortingOnly) {

        opendb();

        Cursor cursor = db.rawQuery("select * from " + tableName + " ORDER BY "
                + sortcolumn + " " + sortOrder + ";", null);

        closedb();
        Log.e("getRecordCursor", "------- size : " + cursor.getCount());
        return cursor;
    }

    public Cursor getRecordCursor(String tableName, String filterColumn,
                                  int filterValue) {


        MyLogs.printinfo("query " + "select * from " + tableName + " where "
                + filterColumn + " = " + filterValue + ";");
        opendb();

        Cursor cursor = db.rawQuery("select * from " + tableName + " where "
                + filterColumn + " = " + filterValue + ";", null);

        closedb();
        Log.e("getRecordCursor", "-------*" + cursor);
        return cursor;
    }

    public Cursor getTagRecordCursor(String tableName, String filterColumn,
                                     int filterValue, String sortOrder, String sortcolumn,
                                     int lastFeedId, int total, int offset) {

        opendb();

        // cursor = db.rawQuery("select * from " + tableName + " where "
        // + filterColumn + " = \'" + filterValue + "\' ORDER BY "
        // + sortcolumn + " " + sortOrder + " ;", null);
        if (lastFeedId == 0) {

            cursor = db.rawQuery("select * from " + tableName + " where "
                    + filterColumn + " = \'" + filterValue + "\' ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT   " + total
                    + " , " + offset + " ;", null);

            Log.i("fetching data query: ", "select * from " + tableName
                    + " where " + filterColumn + " = \'" + filterValue
                    + "\' ORDER BY " + sortcolumn + " " + sortOrder
                    + " LIMIT   " + total + " , " + offset + " ;");

        } else {

            cursor = db.rawQuery("select * from " + tableName + " where "
                    + filterColumn + " = \'" + filterValue + "\' AND id < "
                    + lastFeedId + "  ORDER BY " + sortcolumn + " " + sortOrder
                    + " LIMIT " + total + " , " + offset + " ;", null);


        }

        closedb();
        Log.e("getRecordCursor", "-------**" + cursor);
        return cursor;
    }

    public boolean deleteTableRecord(String tableName) {

        opendb();
        // cursor = db.rawQuery("delete from " + tableName, null);
        int x = db.delete(tableName, null, null);
        Log.i("Deleting data query ", "delete from " + tableName
                + " rows effected : " + x);

        closedb();
        return true;
    }

    public int getMaxIdFromColumn(String tableName, String filterColumn,
                                  int filterValue, String column) {
        opendb();

        Cursor cursor = db.rawQuery("select Max(" + column + ") as " + column
                + " from " + tableName + " where " + filterColumn + " = \'"
                + filterValue + "\';", null);


        if (cursor != null) {

            cursor.moveToFirst();
            int x = cursor.getInt(cursor.getColumnIndex(column));

            Log.i("Max value is ", column + " of - " + tableName + "is : " + x);
            cursor.close();
            return x;
        }

        closedb();
        return -1;
    }

    public Cursor getRecordCursor(String tableName, String filterColumn,
                                  int filterValue, String inwhich, String sortOrder, String sortcolumn) {

        opendb();

        if (!inwhich.equals("")) {

            // if (!sortcolumn.equals("")) {
            // cursor = db.rawQuery("select * from " + tableName + " where "
            // + filterColumn + " = \'" + filterValue + "\'  AND "
            // + Feed.F_GUID + " in (" + inwhich + ") ORDER BY "
            // + sortcolumn + " " + sortOrder + " ;", null);
            //
            // // Log.i("fetching data query NEW: ", "select * from " +
            // // tableName
            // // + " where " + filterColumn + " = \'" + filterValue
            // // + "\'  AND " + Feed.F_GUID + " in (" + inwhich
            // // + ") ORDER BY " + sortcolumn + " " + sortOrder + " ;");
            // }
            //
            // else {
            // cursor = db.rawQuery("select * from " + tableName + " where "
            // + filterColumn + " = \'" + filterValue + "\'  AND "
            // + Feed.F_GUID + " in (" + inwhich + ");", null);
            //
            // // Log.i("fetching data query NEW: ", "select * from " +
            // // tableName
            // // + " where " + filterColumn + " = \'" + filterValue
            // // + "\'  AND " + Feed.F_GUID + " in (" + inwhich + ");");
            // }

        } else {
            cursor = db.rawQuery("select * from " + tableName + ";", null);

        }

        // Log.i("fetching data query NEW: ", "select * from " + tableName
        // + " where " + filterColumn + " = \'" + filterValue + "\'  AND "
        // + Feed.F_GUID + " in (" + inwhich + ");");

        closedb();
        Log.e("getRecordCursor", "-------***" + cursor);
        return cursor;
    }

    public Cursor getRecordCursor(String tableName, String filterColumn,
                                  String filterValue, String inwhich, String sortOrder,
                                  String sortcolumn) {

        opendb();

        if (!inwhich.equals("")) {

            // if (!sortcolumn.equals("")) {
            // cursor = db.rawQuery("select * from " + tableName + " where "
            // + filterColumn + " = \'" + filterValue + "\'  AND "
            // + Feed.F_GUID + " in (" + inwhich + ") ORDER BY "
            // + sortcolumn + " " + sortOrder + " ;", null);
            //
            // // Log.i("fetching data query NEW: ", "select * from " +
            // // tableName
            // // + " where " + filterColumn + " = \'" + filterValue
            // // + "\'  AND " + Feed.F_GUID + " in (" + inwhich
            // // + ") ORDER BY " + sortcolumn + " " + sortOrder + " ;");
            // }
            //
            // else {
            // cursor = db.rawQuery("select * from " + tableName + " where "
            // + filterColumn + " = \'" + filterValue + "\'  AND "
            // + Feed.F_GUID + " in (" + inwhich + ");", null);
            //
            // // Log.i("fetching data query NEW: ", "select * from " +
            // // tableName
            // // + " where " + filterColumn + " = \'" + filterValue
            // // + "\'  AND " + Feed.F_GUID + " in (" + inwhich + ");");
            // }

        } else {
            cursor = db.rawQuery("select * from " + tableName + ";", null);
        }

        // Log.i("fetching data query NEW: ", "select * from " + tableName
        // + " where " + filterColumn + " = \'" + filterValue + "\'  AND "
        // + Feed.F_GUID + " in (" + inwhich + ");");

        closedb();
        Log.e("getRecordCursor", "-------***" + cursor);
        return cursor;
    }

    public Cursor getRecordCursor(String tableName, String filterColumn,
                                  String filterValue, String sortOrder, String sortcolumn) {

        opendb();

        cursor = db.rawQuery("select * from " + tableName + " where "
                + filterColumn + " = \'" + filterValue + "\' ORDER BY "
                + sortcolumn + " " + sortOrder + " ;", null);


        closedb();
        Log.e("getRecordCursor", "-------****" + cursor);
        return cursor;
    }

    public Cursor getSingleRecordCursor(String tableName, String columnName,
                                        String columnValue) {

        Log.i("fetching data query : ", "select * from " + tableName
                + " where " + columnName + "= \"" + columnValue + "\";");
        opendb();

        Cursor cursor = db.rawQuery("select * from " + tableName + " where "
                + columnName + " = \'" + columnValue + "\';", null);

        closedb();

        return cursor;
    }

    public ArrayList<String> getUniqueRecords(String tableName,
                                              String columnName) {

        opendb();

        Cursor mCursor = db.rawQuery("SELECT DISTINCT " + columnName + " as "
                + columnName + " FROM " + tableName, null);

        ArrayList<String> mArrayList = new ArrayList<String>();

        if (mCursor != null) {
            if (mCursor.moveToFirst())
                do {
                    try {
                        mArrayList.add(mCursor.getString(mCursor
                                .getColumnIndex(columnName)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (mCursor.moveToNext());
        }

        closedb();

        return mArrayList;
    }

    public ArrayList<String[]> getUniqueRecords(String tableName,
                                                String column4uniqueRecords, String[] req_columns) {

        opendb();

        Cursor mCursor = db.query(true, tableName, req_columns, null, null,
                column4uniqueRecords, null, null, null);

        // Cursor mCursor = db.rawQuery("SELECT DISTINCT " + columnName1 + " , "
        // + columnName2 + " as " + columnName1 + " , " + columnName2
        // + " FROM " + tableName, null);

        ArrayList<String[]> mArrayList = new ArrayList<String[]>();

        if (mCursor != null) {
            if (mCursor.moveToFirst())
                do {
                    try {

                        String[] r = new String[req_columns.length];
                        for (int i = 0; i < req_columns.length; i++) {
                            r[i] = mCursor.getString(mCursor
                                    .getColumnIndex(req_columns[i]));
                        }

                        mArrayList.add(r);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (mCursor.moveToNext());
        }

        closedb();

        return mArrayList;
    }

    public Cursor getPagingRecordsCursor(String tableName, String sortOrder,
                                         String sortcolumn, String whereColumn, String whereValue,
                                         int total, int offset) {

        opendb();

        // cursor = db.rawQuery("select * from " + tableName + " where "
        // + filterColumn + " = \'" + filterValue + "\' ORDER BY "
        // + sortcolumn + " " + sortOrder + " ;", null);
        if (whereValue.equals("0")) {

            cursor = db.rawQuery("select * from " + tableName + " ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT " + total + " , "
                    + offset + " ;", null);

            Log.i("paging : ", "select * from " + tableName + " ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT " + total + " , "
                    + offset + " ;");

        } else {

            cursor = db.rawQuery("select * from " + tableName + " where "
                    + whereColumn + " < " + whereValue + " ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT " + total + " , "
                    + offset + " ;", null);

            Log.i("paging : ", "select * from " + tableName + " where "
                    + whereColumn + " < " + whereValue + " ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT " + total + " , "
                    + offset + " ;");

        }

        closedb();
        Log.e("getRecordCursor", "-------**" + cursor);
        return cursor;
    }

    public Cursor getAllRecordsCursorUsingRawQuery(String rawQuery) {

        opendb();
        Log.i("Cursor4rawQuery", " -> " + rawQuery);
        cursor = db.rawQuery(rawQuery, null);
        closedb();
        return cursor;
    }

    public Cursor getLimitedRecordsCursor(String tableName, String sortOrder,
                                          String sortcolumn, String whereColumn, String whereValue, int total) {

        opendb();

        // cursor = db.rawQuery("select * from " + tableName + " where "
        // + filterColumn + " = \'" + filterValue + "\' ORDER BY "
        // + sortcolumn + " " + sortOrder + " ;", null);
        if (whereValue.equals("0")) {

            cursor = db.rawQuery("select * from " + tableName + " ORDER BY "
                            + sortcolumn + " " + sortOrder + " LIMIT " + total + " ;",
                    null);

            Log.i("paging : ", "select * from " + tableName + " ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT " + total + " ;");

        } else {

            cursor = db.rawQuery("select * from " + tableName + " where "
                            + whereColumn + " < " + whereValue + " ORDER BY "
                            + sortcolumn + " " + sortOrder + " LIMIT " + total + " ;",
                    null);

            Log.i("paging : ", "select * from " + tableName + " where "
                    + whereColumn + " < " + whereValue + " ORDER BY "
                    + sortcolumn + " " + sortOrder + " LIMIT " + total + " ;");

        }

        closedb();
        Log.e("getRecordCursor", "-------**" + cursor);
        return cursor;
    }

}