package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.watcher.ITextValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public abstract class BaseFragment extends Fragment {
    protected ActionBar mToolbar;
    protected Map<TextInputLayout, ITextValidator> mValidationFields;

    protected IBaseFragmentStrategy mLoadingStrategy;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mLoadingStrategy = getLoadingStrategy();

        View view = inflater.inflate(this.mLoadingStrategy.getViewResource(), container, false);

        findView(view);

        setupData();

        prepareValidation();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(this.mLoadingStrategy.getMenuResource(), menu);
    }

    protected void prepareValidation() {
        this.mValidationFields = getValidationMap();

        for (Map.Entry<TextInputLayout, ITextValidator> entry : this.mValidationFields.entrySet()) {
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

    protected abstract IBaseFragmentStrategy getLoadingStrategy();

    protected abstract void findView(View view);

    protected void setupData() {
        setHasOptionsMenu(true);
        this.mToolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        this.mToolbar.setTitle(this.mLoadingStrategy.getToolbarTitleResource());
    }
}
