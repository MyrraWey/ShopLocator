package com.muravyovdmitr.shoplocator.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.DbSchema.ShopTable;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ShopsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Shop getShop() {
        String uuid = getString(getColumnIndex(ShopTable.COLUMNS.UUID));
        String title = getString(getColumnIndex(ShopTable.COLUMNS.TITLE));
        String coord = getString(getColumnIndex(ShopTable.COLUMNS.COORD));
        String owner = getString(getColumnIndex(ShopTable.COLUMNS.OWNER));
        String imageUrl = getString(getColumnIndex(ShopTable.COLUMNS.IMAGE_URL));

        Shop shop = new Shop(uuid);
        shop.setTitle(title);
        shop.setCoord(coord);
        shop.setOwner(owner);
        shop.setImageUrl(imageUrl);

        return shop;
    }
}
