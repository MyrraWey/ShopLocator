package com.muravyovdmitr.shoplocator.watcher;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Dima Muravyov on 07.05.2016.
 */
public abstract class ValidateWatcher implements TextWatcher, ITextValidator {
    protected TextInputLayout mLayout;
    protected String mErrorText;

    public ValidateWatcher(TextInputLayout layout, String errorText) {
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
            mLayout.setError(null);
            mLayout.setErrorEnabled(false);
        } else {
            this.mLayout.setError(mErrorText);
            this.mLayout.setErrorEnabled(true);
        }
    }
}
