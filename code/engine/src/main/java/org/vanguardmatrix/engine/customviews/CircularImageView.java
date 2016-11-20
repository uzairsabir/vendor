package org.vanguardmatrix.engine.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class CircularImageView extends ImageView {

    public CircularImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;

        if (bmp == null) {
//            return ((BitmapDrawable) HomeScreenOld.activity.getResources()
//                    .getDrawable(R.drawable.profile_img)).getBitmap();
        }

        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
                sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap srcBmp = null;
        try {
            srcBmp = ((BitmapDrawable) drawable).getBitmap();
        } catch (Exception e) {
            e.printStackTrace();
            // srcBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.profile_img)).getBitmap();
        }
        Bitmap dstBmp = null; // b.copy(Bitmap.Config.ARGB_8888, true);

        int radiaus = getWidth(), h = getHeight();

        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

            // if (getHeight() > srcBmp.getHeight())
            // radiaus = getHeight() / 2;
            // else
            // radiaus = srcBmp.getHeight() / 2;
            try {
                dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() / 2
                                - srcBmp.getHeight() / 2, 0, srcBmp.getHeight(),
                        srcBmp.getHeight());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                try {
                    dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() / 4
                                    - srcBmp.getHeight() / 4, 0, srcBmp.getHeight(),
                            srcBmp.getHeight());
                } catch (OutOfMemoryError e2) {
                    e2.printStackTrace();
                    dstBmp = srcBmp;
                }

            }

        } else {

            // if (getWidth() > srcBmp.getWidth())
            // radiaus = getWidth() / 2;
            // el;se
            // radiaus = srcBmp.getWidth() / 2;
            try {
                dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight() / 2
                                - srcBmp.getWidth() / 2, srcBmp.getWidth(),
                        srcBmp.getWidth());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                try {
                    dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight()
                                    / 4 - srcBmp.getWidth() / 4, srcBmp.getWidth(),
                            srcBmp.getWidth());
                } catch (OutOfMemoryError e1) {
                    e1.printStackTrace();
                    dstBmp = srcBmp;
                }
            }
        }

        srcBmp = null;

        Bitmap roundBitmap = getCroppedBitmap(dstBmp, radiaus);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }
}