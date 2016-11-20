package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_dialoge_category)
public class MultipleNumbersListAdapter extends ItemViewHolder<String> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.container)
    RelativeLayout container;

    static Activity activity;

    public MultipleNumbersListAdapter(View view) {
        super(view);
    }

    public static Class<MultipleNumbersListAdapter> newInstance(Activity _activity) {
        activity = _activity;
        return MultipleNumbersListAdapter.class;
    }


    @Override
    public void onSetValues(String item, PositionInfo positionInfo) {

        if (positionInfo.getPosition() == 0) {
            sourceImageView.setImageURI(Uri.parse(AppConstants.FRESCO_RES + R.drawable.primary));
        } else if (positionInfo.getPosition() == 1) {
            sourceImageView.setImageURI(Uri.parse(AppConstants.FRESCO_RES + R.drawable.secondary));
        } else {
            sourceImageView.setImageURI(Uri.parse(AppConstants.FRESCO_RES + R.drawable.other));
        }

//        PhoneFormat phoneFormat = new PhoneFormat(activity);
//
//        String numberString;
//        if (item.startsWith("00")) {
//            numberString = item.substring(2);
//        } else {
//            numberString = item.substring(1);
//        }

        //    String formattedString = phoneFormat.format("+" + numberString);

        title.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item));


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();


    }


    public interface Listener {
        void onButtonClicked(VendorCategoryDT datetype);
    }

}
