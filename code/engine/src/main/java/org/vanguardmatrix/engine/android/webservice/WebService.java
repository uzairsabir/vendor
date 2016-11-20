/*
 * @author: Shan-e-Raza
 * Dated: 7th January, 2013
 */

package org.vanguardmatrix.engine.android.webservice;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.Constants;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

public class WebService {

    static ResponseHandlerInterface rHandler = new ResponseHandlerInterface() {

        @Override
        public void sendSuccessMessage(int arg0, Header[] arg1, byte[] arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendStartMessage() {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendRetryMessage(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendResponseMessage(HttpResponse arg0) throws IOException {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendProgressMessage(int arg0, int arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendFinishMessage() {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendFailureMessage(int arg0, Header[] arg1, byte[] arg2,
                                       Throwable arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void sendCancelMessage() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPreProcessResponse(ResponseHandlerInterface arg0,
                                         HttpResponse arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPostProcessResponse(ResponseHandlerInterface arg0,
                                          HttpResponse arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean getUseSynchronousMode() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void setUseSynchronousMode(boolean arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public URI getRequestURI() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setRequestURI(URI arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public Header[] getRequestHeaders() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setRequestHeaders(Header[] arg0) {
            // TODO Auto-generated method stub

        }
    };
    private static DefaultHttpClient httpClient;

    // private static AndroidHttpClient httpClient;
    private static AsyncHttpClient asyncHttpClient;

    // public static DefaultHttpClient getDefaultHTTPClient() {
    // return new DefaultHttpClient(new BasicHttpParams());
    // }

    public static void resetHttpClient() {
        WebService.httpClient = null;
    }

    public static DefaultHttpClient getDefaultHTTPClient() {
//        if (WebService.httpClient == null)
//            WebService.httpClient = new DefaultHttpClient(new BasicHttpParams());
//        return WebService.httpClient;
        return new DefaultHttpClient(new BasicHttpParams());
    }

    public static AsyncHttpClient getAsyncHTTPClient() {
        if (WebService.asyncHttpClient == null)
            WebService.asyncHttpClient = new AsyncHttpClient();
        return WebService.asyncHttpClient;
    }

    // public static HttpClient createHttpClient(){
    // try {
    // KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
    // trustStore.load(null, null);
    //
    // SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
    // sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    //
    // HttpParams params = new BasicHttpParams();
    // HttpConnectionParams.setConnectionTimeout(params, 15000);
    // HttpConnectionParams.setSoTimeout(params, 5000);
    //
    // SchemeRegistry registry = new SchemeRegistry();
    // registry.register(new Scheme("http",
    // PlainSocketFactory.getSocketFactory(), 80));
    // registry.register(new Scheme("https", sf, 443));
    //
    // ClientConnectionManager ccm = new ThreadSafeClientConnManager(params,
    // registry);
    //
    // return new DefaultHttpClient(ccm, params);
    // } catch (Exception e) {
    // return new DefaultHttpClient();
    // }
    // }

    // public static AndroidHttpClient getDefaultHTTPClient() {
    // if (WebService.httpClient == null)
    // WebService.httpClient = AndroidHttpClient.newInstance("Android");
    // return WebService.httpClient;
    // }


    public static WebServiceResponse callHTTPPost(String serviceMethod_inURL,
                                                  List<NameValuePair> params) {
        return WebService.callHTTPPost(serviceMethod_inURL, params, false);
    }

    public static WebServiceResponse callHTTPPost(String serviceMethod_inURL,
                                                  List<NameValuePair> params, List<NameValuePair> nonsignatureparams,
                                                  boolean absoluteURL) {
        // public static WebServiceResponse callHTTPPost(String webserviceURL,
        // List<NameValuePair> params, boolean absoluteURL) {
        String responseDataString = "";

        // Concatenating Base URL with Service URL
        String webserviceURL = (absoluteURL ? serviceMethod_inURL : Constants
                .getPublicUrl() + serviceMethod_inURL);

        // webserviceURL = Constants.XMPP_BASE_URL + Constants.getSubUrl()
        // + serviceMethod_inURL;
        System.out.println(webserviceURL);

        HttpPost postRequest = new HttpPost(webserviceURL);

        try {

            //           params.add(new BasicNameValuePair("email", UserEmailFetcher.getEmail(ActivityManager.getInstance().getRunningActivity())));
//            params.add(new BasicNameValuePair("macAddress", UtilityFunctions.getDeviceMacAddress(ActivityManager.getInstance().getRunningActivity())));
            // params.add(new BasicNameValuePair("app_id", appConstants.app_id));

//            ArrayList<NameValuePair> non_sign_params = new ArrayList<NameValuePair>();
//            non_sign_params.add(new BasicNameValuePair("deviceId", ZaairController.getDeviceGCMID()));
//            non_sign_params.add(new BasicNameValuePair("deviceGmail", UserEmailFetcher.getEmail(ActivityManager.getInstance().getRunningActivity())));
//            non_sign_params.add(new BasicNameValuePair("deviceMacAddress", UtilityFunctions.getDeviceMacAddress(ActivityManager.getInstance().getRunningActivity())));

            String ss = "";

            if (serviceMethod_inURL.equals(Constants.update_paid_status)) {
                ss = ss + "1";
            }

            for (NameValuePair nvp : params) {
                // String name = nvp.getName();
                String value = nvp.getValue();
                ss = ss + value;
            }

            // Log.e("ss", ": " + ss);

            JSONObject json = new JSONObject();
            for (NameValuePair nvp : params) {
                String name = nvp.getName();
                String value = nvp.getValue();
                try {
                    json.put(name, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (nonsignatureparams != null)
                for (NameValuePair nvpnsp : nonsignatureparams) {
                    String nsp_name = nvpnsp.getName();
                    String nsp_value = nvpnsp.getValue();
                    try {
                        json.put(nsp_name, nsp_value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            try {
                json.put("signature", get_MD5(ss + Constants.testingParameter));

                if (serviceMethod_inURL.equals(Constants.update_paid_status)) {
                    MyLogs.printerror("signature params : " + ss + "\n salt " + Constants.testingParameter
                            + "\n Signature : " + get_MD5(ss + Constants.testingParameter));
                }

//                json.put("profile_name", AppPreferences
//                        .getString(AppPreferences.PREF_FIRSTNAME)
//                        + " " + AppPreferences
//                        .getString(AppPreferences.PREF_LASTNAME));
                // json.put("signature", get_MD5(ss +
                // Constants.testingParameter));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json.toString());
            String prettyJsonString = gson.toJson(je);
            Log.e("service json", prettyJsonString.toString());

            // Setting Post Request Parameters
            postRequest.setHeader("Content-Type", "application/json");
            // postRequest.setEntity(new UrlEncodedFormEntity(params));
            postRequest.setEntity(new StringEntity(json.toString()));

            // Calling WebService
            HttpResponse serviceResponse = WebService.getDefaultHTTPClient()
                    .execute(postRequest);

            // Extracting contents from Response Object
            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(serviceResponse.getEntity()
                            .getContent()));

            // Converting the response to a Single String
            String tmpString = null;
            while ((tmpString = bufferReader.readLine()) != null)
                responseDataString += tmpString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("post response : " + responseDataString);

        return new WebServiceResponse(responseDataString);
    }

    public static WebServiceResponse callHTTPPost(String serviceMethod_inURL,
                                                  List<NameValuePair> params, boolean absoluteURL) {
        return WebService.callHTTPPost(serviceMethod_inURL, params, null, absoluteURL);
        // public static WebServiceResponse callHTTPPost(String webserviceURL,
//        // List<NameValuePair> params, boolean absoluteURL) {
//        String responseDataString = "";
//
//        // Concatenating Base URL with Service URL
//        String webserviceURL = (absoluteURL ? serviceMethod_inURL : Constants
//                .getPublicUrl() + serviceMethod_inURL);
//
//        // webserviceURL = Constants.XMPP_BASE_URL + Constants.getSubUrl()
//        // + serviceMethod_inURL;
//        System.out.println(webserviceURL);
//
//        HttpPost postRequest = new HttpPost(webserviceURL);
//
//        try {
//
//            String ss = "";
//
//            for (NameValuePair nvp : params) {
//                // String name = nvp.getName();
//                String value = nvp.getValue();
//                ss = ss + value;
//            }
//
//            // Log.e("ss", ": " + ss);
//
//            JSONObject json = new JSONObject();
//            for (NameValuePair nvp : params) {
//                String name = nvp.getName();
//                String value = nvp.getValue();
//                try {
//                    json.put(name, value);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                json.put("signature", get_MD5(ss + Constants.testingParameter));
//                json.put("profile_name", AppPreferences
//                        .getString(AppPreferences.PREF_FIRSTNAME)
//                        + " " + AppPreferences
//                        .getString(AppPreferences.PREF_LASTNAME));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            JsonParser jp = new JsonParser();
//            JsonElement je = jp.parse(json.toString());
//            String prettyJsonString = gson.toJson(je);
//            Log.e("service json", prettyJsonString.toString());
//
//            // Setting Post Request Parameters
//            postRequest.setHeader("Content-Type", "application/json");
//            // postRequest.setEntity(new UrlEncodedFormEntity(params));
//            postRequest.setEntity(new StringEntity(json.toString()));
//
//            // Calling WebService
//            HttpResponse serviceResponse = WebService.getDefaultHTTPClient()
//                    .execute(postRequest);
//
//            // Extracting contents from Response Object
//            BufferedReader bufferReader = new BufferedReader(
//                    new InputStreamReader(serviceResponse.getEntity()
//                            .getContent()));
//
//            // Converting the response to a Single String
//            String tmpString = null;
//            while ((tmpString = bufferReader.readLine()) != null)
//                responseDataString += tmpString;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        System.out.println("post response : " + responseDataString);
//
//        return new WebServiceResponse(responseDataString);
//
    }

    public static WebServiceResponse callHTTPPost_uploadMedia3(Context context,
                                                               String serviceMethod_inURL, List<String> params,
                                                               List<String> mediaPaths, boolean absoluteURL) {

        String responseDataString = "";
        // Concatenating Base URL with Service URL
        String webserviceURL = (absoluteURL ? serviceMethod_inURL : Constants
                .getPublicUrl() + serviceMethod_inURL);

        // HttpContext localContext = new BasicHttpContext();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpPost postRequest = null;

        try {

            String sub_url = "";
            String parameters = "";
            String signature = "";

            for (String s : params) {
                parameters = parameters + s;
                sub_url = sub_url + "/" + s;
            }

            Log.e("params4 signature", ": " + parameters);

            signature = get_MD5(parameters + Constants.testingParameter);

            sub_url = sub_url + "/" + signature;

            postRequest = new HttpPost(webserviceURL + sub_url);
            // postRequest.setHeader(HTTP.CONTENT_TYPE, "multipart/form-data");

            System.out.println(webserviceURL + sub_url);

            // MultipartEntity entity = new MultipartEntity(
            // HttpMultipartMode.BROWSER_COMPATIBLE);
            // MultipartEntity entity = new MultipartEntity();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            for (int i = 0; i < mediaPaths.size(); i++) {
                // for (String mediaPath : mediaPaths) {
                File tmpFile = new File(Uri.parse(mediaPaths.get(i)).getPath());
                String fileName = "";

                if (tmpFile.exists()) {

                    try {
                        String[] fileNameIndex = mediaPaths.get(i).split("/");
                        fileName = fileNameIndex[fileNameIndex.length - 1];

                        ContentBody cbFile = new FileBody(tmpFile,
                                ContentType.APPLICATION_OCTET_STREAM, fileName);
                        // Log.e("uploading File f", cbFile.getContentLength());
                        // entity.addPart(fileName, cbFile);
                        // entity.addPart(fileName, cbFile);

                        builder.addPart(fileName, cbFile);

                        // builder.addPart(fileName + "1", cbFile);

                        // entity.addPart("file_" + (i + 1), new
                        // FileBody(tmpFile,
                        // "image/jpeg"));
                        Log.e("uploading File found ", ": " + fileName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    MyLogs.printinfo("uploading File not exist : " + mediaPaths.get(i));
                }
            }

            HttpEntity entity = builder.build();

            postRequest.setEntity(entity);

            // Calling WebService
            responseDataString = WebService.getAsyncHTTPClient()
                    .getHttpClient().execute(postRequest, responseHandler);

            // WebService.getDefaultHTTPClient().post(context,
            // webserviceURL + sub_url, entity, "multipart/form-data",
            // rHandler);

            // // Extracting contents from Response Object
            // BufferedReader bufferReader = new BufferedReader(
            // new InputStreamReader(serviceResponse.getEntity()
            // .getContent()));
            //
            // // Converting the response to a Single String
            // String tmpString = null;
            // while ((tmpString = bufferReader.readLine()) != null)
            // responseDataString += tmpString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("post response : " + responseDataString);

        return new WebServiceResponse(responseDataString);
    }

    public static WebServiceResponse callHTTPPost_uploadMedia2(
            String serviceMethod_inURL, List<String> params,
            List<String> mediaPaths, boolean absoluteURL) {

        int serverResponseCode = 0;
        String responseDataString = "";

        // Concatenating Base URL with Service URL
        String webserviceURL = (absoluteURL ? serviceMethod_inURL : Constants
                .getPublicUrl() + serviceMethod_inURL);

        String sub_url = "";
        String parameters = "";
        String signature = "";

        for (String s : params) {
            parameters = parameters + s;
            sub_url = sub_url + "/" + s;
        }

        Log.e("params4 signature", ": " + parameters);

        signature = get_MD5(parameters + Constants.testingParameter);

        sub_url = sub_url + "/" + signature;

        // postRequest = new HttpPost(webserviceURL + sub_url);
        // postRequest.setHeader(HTTP.CONTENT_TYPE, "multipart/form-data");

        System.out.println(webserviceURL + sub_url);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        FileInputStream fileInputStream = null;

        try {
            File sourceFile = new File(mediaPaths.get(0));

            // }

            if (sourceFile.exists()) {

                Log.e("File  found", " continue : ");
                fileInputStream = new FileInputStream(sourceFile);

            } else {
                Log.e("File not found", "Exception : ");
                return null;
            }

            URL url = new URL(webserviceURL + sub_url);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", "image1.jpg");

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=" + "image"
                    + ";filename=" + "image1.jpg" + "" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
                    + ": " + serverResponseCode);

            if (serverResponseCode == HttpURLConnection.HTTP_OK) {
                String msg = "File Upload Completed. ";
                Log.e("File Upload Complete.", msg);

                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {

            ex.printStackTrace();

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            e.printStackTrace();

            MyLogs.printinfo("Upload file to server Exception : " + e.getMessage());
        }

        // return serverResponseCode;

        System.out.println("post response : " + responseDataString);

        return new WebServiceResponse(responseDataString);
    }

    public static WebServiceResponse callHTTPPost_uploadMedia1(Context context,
                                                               String serviceMethod_inURL, List<String> params,
                                                               List<String> mediaPaths, boolean absoluteURL) {

        String responseDataString = "";

        // Concatenating Base URL with Service URL
        String webserviceURL = (absoluteURL ? serviceMethod_inURL : Constants
                .getPublicUrl() + serviceMethod_inURL);

        // webserviceURL = Constants.XMPP_BASE_URL + Constants.getSubUrl()
        // + serviceMethod_inURL;

        HttpContext localContext = new BasicHttpContext();
        HttpPost postRequest = null;

        // HttpMultipartPost repost

        try {

            String sub_url = "";
            String parameters = "";
            String signature = "";

            for (String s : params) {
                parameters = parameters + s;
                sub_url = sub_url + "/" + s;
            }

            Log.e("params4 signature", ": " + parameters);

            signature = get_MD5(parameters + Constants.testingParameter);

            sub_url = sub_url + "/" + signature;

            postRequest = new HttpPost(webserviceURL + sub_url);
            postRequest.setHeader(HTTP.CONTENT_TYPE, "multipart/form-data");

            System.out.println(webserviceURL + sub_url);

            // MultipartEntity reqEntity = new MultipartEntity();
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            // MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            // builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            HttpEntity entity = null;

            // InputStreamEntity reqEntity = null;

            // httppost.setEntity(reqEntity);

            for (int i = 0; i < mediaPaths.size(); i++) {
                // for (String mediaPath : mediaPaths) {
                File tmpFile = new File(mediaPaths.get(i));
                if (tmpFile.exists()) {

                    try {

                        Log.e("uploading File found ", ": " + mediaPaths.get(i));

                        FileBody bin = new FileBody(tmpFile, "image/jpeg");
                        // FileBody bin = new FileBody(tmpFile, "image" + i,
                        // "image/jpeg");// "/sdcard/DCIM/cam.jpg"));//
                        long size = bin.getContentLength();

                        // builder.addPart("file", bin);
                        // builder.addBinaryBody("upfile" + i, tmpFile);
                        // , ContentType.DEFAULT_BINARY, textFileName);

                        // postRequest.setEntity(new FileEntity(tmpFile,
                        // "image/jpeg"));

                        // reqEntity.addPart(
                        // "image" + i + "_" + mediaPaths.get(i), bin);
                        // reqEntity = new InputStreamEntity(new
                        // FileInputStream(
                        // tmpFile), -1);
                        // reqEntity.setContentType("binary/octet-stream");
                        // reqEntity.setChunked(true);
                        // Send in multiple parts if needed

                        entity = new FileEntity(tmpFile, "multipart/form-data");

                        // reqEntity.addPart(new FormBodyPart("file" + i, bin));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    MyLogs.printinfo("uploading File not exist : " + mediaPaths.get(i));
                }
            }

            // reqEntity.notify();

            // HttpEntity yourEntity = builder.build();
            // Setting Post Request Parameters
            // postRequest.setHeader("Content-Type", "application/json");
            // postRequest.setEntity(new UrlEncodedFormEntity(params));
            // postRequest.setEntity(new StringEntity(json.toString()));

            postRequest.setEntity(entity);
            // postRequest.setEntity(reqEntity);
            // postRequest.setEntity(yourEntity);

            // Calling WebService
            HttpResponse serviceResponse = WebService.getDefaultHTTPClient()
                    .execute(postRequest, localContext);

            // Extracting contents from Response Object
            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(serviceResponse.getEntity()
                            .getContent()));

            // Converting the response to a Single String
            String tmpString = null;
            while ((tmpString = bufferReader.readLine()) != null)
                responseDataString += tmpString;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("post response : " + responseDataString);

        return new WebServiceResponse(responseDataString);
    }

    public static WebServiceResponse callHTTPGet(String webserviceURL,
                                                 List<NameValuePair> params) {
        return WebService.callHTTPGet(webserviceURL, params, false);
    }

    public static WebServiceResponse callHTTPGet(String webserviceURL,
                                                 List<NameValuePair> params, boolean absoluteURL) {
        String responseDataString = "";

        // Concatenating Base URL with Service URL
        webserviceURL = (absoluteURL ? webserviceURL : WebServiceConfig
                .getBaseURL() + webserviceURL);

        String parameters = "";
        if (params != null && params.size() > 0) {
            for (int location = 0; location < params.size(); location++) {
                NameValuePair pair = params.get(location);
                parameters += "&" + pair.getName() + "=" + pair.getValue();
            }
            parameters = "?" + parameters.substring(1); // Replacing first &
            // with ?
        }

        System.out.println("url: " + webserviceURL + parameters);

        try {
            Calendar rightNow = Calendar.getInstance();
            long time = (rightNow.getTimeInMillis());
            time /= 1000;
            Log.e("time", " : " + time);
            HttpGet httpget = new HttpGet(webserviceURL + parameters);

            // Calling WebService
            HttpResponse serviceResponse = WebService.getDefaultHTTPClient()
                    .execute(httpget);

            Log.i("HttpGet", serviceResponse.getStatusLine().toString());

            // Extracting contents from Response Object
            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(serviceResponse.getEntity()
                            .getContent()));

            // Converting the response to a Single String
            String tmpString = null;
            while ((tmpString = bufferReader.readLine()) != null)
                responseDataString += tmpString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("get resposne : " + responseDataString);

        return new WebServiceResponse(responseDataString);
    }

    public static WebServiceResponse post_request_with_json(String URL,
                                                            JSONObject json) {
        String responseDataString = "";

        try {

            // String imkhi_acc_create_url =
            // "http://202.142.175.165/iamkarachi/public/users/create";
            HttpPost postRequest = new HttpPost(URL);
            // HttpPost postRequest = new HttpPost(Constants.XMPP_REGISTER_URL);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(json.toString()));

            // Calling WebService
            HttpResponse serviceResponse = WebService.getDefaultHTTPClient()
                    .execute(postRequest);

            // Extracting contents from Response Object
            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(serviceResponse.getEntity()
                            .getContent()));

            // Converting the response to a Single String
            String tmpString = null;
            while ((tmpString = bufferReader.readLine()) != null)
                responseDataString += tmpString;

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("post response : " + responseDataString);

        return new WebServiceResponse(responseDataString);
    }

    public static WebServiceResponse register(String number) {

        JSONObject json;
        String responseDataString = "";

        try {

            String signature = get_MD5(number + Constants.testingParameter);

            json = new JSONObject();
            json.put("username", number);
            json.put("signature", signature);

            return post_request_with_json(
                    "http://202.142.175.165/iamkarachi/public/users/create",
                    json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("post response : " + responseDataString);

        return new WebServiceResponse(responseDataString);

    }

    private static String get_MD5(String str) {

        try {
            // Create MD5 Hash
            String result = "";
            MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
            md.update(str.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while (result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.i("MD5", "Some thing went wrong creating hash" + e.toString());
        }

        return null;
    }

    public static int getResponseCode(JSONObject result) {
        try {
            return result.getJSONObject("header").getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static String getResponseError(JSONObject result) {
        try {
            return result.getJSONObject("header").getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return "Processing Failed! Check Your Internet Connection or Try after sometime";
        }
    }

    public static String getResponseBodyObject(JSONObject result) {
        try {
            return result.getJSONObject("header").getString("body");
        } catch (JSONException e) {
            e.printStackTrace();
            return "Processing Failed! Check Your Internet Connection or Try after sometime";
        }
    }

    public static int getVerificationCode(JSONObject result) {
        try {
            return result.getJSONObject("body").getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getEmailVerification(JSONObject result) {
        try {
            JSONObject jsonObject = result.getJSONArray("body").getJSONObject(0);
            return jsonObject.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    // public static String getProfileResponseError(JSONObject result) {
    // try {
    // return result.getJSONObject("header").getString("message");
    // } catch (JSONException e) {
    // e.printStackTrace();
    // return "Processing Failed! Please try after sometime";
    // }
    // }

    public static JSONObject getProfileFromResponseData(JSONObject result) {
        try {
            return result.getJSONObject("body");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getJSONObjectBodyFromResponseData(JSONObject result) {
        try {
            return result.getJSONObject("body");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getJSONArrayBodyFromResponseData(JSONObject result) {
        try {
            return result.getJSONArray("body");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject callCurlRequest(String completePath) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(completePath);
            HttpResponse response;
            String result;
            try {
                response = client.execute(request);
                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    // A Simple JSON Response Read
                    InputStream instream = entity.getContent();
                    result = UtilityFunctions.convertStreamToString(instream);
                    // now you have the string representation of the HTML request
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println("RESPONSE: " + result);
                    instream.close();
                    return jsonObject;
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

}