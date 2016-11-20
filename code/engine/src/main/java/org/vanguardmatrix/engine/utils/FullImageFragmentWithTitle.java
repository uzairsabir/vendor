package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.imageloader.ImageLoaderManager;

public class FullImageFragmentWithTitle extends Fragment {

    public static final String ARG_SECTION_IMAGE_URI = "ARG_SECTION_IMAGE_URI";
    public static final String ARG_SECTION_TITLE = "ARG_SECTION_TITLE";
    RelativeLayout rl;
    TextView pagetitle;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static FullImageFragmentWithTitle newInstance(String uri, String title) {
        FullImageFragmentWithTitle fragment = new FullImageFragmentWithTitle();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_IMAGE_URI, uri);
        args.putString(ARG_SECTION_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.full_image_fragment,
                container, false);

        rl = (RelativeLayout) rootView.findViewById(R.id.imageResize);
        pagetitle = (TextView) rootView.findViewById(R.id.imageTitle);
        pagetitle.setVisibility(View.VISIBLE);

        TouchImageView touch = new TouchImageView(getActivity());
        touch.setMaxZoom(4f); // change the max level of zoom, default is 3f
        touch.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        String url = String.valueOf(getArguments().getString(
                ARG_SECTION_IMAGE_URI));
        String title = String.valueOf(getArguments().getString(
                ARG_SECTION_TITLE));
        try {
            pagetitle.setText(title);
        } catch (Exception e) {

        }

        try {
            ImageLoaderManager.getImageLoaderObj(getActivity())
                    .DisplayImage_old(url, touch);
        } catch (Exception e) {

        }


        rl.addView(touch);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
