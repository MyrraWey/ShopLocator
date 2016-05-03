package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.decoration.ShopsListItemDecorator;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseFragment {
    private LinearLayout mEmptyBlock;
    private Button mCreateShopButton;
    private RecyclerView mRecyclerView;
    private ShopsListAdapter mShopsListAdapter;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.shops_list_empty_create_shop:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.single_fragment_fragment_container, new CreateShopFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        }
    };

    @Override
    protected int getResource() {
        return R.layout.fragment_shops_list;
    }

    @Override
    protected void findView(View view) {
        this.mEmptyBlock = (LinearLayout) view.findViewById(R.id.shops_list_empty_block);
        this.mCreateShopButton = (Button) view.findViewById(R.id.shops_list_empty_create_shop);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.shops_list_recycler_view);
    }

    @Override
    protected void setupData() {
        this.mCreateShopButton.setOnClickListener(mClickListener);

        this.mShopsListAdapter = new ShopsListAdapter(ShopFactory.getInstance(getContext()).getShops());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new ShopsListItemDecorator());
        this.mRecyclerView.setAdapter(this.mShopsListAdapter);

        setListVisibility(this.mShopsListAdapter.getItemCount() != 0);
    }

    private void setListVisibility(boolean isVisible) {
        this.mEmptyBlock.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        this.mRecyclerView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
