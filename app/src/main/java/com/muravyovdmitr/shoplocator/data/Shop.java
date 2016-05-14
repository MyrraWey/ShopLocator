package com.muravyovdmitr.shoplocator.data;

import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class Shop {
    private UUID mId;
    private String mTitle;
    private String mCoord;
    private UUID mOwner;
    private String mImageUrl;

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
