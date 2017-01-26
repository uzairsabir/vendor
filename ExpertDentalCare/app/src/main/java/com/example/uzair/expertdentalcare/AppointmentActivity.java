package com.example.uzair.expertdentalcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by Uzair on 12/18/2016.
 */

public class AppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Activity activity;
    private EditText name, last_name, select_date_time, phone, dentist_name;
    private Button create_appointment_btn;
    String date, Time;


    public static Intent createIntent(Activity activity) {
        return new Intent(activity, AppointmentActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_appointment);
        getSupportActionBar().setTitle("Create Appointment");
        name = (EditText) findViewById(R.id.name);
        last_name = (EditText) findViewById(R.id.last_name);
        select_date_time = (EditText) findViewById(R.id.select_date_time);
        phone = (EditText) findViewById(R.id.phone);
        dentist_name = (EditText) findViewById(R.id.dentist_name);
        create_appointment_btn = (Button) findViewById(R.id.create_appointment_btn);


        select_date_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                setDate();
            }
        });

        create_appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(activity, "name is empty",
                            Toast.LENGTH_LONG).show();
                    return;
                } else if (last_name.getText().toString().isEmpty()) {
                    Toast.makeText(activity, "last name is empty",
                            Toast.LENGTH_LONG).show();
                    return;
                } else if (select_date_time.getText().toString().isEmpty()) {
                    Toast.makeText(activity, "Date is empty",
                            Toast.LENGTH_LONG).show();
                    return;
                } else if (dentist_name.getText().toString().isEmpty()) {
                    Toast.makeText(activity, "Dentist name is empty",
                            Toast.LENGTH_LONG).show();
                    return;
                } else if (phone.getText().toString().isEmpty()) {
                    Toast.makeText(activity, "phone No is empty",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                AppointmentDatatype appointment = new AppointmentDatatype(name.getText().toString(), last_name.getText().toString(), select_date_time.getText().toString(), dentist_name.getText().toString(), phone.getText().toString());
                appointment.save();
                Toast.makeText(activity, "Your appointment has been created successfully",
                        Toast.LENGTH_LONG).show();
                activity.finish();

            }
        });


    }

//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//
//    }
//
//    @Override
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//
//    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + (Integer.parseInt("" + monthOfYear) + 1) + "-" + dayOfMonth;

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE), false);
        tpd.setAccentColor(activity.getResources().getColor(R.color.colorPrimary));
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

//    @Override
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//        Time = hourOfDay + " " + minute;
//
//        select_date_time.setText(date + " " + Time);
//    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Time = hourOfDay + ":" + minute;

        select_date_time.setText(date + " @ " + Time);
    }

    public void setDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(activity.getResources().getColor(R.color.colorPrimary));
        // dpd.setAccentColor(activity.getResources().getColor(R.color.who_do_u_blue));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
}
