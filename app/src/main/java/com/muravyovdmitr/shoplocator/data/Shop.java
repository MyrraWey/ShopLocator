package com.muravyovdmitr.shoplocator.data;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class Shop extends BaseBackendlessObject {
    @SerializedName("id")
    private UUID mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("coord")
    private String mCoord;

    @SerializedName("owner")
    private UUID mOwner;

    @SerializedName("imageUrl")
    private String mImageUrl;

    @Override
    public String toString() {
        return mTitle;
    }

    public Shop() {
        this(UUID.randomUUID());
    }

    public Shop(UUID id) {
        this.mId = id;
    }

    public Shop(String id) {
        this.mId = UUID.fromString(id);
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCoord() {
        return mCoord;
    }

    public void setCoord(String coord) {
        mCoord = coord;
    }

    public UUID getOwner() {
        return mOwner;
    }

    public void setOwner(UUID owner) {
        mOwner = owner;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
