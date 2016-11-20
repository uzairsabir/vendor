package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.ui.helper.ManagedFragmentActivity;
import org.vanguardmatrix.engine.imageloader.ImageLoaderManager;

public class FullImageView extends ManagedFragmentActivity {

    static String pageTitle = "";
    static String imageUri = "";
    static String[] imageUris = null;
    static int position = 0;
    protected ViewPager mPager;
    RelativeLayout rl;
    Activity activity;
    PagerAdapter mPagerAdapter;
    TextView imagecount;

    public static Intent createIntent(Context context, String title, String imageURI, String[] imageURIs, int _position) {
        pageTitle = title;
        imageUri = imageURI;
        imageUris = imageURIs;
        position = _position;
        return new Intent(context, FullImageView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_resize_view);

        rl = (RelativeLayout) findViewById(R.id.imageResize);
        mPager = (ViewPager) findViewById(R.id.screen_pager);
        imagecount = (TextView) findViewById(R.id.imagecount);


//        imageUri = getIntent().getExtras().getString("ImageUri");

        try {
            ((TextView) findViewById(R.id.imageTitle)).setText(pageTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TouchImageView touch = new TouchImageView(this);
        touch.setMaxZoom(4f); // change the max level of zoom, default is 3f
        touch.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        /*
         * //////////// Load Image byteData to touchImageView; ////////////////
		 * File imgFile = new File(imageUri); /////////////////////////////////
		 * if (imgFile.exists()) { ///////////////////////////////////////////
		 * Bitmap myBitmap = APP_Config.decodeFile(imgFile, 2, activity);
		 * touch.setImageBitmap(myBitmap);}
		 */
        try {
            ImageLoaderManager.getImageLoaderObj(this)
                    .DisplayImage_old(imageUri, touch);
        } catch (Exception e) {

        }


//        ImageLoadingHandler.Display(imageUri, touch, true, false, true);

        rl.addView(touch);

        if (imageUris != null && imageUris.length > 0) {
            rl.setVisibility(View.GONE);
            mPager.setVisibility(View.VISIBLE);
            mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), imageUris);
            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);

            imagecount.setText(position + 1 + " of " + imageUris.length);


            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    imagecount.setText(position + 1 + " of " + imageUris.length);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        } else {
            rl.setVisibility(View.VISIBLE);
            mPager.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            System.gc();
            UtilityFunctions.unbindDrawables(findViewById(R.id.root_view));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        String[] images = null;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm, String[] imagesURIs) {
            super(fm);
            images = imagesURIs;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            return FullImageFragment.newInstance(images[pos]);
        }

        @Override
        public int getCount() {
            return images.length;
        }
    }

}
