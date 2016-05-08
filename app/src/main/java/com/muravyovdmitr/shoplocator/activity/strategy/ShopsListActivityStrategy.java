package com.muravyovdmitr.shoplocator.activity.strategy;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.fragment.ShopsListFragment;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public class ShopsListActivityStrategy implements ISingleFragmentActivityStrategy {
    @Override
    public Fragment getFragment() {
        return new ShopsListFragment();
    }

    @Override
    public int getSelectedByDefaultNavBarItem() {
        return R.id.drawer_shops;
    }
}
