package com.thetechsolutions.whodouvendor.AppHelpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.R;

import java.util.ArrayList;

/**
 * Created by Uzair on 10/16/2015.
 */
public class BottomListAdapter extends ArrayAdapter<BottomListItemType> {

    Activity activity;
    ArrayList<BottomListItemType> list;
    int itemWidth = 0;
    int itemId;
    int resourceLayout = R.layout.item_home_bottom_listview;

    public BottomListAdapter(Activity _activity, int resource, ArrayList<BottomListItemType> _list, int _itemWidth, int id) {
        super(_activity, resource, _list);
        this.activity = _activity;
        this.list = _list;
        this.itemId = id;
        this.resourceLayout = resource;
        this.itemWidth = _itemWidth;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(resourceLayout, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(resourceLayout, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }

        if (list.get(position) != null) {

            holder.imageView = (ImageView) convertView.findViewById(R.id.item_image);
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);
            holder.container = (LinearLayout) convertView.findViewById(R.id.item_panel);

            ViewGroup.LayoutParams layoutParams = holder.container.getLayoutParams();
            layoutParams.width = itemWidth;
            holder.container.setLayoutParams(layoutParams);

            holder.imageView.setImageResource(list.get(position).drawable);
            holder.textView.setText(list.get(position).name);

            //View target = convertView.findViewById(R.id.view_badge);

            // holder.badge = (View) convertView.findViewById(R.id.view_badge);

            if (position == 0) {
                try {
//                    int curr = AppPreferences.getInt(AppPreferences.PREF_CURRENT_NUMBER_OF_MESSAGES);
//                    int last = AppPreferences.getInt(AppPreferences.PREF_LAST_NUMBER_OF_MESSAGES);
//                    if (curr > last) {
//                        int newMessage = curr - last;
//                        BadgeView badge = new BadgeView(activity, holder.container);
//                        badge.setText("" + newMessage);
//                        badge.show();
//
//                        AppPreferences.setInt(AppPreferences.PREF_LAST_NUMBER_OF_MESSAGES, curr);
//
//                    }
                } catch (Exception e) {

                }
            } else if (position == 1) {

                try {
//                    int curr = AppPreferences.getInt(AppPreferences.PREF_CURRENT_NUMBER_OF_APPOINTMENT);
//                    int last = AppPreferences.getInt(AppPreferences.PREF_LAST_NUMBER_OF_APPOINTMENT);
//                    if (curr > last) {
//                        int newMessage = curr - last;
//                        BadgeView badge = new BadgeView(activity, holder.container);
//                        badge.setText("" + newMessage);
//                        badge.show();
//
//                        //AppPreferences.setInt(AppPreferences.PREF_LAST_NUMBER_OF_APPOINTMENT, AppPreferences.getInt(AppPreferences.PREF_CURRENT_NUMBER_OF_APPOINTMENT));
//
//                    }
                } catch (Exception e) {

                }

            } else if (position == 2) {
                try {
//                    int curr = AppPreferences.getInt(AppPreferences.PREF_CURRENT_NUMBER_OF_PAYMENTS);
//                    int last = AppPreferences.getInt(AppPreferences.PREF_LAST_NUMBER_OF_PAYMENTS);
//                    if (curr > last) {
//                        int newMessage = curr - last;
//                        BadgeView badge = new BadgeView(activity, holder.container);
//                        badge.setText("" + newMessage);
//                        badge.show();
//
//
//                    }
                } catch (Exception e) {

                }
            }


            if (itemId == 0) {
                if (position == 0) {
                    //holder.container.setBackgroundColor(activity.getResources().getColor(R.color.bottom_list_hover));
                    holder.imageView.setImageResource(R.drawable.home_icon_hover);
                    // holder.textView.setTextColor(activity.getResources().getColor(R.color.app_text_blue));


                }
            } else if (itemId == 1) {
                if (position == 1) {
                    //holder.container.setBackgroundColor(activity.getResources().getColor(R.color.bottom_list_hover));
                    holder.imageView.setImageResource(R.drawable.chat_icon_hover);
                    //  holder.textView.setTextColor(activity.getResources().getColor(R.color.app_text_blue));


                }
            } else if (itemId == 2) {
                if (position == 2) {
                    // holder.container.setBackgroundColor(activity.getResources().getColor(R.color.bottom_list_hover));
                    holder.imageView.setImageResource(R.drawable.schedule_icon_hover);
                    //holder.textView.setTextColor(activity.getResources().getColor(R.color.app_text_blue));


                }
            } else if (itemId == 3) {
                if (position == 3) {
                    //  holder.container.setBackgroundColor(activity.getResources().getColor(R.color.bottom_list_hover));
                    holder.imageView.setImageResource(R.drawable.pay_icon_hover);
                    //holder.textView.setTextColor(activity.getResources().getColor(R.color.app_text_blue));
                }
            } else if (itemId == 4) {
                if (position == 4) {
                    // holder.container.setBackgroundColor(activity.getResources().getColor(R.color.bottom_list_hover));
                    holder.imageView.setImageResource(R.drawable.setting_icon_hover);
                    //holder.textView.setTextColor(activity.getResources().getColor(R.color.app_text_blue));

                }
            }
//
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        BottomMenuController.bottomOptionClick(position, activity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }


        return convertView;
    }


    class ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected LinearLayout container;
        protected View badge;
    }


}
