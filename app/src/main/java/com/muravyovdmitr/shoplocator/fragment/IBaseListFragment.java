package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public interface IBaseListFragment<LA> {
    LA getItemsListAdapter();

    Fragment getCreateItemFragment();
}
