package com.muravyovdmitr.shoplocator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.DbSchema.ShopTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class ShopsDatabaseWrapper extends DatabaseWrapper<Shop, ShopsCursorWrapper> implements IDataOperations<Shop> {
    public ShopsDatabaseWrapper(Context context) {
        super(context);
    }

    @Override
    protected ContentValues getContentValues(Shop item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopTable.COLUMNS.UUID, item.getId().toString());
        contentValues.put(ShopTable.COLUMNS.TITLE, item.getTitle());
        contentValues.put(ShopTable.COLUMNS.COORD, item.getCoord());
        contentValues.put(ShopTable.COLUMNS.OWNER, item.getOwner().toString());
        contentValues.put(ShopTable.COLUMNS.IMAGE_URL, item.getImageUrl());
        contentValues.put(ShopTable.COLUMNS.UPDATED, item.getUpdated());

        return contentValues;
    }

    @Override
    protected ShopsCursorWrapper queryItem(String whereClause, String[] whereArgs) {
        Cursor cursor = this.mDatabase.query(
                ShopTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ShopsCursorWrapper(cursor);
    }

    @Override
    public List<Shop> getItems() {
        List<Shop> items = new ArrayList<>();

        ShopsCursorWrapper cursor = queryItem(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getShop());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return items;
    }

    @Override
    public Shop getItem(UUID id) {
        ShopsCursorWrapper cursor = queryItem(
                ShopTable.COLUMNS.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getShop();
        } finally {
            cursor.close();
        }
    }

    @Override
    public void addItem(Shop item) {
        ContentValues contentValues = getContentValues(item);
        this.mDatabase.insert(ShopTable.NAME, null, contentValues);
    }

    @Override
    public void updateItem(Shop item) {
        ContentValues values = getContentValues(item);

        this.mDatabase.update(
                ShopTable.NAME,
                values,
                ShopTable.COLUMNS.UUID + " = ?",
                new String[]{item.getId().toString()}
        );
    }

    @Override
    public void deleteItem(Shop item) {
        this.mDatabase.delete(
                ShopTable.NAME,
                ShopTable.COLUMNS.UUID + " = ?",
                new String[]{item.getId().toString()}
        );
    }
}
