package org.vanguardmatrix.engine.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import org.vanguardmatrix.engine.android.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    Constants.IMAGES_FOLDER_NAME);
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public static File getExternalStorageDirectory(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return android.os.Environment.getExternalStorageDirectory();
        else
            return context.getCacheDir();
    }

    public static String saveBitmaptoInternalDirectory(Activity activity,
                                                       Bitmap btmp, String fileName) {
        File file = null;
        try {
            // String path = FileCache.getInternalStorageDirectory(activity)
            // .getAbsolutePath();
            OutputStream fOut = null;
            file = new File(FileCache.getExternalStorageDirectory(activity),
                    fileName);
            file.createNewFile();
            if (!file.exists()) {
                file.mkdirs();
                file.mkdir();
            }
            fOut = new FileOutputStream(file);
            btmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), file.getName());

            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // public static File getInternalStorageDirectory(Context context) {
    // File storageFile;
    // // Find the dir to save cached images
    // if (android.os.Environment.getExternalStorageState().equals(
    // android.os.Environment.MEDIA_MOUNTED))
    // storageFile = new File(android.os.Environment.getDataDirectory()
    // .getAbsolutePath());
    // else
    // storageFile = context.getCacheDir();
    // if (!storageFile.exists())
    // storageFile.mkdirs();
    //
    // return storageFile;
    // }

    public File getFile(String url) {
        // I identify images by hashcode. Not a perfect solution, good for the
        // demo.
        String filename = String.valueOf(url.hashCode());
        // Another possible solution (thanks to grantland)
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}