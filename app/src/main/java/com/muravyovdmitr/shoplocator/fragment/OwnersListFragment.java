package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.adapter.OwnersListAdapter;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.OwnersListStrategy;

import java.util.List;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class OwnersListFragment extends BaseListFragment<OwnersListAdapter> {

    @Override
    public OwnersListAdapter getItemsListAdapter() {
        final IDataOperations mOwnersData = new OwnersDatabaseWrapper(getContext());

        final List<Owner> owners = mOwnersData.getItems();
        final OwnersListAdapter adapter = new OwnersListAdapter(owners);
        adapter.setOnItemRemove(new IOnItemRemove() {
            @Override
            public void removeItem(int position) {
                owners.remove(position);
                adapter.notifyItemRemoved(position);

                setListVisibility(owners.size() != 0);
            }
        });

        return adapter;
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
