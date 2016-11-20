package org.vanguardmatrix.engine.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.view.View;

import java.io.InputStream;

public class GifMovieView extends View {

    Bitmap bmp;
    private Movie mMovie;
    private long mMoviestart;

    public GifMovieView(Context context, InputStream stream) {
        super(context);

        mMovie = Movie.decodeStream(stream);

        // File file = new File
        // (FileCache
        // .getStorageDirectory(context).getAbsolutePath(),"piggy.gif");
        //
        // mMovie = Movie.decodeFile(file.getPath());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);

        try {

            final long now = SystemClock.uptimeMillis();

            if (mMoviestart == 0) {
                mMoviestart = now;
            }

            final int relTime = (int) ((now - mMoviestart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, 10, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.invalidate();
    }
}
