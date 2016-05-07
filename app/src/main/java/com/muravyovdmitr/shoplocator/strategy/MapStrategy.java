package com.muravyovdmitr.shoplocator.strategy;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.fragment.MapFragment;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public class MapStrategy implements ISingleFragmentStrategy {
    @Override
    public Fragment getFragment() {
        return new MapFragment();
    }

    @Override
    public int getSelectedByDefaultNavBarItem() {
        return R.id.drawer_map;
    }
}
