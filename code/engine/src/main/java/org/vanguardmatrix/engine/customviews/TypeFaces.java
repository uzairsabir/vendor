package org.vanguardmatrix.engine.customviews;

import android.content.Context;
import android.graphics.Typeface;

import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.HashMap;

/**
 * Created by Abdul Wahab on 10/29/2015.
 */
public class TypeFaces {

    public static HashMap<String, Typeface> cache = new HashMap<>();

    public static Typeface getTypeFace(Context context, String assetPath) {

        if (cache == null) {
            cache = new HashMap<String, Typeface>();
        }

        if (!cache.containsKey(assetPath)) {
            try {
                Typeface typeFace = Typeface.createFromAsset(
                        context.getAssets(), assetPath);
                cache.put(assetPath, typeFace);
            } catch (Exception e) {
                MyLogs.printerror("TypeFaces" + "Typeface not loaded.");
                return null;
            }
        }
        return cache.get(assetPath);

    }
}
