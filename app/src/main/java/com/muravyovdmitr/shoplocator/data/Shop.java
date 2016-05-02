package com.muravyovdmitr.shoplocator.data;

import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class Shop {
    private UUID mID;
    private String mTitle;
    private String mCoord;
    private String mOwner;
    private String mImageUrl;

    public Shop() {
        this(UUID.randomUUID());
    }

    public Shop(UUID id) {
        this.mID = id;
    }

    public Shop(String id) {
        this.mID = UUID.fromString(id);
    }

    public UUID getID() {
        return mID;
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

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
