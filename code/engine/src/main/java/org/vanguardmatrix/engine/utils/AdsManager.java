package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * Created by Abdul Wahab on 7/7/2015.
 */
public class AdsManager {

    public static AdView get_Medium_Rectangular_Ad(Activity activity) {

        AdView adView = new AdView(activity.getApplicationContext());
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        //adView.setAdUnitId(activity.getResources().getString(R.string.ad_medium_rect_unit_id));

        // Disable focus for sub-views of the AdView to avoid problems with
        // trackpad navigation of the list.
        for (int i = 0; i < adView.getChildCount(); i++) {
            adView.getChildAt(i).setFocusable(false);
        }
        adView.setFocusable(true);
        // Convert the default layout parameters so that they play nice with
        // ListView.

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        int height = Math.round(AdSize.MEDIUM_RECTANGLE.getHeight() * density);
        int width = Math.round(AdSize.MEDIUM_RECTANGLE.getWidth() * density);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        adView.setLayoutParams(params);

        AdRequest bannerIntermediateReq = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .addTestDevice("08C5AC03F31B09453779FFB52ACDD3F3") //means that a test ad is shown on my phone
                .build();

        adView.loadAd(bannerIntermediateReq);

        return adView;
    }

    public static AdView get_small_banner_Ad(Activity activity) {

        AdView adView = new AdView(activity.getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId(activity.getResources().getString(R.string.ad_bottom_banner_unit_id));

        for (int i = 0; i < adView.getChildCount(); i++) {
            adView.getChildAt(i).setFocusable(false);
        }
        adView.setFocusable(true);

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        int height = Math.round(AdSize.BANNER.getHeight() * density);
        int width = Math.round(AdSize.BANNER.getWidth() * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        adView.setLayoutParams(params);

        AdRequest bannerIntermediateReq = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("08C5AC03F31B09453779FFB52ACDD3F3") //means that a test ad is shown on my phone
                .addTestDevice("C4A8A1818B2E42A929CEF580EDF897B0") //means that a test ad is shown on my phone
                .build();

        adView.loadAd(bannerIntermediateReq);

        return adView;
    }

    public static AdView get_large_banner_Ad(Activity activity) {

        AdView adView = new AdView(activity.getApplicationContext());
        adView.setAdSize(AdSize.LARGE_BANNER);
        // adView.setAdUnitId(activity.getResources().getString(R.string.ad_bottom_banner_unit_id));

        for (int i = 0; i < adView.getChildCount(); i++) {
            adView.getChildAt(i).setFocusable(false);
        }
        adView.setFocusable(true);

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        int height = Math.round(AdSize.LARGE_BANNER.getHeight() * density);
        int width = Math.round(AdSize.LARGE_BANNER.getWidth() * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        adView.setLayoutParams(params);

        AdRequest bannerIntermediateReq = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("08C5AC03F31B09453779FFB52ACDD3F3") //means that a test ad is shown on my phone
                .addTestDevice("C4A8A1818B2E42A929CEF580EDF897B0") //means that a test ad is shown on my phone
                .build();

        adView.loadAd(bannerIntermediateReq);

        return adView;
    }

//    public static ArrayList<FeedItem> pushAdsInTimeline(ArrayList<FeedItem> temp_feeds) {
//        ArrayList<FeedItem> lists_with_ads = new ArrayList<FeedItem>();
//        for (int i = 1; i < temp_feeds.size(); i++) {
//            if (i % 8 == 0) {
//                FeedItem x = temp_feeds.get(0);
//                x.set_target_type(FeedType.ADS.toString());
//                x.set_type(FeedType.ADS.toString());
//                lists_with_ads.add(x);
//            } else {
//                lists_with_ads.add(temp_feeds.get(i));
//            }
//        }
//        return lists_with_ads;
//    }
//
//    public static ArrayList<Directory> pushAdsInDirectories(ArrayList<Directory> temp_feeds) {
//        ArrayList<Directory> lists_with_ads = new ArrayList<Directory>();
//        for (int i = 1; i < temp_feeds.size(); i++) {
//            if (i % 8 == 0) {
//                Directory x = temp_feeds.get(0);
//                x.set_maincategory(FeedType.ADS.toString());
//                lists_with_ads.add(x);
//            } else {
//                lists_with_ads.add(temp_feeds.get(i));
//            }
//        }
//        return lists_with_ads;
//    }
}
