package com.muravyovdmitr.shoplocator.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.adapter.ShopsListAdapter;
import com.muravyovdmitr.shoplocator.data.DataWrapperFactory;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.ShopsListStrategy;
import com.muravyovdmitr.shoplocator.util.NetworkStateChecker;
import com.muravyovdmitr.shoplocator.util.SettingsManager;
import com.muravyovdmitr.shoplocator.util.TextUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListFragment extends BaseListFragment<ShopsListAdapter> {
    private final IDataOperations<Shop> mShopsData = DataWrapperFactory.getShopsDataWrapper();
    private final IDataOperations<Owner> mOwnersData = DataWrapperFactory.getOwnersDataWrapper();

    private class SyncDataTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog mProgressDialog;
        Date mLastSyncDate;
        SettingsManager mSettingsManager;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            initProgressDialog();
            mProgressDialog.show();

            mSettingsManager = new SettingsManager(getContext());
            mLastSyncDate = getLastSyncDate();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final IDataOperations<Owner> localOwnersData = new OwnersDatabaseWrapper(getContext());
            Map<UUID, Owner> localOwnersMap = createOwnersMap(localOwnersData.getItems());
            Map<UUID, Owner> serverOwnersMap = createOwnersMap(mOwnersData.getItems());

            for (Map.Entry<UUID, Owner> serverItem : serverOwnersMap.entrySet()) {
                if (localOwnersMap.containsKey(serverItem.getKey())) {
                    localOwnersData.updateItem(serverItem.getValue());

                    localOwnersMap.remove(serverItem.getKey());
                } else {
                    localOwnersData.addItem(serverItem.getValue());
                }
            }

            for (Map.Entry<UUID, Owner> localItem : localOwnersMap.entrySet()) {
                if (isItemDeleted(localItem.getValue().getUpdated())) {
                    localOwnersData.deleteItem(localItem.getValue());
                } else {
                    mOwnersData.addItem(localItem.getValue());

                    localOwnersData.deleteItem(localItem.getValue());
                }
            }

            //TODO make same with shops

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mSettingsManager.setLastSyncDate((new Date()).toString());

            mProgressDialog.hide();
        }

        private void initProgressDialog() {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Sync Data");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }

        private Date getLastSyncDate() {
            String lastSyncDate = mSettingsManager.getLastSyncDate();

            Date syncDate;
            if (lastSyncDate == null) {
                syncDate = null;
            } else {
                syncDate = TextUtils.getDateFromString(lastSyncDate);
            }

            return syncDate;
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

        private boolean isItemDeleted(String updated) {
            boolean result = false;

            //TODO mLastSyncDate == null incorrect behaviour
            if(updated == null || mLastSyncDate == null) {
                return result;
            }

            Date itemUpdateDate = TextUtils.getDateFromString(updated, "mm/dd/yyyy hh:mm:ss");
            if(itemUpdateDate.after(mLastSyncDate)){
                result = true;
            }

            return result;
        }
    }

    @Override
    public ShopsListAdapter getItemsListAdapter() {
        syncData();

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

    private void syncData() {
        if (NetworkStateChecker.isNetworkAvailable(getContext())) {
            SyncDataTask syncData = new SyncDataTask();
            syncData.execute();
        }
    }
}