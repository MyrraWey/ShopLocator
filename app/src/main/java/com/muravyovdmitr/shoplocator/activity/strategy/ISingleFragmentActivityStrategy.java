package com.muravyovdmitr.shoplocator.activity.strategy;

import android.support.v4.app.Fragment;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public interface ISingleFragmentActivityStrategy {
    Fragment getFragment();

    int getSelectedByDefaultNavBarItem();
}
