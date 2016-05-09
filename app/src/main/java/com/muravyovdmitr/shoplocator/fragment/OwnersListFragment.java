package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.adapter.OwnersListAdapter;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.OwnersListStrategy;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class OwnersListFragment extends BaseListFragment<OwnersListAdapter> {

    @Override
    public OwnersListAdapter getItemsListAdapter() {
        IDataOperations dataOperations = new OwnersDatabaseWrapper(getContext());

        return new OwnersListAdapter(dataOperations.getItems());
    }

    @Override
    public Fragment getCreateItemFragment() {
        return new CreateOwnerFragment();
    }

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new OwnersListStrategy();
    }
}
