package com.muravyovdmitr.shoplocator.activity;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.fragment.OwnersListFragment;

/**
 * Created by MyrraWey on 04.05.2016.
 */
public class OwnersListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new OwnersListFragment();
    }

    @Override
    protected int getDefaultNavBarItem() {
        return R.id.drawer_owners;
    }
}