package com.muravyovdmitr.shoplocator.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.ShopsListStrategy;

import java.util.List;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseListFragment<ShopsListAdapter> {
    private IDataOperations mDataOperations;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataOperations = new OwnersDatabaseWrapper(getContext());
    }

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
    protected void createNewItem() {
        if (mDataOperations.getItems().isEmpty()) {
            getNoOwnersDialog().show();
        } else {
            super.createNewItem();
        }
    }

    @Override
    public Fragment getCreateItemFragment() {
        return this.mDataOperations.getItems().isEmpty() ?
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
