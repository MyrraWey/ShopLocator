package com.muravyovdmitr.shoplocator.util;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Dima Muravyov on 07.05.2016.
 */
public class TextUtils {
    public static LatLng getLatLngFromFormattedString(String location) {
        String[] coords = location.split(", ");

        double latitude = Double.valueOf(coords[0]);
        double longitude = Double.valueOf(coords[1]);

        return new LatLng(latitude, longitude);
    }

    public static String implode(List<String> list, String glue) {
        String result = "";

        if (list.isEmpty()) {
            return result;
        }

        StringBuilder builder = new StringBuilder();
        for (String item : list) {
            builder.append(item + glue);
        }
        result = builder.substring(0, builder.length() - glue.length());

        return result;
    }
}
