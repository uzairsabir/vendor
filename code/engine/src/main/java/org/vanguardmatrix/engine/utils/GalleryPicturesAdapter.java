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
package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.initializer.engine.R;

import java.util.List;

public class GalleryPicturesAdapter extends ArrayAdapter<GalleryItem> {

    public List<GalleryItem> list = null;

    Activity activity;

    int selectedCount = 0;

    public GalleryPicturesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    public GalleryPicturesAdapter(Activity context, int resource,
                                  List<GalleryItem> items) {
        super(context, resource, items);
        this.list = items;
        activity = context;
        selectedCount = 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if (convertView == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.gallery_pick_item, null, true);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position) != null) {

            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.selectedIcon = (ImageView) convertView.findViewById(R.id.selected);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleImage(position);
                }
            });

            holder.selectedIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleImage(position);
                }
            });

            if (position == 0) {
                //holder.imageView.setImageResource(R.drawable.timeline_postscreen_camera_icon);
            } else {
                ImageLoadingHandler.Display(list.get(position).get_filePath(), holder.imageView, true, false);
            }

            try {

                if (list.get(position).is_isSeleted()) {
                    holder.selectedIcon.setVisibility(View.VISIBLE);
//                    ImageLoader.getInstance().displayImage("R.drawable.", selected, animateFirstListener);
                } else {
                    holder.selectedIcon.setVisibility(View.INVISIBLE);
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }

        }

        return convertView;

    }

    private void toggleImage(final int position) {
        if (!((ImagePicker) activity).selectSingleImage) {
            if (position != 0) {
                if (list.get(position).is_isSeleted())
                    list.get(position).set_isSeleted(false);
                else
                    list.get(position).set_isSeleted(true);
                notifyDataSetChanged();
            } else {
                ((ImagePicker) activity).cameraButtonClick();
            }
        } else {

            for (int i = 0; i < list.size(); i++) {
                list.get(i).set_isSeleted(false);
            }

            if (position != 0) {
                if (list.get(position).is_isSeleted())
                    list.get(position).set_isSeleted(false);
                else
                    list.get(position).set_isSeleted(true);
                notifyDataSetChanged();
            } else {
                ((ImagePicker) activity).cameraButtonClick();
            }

        }
    }

    class ViewHolder {
        protected ImageView imageView, selectedIcon;
    }
}