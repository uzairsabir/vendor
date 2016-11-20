package com.thetechsolutions.whodouvendor.Home.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.SearchInDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouvendor.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_dialoge_category)
public class SearchInListAdapter extends ItemViewHolder<SearchInDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.container)
    RelativeLayout container;


    public SearchInListAdapter(View view) {
        super(view);
    }

    public static Class<SearchInListAdapter> newInstance() {

        return SearchInListAdapter.class;
    }


    @Override
    public void onSetValues(SearchInDT item, PositionInfo positionInfo) {

        sourceImageView.setImageURI(Uri.parse(item.getImage_url()));
        title.setText(item.getTitle());


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();


    }


    public interface Listener {
        void onButtonClicked(VendorCategoryDT datetype);
    }

}
