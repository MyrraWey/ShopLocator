package com.muravyovdmitr.shoplocator.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.BaseBackendlessObject;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.decoration.ItemsListItemDecorator;
import com.muravyovdmitr.shoplocator.network.OwnersNetworkWrapper;
import com.muravyovdmitr.shoplocator.network.ShopsNetworkWrapper;
import com.muravyovdmitr.shoplocator.util.NetworkStateChecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public abstract class BaseListFragment<LA extends RecyclerView.Adapter> extends BaseFragment implements IBaseListFragment<LA> {
    protected LinearLayout mEmptyBlock;
    protected Button mCreateItemButton;
    protected RecyclerView mRecyclerView;
    protected LA mItemsListAdapter;

    protected OnClickListener mCreateItemButtonClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.base_list_empty_create_item:
                    createNewItem();
                    break;
            }
        }
    };

    private final IDataSync mDataSync = new IDataSync() {
        @Override
        public void OnDataSync() {
            mItemsListAdapter = getItemsListAdapter();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(new ItemsListItemDecorator());
            mRecyclerView.setAdapter(mItemsListAdapter);

            setListVisibility(mItemsListAdapter.getItemCount() != 0);
        }
    };

    class SyncDataTask extends AsyncTask<Void, Void, Void> {
        Context mContext;
        IDataSync mDataSync;
        ProgressDialog mProgressDialog;

        public SyncDataTask(Context context, IDataSync dataSync) {
            mContext = context;
            mDataSync = dataSync;

            initProgressDialog();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final IDataOperations<Owner> localOwnersData = new OwnersDatabaseWrapper(mContext);
            final IDataOperations<Owner> serverOwnersData = new OwnersNetworkWrapper(localOwnersData);
            Map<UUID, Owner> localOwnersMap = createOwnersMap(localOwnersData.getItems());
            Map<UUID, Owner> serverOwnersMap = createOwnersMap(serverOwnersData.getItems());

            final IDataOperations<Shop> localShopsData = new ShopsDatabaseWrapper(mContext);
            final IDataOperations<Shop> serverShopsData = new ShopsNetworkWrapper(localShopsData);
            Map<UUID, Shop> localShopsMap = createShopsMap(localShopsData.getItems());
            Map<UUID, Shop> serverShopsMap = createShopsMap(serverShopsData.getItems());

            syncData(localOwnersData, localOwnersMap, serverOwnersData, serverOwnersMap);
            syncData(localShopsData, localShopsMap, serverShopsData, serverShopsMap);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mDataSync.OnDataSync();

            mProgressDialog.dismiss();
        }

        private void initProgressDialog() {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Sync Data");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }

        private Map<UUID, Owner> createOwnersMap(List<Owner> list) {
            Map<UUID, Owner> map = new HashMap<>();

            for (Owner item : list) {
                map.put(item.getId(), item);
            }

            return map;
        }

        private Map<UUID, Shop> createShopsMap(List<Shop> list) {
            Map<UUID, Shop> map = new HashMap<>();

            for (Shop item : list) {
                map.put(item.getId(), item);
            }

            return map;
        }

        private <T extends BaseBackendlessObject> void syncData(
                IDataOperations<T> localData,
                Map<UUID, T> localMap,
                IDataOperations<T> serverData,
                Map<UUID, T> serverMap
        ) {
            for (Map.Entry<UUID, T> serverItem : serverMap.entrySet()) {
                if (localMap.containsKey(serverItem.getKey())) {
                    localData.updateItem(serverItem.getValue());

                    localMap.remove(serverItem.getKey());
                } else {
                    localData.addItem(serverItem.getValue());
                }
            }

            for (Map.Entry<UUID, T> localItem : localMap.entrySet()) {
                if (localItem.getValue().getCreated() != null) {
                    localData.deleteItem(localItem.getValue());
                } else {
                    serverData.addItem(localItem.getValue());
                }
            }
        }
    }

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

        syncData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_base_list_create_item:
                createNewItem();
                return true;
            default:
                return false;
        }
    }

    public void setListVisibility(boolean isVisible) {
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

    protected void syncData() {
        if (NetworkStateChecker.isNetworkAvailable(getContext())) {
            SyncDataTask syncDataTask = new SyncDataTask(getContext(), mDataSync);
            syncDataTask.execute();
        } else {
            mDataSync.OnDataSync();
        }
    }
}
