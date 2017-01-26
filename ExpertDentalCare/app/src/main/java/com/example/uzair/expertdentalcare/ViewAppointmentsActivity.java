package com.example.uzair.expertdentalcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Uzair on 12/18/2016.
 */

public class ViewAppointmentsActivity extends AppCompatActivity {
    Activity activity;
    ListView listview;
    ViewAppointmentListAdapter adapter;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, ViewAppointmentsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        activity = this;
        getSupportActionBar().setTitle("Your's Appointment");
        listview = (ListView) findViewById(R.id.listview);
        List<AppointmentDatatype> books = AppointmentDatatype.listAll(AppointmentDatatype.class);
        adapter = new ViewAppointmentListAdapter(activity, R.layout.item_view_appointment, books);
        listview.setAdapter(adapter);


    }


}
