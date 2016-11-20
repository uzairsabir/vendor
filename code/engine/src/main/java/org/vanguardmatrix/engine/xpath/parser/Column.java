package org.vanguardmatrix.engine.xpath.parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Column {

    private String _fName;
    private String _dataType;
    private String _primaryKey;
    private String _autoIncr;

    public Column(String fname, String dType, String pk, String ai) {
        _fName = fname;
        _dataType = dType;
        _primaryKey = pk;
        _autoIncr = ai;
    }

    public Column(Node node) {
        NodeList theNodeList = node.getChildNodes();

        for (int k = 0; k < theNodeList.getLength(); k++) {
            if (isNotTextNode(theNodeList.item(k).getNodeName().toString())) {
                String tag = theNodeList.item(k).getNodeName().toString();

                if (theNodeList.item(k).getFirstChild().getNodeValue() == null) {
                    // System.out.println("Column - " + tag + " - null");
                } else {
                    // System.out.println("Column - "
                    // + tag
                    // + " - "
                    // + theNodeList.item(k).getFirstChild()
                    // .getNodeValue().toString());

                    if (tag.equalsIgnoreCase("FNAME")) {
                        _fName = theNodeList.item(k).getFirstChild()
                                .getNodeValue().toString();
                    } else if (tag.equalsIgnoreCase("DT")) {
                        _dataType = theNodeList.item(k).getFirstChild()
                                .getNodeValue().toString();
                    } else if (tag.equalsIgnoreCase("PK")) {
                        _primaryKey = theNodeList.item(k).getFirstChild()
                                .getNodeValue().toString();
                    } else if (tag.equalsIgnoreCase("AI")) {
                        _autoIncr = theNodeList.item(k).getFirstChild()
                                .getNodeValue().toString();
                    }
                }
            }
        }
    }

    public String getfieldName() {
        return _fName;
    }

    public void setfieldName(String fname) {
        _fName = fname;
    }

    public String getdataType() {
        return _dataType;
    }

    public void setdataType(String dataType) {
        _dataType = dataType;
    }

    public String getPK() {
        return _primaryKey;
    }

    public void setPK(String pk) {
        _primaryKey = pk;
    }

    public String getAI() {
        return _autoIncr;
    }

    public void setAI(String autoInc) {
        _autoIncr = autoInc;
    }

    private boolean isNotTextNode(String string) {
        if (string.equals("#text") || string.equals("#comment")
                || string.equals(""))
            return false;
        return true;
    }
}