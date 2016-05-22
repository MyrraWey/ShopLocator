package com.muravyovdmitr.shoplocator.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dima Muravyov on 23.05.2016.
 */
public class BackendlessListResponse<T> {
    @SerializedName("nextPage")
    private int nextPage;

    @SerializedName("data")
    private List<T> itemsList;

    @SerializedName("offset")
    private int offset;

    @SerializedName("totalObjects")
    private int totalObjects;

    public int getNextPage() {
        return nextPage;
    }

    public List<T> getItemsList() {
        return itemsList;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotalObjects() {
        return totalObjects;
    }
}
