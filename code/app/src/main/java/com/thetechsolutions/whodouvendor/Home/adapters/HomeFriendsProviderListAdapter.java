package com.thetechsolutions.whodouvendor.Home.adapters;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.models.ContactModel;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouvendor.Home.model.HomeModel;
import com.thetechsolutions.whodouvendor.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_home_friend_provider)
public class HomeFriendsProviderListAdapter extends ItemViewHolder<CustomersDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.friends_name)
    TextView friends_name;

    @ViewId(R.id.service_name)
    TextView service_name;

    @ViewId(R.id.address)
    TextView address;

    @ViewId(R.id.container)
    RelativeLayout container;

    @ViewId(R.id.add_btn)
    TextView add_btn;

    boolean isdeleteRequire;


    static Activity activity;
    CustomersDT item;

    boolean isRefresh =false;


    public HomeFriendsProviderListAdapter(View view) {
        super(view);
    }

    public static Class<HomeFriendsProviderListAdapter> newInstance(Activity _activity) {
        activity = _activity;

        return HomeFriendsProviderListAdapter.class;
    }


    @Override
    public void onSetValues(CustomersDT item, PositionInfo positionInfo) {
        sourceImageView.setImageURI(Uri.parse(item.getImage_url()));
        title.setText(item.getFirst_name() + " " + item.getLast_name());
        service_name.setText(item.getSub_category_title());
        address.setText(item.getAddress());
        friends_name.setText("(" + item.getFriend_name() + ")");
        this.item = item;

        try {

            if (RealmDataRetrive.getCustomersDetail(getItem().getMobile_number_1()) != null) {
                add_btn.setText("Remove");
                add_btn.setTextColor(activity.getResources().getColor(R.color.color_red));
                add_btn.setBackground(activity.getResources().getDrawable(R.drawable.bg_red_with_round_edges_center_white));
                isdeleteRequire = true;
            } else {
                add_btn.setText("Add");
                add_btn.setTextColor(activity.getResources().getColor(R.color.who_do_u_blue));
                add_btn.setBackground(activity.getResources().getDrawable(R.drawable.bg_blue_with_round_edges_center_white));

                isdeleteRequire = false;
            }
        } catch (Exception e) {

        }


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();


//        container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             //   ListenerController.openFriendProfileActivity(activity, 0, getItem().getId());
//            }
//        });

        //   MyLogs.printinfo("get_uid"+item);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new callConsumerVendorDelete(item.getUsername(), String.valueOf(item.getId()), item.getFirst_name(), item.getLast_name(), isdeleteRequire).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }
        });


    }


    public interface Listener {
        void onButtonClicked(CustomersDT datetype);
    }

    private class callConsumerVendorDelete extends AsyncTask<String, Void, Boolean> {


        String providername = "", providerId, fname, lname;
        boolean isDeleteRequire;


        public callConsumerVendorDelete(String _providername, String _providerId, String _fname, String _lname, boolean _isDeleteRequire) {

            providername = _providername;
            isDeleteRequire = _isDeleteRequire;
            providerId = _providerId;
            fname = _fname;
            lname = _lname;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (isDeleteRequire) {
                    return ContactModel.checkDeleteConsumerVendorLink(providername);
                } else {
                    return HomeModel.createConsumerProviderLink(providerId, fname, lname, providername, 2);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            AppController.hideDialoge();

            if (result) {


//                if (isDeleteRequire) {
//                    RealmDataDelete.deleteConsumerProviderLink(providername, 2);
//                }


                AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, 0, false);


            }


        }
    }



    public void refreshAdapter() {
        Listener listener = getListener(Listener.class);
        if (listener != null) {
            listener.onButtonClicked(item);

        }
    }


}
