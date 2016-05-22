package com.muravyovdmitr.shoplocator.fragment;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.DataWrapperFactory;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.ShopsListStrategy;

import java.util.List;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseListFragment<ShopsListAdapter> {
    private final IDataOperations mShopsData = DataWrapperFactory.getShopsDataWrapper();
    private final IDataOperations mOwnersData = DataWrapperFactory.getOwnersDataWrapper();

    @Override
    public ShopsListAdapter getItemsListAdapter() {
        final List<Shop> shops = mShopsData.getItems();
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
    protected void createNewItem() {
        if (mOwnersData.getItems().isEmpty()) {
            getNoOwnersDialog().show();
        } else {
            super.createNewItem();
        }
    }

    @Override
    public Fragment getCreateItemFragment() {
        return mOwnersData.getItems().isEmpty() ?
                new CreateOwnerFragment() :
                new CreateShopFragment();
    }

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new ShopsListStrategy();
    }

    //TODO replace to final property
    protected AlertDialog.Builder getNoOwnersDialog() {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.shops_list_dialog_title)
                .setMessage(R.string.shops_list_dialog_message)
                .setPositiveButton(R.string.shops_list_dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShopsListFragment.super.createNewItem();
                    }
                })
                .setNeutralButton(android.R.string.cancel, null);
    }
}
