package com.muravyovdmitr.shoplocator.data;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by Dima Muravyov on 22.05.2016.
 */
public class BaseBackendlessObject {
    @SerializedName("objectId")
    private UUID objectId;

    @SerializedName("created")
    private String created;

    @SerializedName("updated")
    private String updated;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
