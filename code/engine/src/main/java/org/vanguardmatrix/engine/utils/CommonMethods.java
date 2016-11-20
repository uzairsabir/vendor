package org.vanguardmatrix.engine.utils;

import android.text.SpannableString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethods {

    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {

        String result = "";

        // Log.e("Service Responce", "convertInputStreamToString start");

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (OutOfMemoryError e) {
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        result = sb.toString();

        // BufferedReader bufferedReader = new BufferedReader(
        // new InputStreamReader(inputStream));
        // String line = "";
        //
        // while ((line = bufferedReader.readLine()) != null)
        // result += line;
        //
        // inputStream.close();

        // Log.e("Service Responce", "convertInputStreamToString" + result);

        return result;

    }

    public static ArrayList<String> getTagsFromText(String text) {

        ArrayList<String> tagsList = new ArrayList<>();

        SpannableString hashtagintitle = new SpannableString(text);
        Matcher matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(
                hashtagintitle);
        while (matcher.find()) {
            tagsList.add(matcher.group());
        }
        return tagsList;
    }

    public static String[] getTagFromText(String text) {

        String[] tagsList = new String[11];
        int i = 0;

        SpannableString hashtagintitle = new SpannableString(text);
        Matcher matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(
                hashtagintitle);
        while (matcher.find()) {
            if (i < 10)
                tagsList[i++] = matcher.group();
        }
        return tagsList;
    }

    public static String[] getArrayFromString(String text) {

        String[] separated = text.split(",");
        return separated;

    }
}
