package com.muravyovdmitr.shoplocator.util;

import android.app.Application;

/**
 * Created by Dima Muravyov on 14.05.2016.
 */
public class ShopLocatorApplication extends Application {
    private static ShopLocatorApplication mInstance;

    public ShopLocatorApplication() {
        mInstance = this;
    }

    public static ShopLocatorApplication getInstance() {
        return mInstance;
    }
}
