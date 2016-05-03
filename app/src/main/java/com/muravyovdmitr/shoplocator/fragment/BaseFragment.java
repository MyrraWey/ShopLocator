package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MyrraWey on 03.05.2016.
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResource(), container, false);

        findView(view);

        setupData();

        return view;
    }

    protected abstract int getResource();

    protected abstract void findView(View view);

    protected abstract void setupData();
}
