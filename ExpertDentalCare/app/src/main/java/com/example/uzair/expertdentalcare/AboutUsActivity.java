package com.example.uzair.expertdentalcare;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hkm.slider.SliderLayout;
import com.orm.SugarContext;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Uzair on 12/18/2016.
 */

public class AboutUsActivity extends AppCompatActivity {
    Activity activity;
    SliderLayout slider;
    ImageView main_image;
    MyCountDownTimer myCountDownTimer;
    Random rand; // Global variable
    private Button create_appointment, view_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        activity = this;
        getSupportActionBar().setTitle("Home");
        SugarContext.init(activity);
        main_image = (ImageView) findViewById(R.id.main_image);
        create_appointment = (Button) findViewById(R.id.create_appointment);
        view_appointments = (Button) findViewById(R.id.view_appointments);
        create_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(AppointmentActivity.createIntent(activity));
            }
        });
        view_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AppointmentDatatype> books = AppointmentDatatype.listAll(AppointmentDatatype.class);
                if (books.size() > 0) {
                    activity.startActivity(ViewAppointmentsActivity.createIntent(activity));
                } else {
                    Toast.makeText(activity, "No Appointment Found",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //  slider = (SliderLayout) findViewById(R.id.slider);

//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(R.drawable.slide_one);
//        list.add(R.drawable.slide_two);
//        list.add(R.drawable.slide_three);
//        slider.addSliderList(getModel(ImageType.class));

        myCountDownTimer = new MyCountDownTimer(2000, 1000);
        myCountDownTimer.start();
        activity = this;
        if (!Prefs.getBoolean(AppConstant.IS_LOGIN, false)) {
            activity.startActivity(LoginActivity.createIntent(activity));
        }
    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(R.drawable.slide_one);
            list.add(R.drawable.slide_two);
            list.add(R.drawable.slide_three);
            rand = new Random();

            main_image.setImageResource(list.get(rand.nextInt(list.size())));
            main_image.animate();
            myCountDownTimer = new MyCountDownTimer(2000, 1000);
            myCountDownTimer.start();
            //finish();
        }
    }


}
