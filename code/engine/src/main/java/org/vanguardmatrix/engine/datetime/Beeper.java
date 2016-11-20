package org.vanguardmatrix.engine.datetime;

import android.app.Activity;

import org.vanguardmatrix.engine.interfaces.CallBackBeeper;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Beeper {

    protected int interval;
    protected CallBackBeeper callBackFunction;

    public Beeper(int interval, CallBackBeeper callbackFunction) {
        this.interval = interval;
        this.callBackFunction = callbackFunction;
    }

    public static void processTimer(final Timer timer2, boolean start_or_stop,
                                    final Runnable updateTask2, final Activity activity2,
                                    final int durationMSEC) {

        try {

            Timer timer = timer2;

            if (start_or_stop) {

                Calendar calendar = Calendar.getInstance();

                // update the UI
                int msec = (durationMSEC - 1)
                        - calendar.get(Calendar.MILLISECOND);
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        activity2.runOnUiThread(updateTask2);
                    }
                }, msec, durationMSEC);
            } else {
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void processTimer2(final Timer timer2, boolean start_or_stop,
                                     final Runnable updateTask2, final Activity activity2,
                                     final int durationMSEC) {

        try {

            Timer timer = timer2;

            if (start_or_stop) {

                Calendar calendar = Calendar.getInstance();

                // update the UI
                int msec = (durationMSEC - 1)
                        - calendar.get(Calendar.MILLISECOND);
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        activity2.runOnUiThread(updateTask2);
                    }
                }, msec, durationMSEC);
            } else {
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopTimer(final Runnable updateTask2,
                                 final Activity activity2, final int durationMSEC) {
        Timer timer = new Timer("DigitalClock");
        Calendar calendar = Calendar.getInstance();

        // // Get the Current Time
        // final Runnable updateTask = new Runnable() {
        // public void run() {
        // time.setText(getCurrentTimeString());
        // // shows the time of the day
        // // countdown.setText(getReminingTime());
        // // shows the remaining time of the day
        // // if (CommonObjects.stop_sos) {
        // // stopSOS();
        // // }
        // }
        // };

        // update the UI
        int msec = (durationMSEC - 1) - calendar.get(Calendar.MILLISECOND);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity2.runOnUiThread(updateTask2);
            }
        }, msec, durationMSEC);
    }

    public void startBeeper() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Beeper.this.callBackFunction.callBack();

            }
        }, 0, Beeper.this.interval);// Update text every second
    }

}
