package com.muravyovdmitr.shoplocator.util;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static Date getDateFromString(String date) {
        return getDateFromString(date, "EEE MMM dd HH:mm:ss zzz yyyy");
    }

    public static Date getDateFromString(String date, String timeFormat) {
        DateFormat format = new SimpleDateFormat(timeFormat);

        Date result;

        try {
            result = format.parse(date);
        } catch (ParseException e) {
            result = null;
        }

        return result;
    }
}
