package com.muravyovdmitr.shoplocator.database;

import android.provider.BaseColumns;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public final class DbSchema {
    private DbSchema(){}

    public static abstract class ShopTable {
        public static final String NAME = "Shops";

        public static abstract class COLUMNS implements BaseColumns{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String COORD = "coord";
            public static final String OWNER = "owner";
            public static final String IMAGE_URL = "image_url";
        }
    }
}
