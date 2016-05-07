package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.watcher.ITextValidator;
import com.muravyovdmitr.shoplocator.watcher.SingleTextWatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public abstract class BaseFragment extends Fragment {
    protected Map<TextInputLayout, ITextValidator> mValidationFields;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getViewResource(), container, false);
        setHasOptionsMenu(true);

        findView(view);

        setupData();

        prepareValidation();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(getMenuResource(), menu);
    }

    protected void prepareValidation() {
        this.mValidationFields = getValidationMap();

        for(Map.Entry<TextInputLayout, ITextValidator> entry : this.mValidationFields.entrySet()) {
            entry.getKey().getEditText().addTextChangedListener((TextWatcher) entry.getValue());
        }
    }

    protected Map<TextInputLayout, ITextValidator> getValidationMap() {
        return new HashMap<>();
    }

    protected boolean validateDataFields() {
        boolean correct = true;

        for (Map.Entry<TextInputLayout, ITextValidator> entry : this.mValidationFields.entrySet()) {
            String editText = entry.getKey().getEditText().getText().toString();
            if (!entry.getValue().isValid(editText)) {
                correct = false;

                break;
            }
        }

        return correct;
    }

    protected void clearLayoutErrors() {
        for (Map.Entry<TextInputLayout, ITextValidator> entry : this.mValidationFields.entrySet()) {
            entry.getKey().setErrorEnabled(false);
        }
    }

    protected abstract int getViewResource();

    protected abstract void findView(View view);

    protected abstract void setupData();

    protected abstract int getMenuResource();
}
