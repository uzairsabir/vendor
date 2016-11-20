package org.vanguardmatrix.engine.utils;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Abdul Wahab on 5/27/2015.
 */

public class GalleryItem implements Serializable {


    public static String ID = "id";
    public static String NAME = "addressee";

    private int _id = -1;
    private String _name = "";
    private String _imagePath = "";
    private String _thumbPath = "";
    private String _filePath = "";
    private Uri _imageUri = null;
    private boolean _isSeleted = false;

    public static GalleryItem importFromJson(JSONObject json) {

        GalleryItem item = new GalleryItem();
        try {
            item.set_id(json.getInt(ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            item.set_name(json.getString(NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    public String get_name() {
        return this._name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String get_imagePath() {
        return this._imagePath;
    }

    public void set_imagePath(String _imagePath) {
        this._imagePath = _imagePath;
    }

    public String get_thumbPath() {
        return this._thumbPath;
    }

    public void set_thumbPath(String _thumbPath) {
        this._thumbPath = _thumbPath;
    }

    public Uri get_imageUri() {
        return this._imageUri;
    }

    public void set_imageUri(Uri _imageUri) {
        this._imageUri = _imageUri;
    }

    public boolean is_isSeleted() {
        return this._isSeleted;
    }

    public void set_isSeleted(boolean _isSeleted) {
        this._isSeleted = _isSeleted;
    }

    public String get_filePath() {
        return _filePath;
    }

    public void set_filePath(String _filePath) {
        this._filePath = _filePath;
    }
}
