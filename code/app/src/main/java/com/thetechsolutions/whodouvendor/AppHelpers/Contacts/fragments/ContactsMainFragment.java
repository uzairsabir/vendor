package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.adapters.StickyListViewAdapter;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.controllers.ContactsController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ContactsMainFragment extends Fragment implements View.OnClickListener {

    public static ContactsMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";

    ProgressActivity progressActivity;
    ArrayList<ContactDT> arrayList = new ArrayList<>();
    StickyListHeadersListView listView;
    RelativeLayout search_container;
    TextView search_text;
    EditText search_edit_text;
    String keyword;


    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new ContactsMainFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, ContactsController.getIntroLayout(sectionNumber));
        fragment.setArguments(args);

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(getArguments().getInt(ARG_SECTION), container, false);
            listView = (StickyListHeadersListView) rootView.findViewById(R.id.listview);
            progressActivity = (ProgressActivity) rootView.findViewById(R.id.progressActivity);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            search_container = (RelativeLayout) rootView.findViewById(R.id.search_container);
            search_text = (TextView) rootView.findViewById(R.id.search_text);
            search_edit_text = (EditText) rootView.findViewById(R.id.search_edit_text);
        } catch (Exception e) {

        }

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //keyword += s;
                performSearch(search_edit_text.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(search_edit_text.getText().toString());
                    return true;
                }
                return false;
            }


        });

        search_container.setOnClickListener(this);

        loadData();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_container:
                search_text.setVisibility(View.INVISIBLE);
                search_edit_text.setVisibility(View.VISIBLE);
                search_edit_text.requestFocus();
                UtilityFunctions.showSoftKeyboard(activity, search_edit_text);
                break;

        }
    }

    public void loadData() {
        new getDataList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class getDataList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

//                Gson gson = new Gson();
//                arrayList = gson.fromJson(SyncFriendsController.excuteContactListFromPhoneAsyn(activity), new TypeToken<List<PhoneContact>>() {
//                }.getType());
//
//                if (arrayList.size() > 0) {
//                    return true;
//                }

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                progressActivity.showContent();
                arrayList.addAll(RealmDataRetrive.getContactList());
                setAdapter(arrayList);
            } else if (!NetworkManager.checkInternet(activity)) {

            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        "Opps! There is something went wrong unable to get your contact list.");

            }

        }
    }

    private void performSearch(String keyword) {
        if (!UtilityFunctions.isEmpty(keyword)) {
            ArrayList<ContactDT> temp = new ArrayList<>();
            if (arrayList != null)
                if (arrayList.size() > 0) {

                    for (ContactDT item : arrayList) {
                        // MyLogs.printinfo("item_list" + item.getName() + " keyword " + keyword);
                        if (keyword.length() == 1) {
                            if (item.getFirstname().toLowerCase().startsWith(keyword.toLowerCase())) {
                                temp.add(item);
                            }
                        } else {
                            if ((item.getFirstname().toLowerCase() + item.getLastname().toLowerCase()).contains(keyword.toLowerCase())) {
                                temp.add(item);
                            }
                        }
                    }
                    setAdapter(temp);

                }

        } else {
            setAdapter(arrayList);
        }
    }


    private void setAdapter(ArrayList<ContactDT> list) {
        StickyListViewAdapter adapter = new StickyListViewAdapter(activity, activity, list);
        listView.setAdapter(adapter);

    }


}
