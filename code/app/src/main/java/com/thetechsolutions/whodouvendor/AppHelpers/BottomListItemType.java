package com.thetechsolutions.whodouvendor.AppHelpers;

import com.thetechsolutions.whodouvendor.R;

/**
 * Created by Abdul Wahab on 11/20/2015.
 */

public class BottomListItemType {
    public String name = "";
    public int drawable = R.drawable.ic_launcher;

    public BottomListItemType() {
        this.name = "";
        this.drawable = R.drawable.ic_launcher;
        //default construction default values will apply
    }

    public BottomListItemType(String _name, int _drawable) {
        this.name = _name;
        this.drawable = _drawable;
    }

    public BottomListItemType(int _drawable, String _name) {
        this.name = _name;
        this.drawable = _drawable;
    }
}