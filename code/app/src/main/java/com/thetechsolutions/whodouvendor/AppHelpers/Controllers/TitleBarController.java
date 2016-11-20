package com.thetechsolutions.whodouvendor.AppHelpers.Controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.andreabaccega.widget.FormEditText;
import com.cocosw.bottomsheet.BottomSheet;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.SearchInDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatMainActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeCreateNewContactActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeFriendProfileActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainSearchActivity;
import com.thetechsolutions.whodouvendor.Home.adapters.CategoryListAdapter;
import com.thetechsolutions.whodouvendor.Home.adapters.SearchInListAdapter;
import com.thetechsolutions.whodouvendor.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouvendor.Pay.activities.PayMainActivity;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.activities.ScheduleMainActivity;
import com.thetechsolutions.whodouvendor.Settings.activities.SettingsMainActivity;
import com.thetechsolutions.whodouvendor.Settings.controller.SettingsController;
import com.thetechsolutions.whodouvendor.Settings.fragments.SettingProfileFragment;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/16/2016.
 */
public class TitleBarController {

    TextView title;
    public static ImageView leftImage, rightImage;
    static Activity activity;
    private ViewGroup mLinearLayout;
    int catId = 0, subCatId = 0, tab_pos = 0;

    private static TitleBarController ourInstance = new TitleBarController(activity);

    public static TitleBarController getInstance(Activity _activity) {

        activity = _activity;
        return ourInstance;
    }

    private TitleBarController(Activity activity) {


    }

    public void setTitleBar(final Activity activity, String _title, boolean isBackPressRequired, boolean isAddIconRequired, boolean isSearchRequired) {

        try {
            mLinearLayout = (ViewGroup) activity.findViewById(R.id.container);
            View layout2 = LayoutInflater.from(activity).inflate(R.layout.item_title_bar, mLinearLayout, false);
            title = (TextView) layout2.findViewById(R.id.title);
            leftImage = (ImageView) layout2.findViewById(R.id.left_image);
            rightImage = (ImageView) layout2.findViewById(R.id.right_image);
            mLinearLayout.addView(layout2);
        } catch (Exception e) {
            e.printStackTrace();
        }


        title.setText(_title);
        if (isBackPressRequired) {
            leftImage.setVisibility(View.VISIBLE);
        } else {
            leftImage.setVisibility(View.GONE);
        }
        if (isAddIconRequired) {
            rightImage.setVisibility(View.VISIBLE);
        } else {
            rightImage.setVisibility(View.GONE);
        }
        if (isSearchRequired) {

        } else {

        }


        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightImageClick(activity);

            }
        });

        RightImageNavigation(activity);
        LeftImageNavigation(activity);


    }

    private void LeftImageNavigation(final Activity activity) {
        if (activity instanceof HomeMainActivity) {

            leftImage.setImageResource(R.drawable.nav_search_ico);
            leftImage.setPadding(45, 45, 45, 45);
            leftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    catId = 0;
                    subCatId = 0;
                    tab_pos = 0;

                    final Dialog workDialog = new Dialog(activity);
                    workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    workDialog.setContentView(R.layout.dialoge_search_box);

                    final FormEditText search_in = (FormEditText) workDialog.findViewById(R.id.search_in);
                    final FormEditText category = (FormEditText) workDialog.findViewById(R.id.category);
                    final FormEditText sub_category = (FormEditText) workDialog.findViewById(R.id.sub_category);
                    final FormEditText keyword = (FormEditText) workDialog.findViewById(R.id.keyword);

                    Window window = workDialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.FILL_PARENT, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 800);


                    workDialog.show();


                    Button clear = (Button) workDialog.findViewById(R.id.clear);

                    final Button search = (Button) workDialog.findViewById(R.id.search);

                    search_in.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callSearchInDialogue(activity, search_in);

                        }
                    });

                    category.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callCategoryListDialogue(activity, false, category, sub_category);
                        }
                    });

                    sub_category.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callCategoryListDialogue(activity, true, category, sub_category);
                        }
                    });

                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            workDialog.cancel();

                            ListenerController.openHomeSearchActivity(activity, keyword.getText().toString(), tab_pos, catId, subCatId, "Search result in,");

                        }
                    });

                    clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            search_in.setText("");
                            category.setText("");
                            sub_category.setText("");
                            keyword.setText("");
                            catId = 0;
                            subCatId = 0;
                            tab_pos = 0;

                        }
                    });


                }

            });

        } else {
            leftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeActivityController(activity);
                    activity.finish();

                }
            });

        }

    }

    private void rightImageClick(final Activity activity) {
        if (activity instanceof HomeMainActivity) {

            if (((HomeMainActivity) activity).pager.getCurrentItem() == 1) {


                new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog).title("How would you like to add them ?")
                        .sheet(R.menu.sheet_menu).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.create_new:

                                ListenerController.openCreateNewContacts(activity, 1, "");
                                break;
                            case R.id.contact_list:
                                ListenerController.openContacts(activity, 1);

                                break;
                            case R.id.scan_card:

                                ListenerController.openOCRActivity(activity);

                                // EasyImage.openCamera(activity, EasyImageConfig.REQ_TAKE_PICTURE);


                                break;
                            case R.id.cancel:

                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();

            } else if (((HomeMainActivity) activity).pager.getCurrentItem() == 0) {


                new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog).title("How would you like to add them ?")
                        .sheet(R.menu.sheet_menu_other).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.create_new:

                                ListenerController.openCreateNewContacts(activity, 0, "");
                                break;
                            case R.id.contact_list:
                                ListenerController.openContacts(activity, 0);

                                break;

                            case R.id.cancel:

                                dialog.dismiss();
                                break;
                        }
                    }
                }).show();
            } else if (((HomeMainActivity) activity).pager.getCurrentItem() == 2) {

                catId = 0;
                subCatId = 0;
                tab_pos = 0;

                final Dialog workDialog = new Dialog(activity);
                workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                workDialog.setContentView(R.layout.dialoge_search_box);

                final FormEditText search_in = (FormEditText) workDialog.findViewById(R.id.search_in);
                final FormEditText category = (FormEditText) workDialog.findViewById(R.id.category);
                final FormEditText sub_category = (FormEditText) workDialog.findViewById(R.id.sub_category);
                final FormEditText keyword = (FormEditText) workDialog.findViewById(R.id.keyword);

                Window window = workDialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.FILL_PARENT, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 800);


                workDialog.show();


                Button clear = (Button) workDialog.findViewById(R.id.clear);

                final Button search = (Button) workDialog.findViewById(R.id.search);

                search_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callSearchInDialogue(activity, search_in);

                    }
                });

                category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callCategoryListDialogue(activity, false, category, sub_category);
                    }
                });

                sub_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callCategoryListDialogue(activity, true, category, sub_category);
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        workDialog.cancel();

                        ListenerController.openHomeSearchActivity(activity, keyword.getText().toString(), tab_pos, catId, subCatId, "Search result in,");

                    }
                });

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search_in.setText("");
                        category.setText("");
                        sub_category.setText("");
                        keyword.setText("");
                        catId = 0;
                        subCatId = 0;
                        tab_pos = 0;

                    }
                });


            }

        } else if (activity instanceof PayMainActivity) {

            ListenerController.openPaymentDetail(activity, 0, 3, "Create Payment");

        } else if (activity instanceof ScheduleMainActivity) {
            final RealmResults<ScheduleDT> list = RealmDataRetrive.getScheduleList(0);
            if (list.size() > 0) {
                MaterialDialog loaderDialoge = new MaterialDialog.Builder(activity)
                        .title("Add to Calendar")
                        .content("Would you like to add/update your upcoming appointments in calendar ?")
                        .contentGravity(GravityEnum.CENTER)
                        .positiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                for (ScheduleDT item : list
                                        ) {
                                    try {
                                        UtilityFunctions.deleteEventNew(activity, Integer.parseInt(item.getCalendar_guid()));
                                    } catch (Exception e) {

                                    }

                                }


                                for (ScheduleDT item : list
                                        ) {
                                    try {
                                        UtilityFunctions.addEvent(activity, UtilityFunctions.converMillisToDate(item.getAppointment_date_time(), "yyyy-MM-dd HH:mm:ss"), "Appointment with " + item.getConsumer_name(), Integer.parseInt(UtilityFunctions.spaceSplit(item.getEstimated_duration())[0]));
                                    } catch (Exception e) {

                                    }

                                }


                            }
                        })
                        .widgetColor(activity.getResources().getColor(R.color.who_do_u_blue))
                        .backgroundColor(activity.getResources().getColor(R.color.white))
                        .titleColor(activity.getResources().getColor(R.color.black))
                        .contentColor(activity.getResources().getColor(R.color.who_do_u_medium_grey))
                        .show();
            } else {
                AppController.showToast(activity, " No upcoming appointments found ");

            }


            // ListenerController.openScheduleDetail(activity, 0, 2, "Create Appointment","");


        } else if (activity instanceof HomeCreateNewContactActivity) {
            ((HomeCreateNewContactActivity) activity).validatorCode();
        } else if (activity instanceof ChatMainActivity) {
            callChatInDialogue(activity);
        } else if (activity instanceof SettingsMainActivity) {

            if (((SettingsMainActivity) activity).pager.getCurrentItem() == 0) {

            } else if (((SettingsMainActivity) activity).pager.getCurrentItem() == 1) {

                SettingsController.updateProfile(activity,
                        AppPreferences.getString(AppPreferences.PREF_USER_NUMBER),
                        SettingProfileFragment.fragment.firstName.getText().toString(),
                        SettingProfileFragment.fragment.lastName.getText().toString(), SettingProfileFragment.address, SettingProfileFragment.fragment.city_state.getText().toString(), "",
                        "", SettingProfileFragment.email, SettingProfileFragment.fragment.zip_codes.getText().toString(), SettingProfileFragment.sub_cat_id, SettingProfileFragment.fragment.imageUrl, 1
                );

            }

        }
    }


    public void callCategoryListDialogue(Activity activity, final boolean isSubCategory,
                                         final FormEditText cat_view, final FormEditText sub_cat_view) {


        final Dialog categoryDialoge = new Dialog(activity);
        categoryDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialoge.setContentView(R.layout.dialoge_listview);
        categoryDialoge.show();
        Window window = categoryDialoge.getWindow();
        window.setLayout(AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) - 100, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 700);
        DynamicListView listView = (DynamicListView) categoryDialoge.findViewById(R.id.listview);
        RelativeLayout loadingContainer = (RelativeLayout) categoryDialoge.findViewById(R.id.loading_container);
        TextView title = (TextView) categoryDialoge.findViewById(R.id.title_cat);

        loadingContainer.setVisibility(View.GONE);
        EasyAdapter easyAdapter;

        final RealmResults<VendorCategoryDT> subcatList = RealmDataRetrive.getSubCategoryList(catId);
        final RealmResults<VendorCategoryDT> catList = RealmDataRetrive.getCategoryList(0);
        if (isSubCategory) {
            title.setText("Select a sub category");
            easyAdapter = new EasyAdapter<>(
                    activity,
                    CategoryListAdapter.newInstance(isSubCategory),
                    subcatList);
        } else {
            title.setText("Select a category");

            easyAdapter = new EasyAdapter<>(
                    activity,
                    CategoryListAdapter.newInstance(isSubCategory),
                    catList);
        }


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                categoryDialoge.dismiss();

                if (isSubCategory) {

                    sub_cat_view.setText(subcatList.get(position).getSubcategory());
                    subCatId = (subcatList.get(position).getSubcategory_id());

                    if (catId == 0) {
                        catId = (RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory_id());
                        cat_view.setText(RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory());
                    }
                } else {
                    catId = (catList.get(position).getCategory_id());
                    cat_view.setText(catList.get(position).getCategory());

                    sub_cat_view.setText("");
                    subCatId = 0;
                }


            }
        });


    }

    public void callSearchInDialogue(Activity activity, final FormEditText searchinView) {
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


        final RealmResults<SearchInDT> catList = RealmDataRetrive.getSearchInList(false);

        title.setText("Search In,");

        easyAdapter = new EasyAdapter<>(
                activity,
                SearchInListAdapter.newInstance(),
                catList);


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryDialoge.dismiss();
                searchinView.setText("Search In, " + catList.get(position).getTitle());
                tab_pos = position;

            }
        });


    }

    public void callChatInDialogue(final Activity activity) {
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


        final RealmResults<SearchInDT> catList = RealmDataRetrive.getSearchInList(true);

        title.setText("Chat With...");

        easyAdapter = new EasyAdapter<>(
                activity,
                SearchInListAdapter.newInstance(),
                catList);


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListenerController.openChatFromListActivity(activity, "", position, 0, 0, "Start Conversation");


                categoryDialoge.dismiss();


            }
        });


    }

    public void RightImageNavigation(final Activity activity) {
        if (activity instanceof SettingsMainActivity) {
            try {
                ((SettingsMainActivity) activity).pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        if (position == 1) {
                            rightImage.setVisibility(View.VISIBLE);
                            rightImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.title_tick));
                        } else {
                            rightImage.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


            } catch (Exception e) {

            }

        } else if (activity instanceof HomeCreateNewContactActivity) {
            rightImage.setVisibility(View.VISIBLE);
            rightImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.title_tick));


        } else if (activity instanceof HomeMainActivity) {

            try {

                ((HomeMainActivity) activity).pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        if (position == 0 || position == 1) {
                            rightImage.setVisibility(View.VISIBLE);
                            rightImage.setImageResource(R.drawable.title_add_icon);
                            rightImage.setPadding(30, 30, 30, 30);
                        } else {
                            rightImage.setImageResource(R.drawable.nav_search_ico);
                            rightImage.setPadding(45, 45, 45, 45);
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();

            }


        }
    }

    public void homeActivityController(Activity activity) {
        if (activity instanceof HomeCreateNewContactActivity) {
            try {
                HomeMainActivity.activity.finish();
                HomeMainActivity.activity.finish();
            } catch (Exception e) {

            }

            try {
                MyLogs.printinfo("new_tab_pos  " + HomeCreateNewContactActivity.tab_pos);
                ListenerController.openHomeActivity(activity, HomeCreateNewContactActivity.tab_pos);
            } catch (Exception e) {

            }

        } else if (activity instanceof ContactsMainActivity) {
            try {
                HomeMainActivity.activity.finish();
            } catch (Exception e) {

            }
            try {
                ListenerController.openHomeActivity(activity, ContactsMainActivity.tab_pos);
            } catch (Exception e) {

            }


        } else if (activity instanceof HomeFriendProfileActivity) {
            try {
                HomeMainActivity.activity.finish();
            } catch (Exception e) {

            }
            try {
                ListenerController.openHomeActivity(activity, HomeMainFragment.pos);
            } catch (Exception e) {

            }


        } else if (activity instanceof HomeMainSearchActivity) {
            try {
                HomeMainActivity.activity.finish();
            } catch (Exception e) {

            }
            try {
                ListenerController.openHomeActivity(activity, 2);
            } catch (Exception e) {

            }


        }
    }


}
