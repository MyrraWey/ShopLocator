package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.decoration.ItemsListItemDecorator;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public abstract class BaseListFragment<LA extends RecyclerView.Adapter> extends BaseFragment implements IBaseListFragment<LA> {
    protected LinearLayout mEmptyBlock;
    protected Button mCreateItemButton;
    protected RecyclerView mRecyclerView;
    protected LA mItemsListAdapter;

    protected View.OnClickListener mCreateItemButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.base_list_empty_create_item:
                    createNewItem();
                    break;
            }
        }
    };

    @Override
    protected void findView(View view) {
        this.mEmptyBlock = (LinearLayout) view.findViewById(R.id.base_list_empty_block);
        this.mCreateItemButton = (Button) view.findViewById(R.id.base_list_empty_create_item);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.base_list_recycler_view);
    }

    @Override
    protected void setupData() {
        super.setupData();

        this.mCreateItemButton.setOnClickListener(mCreateItemButtonClick);

        this.mItemsListAdapter = getItemsListAdapter();
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new ItemsListItemDecorator());
        this.mRecyclerView.setAdapter(this.mItemsListAdapter);

        setListVisibility(this.mItemsListAdapter.getItemCount() != 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_base_list_create_item:
                createNewItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setListVisibility(boolean isVisible) {
        this.mEmptyBlock.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        this.mRecyclerView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    protected void createNewItem() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.single_fragment_fragment_container, getCreateItemFragment())
                .addToBackStack(null)
                .commit();
    }
}
