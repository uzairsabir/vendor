package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.models.ContactModel;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataDelete;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.android.AppPreferences;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 8/8/2016.
 */
public class StickyListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    //  private String[] countries;
    private LayoutInflater inflater;
    private ArrayList<ContactDT> phoneContact;
    Activity activity;
    String user_type = "";


    public StickyListViewAdapter(Context context, Activity _activity, ArrayList<ContactDT> _phoneContact) {
        inflater = LayoutInflater.from(context);
        phoneContact = _phoneContact;
        activity = _activity;
        //countries = context.getResources().getStringArray(R.array.countries);
    }

    @Override
    public int getCount() {
        return phoneContact.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneContact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_sticky_child_contact_list, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.title_name);
            holder.add_icon = (ImageView) convertView.findViewById(R.id.add_icon);
            holder.container = (RelativeLayout) convertView.findViewById(R.id.container);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (ContactsMainActivity.tab_pos == 1) {
            user_type = "vendor";
        } else {
            user_type = "consumer";
        }

        if (phoneContact.get(position).getNumbersToPerform().size() > 1) {

        } else {
            try {
                if(ContactsMainActivity.tab_pos==0){
                    if (RealmDataRetrive.getCustomersDetail(phoneContact.get(position).getNumbersToPerform().get(0)) != null) {
                        holder.add_icon.setImageResource(R.drawable.minu_red_icon);
                        holder.isdeleteRequire = true;
                    } else {
                        holder.add_icon.setImageResource(R.drawable.add_blue_icon);
                        holder.isdeleteRequire = false;
                    }
                }else if(ContactsMainActivity.tab_pos==1){
                    if (RealmDataRetrive.getColleaguesDetail(phoneContact.get(position).getNumbersToPerform().get(0)) != null) {
                        holder.add_icon.setImageResource(R.drawable.minu_red_icon);
                        holder.isdeleteRequire = true;
                    } else {
                        holder.add_icon.setImageResource(R.drawable.add_blue_icon);
                        holder.isdeleteRequire = false;
                    }
                }

            } catch (Exception e) {

            }

        }


        try{
            holder.text.setText(phoneContact.get(position).getFirstname() + " " + phoneContact.get(position).getLastname());
        }catch(Exception e){

        }


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.isdeleteRequire) {

                    new callConsumerVendorDelete(phoneContact.get(position).getNumbersToPerform().get(0)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {

                    if (phoneContact.get(position).getNumbersToPerform().size() > 1) {
                        callDialogeForMultipleNumbers(phoneContact.get(position).getNumbersToPerform());
                    } else {
                        new callUserExistence(activity, phoneContact.get(position).getNumbersToPerform().get(0), position, false, null, user_type).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    }
                }

            }
        });

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.item_sticky_header_contact_list, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_title);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        try{
            String headerText = "" + phoneContact.get(position).getFirstname().subSequence(0, 1).charAt(0);
            holder.text.setText(headerText);
        }catch (Exception e){

        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return phoneContact.get(position).getFirstname().subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
        ImageView add_icon;
        RelativeLayout container;
        boolean isdeleteRequire = false;
    }

    public void callDialogeForMultipleNumbers(final ArrayList<String> arrayList) {
        final Dialog categoryDialoge = new Dialog(activity);
        categoryDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialoge.setContentView(R.layout.dialoge_listview);
        categoryDialoge.show();
        Window window = categoryDialoge.getWindow();
        window.setLayout(AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) - 100, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 1000);
        DynamicListView listView = (DynamicListView) categoryDialoge.findViewById(R.id.listview);
        RelativeLayout loadingContainer = (RelativeLayout) categoryDialoge.findViewById(R.id.loading_container);
        TextView title = (TextView) categoryDialoge.findViewById(R.id.title_cat);

        loadingContainer.setVisibility(View.GONE);
        EasyAdapter easyAdapter;

        title.setText("Select a number to create provider from.");

        easyAdapter = new EasyAdapter<>(
                activity,
                MultipleNumbersListAdapter.newInstance(activity),
                arrayList);


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryDialoge.dismiss();

                new callUserExistence(activity, phoneContact.get(position).getNumbersToPerform().get(0), position, true, arrayList, user_type).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }
        });


    }


    private class callUserExistence extends AsyncTask<String, Void, Boolean> {


        String userName = "", user_type = "";
        Activity activity;
        int position;
        boolean isMultpleNumber;
        ArrayList<String> arrayList = new ArrayList<>();


        public callUserExistence(Activity _activity, String username, int _position, boolean _isMultpleNumber, ArrayList<String> _arrayList, String _user_type) {
            this.userName = username;
            activity = _activity;
            position = _position;
            isMultpleNumber = _isMultpleNumber;
            arrayList = _arrayList;
            user_type = _user_type;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                return ContactModel.checkUserExistence(userName, user_type);


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
                if (isMultpleNumber) {
                    ListenerController.openCreateNewContacts(activity, ContactsMainActivity.tab_pos, arrayList.get(position));

                } else {
                    ListenerController.openCreateNewContacts(activity, ContactsMainActivity.tab_pos, (phoneContact.get(position).getNumbersToPerform().get(0)));
                }


            }


        }
    }

    private class callConsumerVendorDelete extends AsyncTask<String, Void, Boolean> {


        String providername = "";


        public callConsumerVendorDelete(String _providername) {

            providername = _providername;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (ContactsMainActivity.tab_pos == 0) {
                    return ContactModel.checkDeleteConsumerFriendLink(providername);

                } else {
                    return ContactModel.checkDeleteConsumerVendorLink(providername);
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
                //   RealmDataDelete.deleteHomeItem(providerUserName, ContactsMainActivity.tab_pos);
                RealmDataDelete.deleteHomeItem(providername, ContactsMainActivity.tab_pos);
//                try{
//                    ((ContactsMainActivity) activity).viewUpdate();
//                }catch (Exception e){
//
//                }

                //notifyDataSetChanged();
                //RealmDataDelete.deleteConsumerProviderLink(providername, ContactsMainActivity.tab_pos);
                // notifyDataSetChanged();
                AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, ContactsMainActivity.tab_pos, false);


            }


        }
    }


}

