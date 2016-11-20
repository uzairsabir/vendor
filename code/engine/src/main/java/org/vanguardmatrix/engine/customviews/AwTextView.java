package org.vanguardmatrix.engine.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.CustomStyle;

import java.lang.reflect.Method;

public class AwTextView extends TextView {

    private String defaultFontName = "corbel.ttf";

    public AwTextView(Context context) {
        super(context);
        setFont();
    }

    public AwTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateAttributes(attrs);
    }

    public AwTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        updateAttributes(attrs);
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/" + defaultFontName);
        setTypeface(font, Typeface.NORMAL);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void updateAttributes(AttributeSet attrs) {

        Class noparams[] = {};

        String currentFont = "", currentFontTypeFace = "";

        try {
            if (attrs != null) {
                Log.e(this.getClass().toString(),
                        "position : "
                                + attrs.getAttributeCount()
                                + " attr->Name : "
                                + attrs.getAttributeName(attrs
                                .getAttributeCount() - 1));
                Log.e(this.getClass().toString(),
                        "attr->Value : "
                                + attrs.getAttributeValue(attrs
                                .getAttributeCount() - 1));

                try {

                    // load the AppTest at runtime
                    Class cls = Class.forName(attrs.getAttributeValue(attrs
                            .getAttributeCount() - 1));
                    Object obj = cls.newInstance();

                    // call the printIt method
                    Method method = cls.getDeclaredMethod("getAttributes",
                            noparams);
                    JSONObject attributes = (JSONObject) method.invoke(obj);

                    setTextColor(Color.parseColor(attributes
                            .getString(CustomStyle.COLOR)));

                    currentFont = attributes.getString(CustomStyle.FONTNAME);

                    currentFontTypeFace = attributes
                            .getString(CustomStyle.TYPEFACE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            if (currentFont.equals("")) {

                Typeface font = Typeface.createFromAsset(getContext()
                        .getAssets(), "fonts/" + defaultFontName);
                setTypeface(font, Typeface.NORMAL);

            } else {

                if (currentFontTypeFace.equals("")) {

                    Typeface font = Typeface.createFromAsset(getContext()
                            .getAssets(), "fonts/" + currentFont);

                    if (currentFontTypeFace.equalsIgnoreCase("bold"))
                        setTypeface(font, Typeface.BOLD);
                    else if (currentFontTypeFace.equalsIgnoreCase("italic"))
                        setTypeface(font, Typeface.ITALIC);
                    else if (currentFontTypeFace
                            .equalsIgnoreCase("bold_italic"))
                        setTypeface(font, Typeface.BOLD_ITALIC);
                    else
                        setTypeface(font, Typeface.NORMAL);

                } else {

                    Typeface font = Typeface.createFromAsset(getContext()
                            .getAssets(), "fonts/" + currentFont);

                    if (currentFontTypeFace.equalsIgnoreCase("bold"))
                        setTypeface(font, Typeface.BOLD);
                    else if (currentFontTypeFace.equalsIgnoreCase("italic"))
                        setTypeface(font, Typeface.ITALIC);
                    else if (currentFontTypeFace
                            .equalsIgnoreCase("bold_italic"))
                        setTypeface(font, Typeface.BOLD_ITALIC);
                    else
                        setTypeface(font, Typeface.NORMAL);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.invalidate();
        // try {
        // if (attrs != null) {
        // for (int i = 0; i < attrs.getAttributeCount(); i++) {
        // Log.e(this.getClass().toString(), "position : " + i
        // + " attr->Name : " + attrs.getAttributeName(i));
        // Log.e(this.getClass().toString(),
        // "attr->Value : " + attrs.getAttributeValue(i));
        // }
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

    }
}