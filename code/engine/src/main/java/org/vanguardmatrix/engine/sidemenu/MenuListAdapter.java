/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * <p/>
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * <p/>
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.vanguardmatrix.engine.sidemenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.initializer.engine.R;

import java.util.ArrayList;
import java.util.List;

public class MenuListAdapter extends ArrayAdapter<SideMenuItem> {

    static List<SideMenuItem> list = new ArrayList<SideMenuItem>();
    Context context;
    List<SideMenuItem> drawerItemList;
    int layoutResID;
    Activity activity;

    public MenuListAdapter(Context context, int layoutResourceID,
                           List<SideMenuItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.menu_item_title);
            drawerHolder.icon = (ImageView) view
                    .findViewById(R.id.menu_item_icon);
            drawerHolder.panel = (RelativeLayout) view
                    .findViewById(R.id.menu_item_panel);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        SideMenuItem dItem = (SideMenuItem) this.drawerItemList.get(position);

        if (dItem.isSelected()) {
            try {
                drawerHolder.panel.setBackgroundColor(activity.getResources()
                        .getColor(R.color.imkhi_brown));

            } catch (Exception e) {
                // e.printStackTrace();
            }
        } else {
            try {
                drawerHolder.panel.setBackgroundColor(activity.getResources()
                        .getColor(android.R.color.transparent));

            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResID()));
        drawerHolder.ItemName.setText(dItem.getItemName());

        // drawerHolder.panel.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // Log.e("aw", "menu item clicked : " + position);
        // HomeScreenOld.mNavigationDrawerFragment.selectItem(position + 1);
        //
        // if (drawerItemList.get(position).isSelected()) {
        // drawerItemList.get(position).setSelected(false);
        // } else {
        // for (int i = 0; i < getCount(); i++) {
        // drawerItemList.get(i).setSelected(false);
        // }
        // drawerItemList.get(position).setSelected(true);
        // }
        // notifyDataSetChanged();
        // }
        // });

        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
        RelativeLayout panel;
    }

}