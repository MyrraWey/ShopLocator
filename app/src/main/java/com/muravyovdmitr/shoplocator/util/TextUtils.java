package com.muravyovdmitr.shoplocator.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dima Muravyov on 07.05.2016.
 */
public class TextUtils {
    public static LatLng getLatLngFromFormattedString(String location) {
        String[] coords = location.split(", ");

        double latitude = Double.valueOf(coords[0]);
        double longitude= Double.valueOf(coords[1]);

       return new LatLng(latitude, longitude);
    }
}
