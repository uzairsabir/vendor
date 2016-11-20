package org.vanguardmatrix.engine.FileDownloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.data.ActivityManager;
import org.vanguardmatrix.engine.parser.ParseString;
import org.vanguardmatrix.engine.utils.MyLogs;

public class FileDownloader {

    static FileDownloader instance;
    static DownloadManager downloadmanager;

    private Activity activity;

    public FileDownloader(Activity _activity) {
        activity = _activity;
    }

    public static FileDownloader getInstance(Activity activity) {
        if (instance == null)
            instance = new FileDownloader(activity);
        if (downloadmanager == null)
            downloadmanager = (DownloadManager) activity.getSystemService(activity.getApplicationContext().DOWNLOAD_SERVICE);
        return instance;
    }

    public static long getDownloadFileID(String filePath) {
        return AppPreferences.getLong(AppPreferences.PREF_DOWNLOAD_FILE + filePath);
    }

    public static Uri getFileUri(long downloadID) {
        try {
            return downloadmanager.getUriForDownloadedFile(downloadID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri getFileUri(String filePath) {
        try {

            if (downloadmanager == null)
                downloadmanager = (DownloadManager) ActivityManager.getInstance().getRunningActivity().getSystemService(Context.DOWNLOAD_SERVICE);

            return downloadmanager.getUriForDownloadedFile(getDownloadFileID(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileExtension(String filePath) {
        int dotposition = filePath.lastIndexOf(".");
//        filename_Without_Ext = file.substring(0,dotposition);
        return filePath.substring(dotposition + 1, filePath.length());
    }

    public static boolean isFileExist(String filePath) {

        try {
            if (getDownloadFileID(filePath) > 0) {
//                MyLogs.printerror("File " + filePath + "\n Already exist dm ID : " + getDownloadFileID(filePath));
//                if (getFileUri(getDownloadFileID(filePath)) != null) {
                return true;
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDMFileName(String url, String id) {
        String extension = getFileExtension(url);
        String newName = ParseString.filterTag(url);
        return "DM_" + id + "_" + newName + "." + extension;
    }

    public DownloadManager getDownloadmanager(Activity activity) {
        if (instance == null)
            instance = new FileDownloader(activity);
        if (downloadmanager == null)
            downloadmanager = (DownloadManager) activity.getSystemService(activity.getApplicationContext().DOWNLOAD_SERVICE);
        return downloadmanager;
    }

    public long downloadFile(String fileName2Display, String filePath, String asset_new_name) {

        try {

            if (!isFileExist(asset_new_name)) {
//        Uri uri = Uri.parse("www.domain.com/fileName.ext");
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filePath));

                //Restrict the types of networks over which this download may proceed.
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                //Set whether this download may proceed over a roaming connection.
                request.setAllowedOverRoaming(false);
                //Set the title of this download, to be displayed in notifications (if enabled).
                request.setTitle(activity.getString(R.string.application_name) + " Media File Downloading for Offline Use");
                //Set a description of this download, to be displayed in notifications (if enabled)
                request.setDescription(fileName2Display + " downloading ...");
                //Set the local destination for the downloaded file to a path within the application's external files directory
                request.setDestinationInExternalFilesDir(activity.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, asset_new_name);
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, asset_new_name);

                Long referenceID = downloadmanager.enqueue(request);


                AppPreferences.setLong(AppPreferences.PREF_DOWNLOAD_FILE + asset_new_name, referenceID);

                return referenceID;
            } else {
                MyLogs.printerror("File " + asset_new_name + " is Exist, not downloading again");
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }

//    public static void downloadNewAsset(RecitalItem recitalItem) {
//        getInstance(ActivityManager.getInstance().getRunningActivity()).
//                downloadFile("Downloading... Ziarat for " + recitalItem.getTitle(),
//                        recitalItem.getAssets_url_pre(),
//                        getDMFileName(recitalItem.getAssets_url_pre(), recitalItem.getId()));
//    }
}
