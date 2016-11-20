package com.thetechsolutions.whodouvendor.Home.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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
public class CategoryListAdapter extends ItemViewHolder<VendorCategoryDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.container)
    RelativeLayout container;


    static boolean isSubCategory;


    public CategoryListAdapter(View view) {
        super(view);
    }

    public static Class<CategoryListAdapter> newInstance(boolean _isSubCategory) {
        isSubCategory = _isSubCategory;
        return CategoryListAdapter.class;
    }


    @Override
    public void onSetValues(VendorCategoryDT item, PositionInfo positionInfo) {
        if (isSubCategory) {
            try {
                sourceImageView.setImageURI(Uri.parse(item.getSubcategory_image_url()));
                title.setText(item.getSubcategory());
            } catch (Exception e) {

            }

        } else {
            sourceImageView.setImageURI(Uri.parse(item.getCategory_image_url()));
            title.setText(item.getCategory());
        }


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();


    }


    public interface Listener {
        void onButtonClicked(VendorCategoryDT datetype);
    }

}
