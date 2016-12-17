package com.thetechsolutions.whodouvendor.Pay.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.PayDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.Pay.fragments.PayMainFragment;
import com.thetechsolutions.whodouvendor.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_pay_list)
public class PayListAdapter extends ItemViewHolder<PayDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.service_date)
    TextView service_date;

    @ViewId(R.id.amount)
    TextView amount;

    @ViewId(R.id.container)
    RelativeLayout container;

    @ViewId(R.id.dollar_icon)
    ImageView dollar_icon;


    static Activity activity;
    static int pos;


    public PayListAdapter(View view) {
        super(view);
    }

    public static Class<PayListAdapter> newInstance(Activity _activity,int _pos) {
        activity = _activity;
        pos=_pos;
        return PayListAdapter.class;
    }


    @Override
    public void onSetValues(PayDT item, PositionInfo positionInfo) {
        sourceImageView.setImageURI(Uri.parse(item.getConsumer_image_url()));
        title.setText(item.getConsumer_name());
        if (pos == 1) {
            amount.setText("Amount Paid: $" + item.getAmount());
            dollar_icon.setVisibility(View.VISIBLE);
        } else if (pos== 0) {
            amount.setText("Amount Requested: $" + item.getAmount());
            dollar_icon.setVisibility(View.GONE);
        }

        service_date.setText(item.getDateToDisplay());


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListenerController.openPaymentDetail(activity, getItem().getId(), PayMainFragment.tab_pos,"Payment","");
            }
        });


    }


    public interface Listener {
        void onButtonClicked(ScheduleDT datetype);
    }

}
