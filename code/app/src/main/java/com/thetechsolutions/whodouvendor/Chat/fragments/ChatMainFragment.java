package com.thetechsolutions.whodouvendor.Chat.fragments;

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

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ChatDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.Chat.adapters.ChatListAdapter;
import com.thetechsolutions.whodouvendor.Chat.controllers.ChatController;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.adapters.ScheduleListAdapter;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ChatMainFragment extends Fragment implements View.OnClickListener {

    static ChatMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";

    ProgressActivity progressActivity;
    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;

    RelativeLayout search_container;
    TextView search_text;
    EditText search_edit_text;


    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new ChatMainFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, ChatController.getIntroLayout(sectionNumber));
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(getArguments().getInt(ARG_SECTION), container, false);
            progressActivity = (ProgressActivity) rootView.findViewById(R.id.progressActivity);
            dynamicListView = (DynamicListView) rootView.findViewById(R.id.dynamiclistview);

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

    private void loadData() {
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
                setAdapter(RealmDataRetrive.getChatList());

            } else if (!NetworkManager.checkInternet(activity)) {

            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.empty_chat_icon), "",
                        "Live chat allows you to get immediate feedback and keep track of your communications with consumers!" +
                                "Just click on the + button above to begin chatting.");

            }

        }
    }

    private void setAdapter(RealmResults<ChatDT> arrayList) {
        MyLogs.printinfo("arrayList " + arrayList.size());
        easyAdapter = new EasyAdapter<>(
                activity,
                ChatListAdapter.newInstance(activity),
                arrayList, mListener);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(dynamicListView);
        dynamicListView.setAdapter(animationAdapter);

    }

    public ScheduleListAdapter.Listener mListener = new ScheduleListAdapter.Listener() {

        @Override
        public void onButtonClicked(ScheduleDT person) {
            easyAdapter.notifyDataSetChanged();
            dynamicListView.invalidate();
        }
    };

    private void performSearch(String keyword) {
        if (!UtilityFunctions.isEmpty(keyword)) {

            setAdapter(RealmDataRetrive.getChatSearch(keyword));

        } else {
            setAdapter(RealmDataRetrive.getChatList());
        }

    }


}
