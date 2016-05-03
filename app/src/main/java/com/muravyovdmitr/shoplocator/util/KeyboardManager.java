package com.muravyovdmitr.shoplocator.util;

import android.app.Activity;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public class KeyboardManager {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            return;
        }

        IBinder binder = view.getWindowToken();

        if (binder != null) {
            inputMethodManager.hideSoftInputFromWindow(binder, 0);
        }
    }
}
