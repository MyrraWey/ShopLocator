package com.muravyovdmitr.shoplocator.network;

import android.os.StrictMode;

import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit.Response;

/**
 * Created by Dima Muravyov on 22.05.2016.
 */
public class ShopsNetworkWrapper implements IDataOperations<Shop> {
    private Services.ShopService mService;
    private IDataOperations<Shop> mDataChainOperations;

    public ShopsNetworkWrapper(IDataOperations<Shop> chainOperations) {
        mService = ServiceFactory.createRetrofitService(Services.ShopService.class);
        mDataChainOperations = chainOperations;

        //TODO temporary solution, replace it
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public List<Shop> getItems() {
        List<Shop> result;

        try {
            Response<BackendlessListResponse<Shop>> response = mService.getShops().execute();

            if (response.body() != null) {
                result = response.body().getItemsList();
            } else {
                result = new ArrayList<>();
            }
        } catch (IOException e) {
            result = new ArrayList<>();
        }

        return result;
    }

    @Override
    public Shop getItem(UUID id) {
        Shop result;

        try {
            Response<Shop> response = mService.getShop(getBackendlessId(id)).execute();
            result = response.body();
        } catch (IOException e) {
            result = null;
        }

        return result;
    }

    @Override
    public void addItem(Shop item) {
        mDataChainOperations.addItem(item);

        try {
            mService.createShop(item).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Shop item) {
        mDataChainOperations.updateItem(item);

        try {
            mService.updateShop(getBackendlessId(item.getId()), item).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(Shop item) {
        mDataChainOperations.deleteItem(item);

        try {
            mService.deleteShop(getBackendlessId(item.getId())).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UUID getBackendlessId(UUID shopId) {
        Shop shop;

        try {
            Response<BackendlessListResponse<Shop>> response = mService.getShopByQuery(prepareShopId(shopId)).execute();
            if (response.body() != null && !response.body().getItemsList().isEmpty()) {
                shop = response.body().getItemsList().get(0);
            } else {
                shop = null;
            }
        } catch (IOException e) {
            shop = null;
        }

        if (shop == null) {
            return null;
        } else {
            return shop.getObjectId();
        }
    }

    private String prepareShopId(UUID id) {
        return "id='" + id.toString() + "'";
    }
}
