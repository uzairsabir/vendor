package com.thetechsolutions.whodouvendor.Pay.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kyleduo.switchbutton.SwitchButton;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.PayDT;
import com.thetechsolutions.whodouvendor.Pay.controller.PayController;
import com.thetechsolutions.whodouvendor.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmResults;

/**
 * Created by Uzair on 7/12/2016.
 */
public class PayDetailActivity extends FragmentActivityController implements MethodGenerator, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    static Activity activity;

    TextView title_name, service_name, location_name, default_gateway, receipt;
    SimpleDraweeView fresco_view;
    EditText amount, service_date, description;
    SwitchButton switch_button;
    Button payment_btn;
    static int id, tab_pos;
    RelativeLayout top_item;
    AutoCompleteTextView auto_com_cutomer_name;
    static String title;
    MaterialSpinner spinner;
    String selectedDateTime;
    String consumerId;
    String sqlDateTime;
    String callMessgae = "1";

    public static Intent createIntent(Activity _activity, int _id, int _tab_pos, String _title) {
        activity = _activity;
        id = _id;
        title = _title;
        tab_pos = _tab_pos;
        return new Intent(activity, PayDetailActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, title, true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
        title_name = (TextView) findViewById(R.id.title_name);
        service_name = (TextView) findViewById(R.id.service_name);
        location_name = (TextView) findViewById(R.id.address);
        amount = (EditText) findViewById(R.id.amount);
        service_date = (EditText) findViewById(R.id.service_date);
        description = (EditText) findViewById(R.id.description);
        default_gateway = (TextView) findViewById(R.id.default_gateway);

        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);
        switch_button = (SwitchButton) findViewById(R.id.switch_button);
        payment_btn = (Button) findViewById(R.id.payment_btn);

        top_item = (RelativeLayout) findViewById(R.id.top_item);
        payment_btn.setOnClickListener(this);

        auto_com_cutomer_name = (AutoCompleteTextView) findViewById(R.id.auto_com_cutomer_name);
        receipt = (TextView) findViewById(R.id.receipt);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);

        // MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("PayPal (Default)", "Stripe");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

        service_date.setOnClickListener(this);

        // search_view = (MaterialSearchView) findViewById(R.id.search_view);


    }

    @Override
    public void viewUpdate() {

        if (id == 0) {
            top_item.setVisibility(View.GONE);
            auto_com_cutomer_name.setVisibility(View.VISIBLE);
            //  final String[] names = new String[]{"Ricky", "Aubery", "David"};
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
                    String selected = (String) view.getItemAtPosition(position);
                    int pos = -1;
                    RealmResults<CustomersDT> arrayList = RealmDataRetrive.getCustomerList();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if ((arrayList.get(i).getFirst_name() + " " + arrayList.get(i).getLast_name()).equals(selected)) {
                            pos = i;
                            System.out.println("Position " + pos);

                            consumerId = "" + arrayList.get(i).getId();
                            break;
                        }
                    }

                }
            });

        } else {

            PayDT item_detail = RealmDataRetrive.getPayDetail(id);

            title_name.setText(item_detail.getConsumer_name());
            service_name.setText("No address");
            // service_name.setText("");
            // location_name.setText("");
            amount.setText(item_detail.getAmount());
            service_date.setText(item_detail.getDateToDisplay());
            fresco_view.setImageURI(Uri.parse(item_detail.getConsumer_image_url()));
            consumerId = "" + item_detail.getConsumer_id();

            if (item_detail.getRequest_receipt().equals("1")) {
                switch_button.setChecked(true);
            } else {
                switch_button.setChecked(false);
            }


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
        if (tab_pos == 1) {
            switch_button.setVisibility(View.GONE);
            payment_btn.setVisibility(View.GONE);
            receipt.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            amount.setEnabled(false);
            service_date.setEnabled(false);
            description.setEnabled(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_btn:
                validator(false);
                break;
            case R.id.service_date:
                // MyLogs.printinfo("appointment_date");
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
        //selectedDateTime = UtilityFunctions.ordinal(dayOfMonth) + " " + UtilityFunctions.getMonthFromInt(monthOfYear + 1) + ", " + year;
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

        service_date.setText(selectedDateTime);

    }

    private void validator(boolean isUpdate) {
        // MyLogs.printinfo("sqlDatetime " + sqlDateTime);
        if (UtilityFunctions.isEmpty(consumerId)) {
            UtilityFunctions.showToast_onCenter("Please select a Customer", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(sqlDateTime)) {
            UtilityFunctions.showToast_onCenter("Please select Service Date Time", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(amount.getText().toString())) {
            UtilityFunctions.showToast_onCenter("Please enter amount", activity);
            return;
        }


        PayController.getInstance().createPayment(activity, consumerId, amount.getText().toString(), description.getText().toString(), sqlDateTime, "pending", callMessgae);


    }
}
