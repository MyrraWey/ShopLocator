package com.muravyovdmitr.shoplocator.watcher;

import android.support.design.widget.TextInputLayout;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public class SingleTextWatcher extends ValidateWatcher {

    public SingleTextWatcher(TextInputLayout layout, String errorText) {
        super(layout, errorText);
    }

    public boolean isValid(String text) {
        boolean valid = true;

        if (text.trim().isEmpty()) {
            valid = false;
        }

        return valid;
    }
}
