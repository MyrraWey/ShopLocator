package com.muravyovdmitr.shoplocator.watcher;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public class SingleTextWatcher implements TextWatcher {
    private TextInputLayout mLayout;
    private String mErrorText;

    public SingleTextWatcher(TextInputLayout layout, String errorText) {
        this.mLayout = layout;
        this.mErrorText = errorText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isValid(s.toString())) {
            mLayout.setErrorEnabled(false);
        } else {
            //TODO error shows only first time, fix
            this.mLayout.setErrorEnabled(true);
            if (this.mLayout.getError() == null) {
                this.mLayout.setError(mErrorText);
            }
        }
    }

    //TODO remove to separate class or find ready 'Validation Rules'
    public static boolean isValid(String text) {
        boolean valid = true;

        if (text.trim().length() == 0) {
            valid = false;
        }

        return valid;
    }
}
