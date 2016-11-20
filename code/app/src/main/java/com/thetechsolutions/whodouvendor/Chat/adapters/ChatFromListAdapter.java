package com.thetechsolutions.whodouvendor.Chat.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatFromMainActivity;
import com.thetechsolutions.whodouvendor.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_home_list)
public class ChatFromListAdapter extends ItemViewHolder<CustomersDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.service_name)
    TextView service_name;

    @ViewId(R.id.address)
    TextView address;

    @ViewId(R.id.chat_icon)
    ImageView chat_icon;

    @ViewId(R.id.call_icon)
    ImageView call_icon;

    @ViewId(R.id.container)
    RelativeLayout container;


    static Activity activity;

    CustomersDT item;

    public ChatFromListAdapter(View view) {
        super(view);
    }

    static int pos, positioninfo;

    public static Class<ChatFromListAdapter> newInstance(Activity _activity, int _pos) {
        activity = _activity;
        pos = _pos;
        return ChatFromListAdapter.class;
    }


    @Override
    public void onSetValues(CustomersDT item, PositionInfo positionInfo) {
        this.item = item;
        this.positioninfo = positionInfo.getPosition();
        try {
            sourceImageView.setImageURI(Uri.parse(item.getImage_url()));
            title.setText(item.getFirst_name() + " " + item.getLast_name());
            service_name.setText(item.getSub_category_title());
            address.setText(item.getAddress());
        } catch (Exception e) {

        }


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();

        call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.getIs_register_user().equals("1")) {
                    activity.finish();
                }

                AppController.openChat(activity, item.getUsername(), item.getFirst_name() + " " + item.getLast_name(), item.getImage_url(), item.getIs_register_user(),ChatFromMainActivity.tab_pos);
                // activity.startActivity(ConversationActivity.createIntent(activity, "123456", "", item.getUsername(), item.getFirst_name() + " " + item.getLast_name()));


            }
        });


    }


    public interface Listener {
        void onButtonClicked(CustomersDT datetype);
    }

}
