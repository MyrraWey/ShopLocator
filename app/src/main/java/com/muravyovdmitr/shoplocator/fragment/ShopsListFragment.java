package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.ShopsListStrategy;

import java.util.List;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseListFragment<ShopsListAdapter> {

    @Override
    public ShopsListAdapter getItemsListAdapter() {
        IDataOperations dataOperations = new ShopsDatabaseWrapper(getContext());

        final List<Shop> shops = dataOperations.getItems();
        final ShopsListAdapter adapter = new ShopsListAdapter(shops);
        adapter.setOnItemRemove(new IOnItemRemove() {
            @Override
            public void removeItem(int position) {
                shops.remove(position);
                adapter.notifyItemRemoved(position);

                setListVisibility(shops.size() != 0);
            }
        });

        return adapter;
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
