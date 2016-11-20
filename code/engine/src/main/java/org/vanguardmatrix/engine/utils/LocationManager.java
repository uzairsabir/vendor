package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import org.vanguardmatrix.engine.android.data.ActivityManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationManager {

    // public static final int MIN_CHANGED_DIST = 250;
    public static final int MIN_CHANGED_DIST = 325;
    // Bur Dubai 25.2~55.3
    // Karachi 24.893379~67.02806
    // public static double default_latitude = 24.893379;
    // public static double default_longitude = 67.02806;
    public static double default_latitude = 0.0; // latitude
    public static double default_longitude = 0.0; // longitude
    public static MyLocationAddress myAddress;
    static GPSTracker gps = null;
    private static Location location = null;

//    public static GPSTracker getLocationManager(Context context) {
//        try {
//            if (LocationManager.gps == null) {
////            ;
//                LocationManager.gps = new GPSTracker(context);
//            }
//            return LocationManager.gps;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return LocationManager.gps;
//        }
//    }

    public static void updateCustomLocation(double lati, double longi) {
        location.setLatitude(lati);
        location.setLongitude(longi);
    }

    private static void setDefaultLocation() {
        location = new Location("");
        location.setLatitude(default_latitude);
        location.setLongitude(default_longitude);
    }

    public static void setLocation(Location _location) {

        Log.e("LocationManager", "=> " + "setLocationManager");
//		try {
//			UtilityFunctions.showToast_normal("setLocationisCalled",
//					HomeScreenOld.activity);
//		} catch (Exception e) {
//
//		}

//        if (AppPreferences.getCurrentBestLocation(HomeScreenOld.activity) != null)
//            UpdateLocation.update(
//                    AppPreferences.getCurrentBestLocation(HomeScreenOld.activity),
//                    _location);
//        else
//            UpdateLocation.update(LocationManager.location, _location);

        LocationManager.location = _location;

    }

    public static void setLocation(double lat, double lng) {

        Location temp = new Location(""); // = null;
        temp.setLatitude(lat);
        temp.setLongitude(lng);
        setLocation(temp);
    }

    public static void updateLocation(Context context) {
        //setLocation(getLocationManager(context).getLocation());
    }

    public static List<Address> getCityCountryByLocation(Location location,
                                                         Context context) {

        Geocoder deviceLocation = null;
        List<Address> myList = null;


//        location = ActivityManager.getInstance().getLastKnownLocation();

        Log.e("LocationManager",
                "CurrentCountry Request with lat:"
                        + location.getLatitude() + " lon:"
                        + location.getLongitude());


        try {
            deviceLocation = new Geocoder(context, Locale.getDefault());
            myList = deviceLocation.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (myList != null && myList.size() > 0) {
            for (Address add : myList) {
                MyLogs.printinfo(" ADDRESS from Location Manager ll" + "add @ curr Index"
                        + " countryName():" + add.getCountryName()
                        + " Localitity:" + add.getLocality()
                        + " subLocalitity:" + add.getSubLocality()
                        + " FeatureName():" + add.getFeatureName());
            }
        }

        return myList;
    }

    public static MyLocationAddress getCurrentAddress(Context context) {

        Geocoder deviceLocation = null;
        List<Address> myList = null;

        // String address = "", city = "", country = "", lati = "", longi = "",
        // locality = "", subLocality = "";
        //
        // String[] addressCityCountry = { address, city, country, lati, longi,
        // locality, subLocality };

        location = ActivityManager.getInstance().getLastKnownLocation();

        try {

            Log.e("LocationManager", "getCurrentAddress Request with lat:"
                    + location.getLatitude() + " lon:"
                    + location.getLongitude());

            deviceLocation = new Geocoder(context, Locale.getDefault());
            if (location != null)
                myList = deviceLocation.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (myList != null && myList.size() > 0) {
                for (Address add : myList) {

                    MyLogs.printinfo(" ADDRESS from Location Manager cl " +
                            "add @ curr Index" + " countryName():"
                            + add.getCountryName() + " Localitity:"
                            + add.getLocality() + " subLocalitity:"
                            + add.getSubLocality() + " FeatureName():"
                            + add.getFeatureName() + " getAdminArea():"
                            + add.getAdminArea()
                            + " getThoroughfare():"
                            + add.getThoroughfare()
                            + " getMaxAddressLineIndex():"
                            + add.getMaxAddressLineIndex()
                            + " getPremises():" + add.getPremises()
                            + " getPostalCode():" + add.getPostalCode());
                }

                myAddress = new MyLocationAddress(myList.get(0));

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return myAddress;
    }

    public static MyLocationAddress getLocationAddressFromName(
            Activity context, String name) {
        MyLocationAddress result = null;
        Geocoder deviceLocation = null;
        List<Address> myList = null;

        try {
            deviceLocation = new Geocoder(context, Locale.getDefault());
            myList = deviceLocation.getFromLocationName(name, 10);

            if (myList != null && myList.size() > 0) {
                for (Address add : myList) {

                    // Log.e(" ADDRESS from Location Manager",
                    // "for location : " + name + " add @ curr Index"
                    // + " countryName():" + add.getCountryName()
                    // + " Localitity:" + add.getLocality()
                    // + " subLocalitity:" + add.getSubLocality()
                    // + " FeatureName():" + add.getFeatureName()
                    // + " getAdminArea():" + add.getAdminArea()
                    // + " getThoroughfare():"
                    // + add.getThoroughfare()
                    // + " getMaxAddressLineIndex():"
                    // + add.getMaxAddressLineIndex()
                    // + " getPremises():" + add.getPremises()
                    // + " getPostalCode():" + add.getPostalCode());
                }

                result = new MyLocationAddress(myList.get(0));

            }

        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e("aw", "exception " + e1.getMessage());
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e("aw", "exception " + e2.getMessage());
        }

        return result;
    }

    public static ArrayList<MyLocationAddress> getPossibleLocationAddressFromName(
            Activity context, String name) {
        ArrayList<MyLocationAddress> resultList = new ArrayList<>();
        MyLocationAddress result = null;
        Geocoder deviceLocation = null;
        List<Address> myList = null;

        try {
            deviceLocation = new Geocoder(context, Locale.getDefault());
            myList = deviceLocation.getFromLocationName(name, 10);

            if (myList != null && myList.size() > 0) {
                for (Address add : myList) {

                    // Log.e(" ADDRESS from Location Manager",
                    // "for location : " + name + " add @ curr Index"
                    // + " countryName():" + add.getCountryName()
                    // + " Localitity:" + add.getLocality()
                    // + " subLocalitity:" + add.getSubLocality()
                    // + " FeatureName():" + add.getFeatureName()
                    // + " getAdminArea():" + add.getAdminArea()
                    // + " getThoroughfare():"
                    // + add.getThoroughfare()
                    // + " getMaxAddressLineIndex():"
                    // + add.getMaxAddressLineIndex()
                    // + " getPremises():" + add.getPremises()
                    // + " getPostalCode():" + add.getPostalCode());
                    result = new MyLocationAddress(add);
                    resultList.add(result);
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return resultList;
    }

    public static MyLocationAddress getLocationAddress(Activity context,
                                                       double lati, double longi) {

        MyLocationAddress result = null;
        Geocoder deviceLocation = null;
        List<Address> myList = null;

        try {
            deviceLocation = new Geocoder(context, Locale.getDefault());
            myList = deviceLocation.getFromLocation(lati, longi, 1);

            if (myList != null && myList.size() > 0) {
                for (Address add : myList) {

                    MyLogs.printinfo(" ADDRESS from Location Manager" +
                            "for location : " + lati + "^" + longi
                            + " add @ curr Index" + " countryName():"
                            + add.getCountryName() + " Localitity:"
                            + add.getLocality() + " subLocalitity:"
                            + add.getSubLocality() + " FeatureName():"
                            + add.getFeatureName() + " getAdminArea():"
                            + add.getAdminArea()
                            + " getThoroughfare():"
                            + add.getThoroughfare()
                            + " getMaxAddressLineIndex():"
                            + add.getMaxAddressLineIndex()
                            + " getPremises():" + add.getPremises()
                            + " getPostalCode():" + add.getPostalCode());
                }

                result = new MyLocationAddress(myList.get(0));

            }

        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e("aw", "exception " + e1.getMessage());
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e("aw", "exception " + e2.getMessage());
        }

        return result;
    }

    public static ArrayList<MyLocationAddress> getPossibleLocationAddress(Activity context,
                                                                          double lati, double longi) {

        ArrayList<MyLocationAddress> resultList = new ArrayList<>();

        Geocoder deviceLocation = null;
        List<Address> myList = null;

        try {
            deviceLocation = new Geocoder(context, Locale.getDefault());
            myList = deviceLocation.getFromLocation(lati, longi, 10);

            if (myList != null && myList.size() > 0) {
                for (Address add : myList) {
                    MyLocationAddress result = null;
                    MyLogs.printinfo(" ADDRESS from Location Manager" +
                            "for location : " + lati + "^" + longi
                            + " add @ curr Index" + " countryName():"
                            + add.getCountryName() + " Localitity:"
                            + add.getLocality() + " subLocalitity:"
                            + add.getSubLocality() + " FeatureName():"
                            + add.getFeatureName() + " getAdminArea():"
                            + add.getAdminArea()
                            + " getThoroughfare():"
                            + add.getThoroughfare()
                            + " getMaxAddressLineIndex():"
                            + add.getMaxAddressLineIndex()
                            + " getPremises():" + add.getPremises()
                            + " getPostalCode():" + add.getPostalCode());

                    result = new MyLocationAddress(add);

                    resultList.add(result);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return resultList;
    }

    public static ArrayList<MyLocationAddress> getPossibleLocationAddress(Activity context,
                                                                          double lati, double longi, int maxResult) {

        ArrayList<MyLocationAddress> resultList = new ArrayList<>();

        Geocoder deviceLocation = null;
        List<Address> myList = null;

        try {
            deviceLocation = new Geocoder(context, Locale.getDefault());
            myList = deviceLocation.getFromLocation(lati, longi, maxResult);

            if (myList != null && myList.size() > 0) {
                for (Address add : myList) {
                    MyLocationAddress result = null;
                    MyLogs.printinfo(" ADDRESS from Location Manager" +
                            "for location : " + lati + "^" + longi
                            + " add @ curr Index" + " countryName():"
                            + add.getCountryName() + " Localitity:"
                            + add.getLocality() + " subLocalitity:"
                            + add.getSubLocality() + " FeatureName():"
                            + add.getFeatureName() + " getAdminArea():"
                            + add.getAdminArea()
                            + " getThoroughfare():"
                            + add.getThoroughfare()
                            + " getMaxAddressLineIndex():"
                            + add.getMaxAddressLineIndex()
                            + " getPremises():" + add.getPremises()
                            + " getPostalCode():" + add.getPostalCode());

                    result = new MyLocationAddress(add);

                    resultList.add(result);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return resultList;
    }

    public static String getDistance(double _lati, double _longi) {

        try {

            float[] addressCityCountry = new float[2];

            Location.distanceBetween(_lati, _longi, location.getLatitude(),
                    location.getLongitude(), addressCityCountry);

            return "" + addressCityCountry[0];

        } catch (Exception e) {
            e.printStackTrace();

            return "" + 0;

        }
    }

    public static String getDistance(String _lati, String _longi) {

        try {

            float[] addressCityCountry = new float[2];

            Location.distanceBetween(Float.parseFloat(_lati),
                    Float.parseFloat(_longi), location.getLatitude(),
                    location.getLongitude(), addressCityCountry);

            return "" + addressCityCountry[0];

        } catch (Exception e) {
            e.printStackTrace();

            return "" + 0;

        }
    }

//    public Location getLocation(Context context) {
//
//        try {
//
//            LocationManager.location = AppPreferences
//                    .getCurrentBestLocation();
//
//            if (LocationManager.location == null)
//                setDefaultLocation();
//
//            LocationManager.location = ActivityManager.getInstance().getLastKnownLocation();
////            LocationManager.location = getLocationManager(context).getLocation();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return LocationManager.location;
//    }

}
