package org.vanguardmatrix.engine.xpath.parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import org.vanguardmatrix.engine.android.data.DatabaseManager;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class Database {

    static Database dbObject;
    static Context context;
    static ArrayList<String> tableKeys;
    static ArrayList<Tables> mytables = new ArrayList<Tables>();
    InputStream inputStream;
    Document xmlDocument;
    ArrayList<String> mytablesNames = new ArrayList<String>();
    Map<String, Tables> _tableList = new HashMap<String, Tables>();
    String dbName = "pingem";
    int dbVersion = 1;

    private Database(Context context) {
        if (!DatabaseManager.dbFileName.contains(".xml")) {
            DatabaseManager.dbFileName = DatabaseManager.dbFileName + ".xml";
        }

        try {
            AssetManager assetManager = context.getResources().getAssets();

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                    .newInstance();

            inputStream = assetManager.open(DatabaseManager.dbFileName);

            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            xmlDocument = builder.parse(inputStream);

            inputStream.close();
            // new String(new byte[inputStream.available()]);
        } catch (Exception e) {
            e.printStackTrace();

            try {

                // AssetManager assetManager = HomeScreenOld.activity.getResources()
                //.getAssets();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                        .newInstance();

                //inputStream = assetManager.open(DatabaseManager.dbFileName);

                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                xmlDocument = builder.parse(inputStream);

                inputStream.close();
            } catch (Exception e2) {
                e.printStackTrace();
            }

        }

        tableKeys = new ArrayList<String>();

        try {

            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList nodeList = (NodeList) xPath.compile("/database/dbname")
                    .evaluate(xmlDocument, XPathConstants.NODESET);
            //Log.e("No of nodes database/table :", "" + nodeList.getLength());
            NodeList nodeLists = (NodeList) xPath.compile("/database/version")
                    .evaluate(xmlDocument, XPathConstants.NODESET);

            dbName = nodeList.item(0).getFirstChild().getNodeValue().toString();

            dbVersion = Integer.parseInt(nodeLists.item(0).getFirstChild()
                    .getNodeValue().toString());

            System.out.println("<<<<!!!!>>>>>" + dbVersion);

            NodeList tables = (NodeList) xPath.compile("database/*").evaluate(
                    xmlDocument, XPathConstants.NODESET);

            for (int k = 0; k < tables.getLength(); k++) {
                if (isNotTextNode(tables.item(k).getNodeName().toString())) {

                    String tag = tables.item(k).getNodeName().toString();
                    if (tables.item(k).getFirstChild().getNodeValue() == null) {
                        System.out.println("Database - " + tag + " - null");
                    } else if (!tag.equalsIgnoreCase("DBNAME")
                            && !tag.equalsIgnoreCase("VERSION")) {

                        NodeList theNodeList2 = tables.item(k).getChildNodes();

                        for (int i = 0; i < theNodeList2.getLength(); i++) {
                            if (isNotTextNode(theNodeList2.item(i)
                                    .getNodeName().toString())) {

                                if (theNodeList2.item(i).getFirstChild()
                                        .getNodeValue() == null) {
                                } else {

                                    String tabTag = theNodeList2.item(i)
                                            .getNodeName().toString();

                                    if (tabTag.equalsIgnoreCase("tname")) {
                                        // System.out
                                        // .println("Database - TABLE - "
                                        // + tabTag
                                        // + " - "
                                        // + theNodeList2
                                        // .item(i)
                                        // .getFirstChild()
                                        // .getNodeValue()
                                        // .toString());

                                        tableKeys.add(theNodeList2.item(i)
                                                .getFirstChild().getNodeValue()
                                                .toString());
                                        Tables table = new Tables(
                                                tables.item(k));

                                        mytables.add(table);
                                        mytablesNames.add(theNodeList2.item(i)
                                                .getFirstChild().getNodeValue()
                                                .toString());

                                        _tableList.put(theNodeList2.item(i)
                                                .getFirstChild().getNodeValue()
                                                .toString(), table);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Log.e("_tableList.size();", "" + _tableList.size());
            // Log.e("aw table 0 ka column 2 : ",
            // mytables.get(0).myColumns.get(2)
            // .getfieldName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance(Context context) {

        if (dbObject == null) {

//            SQLiteDatabase database = null;
//            File mDatabaseFile = new File(Environment.getDataDirectory()
//                    + "/" + DatabaseManager.dbFileName);
////            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DbFile, "pass123", null);
//
//            File dbDir = context.getFilesDir();//Dir("tests", Context.MODE_PRIVATE);
//            mDatabaseFile = new File(dbDir, DatabaseManager.dbFileName + ".db");
//
//            if (!mDatabaseFile.exists()) {
//                mDatabaseFile.mkdir();
//            }
//
//            database = SQLiteDatabase.openOrCreateDatabase(mDatabaseFile.getPath(), null);
//
////            dbObject = database;

            dbObject = new Database(context);

            Database.context = context;
        }
        return dbObject;
    }

    public Map<String, Tables> getTables() {
        return _tableList;
    }

    public void setTables(Map<String, Tables> _tables) {
        this._tableList = _tables;
    }

    public Tables getTable(String key) {
        return this._tableList.get(key);
    }

    public Tables getTable(int key) {
        return this._tableList.get(tableKeys.get(key));
    }

    public String[] getTablesAllColumn(int key) {
        // Set<String> allUserColumns =
        // dbObject.getTable(1).getColumns().keySet();
        Set<String> allColumn = getTable(key).getColumns().keySet();

        String[] allColumnSA = new String[allColumn.size()];
        return allColumnSA = allColumn.toArray(allColumnSA);
    }

    private boolean isNotTextNode(String string) {
        if (string.equals("#text") || string.equals("#comment")
                || string.equals(""))
            return false;
        return true;
    }
}