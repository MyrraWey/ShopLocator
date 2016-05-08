package com.muravyovdmitr.shoplocator.watcher;

import android.support.design.widget.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public class LocationWatcher extends ValidateWatcher {
    public LocationWatcher(TextInputLayout layout, String errorText) {
        super(layout, errorText);
    }

    public boolean isValid(String text) {
        boolean valid = true;

        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?,\\s\\d+(\\.\\d+)?$");
        Matcher match = pattern.matcher(text);

        if (!match.matches()) {
            valid = false;
        }

        return valid;
    }
}
