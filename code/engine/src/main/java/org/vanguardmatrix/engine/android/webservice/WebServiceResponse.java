/*
 * @author: Shan-e-Raza
 * Dated: 24th January, 2013
 */

package org.vanguardmatrix.engine.android.webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WebServiceResponse {
    private String _wsResponse;

    public WebServiceResponse(String strResponse) {
        this.setWSResponse(strResponse);
    }

    private String getWSResponse() {
        return this._wsResponse;
    }

    private void setWSResponse(String _wsResponse) {
        this._wsResponse = _wsResponse;
    }

    public JSONObject extractJSONObject() {
        JSONObject jsonObject;

        // Log.d("the response is", this._wsResponse);
        try {
            jsonObject = new JSONObject(this.getWSResponse());
        } catch (JSONException e) {
            jsonObject = null;
        }

        // If JSON Object is null then it means that the string didn't had a
        // valid JSON
        return jsonObject;
    }

    public JSONArray extractJSONArray() {
        JSONArray jSONArray;

        // Log.d("the response is", this._wsResponse);
        try {
            jSONArray = new JSONArray(this.getWSResponse());
        } catch (JSONException e) {
            jSONArray = null;
        }

        // If JSON Object is null then it means that the string didn't had a
        // valid JSON
        return jSONArray;
    }

    /**
     * @return WebServiceResponse in form of an XML Document
     * @author Ali W. Jafri
     * @Date 27th February, 2013
     */
    public Document extractXMLDocument() {
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(
                    this.getWSResponse()));
            doc = builder.parse(is);
        } catch (IOException e) {
            doc = null;
        } catch (ParserConfigurationException e) {
            doc = null;
        } catch (SAXException e) {
            doc = null;
        }

        return doc;
    }

    public String getString() {
        return _wsResponse;
    }
}
