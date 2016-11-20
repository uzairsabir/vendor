package org.vanguardmatrix.engine.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.data.ActivityManager;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Abdul Wahab on 6/1/2015.
 */
public class ImageLoadingHandler {


    private static DisplayImageOptions options, optionsWithoutCache, optionsRoundedWithoutCache,
            optionsRoundedWithCache, optionsWithPreImageRounded, optionsWithPreImageNotRounded,
            optionsWithPreImageCache, optionsWithPreImageNoCache,
            optionsWithoutPreImageCache, optionsWithoutPreImageNoCache;

    private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private static ImageLoadingListener animateFirstListenerFast = new AnimateFirstDisplayListenerFast();


    public static void initialize() {

        try {

            File cacheDir = StorageUtils.getCacheDirectory(
                    ActivityManager.getInstance().getRunningActivity().getApplicationContext());

            ImageLoaderConfiguration config = new
                    ImageLoaderConfiguration.Builder(
                    ActivityManager.getInstance().getRunningActivity().getApplicationContext())
                    .memoryCache(new UsingFreqLimitedMemoryCache(2000000))
                    .discCache(new UnlimitedDiskCache((cacheDir)))
                    .build();

            ImageLoader.getInstance().init(config);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            L.disableLogging();
        } catch (Exception e) {
            e.printStackTrace();
        }

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer()).build();


        optionsWithoutCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer()).build();

        optionsRoundedWithoutCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();

        optionsRoundedWithCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();

        optionsWithPreImageRounded = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();

        optionsWithPreImageNotRounded = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer()).build();

        optionsWithPreImageCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer(20)).build();

        optionsWithPreImageNoCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer()).build();

        optionsWithoutPreImageCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer(20)).build();

        optionsWithoutPreImageNoCache = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.transparent)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_empty)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new AW_BitmapBisplayer()).build();
    }

    public static void DisplayWithRound(String url, ImageView imageView, boolean storeInCache, boolean withoutPreImage, boolean roundedCorner) {

//        final String uri = Uri.parse(url);
        try {
            url = Uri.decode(url);

            if (roundedCorner) {
                if (storeInCache) {
                    if (withoutPreImage)
                        ImageLoader.getInstance().displayImage(url, imageView, optionsRoundedWithCache, animateFirstListenerFast);
                    else
                        ImageLoader.getInstance().displayImage(url, imageView, optionsRoundedWithCache, animateFirstListener);
                } else {
                    if (withoutPreImage)
                        ImageLoader.getInstance().displayImage(url, imageView, optionsRoundedWithoutCache, animateFirstListenerFast);
                    else
                        ImageLoader.getInstance().displayImage(url, imageView, optionsRoundedWithoutCache, animateFirstListener);
                }
            } else {
                Display(url, imageView, storeInCache, withoutPreImage);
            }

        } catch (OutOfMemoryError e) {
//            ImageLoader.getInstance().clearDiskCache();
            ImageLoader.getInstance().clearMemoryCache();
        }
    }

    public static void Display(String url, ImageView imageView, boolean storeInCache, boolean withoutPreImage) {

//        final String uri = Uri.parse(url);
        try {
            url = Uri.decode(url);

            if (withoutPreImage) {
                if (storeInCache)
                    ImageLoader.getInstance().displayImage(url, imageView, optionsWithoutPreImageCache, animateFirstListenerFast);
                else
                    ImageLoader.getInstance().displayImage(url, imageView, optionsWithoutPreImageNoCache, animateFirstListenerFast);
            } else {
                if (storeInCache)
                    ImageLoader.getInstance().displayImage(url, imageView, optionsWithPreImageCache, animateFirstListener);
                else
                    ImageLoader.getInstance().displayImage(url, imageView, optionsWithPreImageNoCache, animateFirstListener);
            }
        } catch (OutOfMemoryError e) {
//            ImageLoader.getInstance().clearDiskCache();
            ImageLoader.getInstance().clearMemoryCache();
        }
    }

    public static File getCacheFile(String uri) {
        return ImageLoader.getInstance().getDiscCache().get(uri);
    }

    public static String getCacheFilePath(String uri) {
        return ImageLoader.getInstance().getDiscCache().get(uri).getAbsolutePath();
    }

    public static Uri getCacheFileUri(String uri) {
        return Uri.parse(ImageLoader.getInstance().getDiscCache().get(uri).getAbsolutePath());
    }

    private static class AW_BitmapBisplayer implements BitmapDisplayer {

        protected final int cornerRadius;
        protected final int margin;

        public AW_BitmapBisplayer() {
            this(0, 0);
        }

        public AW_BitmapBisplayer(int cornerRadiusPixels) {
            this(cornerRadiusPixels, 0);
        }

        public AW_BitmapBisplayer(int cornerRadiusPixels, int marginPixels) {
            this.cornerRadius = cornerRadiusPixels;
            this.margin = marginPixels;
        }

        @Override
        public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
            if (!(imageAware instanceof ImageViewAware)) {
                throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
            }

            imageAware.setImageDrawable(new BitmapDrawable(bitmap));
//            imageAware.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin));
        }

        public static class RoundedDrawable extends Drawable {

            protected final float cornerRadius;
            protected final int margin;

            protected final RectF mRect = new RectF(),
                    mBitmapRect;
            protected final BitmapShader bitmapShader;
            protected final Paint paint;

            public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin) {
                this.cornerRadius = cornerRadius;
                this.margin = margin;

                bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mBitmapRect = new RectF(margin, margin, bitmap.getWidth() - margin, bitmap.getHeight() - margin);

                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(bitmapShader);
            }

            @Override
            protected void onBoundsChange(Rect bounds) {
                super.onBoundsChange(bounds);
                mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);

                // Resize the original bitmap to fit the new bound
                Matrix shaderMatrix = new Matrix();
                shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
                bitmapShader.setLocalMatrix(shaderMatrix);

            }

            @Override
            public void draw(Canvas canvas) {
                canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }

            @Override
            public void setAlpha(int alpha) {
                paint.setAlpha(alpha);
            }

            @Override
            public void setColorFilter(ColorFilter cf) {
                paint.setColorFilter(cf);
            }
        }
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 750);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    private static class AnimateFirstDisplayListenerFast extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 300);
                    displayedImages.add(imageUri);
                }
            }
        }
    }


}
