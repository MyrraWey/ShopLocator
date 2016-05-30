package com.muravyovdmitr.shoplocator.data;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by MyrraWey on 05.05.2016.
 */
public class Owner extends BaseBackendlessObject {
    @SerializedName("id")
    private UUID mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("imageUrl")
    private String mImageUrl;

    public Owner() {
        this(UUID.randomUUID());
    }

    public Owner(UUID id) {
        this.mId = id;
    }

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public boolean equals(Object o) {
        Owner compare = (Owner) o;

        if (!mId.equals(compare.getId())) {
            return false;
        }

        if (!mName.equals(compare.getName())) {
            return false;
        }

        if (!mImageUrl.equals(compare.getImageUrl())) {
            return false;
        }

        if (!getCreated().equals(compare.getCreated())) {
            return false;
        }

        if (!getUpdated().equals(compare.getUpdated())) {
            return false;
        }

        if (!(getObjectId() == compare.getObjectId())) {
            return false;
        }

        return true;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
