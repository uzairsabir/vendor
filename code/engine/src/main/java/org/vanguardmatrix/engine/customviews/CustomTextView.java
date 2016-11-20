package org.vanguardmatrix.engine.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

    public static String font_Roboto_Light = "Roboto-Light.ttf";
//    public static String font_Roboto_Light_Italic = "Roboto-LightItalic.ttf";
//    public static String font_Roboto_Regular = "Roboto-Medium.ttf";

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    private void init() {
//
//        if (!isInEditMode()) {
//            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), defaultFontName);
//            if (this.getTypeface().isBold()) {
//                setTypeface(tf, Typeface.BOLD);
//            } else if (this.getTypeface().isItalic()) {
//                setTypeface(tf, Typeface.ITALIC);
//            } else
//                setTypeface(tf);
//        }
//    }

    public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
            super.setTypeface(TypeFaces.getTypeFace(getContext(),
                    font_Roboto_Light));
        } else if (style == Typeface.ITALIC) {
            super.setTypeface(TypeFaces.getTypeFace(getContext(),
                    font_Roboto_Light));
        } else {
            super.setTypeface(TypeFaces.getTypeFace(getContext(),
                    font_Roboto_Light));
        }
    }

}