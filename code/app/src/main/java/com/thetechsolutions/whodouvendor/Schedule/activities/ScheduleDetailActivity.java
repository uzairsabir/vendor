package com.thetechsolutions.whodouvendor.Schedule.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kyleduo.switchbutton.SwitchButton;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.adapters.DurationListAdapter;
import com.thetechsolutions.whodouvendor.Schedule.controller.ScheduleController;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import eu.siacs.conversation.Config;
import eu.siacs.conversation.entities.Account;
import eu.siacs.conversation.entities.Contact;
import eu.siacs.conversation.entities.Conversation;
import eu.siacs.conversation.entities.Message;
import eu.siacs.conversation.services.XmppConnectionService;
import eu.siacs.conversation.ui.XmppActivity;
import eu.siacs.conversation.xmpp.jid.InvalidJidException;
import eu.siacs.conversation.xmpp.jid.Jid;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ScheduleDetailActivity extends XmppActivity implements MethodGenerator,
        View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, XmppConnectionService.OnShowErrorToast {

    static Activity activity;

    TextView title_name, location_name;
    SimpleDraweeView fresco_view;
    TextView appointment_date, duration;
    Button accept_btn, reschedule_btn, decline_btn;
    static int id, tab_position;
    RelativeLayout switch_button_container;
    AutoCompleteTextView auto_com_cutomer_name;
    RelativeLayout item_container;
    static String title, providerUserName;
    SwitchButton switch_button;
    String selectedDateTime, appointmentId, appointmentStatus;
    EditText description;

    long calId = 0;
    ScheduleDT item_detail;
    String vendorId;
    String sqlDateTime;
    String callMessgae = "1";
    String durationS = "";


    public static Intent createIntent(Activity _activity, int _id, int _tab_pos, String _title, String _providerUserName) {
        activity = _activity;
        id = _id;
        tab_position = _tab_pos;
        title = _title;
        providerUserName = _providerUserName;
        return new Intent(activity, ScheduleDetailActivity.class);

    }

    @Override
    protected void refreshUiReal() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        activity = this;
        try {
            getActionBar().hide();

        } catch (Exception e) {

        }

        TitleBarController.getInstance(activity).setTitleBar(activity, title, true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
        title_name = (TextView) findViewById(R.id.title_name);
        location_name = (TextView) findViewById(R.id.address);
        appointment_date = (TextView) findViewById(R.id.appointment_date);
        duration = (TextView) findViewById(R.id.duration);
        description = (EditText) findViewById(R.id.description);

        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);

        accept_btn = (Button) findViewById(R.id.accept_btn);
        reschedule_btn = (Button) findViewById(R.id.reschedule_btn);
        decline_btn = (Button) findViewById(R.id.decline_btn);
        switch_button_container = (RelativeLayout) findViewById(R.id.switch_button_container);

        auto_com_cutomer_name = (AutoCompleteTextView) findViewById(R.id.auto_com_cutomer_name);
        item_container = (RelativeLayout) findViewById(R.id.item_container);
        switch_button = (SwitchButton) findViewById(R.id.switch_button);

        accept_btn.setOnClickListener(this);
        reschedule_btn.setOnClickListener(this);
        decline_btn.setOnClickListener(this);
        appointment_date.setOnClickListener(this);
        duration.setOnClickListener(this);

    }

    @Override
    public void viewUpdate() {


        if (id != 0) {
            item_detail = RealmDataRetrive.getScheduleDetail(id);
            if (item_detail.getCall_message() == 1) {
                switch_button.setChecked(true);
            } else {
                switch_button.setChecked(false);
            }

            title_name.setText(item_detail.getConsumer_name());
            location_name.setText(item_detail.getConsumer_address());
            appointment_date.setText(item_detail.getAppointmentDate() + " @ " + UtilityFunctions.formatteSqlTime(UtilityFunctions.converMillisToDate(item_detail.getAppointment_date_time(), "yyyy-MM-dd HH:mm:ss")));
            duration.setText(item_detail.getEstimated_duration() + "");
            fresco_view.setImageURI(Uri.parse(item_detail.getConsumer_image_url()));
            description.setText(item_detail.getDescription());
            appointmentId = "" + item_detail.getAppointment_id();
            appointmentStatus = item_detail.getStatus();
            vendorId = "" + item_detail.getVendor_id();
            sqlDateTime = UtilityFunctions.converMillisToDate(item_detail.getAppointment_date_time(), "yyyy-MM-dd HH:mm:ss");
            durationS = item_detail.getEstimated_duration();
            try {
                calId = Long.parseLong(item_detail.getCalendar_guid());
            } catch (Exception e) {

            }
            if (item_detail.getStatus().equals(AppConstants.ACCEPTED)) {
                accept_btn.setVisibility(View.GONE);
            } else if (item_detail.getStatus().equals(AppConstants.REJECTED)) {
                decline_btn.setVisibility(View.GONE);
                accept_btn.setVisibility(View.GONE);
            }


        }

        if (tab_position == 1) {
            accept_btn.setVisibility(View.GONE);
            decline_btn.setVisibility(View.GONE);
            reschedule_btn.setVisibility(View.GONE);
            switch_button_container.setVisibility(View.GONE);
            appointment_date.setEnabled(false);
            description.setEnabled(false);
            duration.setEnabled(false);

        } else if (tab_position == 2) {
            auto_com_cutomer_name.setVisibility(View.VISIBLE);
            item_container.setVisibility(View.GONE);
            reschedule_btn.setVisibility(View.GONE);
            accept_btn.setVisibility(View.VISIBLE);
            accept_btn.setText("Submit");

            if (!UtilityFunctions.isEmpty(providerUserName)) {
                CustomersDT item = RealmDataRetrive.getCustomersDetail(providerUserName);
                auto_com_cutomer_name.setText("" + item.getFirst_name() + " " + item.getLast_name());
                vendorId = "" + item.getId();
                auto_com_cutomer_name.setEnabled(false);
            }
            //   final String[] names = new String[]{"Ricky", "Aubery", "David"};
            ArrayList<String> providers_name = new ArrayList<>();
            for (CustomersDT item : RealmDataRetrive.getCustomerList()) {
                providers_name.add(item.getFirst_name() + " " + item.getLast_name());
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    activity,
                    R.layout.item_auto_complete,
                    R.id.suggested_text,
                    providers_name);
            auto_com_cutomer_name.setAdapter(adapter);
            auto_com_cutomer_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> view, View arg1, int position, long arg3) {
                    vendorId = "" + RealmDataRetrive.getCustomerList().get(position).getId();

                }
            });
        }

        if (switch_button.isChecked()) {
            switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_green)));

        } else {
            switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_red)));

        }

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_green)));
                    callMessgae = "1";
                } else {
                    switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_red)));
                    callMessgae = "0";
                }

            }
        });

        appointment_date.setEnabled(false);
        description.setEnabled(false);
        duration.setEnabled(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept_btn:
                validator(true, "accepted");
                sendMessage(item_detail.getConsumer_username() + "_c", "Appointment has been accepted:");

                break;
            case R.id.reschedule_btn:

                break;
            case R.id.decline_btn:
                callReason(activity);

                break;
            case R.id.duration:
                callHoursDialogue(activity);
                break;
            case R.id.appointment_date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(activity.getResources().getColor(R.color.who_do_u_blue));
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        sqlDateTime = year + "-" + (Integer.parseInt("" + monthOfYear) + 1) + "-" + dayOfMonth;
        selectedDateTime = UtilityFunctions.getMonthFromInt(monthOfYear + 1) + " " + UtilityFunctions.ordinal(dayOfMonth) + ", " + year;
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE), false);
        tpd.setAccentColor(activity.getResources().getColor(R.color.who_do_u_blue));
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String amPm = "";
        if (view.getIsCurrentlyAmOrPm() == 1) {
            amPm = "PM";
        } else {
            amPm = "AM";
        }
        if (minute < 10) {
            selectedDateTime += "  " + hourOfDay + ":0" + minute + " " + amPm;
        } else {
            selectedDateTime += "  " + hourOfDay + ":" + minute + " " + amPm;
        }
        sqlDateTime += " " + hourOfDay + ":" + minute + ":" + second;
        appointment_date.setText(selectedDateTime);
    }

    public void callHoursDialogue(final Activity activity) {
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


//        final RealmResults<SearchInDT> catList = RealmDataRetrive.getSearchInList(true);

        final ArrayList<String> list = new ArrayList<>();
        list.add("30 minutes");
        list.add("1 hour");
        list.add("1 hour 30 minutes ");
        list.add("2 hours");
        list.add("2 hours 30 minutes");
        list.add("3 hours");
        list.add("3 hours 30 minutes");
        list.add("4 hours");
        list.add("4 hours 30 minutes");
        list.add("5 hours");
        list.add("5 hours 30 minutes");
        list.add("6 hours");
        list.add("6 hours 30 minutes");
        list.add("1 day");
        list.add("2 days");
        list.add("3 days");
        list.add("4 days");
        list.add("5 days");
        list.add("1 week");
        list.add("2 weeks");
        list.add("1 month");
        title.setText(" Please Select Appointment Duration.");

        easyAdapter = new EasyAdapter<>(
                activity,
                DurationListAdapter.newInstance(),
                list);


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                durationS = list.get(position);
                duration.setText(durationS);
                categoryDialoge.dismiss();


            }
        });


    }

    private void validator(boolean isUpdate, String appointmentStatus) {
        // MyLogs.printinfo("sqlDatetime " + sqlDateTime);
        if (UtilityFunctions.isEmpty(vendorId)) {
            UtilityFunctions.showToast_onCenter("Please select a Provider", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(sqlDateTime)) {
            UtilityFunctions.showToast_onCenter("Please select Appointment Date Time", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(durationS)) {
            durationS = "30 minutes";
        }


        try {
            //calId = 0;
            int duration = 0;
            String onlyDuration = UtilityFunctions.spaceSplit(durationS)[0];
            // MyLogs.printinfo("onlyDuration " + onlyDuration);
            if (durationS.contains("minutes")) {
                if (onlyDuration.equals("30")) {
                    duration = 1;
                } else {
                    duration = Integer.parseInt(onlyDuration);
                }
            } else if (durationS.contains("days") || durationS.contains("day")) {
                duration = Integer.parseInt(onlyDuration) * 24;

            } else if (durationS.contains("weeks") || durationS.contains("week")) {
                duration = Integer.parseInt(onlyDuration) * 168;

            } else if (durationS.contains("months") || durationS.contains("month")) {
                duration = Integer.parseInt(onlyDuration) * 730;

            } else {
                duration = Integer.parseInt(onlyDuration);
            }


            //  calId = UtilityFunctions.addEvent(activity, sqlDateTime, auto_com_cutomer_name.getText().toString(), duration);

            if (!appointmentStatus.equals("rejected")) {
                calId = UtilityFunctions.addEvent(activity, sqlDateTime, title_name.getText().toString(), duration);
            } else {
                UtilityFunctions.deleteEventNew(activity, calId);
            }
        } catch (Exception e) {

        }

        if (isUpdate) {
            ScheduleController.getInstance().updateAppointmentStatus(activity, appointmentId, appointmentStatus, "" + calId, "");

            // ScheduleController.getInstance().updateAppointments(activity, appointmentId, sqlDateTime, durationS, description.getText().toString(), appointmentStatus, "" + calId);

        }
//        else {
//            ScheduleController.getInstance().createAppointments(activity, vendorId, sqlDateTime, durationS, description.getText().toString(), callMessgae, "" + calId);
//
//        }

    }


    public void callReason(final Activity activity) {
        final Dialog categoryDialoge = new Dialog(activity);
        categoryDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialoge.setContentView(R.layout.dialoge_reason);
        categoryDialoge.show();
        Window window = categoryDialoge.getWindow();
        window.setLayout(AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) - 100, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 1000);
        final EditText message = (EditText) categoryDialoge.findViewById(R.id.msg_input);
        Button send_btn = (Button) categoryDialoge.findViewById(R.id.send_message);
        Button cancel_btn = (Button) categoryDialoge.findViewById(R.id.cancel_message);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator(true, "rejected");
                sendMessage(item_detail.getConsumer_username() + "_c", "Appointment has been cancelled:\n" + message.getText().toString());

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validator(true, "rejected");

            }
        });


    }

    public void sendMessage(String contactNumber, String message) {
        String accountNumber = AppPreferences.getString(AppPreferences.PREF_USER_NUMBER) + "_v";
        Log.e("prefilledJid ", " " + accountNumber + " " + contactNumber);

        Jid accountJid = null;
        try {

            accountJid = Jid.fromString(accountNumber + "@" + Config.MAGIC_CREATE_DOMAIN);

        } catch (InvalidJidException e) {
            e.printStackTrace();
        }
        Jid contactJid = null;
        try {
            contactJid = Jid.fromString(contactNumber + "@" + Config.MAGIC_CREATE_DOMAIN);
        } catch (InvalidJidException e) {
            e.printStackTrace();
        }
        if (!xmppConnectionServiceBound) {
            return;
        }

        final Account account = xmppConnectionService.findAccountByJid(accountJid);
        if (account == null) {
            return;
        }

        try {
            final Contact contact = account.getRoster().getContact(contactJid);
            Conversation conversation = null;
            if (contact.showInRoster()) {

                conversation = xmppConnectionService
                        .findOrCreateConversation(contact.getAccount(),
                                contact.getJid(), false);


            } else {
                xmppConnectionService.createContact(contact);
                //    switchToConversation(contact);

                conversation = xmppConnectionService
                        .findOrCreateConversation(contact.getAccount(),
                                contact.getJid(), false);
                ///  switchToConversation(conversation);


            }
            sendMessage(conversation, message);
        } catch (Exception e) {

        }

        return;


    }

    private void sendMessage(Conversation conversation, String messagea) {
        final String body = messagea;
        if (body.length() == 0 || conversation == null) {
            return;
        }
        final Message message;
        if (conversation.getCorrectingMessage() == null) {
            message = new Message(conversation, body, conversation.getNextEncryption());
            if (conversation.getMode() == Conversation.MODE_MULTI) {
                if (conversation.getNextCounterpart() != null) {
                    message.setCounterpart(conversation.getNextCounterpart());
                    message.setType(Message.TYPE_PRIVATE);
                }
            }
        } else {
            message = conversation.getCorrectingMessage();
            message.setBody(body);
            message.setEdited(message.getUuid());
            message.setUuid(UUID.randomUUID().toString());
            conversation.setCorrectingMessage(null);
        }
        xmppConnectionService.sendMessage(message);
        //messageSent();
        //sendPlainTextMessage(message);
//        switch (conversation.getNextEncryption()) {
//            case Message.ENCRYPTION_OTR:
//                sendOtrMessage(message);
//                break;
//            case Message.ENCRYPTION_PGP:
//                sendPgpMessage(message);
//                break;
//            case Message.ENCRYPTION_AXOLOTL:
//                if (!activity.trustKeysIfNeeded(ConversationActivity.REQUEST_TRUST_KEYS_TEXT)) {
//                    sendAxolotlMessage(message);
//                }
//                break;
//            default:
//                sendPlainTextMessage(message);
//        }
    }


    @Override
    public void onShowErrorToast(int resId) {

    }

    @Override
    protected void onBackendConnected() {

    }

}
