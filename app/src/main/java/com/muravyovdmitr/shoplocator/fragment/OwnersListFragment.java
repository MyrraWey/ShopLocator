package com.muravyovdmitr.shoplocator.fragment;

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
public class OwnersListFragment extends BaseFragment {
    private LinearLayout mEmptyBlock;
    private Button mCreateOwnerButton;
    private RecyclerView mRecyclerView;
    private OwnersListAdapter mOwnersListAdapter;

    private View.OnClickListener mCreateOwnerButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.owners_list_empty_create_owner:
                    createNewOwner();
                    break;
            }
        }
    };

    @Override
    protected int getViewResource() {
        return R.layout.fragment_owners_list;
    }

    @Override
    protected void findView(View view) {
        this.mEmptyBlock = (LinearLayout) view.findViewById(R.id.owners_list_empty_block);
        this.mCreateOwnerButton = (Button) view.findViewById(R.id.owners_list_empty_create_owner);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.owners_list_recycler_view);
    }

    @Override
    protected void setupData() {
        this.mCreateOwnerButton.setOnClickListener(mCreateOwnerButtonClick);

        this.mOwnersListAdapter = new OwnersListAdapter(ShopFactory.getInstance(getContext()).getOwners());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new ItemsListItemDecorator());
        this.mRecyclerView.setAdapter(this.mOwnersListAdapter);

        setListVisibility(this.mOwnersListAdapter.getItemCount() != 0);
    }

    @Override
    protected int getMenuResource() {
        return R.menu.fragment_owners_list_menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_owners_list_create_owner:
                createNewOwner();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setListVisibility(boolean isVisible) {
        this.mEmptyBlock.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        this.mRecyclerView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    private void createNewOwner() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.single_fragment_fragment_container, new CreateOwnerFragment())
                .addToBackStack(null)
                .commit();
    }
}
