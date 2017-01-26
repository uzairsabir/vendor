package com.thetechsolutions.whodouvendor.Chat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ColleagesDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.Chat.adapters.ChatListAdapter;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.adapters.ScheduleListAdapter;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.List;

import eu.siacs.conversation.entities.Conversation;
import eu.siacs.conversation.ui.UpdateData;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ChatMainActivity extends FragmentActivityController implements MethodGenerator, View.OnClickListener, UpdateData {

    static Activity activity;
    public ViewPager pager;

    ProgressActivity progressActivity;
    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;

    RelativeLayout search_container;
    TextView search_text;
    EditText search_edit_text;

    ArrayList<Conversation> finalList = new ArrayList<>();
    // private ConversationListAdapter listAdapter;

    public static Intent createIntent(Activity _activity) {
        activity = _activity;
        return new Intent(activity, ChatMainActivity.class);

    }
//
//    public ChatMainActivity() {
//        try {
//            ConversationActivity.registerApp(this);
//        } catch (Exception e) {
//
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_listview_progress_activity_with_search_chat);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, "Messaging", false, true, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
//        pager = (ViewPager) findViewById(R.id.view_pager);
//        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);
//        tapStrip.setVisibility(View.GONE);

        try {

            progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
            dynamicListView = (DynamicListView) findViewById(R.id.dynamiclistview);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            search_container = (RelativeLayout) findViewById(R.id.search_container);
            search_text = (TextView) findViewById(R.id.search_text);
            search_edit_text = (EditText) findViewById(R.id.search_edit_text);
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

    @Override
    public void viewUpdate() {

//        adapter = new ChatMainPagerAdapter(
//                getSupportFragmentManager(), activity);
//        pager.setAdapter(adapter);
//        tapStrip.setViewPager(pager);

    }

    private void loadData() {
        new getDataList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public ArrayList<Conversation> conversationList(List<Conversation> list) {

        //MyLogs.printinfo("arraylist " + list.size());

        // AppController.saveChatList(activity, list);
        //AppController
        //AppPreferences.setStringArray("",list);
        //  UtilityFunctions.writeToFileNew(activity,"",list);
        // RealmDataInsert.insertMessageList((ArrayList<Conversation>) list);
        return null;
    }


    class getDataList extends AsyncTask<String, Void, Boolean> {


        ArrayList<Conversation> arrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                arrayList = AppController.getChatList(activity);
                if (arrayList.size() > 0) {
                  // MyLogs.printinfo("arrayList " + arrayList.size());
                    finalList = AppController.removeDuplicates(arrayList);
                }
                MyLogs.printinfo("final_list " + finalList.size());
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


                setAdapter(finalList);


            } else if (!NetworkManager.checkInternet(activity)) {

            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.empty_chat_icon), "",
                        "Live chat allows you to get immediate feedback and keep track of your communications with vendors!" +
                                "Just click on the + button above to begin chatting.");

            }

        }
    }

    private void setAdapter(ArrayList<Conversation> arrayList) {
        MyLogs.printinfo("arrayList " + arrayList.size());
        ArrayList<Conversation> filterList = new ArrayList<>();
        for (Conversation item : arrayList) {
            boolean isFound = false;
            for (ColleagesDT providerDT : RealmDataRetrive.getColleagesList()) {
                if (item.getJid().toBareJid().toString().split("@")[0].equals(providerDT.getUsername() + "_v")) {
                    if (providerDT.getIs_register_user().equals("1")) {
                        filterList.add(item);

                    }
                    isFound=true;
                }
            }
            for (CustomersDT providerDT : RealmDataRetrive.getCustomerList()) {
                if (item.getJid().toBareJid().toString().split("@")[0].equals(providerDT.getUsername() + "_c")) {
                    if (providerDT.getIs_register_user().equals("1")) {
                        filterList.add(item);

                    }
                    isFound=true;
                }
            }
            if(!isFound){
                filterList.add(item);
            }
        }
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

            ArrayList<Conversation> temp = new ArrayList<>();
            if (finalList != null)
                if (finalList.size() > 0) {

                    for (Conversation conversation : finalList) {

                        for (CustomersDT providerDT : RealmDataRetrive.getHomeListOfMyProviderAndMyFriends()) {
                            if (conversation.getJid().toBareJid().toString().contains(providerDT.getUsername())) {
                                if (keyword.length() == 1) {
                                    if (providerDT.getFirst_name().toLowerCase().startsWith(keyword.toLowerCase())) {
                                        temp.add(conversation);
                                    }
                                } else {
                                    if ((providerDT.getFirst_name().toLowerCase() + providerDT.getLast_name().toLowerCase()).contains(keyword.toLowerCase())) {
                                        temp.add(conversation);
                                    }
                                }
                            }
                        }
                        // MyLogs.printinfo("item_list" + item.getName() + " keyword " + keyword);

                    }
                    setAdapter(temp);

                }

//            setAdapter(RealmDataRetrive.getChatSearch(keyword));

        } else {
            setAdapter(finalList);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }
}
