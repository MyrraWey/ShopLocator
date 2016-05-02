package com.muravyovdmitr.shoplocator.activity;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.fragment.ShopsListFragment;

public class ShopsListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new ShopsListFragment();
    }
}
