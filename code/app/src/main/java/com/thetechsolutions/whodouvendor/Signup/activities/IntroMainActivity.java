package com.thetechsolutions.whodouvendor.Signup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Signup.adapters.IntroPagerAdapter;

import org.vanguardmatrix.engine.android.AppPreferences;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Uzair on 7/12/2016.
 */
public class IntroMainActivity extends FragmentActivityController implements MethodGenerator {


    public static Activity activity;
    ViewPager pager;
    IntroPagerAdapter adapter;
    TextView get_started_text;
    CircleIndicator indicator;


    public static Intent createIntent(Activity _activity) {
        activity = _activity;
        return new Intent(activity, IntroMainActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        activity = this;
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        try {
            get_started_text = (TextView) findViewById(R.id.let_get_started);
            get_started_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                    AppPreferences.setBoolean(AppPreferences.PREF_IS_INTRO_COMPLETED, true);
                    ListenerController.openSignupWithNumberActivity(activity);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewUpdate() {

        adapter = new IntroPagerAdapter(
                getSupportFragmentManager(), activity);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

    }

}
