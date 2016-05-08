package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.OwnersListAdapter;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.decoration.ItemsListItemDecorator;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class OwnersListFragment extends BaseListFragment<OwnersListAdapter> {

    @Override
    public OwnersListAdapter getItemsListAdapter() {
        return new OwnersListAdapter(ShopFactory.getInstance(getContext()).getOwners());
    }

    @Override
    public Fragment getCreateItemFragment() {
        return new CreateOwnerFragment();
    }
}
