package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.ShopsListStrategy;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseListFragment<ShopsListAdapter> {

    @Override
    public ShopsListAdapter getItemsListAdapter() {
        IDataOperations dataOperations = new ShopsDatabaseWrapper(getContext());

        return new ShopsListAdapter(dataOperations.getItems());
    }

    @Override
    public Fragment getCreateItemFragment() {
        return new CreateShopFragment();
    }

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new ShopsListStrategy();
    }
}
