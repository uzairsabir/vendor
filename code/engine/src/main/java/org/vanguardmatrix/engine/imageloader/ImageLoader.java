package org.vanguardmatrix.engine.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.initializer.engine.R;

import org.vanguardmatrix.engine.utils.ImageLoadingHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    int view_id = 0;
    // setting default imageView,
    // by getting 1 for crowsel, 2 for user_image, 3 for deal, 4 for clubs,
    // in DisplayImage method,

    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    ExecutorService executorService;
    private Map<ImageView, String> imageViews = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>());
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    // int stub_id = R.drawable.icon;
    private DisplayImageOptions options;

    public ImageLoader(Context context) {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_image)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new com.nostra13.universalimageloader.core.display.BitmapDisplayer() {
                    @Override
                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
                        if (!(imageAware instanceof ImageViewAware)) {
                            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
                        }

                        imageAware.setImageDrawable(new BitmapDrawable(bitmap));
                    }
                }).build();

        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);

    }

    public void DisplayImage(String url, ImageView imageView) {

        ImageLoadingHandler.Display(url, imageView, true, false);
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(url, imageView, options, animateFirstListener);

    }

    public void DisplayImage(String url, ImageView imageView, Boolean storedInCache, Boolean PreImage, Boolean roundedCorner) {

        ImageLoadingHandler.DisplayWithRound(url, imageView, storedInCache, PreImage, roundedCorner);
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(url, imageView, options, animateFirstListener);

    }

    public void DisplayImage_old(String url, ImageView imageView) {

        // url += "?ts=" + System.currentTimeMillis();
        // Log.e("ImageLoader", " loading image URL : " + url);
        // + " ?ts=" + System.currentTimeMillis());
        url = UtilityFunctions.getUrlWithoutSlahesOrDoubleQoute(url);
        //
        imageViews.put(imageView, url);

        Bitmap bitmap = memoryCache.get(url);
//		bitmap.getWidth();
//		bitmap.getHeight();
//		Log.e("Bitmap Size", "" + bitmap.getWidth() + bitmap.getHeight());
        if (bitmap != null) {
            try {
                imageView.setBackgroundResource(android.R.color.transparent);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO: handle exception
            }
            // BitmapDrawable drawable = new BitmapDrawable(bitmap);

        } else {
            queuePhoto(url, imageView);
            // stub_id = R.drawable.feature_logo;
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        // Log.e("aw", "new image to load and cache : url = " + url);
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    public Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        // from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        // from web
        try {
            Bitmap bitmap = null;
            // if (url.equals(""))
            // return BitmapFactory.decodeResource(
            // HomeScreenOld.activity.getResources(),
            // R.drawable.broken_url);
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
            // } catch (Exception e) {
            // e.printStackTrace();
            // // loading image URL :
            // //
            // http://202.142.175.163/iamkarachi/images/defaults/profile/t_male.png
            // if (url.contains("defaults/profile/t_male"))
            // return BitmapFactory.decodeResource(
            // HomeScreenOld.activity.getResources(),
            // R.drawable.profile_img);
            // else
            // return BitmapFactory.decodeResource(
            // HomeScreenOld.activity.getResources(),
            // R.drawable.broken_url);
        } catch (Throwable ex) {
            // ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    // decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 600;
            final int REQUIRED_SIZE_HEIGHT = 600;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE_HEIGHT)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    // Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView imageView2) {
            url = u;
            imageView = imageView2;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad.url);
            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                // BitmapDrawable drawable = new BitmapDrawable(bitmap);
                photoToLoad.imageView
                        .setBackgroundResource(android.R.color.transparent);
                photoToLoad.imageView.setImageBitmap(bitmap);
            }
            // else
            // photoToLoad.imageView.setBackgroundResource(stub_id);
        }
    }

}
