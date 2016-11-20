package org.vanguardmatrix.engine.xpath.parser;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.datatypes.Contact;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tables {

    ArrayList<Column> myColumns = new ArrayList<Column>();
    String _fieldName, _dataType;
    String _primaryKey, _autoIncrement;
    @SuppressWarnings("unused")
    private String _tableName, firstName, lastName, country;
    private ArrayList<String> columnKeys;
    private Map<String, Column> _columnList = new HashMap<String, Column>();

    public Tables(Node node) {

        columnKeys = new ArrayList<String>();
        NodeList theNodeList = node.getChildNodes();

        for (int k = 0; k < theNodeList.getLength(); k++) {
            if (isNotTextNode(theNodeList.item(k).getNodeName().toString())) {

                String tag = theNodeList.item(k).getNodeName().toString();

                if (theNodeList.item(k).getFirstChild().getNodeValue() == null) {
                    System.out.println("Tables - " + tag + " - null");
                } else {
                    if (tag.equalsIgnoreCase("TNAME")) {
                        _tableName = theNodeList.item(k).getFirstChild()
                                .getNodeValue().toString();
                    } else {
                        NodeList theNodeList2 = theNodeList.item(k)
                                .getChildNodes();

                        String createTableQuery = "INSERT INTO contacts (";

                        for (int i = 0; i < theNodeList2.getLength(); i++) {
                            if (isNotTextNode(theNodeList2.item(i)
                                    .getNodeName().toString())) {

                                if (theNodeList2.item(i).getFirstChild()
                                        .getNodeValue() == null) {
                                } else {
                                    String colTag = theNodeList2.item(i)
                                            .getNodeName().toString();

                                    if (colTag.equalsIgnoreCase("fname")) {
                                        // System.out.println("Table - Column - "
                                        // + colTag
                                        // + " - "
                                        // + theNodeList2.item(i)
                                        // .getFirstChild()
                                        // .getNodeValue()
                                        // .toString());

                                        columnKeys.add(theNodeList2.item(i)
                                                .getFirstChild().getNodeValue()
                                                .toString());

                                        Column column = new Column(
                                                theNodeList.item(k));

                                        // Log.e("aw tables",
                                        // ", columsn attrib  : "
                                        // + column.getfieldName()
                                        // + ",,,, dt : "
                                        // + column.getdataType());

                                        createTableQuery = createTableQuery
                                                + column.getfieldName() + " "
                                                + column.getdataType() + ",";

                                        myColumns.add(column);

                                        columnKeys.add(theNodeList2.item(i)
                                                .getFirstChild().getNodeValue()
                                                .toString());
                                        _columnList.put(theNodeList2.item(i)
                                                .getFirstChild().getNodeValue()
                                                .toString(), column);

                                    }
                                }
                            }

                        }

                        createTableQuery = createTableQuery.substring(0,
                                createTableQuery.length() - 1) + ");";
                        // Log.e("insert table Query " , createTableQuery);
                    }

                }
            }
        }
        Log.e("_columnList.size();", "" + _columnList.size());
    }

    public void setTablename(String tableName) {
        _tableName = tableName;
    }

    public String getTableName() {
        return _tableName;
    }

    public Map<String, Column> getColumns() {
        return this._columnList;
    }

    public Column getColumn(String key) {
        return this._columnList.get(key);
    }

    public Column getColumn(int key) {
        return this._columnList.get(columnKeys.get(key));
    }

    private boolean isNotTextNode(String string) {
        if (string.equals("#text") || string.equals("#comment")
                || string.equals(""))
            return false;
        return true;
    }

    @SuppressWarnings("deprecation")
    public int addRecordItem(JSONObject jsonObject, String filterValue,
                             DBHelper dbHelper, String tableNAME) {

        String query = "INSERT INTO " + tableNAME + " ";
        String tableName = "(";
        String value = "VALUES (";

        ContentValues values = new ContentValues();

        for (Map.Entry<String, Column> entry : _columnList.entrySet()) {

            if (entry.getKey() != null) {
                try {

                    values.put(entry.getKey(),
                            jsonObject.getString(entry.getKey()));
                    tableName = tableName + entry.getKey() + ", ";
                    value = value + "'" + jsonObject.getString(entry.getKey())
                            + "'" + ", ";

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        tableName = tableName.substring(0, tableName.length() - 2) + ")  ";
        value = value.substring(0, value.length() - 2) + ");";

        query = query + tableName + value;

        try {

            dbHelper.opendb();

            long x = dbHelper.db.insert(tableNAME, null, values);
            Log.e("Record created  ", " --   " + x + "  --  " + query);

            dbHelper.closedb();

            return (int) x;

        } catch (Exception e) {
            e.printStackTrace();
            dbHelper.closedb();
        }

        return -1;

    }

    @SuppressWarnings("deprecation")
    public long addRecordOnly(JSONObject contact, String filterValue,
                              DBHelper dbHelper, String tableNAME) {

        String query = "INSERT INTO " + tableNAME + " ";
        String tableName = "(";
        String value = "VALUES (";

        ContentValues values = new ContentValues();

        for (Map.Entry<String, Column> entry : _columnList.entrySet()) {

            if (entry.getKey() != null) {
                try {
                    if (entry.getKey().contains(Contact.ATTR_SOCIAL_URLS))
                        values.put(entry.getKey(), URLEncoder.encode(contact
                                .getString(entry.getKey())));
                    else
                        values.put(entry.getKey(),
                                contact.getString(entry.getKey()));
                    tableName = tableName + entry.getKey() + ", ";
                    value = value + "'" + contact.getString(entry.getKey())
                            + "'" + ", ";

                } catch (JSONException e) {
                    // e.printStackTrace();
                }
            }

        }

        tableName = tableName.substring(0, tableName.length() - 2) + ")  ";
        value = value.substring(0, value.length() - 2) + ");";

        query = query + tableName + value;

        try {

            dbHelper.opendb();

            // Log.e("My Sql Query for insert", "" + values);

            String strFilter = "";

//            if (tableNAME.equals(Contact.TABLE_NAME)) {
//                strFilter = Contact.ATTR_PHONE_NUMBER + "=\'" + filterValue
//                        + "\'";
//            } else if (tableNAME.equals(PhoneContact.TABLE_NAME)) {
//                strFilter = PhoneContact.ID + "=\'" + filterValue + "\'";
//            } else if (tableNAME.equals(GroupItem.M_TABLE_NAME)) {
//                strFilter = GroupItem.M_ID + "=\'" + filterValue + "\'";
////            } else if (tableNAME.equals(ReportCategory.C_TABLE_NAME)) {
////                strFilter = ReportCategory.C_ID + "=\'" + filterValue + "\'";
//            } else if (tableNAME.equals(GcmNotificationsModel.N_TABLE)) {
//                strFilter = GcmNotificationsModel.N_ID + "=\'" + filterValue
//                        + "\'";
//            }
            Log.e("strFilter -  ", strFilter);

            // int updateResult = dbHelper.db.update(tableNAME, values,
            // strFilter,
            // null);
            // if (updateResult == 0) {
            long x = dbHelper.db.insert(tableNAME, null, values);
            Log.e("Record created  ", " --   " + x + "  --  " + query);
            // } else {
            // Log.e("Record Updated", "" + updateResult + " -- " + query);
            // }

            dbHelper.closedb();

            return x;

        } catch (Exception e) {
            e.printStackTrace();
            dbHelper.closedb();
        }

        return -1;

    }

}