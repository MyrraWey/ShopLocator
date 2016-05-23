package com.muravyovdmitr.shoplocator.network;

import android.os.StrictMode;

import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit.Response;

/**
 * Created by Dima Muravyov on 22.05.2016.
 */
public class OwnersNetworkWrapper implements IDataOperations<Owner> {
    private Services.OwnerService mService;
    private IDataOperations<Owner> mDataChainOperations;

    public OwnersNetworkWrapper(IDataOperations<Owner> chainOperations) {
        mService = ServiceFactory.createRetrofitService(Services.OwnerService.class);
        mDataChainOperations = chainOperations;

        //TODO temporary solution, replace it
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public List<Owner> getItems() {
        List<Owner> result;

        try {
            Response<BackendlessListResponse<Owner>> response = mService.getOwners().execute();

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
    public Owner getItem(UUID id) {
        Owner result;

        try {
            Response<Owner> response = mService.getOwner(getBackendlessId(id)).execute();
            result = response.body();
        } catch (IOException e) {
            result = null;
        }

        return result;
    }

    @Override
    public void addItem(Owner item) {
        mDataChainOperations.addItem(item);

        try {
            mService.createOwner(item).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Owner item) {
        mDataChainOperations.updateItem(item);

        try {
            mService.updateOwner(getBackendlessId(item.getId()), item).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(Owner item) {
        mDataChainOperations.deleteItem(item);

        try {
            mService.deleteOwner(getBackendlessId(item.getId())).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UUID getBackendlessId(UUID ownerId) {
        Owner owner;

        try {
            Response<BackendlessListResponse<Owner>> response = mService.getOwnerByQuery(prepareOwnerId(ownerId)).execute();
            owner = response.body().getItemsList().get(0);
        } catch (IOException e) {
            owner = null;
        }

        if (owner == null) {
            return null;
        } else {
            return owner.getObjectId();
        }
    }

    private String prepareOwnerId(UUID id) {
        return "id='" + id.toString() + "'";
    }
}
