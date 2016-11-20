package org.vanguardmatrix.engine.imageloader;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class ImageLoaderManager {

    private static ImageLoader imageLoaderObj = null;

    public static ImageLoader getImageLoaderObj(Context context) {
        if (imageLoaderObj == null)
            imageLoaderObj = new ImageLoader(context);
        return imageLoaderObj;
    }

    public static void file_download(String uRl, Activity activity) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/dhaval_files");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) activity
                .getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/dhaval_files", "test.jpg");

        mgr.enqueue(request);

    }
}
