package com.muravyovdmitr.shoplocator.data;

import java.util.UUID;

/**
 * Created by MyrraWey on 05.05.2016.
 */
public class Owner {
    private UUID mId;
    private String mName;
    private String mImageUrl;

    public Owner() {
        this(UUID.randomUUID());
    }

    public Owner(UUID id) {
        this.mId = id;
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
