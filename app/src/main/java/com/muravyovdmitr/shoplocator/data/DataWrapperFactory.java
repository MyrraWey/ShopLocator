package com.muravyovdmitr.shoplocator.data;

import android.content.Context;

import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.network.OwnersNetworkWrapper;
import com.muravyovdmitr.shoplocator.network.ShopsNetworkWrapper;
import com.muravyovdmitr.shoplocator.util.NetworkStateChecker;
import com.muravyovdmitr.shoplocator.util.ShopLocatorApplication;

/**
 * Created by Dima Muravyov on 16.05.2016.
 */
public class DataWrapperFactory {
    public static IDataOperations<Shop> getShopsDataWrapper() {
        IDataOperations<Shop> dataOperations;

        Context context = ShopLocatorApplication.getInstance().getApplicationContext();
        if (NetworkStateChecker.isNetworkAvailable(context)) {
            dataOperations = new ShopsNetworkWrapper(
                    new ShopsDatabaseWrapper(context)
            );
        } else {
            dataOperations = new ShopsDatabaseWrapper(context);
        }

        return dataOperations;
    }

    public static IDataOperations<Owner> getOwnersDataWrapper() {
        IDataOperations<Owner> dataOperations;

        Context context = ShopLocatorApplication.getInstance().getApplicationContext();
        if (NetworkStateChecker.isNetworkAvailable(context)) {
            dataOperations = new OwnersNetworkWrapper(
                    new OwnersDatabaseWrapper(context)
            );
        } else {
            dataOperations = new OwnersDatabaseWrapper(context);
        }

        return dataOperations;
    }
}
