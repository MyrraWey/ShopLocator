package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.deckorator.ShopsListItemDecorator;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ShopsListAdapter mShopsListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops_list, container, false);

        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.shops_list_recycler_view);

        this.mShopsListAdapter = new ShopsListAdapter(ShopFactory.getInstance().getShops());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new ShopsListItemDecorator());
        this.mRecyclerView.setAdapter(this.mShopsListAdapter);

        return view;
    }
}
