package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.ShopFactory;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseListFragment<ShopsListAdapter> {

    @Override
    public ShopsListAdapter getItemsListAdapter() {
        return new ShopsListAdapter(ShopFactory.getInstance(getContext()).getShops());
    }

    @Override
    public Fragment getCreateItemFragment() {
        return new CreateShopFragment();
    }
}
