package com.muravyovdmitr.shoplocator.activity;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.fragment.MapFragment;

/**
 * Created by Dima Muravyov on 06.05.2016.
 */
public class MapActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new MapFragment();
    }

    @Override
    protected int getDefaultNavBarItem() {
        return R.id.drawer_map;
    }
}
