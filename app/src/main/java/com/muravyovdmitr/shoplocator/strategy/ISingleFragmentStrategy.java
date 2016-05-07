package com.muravyovdmitr.shoplocator.strategy;

import android.support.v4.app.Fragment;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public interface ISingleFragmentStrategy {
    Fragment getFragment();
    int getSelectedByDefaultNavBarItem();
}
