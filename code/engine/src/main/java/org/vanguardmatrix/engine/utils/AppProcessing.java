package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.customviews.GifMovieView;

import java.io.InputStream;

public class AppProcessing {

    private static AlertDialog dialog;

    public static InputStream getGifStream(Activity activity, int gif_id) {

        InputStream stream = null;
        // stream = getAssets().open("imkhi_loader.gif");
        stream = activity.getResources().openRawResource(gif_id);

        return stream;

    }

    public static void showProcessingWindow(Activity activity,
                                            Boolean cancelable, int gif_drawable_id, String processingText) {

        dialog = new AlertDialog.Builder(activity).create();

        dialog.setCancelable(cancelable);

        View view = activity.getLayoutInflater().inflate(
                R.layout.dialog_loading_progress, null);

        RelativeLayout loaderContainer = (RelativeLayout) view
                .findViewById(R.id.gif_loader_container);
        loaderContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        loaderContainer.addView(new GifMovieView(activity, AppProcessing
                .getGifStream(activity, R.drawable.newloader_pink_over_white)));

        TextView processingTexView = (TextView) view
                .findViewById(R.id.processing_text);
        processingTexView.setText(processingText);

        dialog.setView(view, 0, 0, 0, 0);
        dialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("status::::", "finishing dialog");

            }
        });
        try {
            if (dialog != null)
                if (!dialog.isShowing())
                    dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressWindow() {
        try {
            if (dialog != null)
                if (dialog.isShowing())
                    dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
