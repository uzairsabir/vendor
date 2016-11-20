package org.vanguardmatrix.engine.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Abdul Wahab on 9/28/2015.
 */
public class LocationsData {

    public static ArrayList<LatLng> getKarachiPoligon() {

        ArrayList<LatLng> list = new ArrayList<>();
        list.add(new LatLng(25.070108, 61.712989));
        list.add(new LatLng(27.455848, 62.895500));
        list.add(new LatLng(29.758998, 61.084781));
        list.add(new LatLng(29.758998, 66.110451));
        list.add(new LatLng(31.140634, 66.761513));
        list.add(new LatLng(31.806423, 68.165744));
        list.add(new LatLng(31.748555, 68.643316));
        list.add(new LatLng(33.026141, 69.610127));
        list.add(new LatLng(34.017645, 71.132287));
        list.add(new LatLng(36.089591, 71.317054));
        list.add(new LatLng(36.788206, 72.687502));
        list.add(new LatLng(36.906492, 74.350408));
        list.add(new LatLng(35.384710, 77.306684));
        list.add(new LatLng(35.475042, 79.745612));
        list.add(new LatLng(33.968820, 78.639003));
        list.add(new LatLng(32.720693, 79.078456));
        list.add(new LatLng(33.273542, 76.353847));
        list.add(new LatLng(32.201589, 75.189296));
        list.add(new LatLng(31.096201, 74.473011));
        list.add(new LatLng(30.014278, 73.327453));
        list.add(new LatLng(28.010833, 71.812362));
        list.add(new LatLng(28.043452, 70.629851));
        list.add(new LatLng(26.895932, 69.447340));
        list.add(new LatLng(24.495670, 70.903617));
        list.add(new LatLng(24.382511, 68.852731));
        list.add(new LatLng(23.776804, 68.127119));
        list.add(new LatLng(23.979547, 67.314142));
        list.add(new LatLng(25.285660, 66.485911));
        list.add(new LatLng(24.966284, 61.769439));

        return list;
    }

    public static boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    public static boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }

}
