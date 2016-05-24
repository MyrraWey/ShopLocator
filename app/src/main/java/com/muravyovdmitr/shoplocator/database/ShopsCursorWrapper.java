package com.muravyovdmitr.shoplocator.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.DbSchema.ShopTable;

import java.util.UUID;

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
        String created = getString(getColumnIndex(ShopTable.COLUMNS.CREATED));
        String updated = getString(getColumnIndex(ShopTable.COLUMNS.UPDATED));

        Shop shop = new Shop(uuid);
        shop.setTitle(title);
        shop.setCoord(coord);
        shop.setOwner(UUID.fromString(owner));
        shop.setImageUrl(imageUrl);
        shop.setCreated(created);
        shop.setUpdated(updated);

        return shop;
    }
}
