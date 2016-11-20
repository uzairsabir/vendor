package com.thetechsolutions.whodouvendor.Signup.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.Signup.controllers.SignupController;

/**
 * Created by Uzair on 7/12/2016.
 */
public class IntroFragment extends Fragment {

    static IntroFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";


    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new IntroFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, SignupController.getIntroLayout(sectionNumber));
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(getArguments().getInt(ARG_SECTION), container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }
}
