package com.muravyovdmitr.shoplocator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.muravyovdmitr.shoplocator.database.DbSchema.OwnerTable;
import com.muravyovdmitr.shoplocator.database.DbSchema.ShopTable;
import com.muravyovdmitr.shoplocator.database.OwnersCursorWrapper;
import com.muravyovdmitr.shoplocator.database.ShopLocatorDbHelper;
import com.muravyovdmitr.shoplocator.database.ShopsCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopFactory {
    private static ShopFactory mShopFactory;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ShopFactory getInstance(Context context) {
        if (mShopFactory == null) {
            mShopFactory = new ShopFactory(context);
        }

        return mShopFactory;
    }

    private ShopFactory(Context context) {
        this.mContext = context;
        this.mDatabase = new ShopLocatorDbHelper(this.mContext).getWritableDatabase();
    }

    // SHOP PAERT START

    private ContentValues getContentValues(Shop shop) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopTable.COLUMNS.UUID, shop.getId().toString());
        contentValues.put(ShopTable.COLUMNS.TITLE, shop.getTitle());
        contentValues.put(ShopTable.COLUMNS.COORD, shop.getCoord());
        contentValues.put(ShopTable.COLUMNS.OWNER, shop.getOwner());
        contentValues.put(ShopTable.COLUMNS.IMAGE_URL, shop.getImageUrl());

        return contentValues;
    }

    private ShopsCursorWrapper queryShop(String whereClause, String[] whereArgs) {
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

    public List<Shop> getShops() {
        List<Shop> shops = new ArrayList<>();

        ShopsCursorWrapper cursor = queryShop(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                shops.add(cursor.getShop());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return shops;
    }

    public Shop getShop(UUID id) {
        ShopsCursorWrapper cursor = queryShop(
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

    public void addShop(Shop shop) {
        ContentValues contentValues = getContentValues(shop);
        this.mDatabase.insert(ShopTable.NAME, null, contentValues);
    }

    public void updateShop(Shop shop) {
        ContentValues values = getContentValues(shop);

        mDatabase.update(
                ShopTable.NAME,
                values,
                ShopTable.COLUMNS.UUID + " = ?",
                new String[]{shop.getId().toString()}
        );
    }

    public void deleteShop(Shop shop) {
        ContentValues content = getContentValues(shop);

        this.mDatabase.delete(
                ShopTable.NAME,
                ShopTable.COLUMNS.UUID + " = ?",
                new String[]{shop.getId().toString()}
        );

    }

    // OWNER PART START

    private ContentValues getContentValues(Owner owner) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OwnerTable.COLUMNS.UUID, owner.getId().toString());
        contentValues.put(OwnerTable.COLUMNS.NAME, owner.getName());
        contentValues.put(OwnerTable.COLUMNS.IMAGE_URL, owner.getImageUrl());

        return contentValues;
    }

    private OwnersCursorWrapper queryOwner(String whereClause, String[] whereArgs) {
        Cursor cursor = this.mDatabase.query(
                OwnerTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new OwnersCursorWrapper(cursor);
    }

    public List<Owner> getOwners() {
        List<Owner> owners = new ArrayList<>();

        OwnersCursorWrapper cursor = queryOwner(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                owners.add(cursor.getOwner());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return owners;
    }

    public Owner getOwner(UUID id) {
        OwnersCursorWrapper cursor = queryOwner(
                OwnerTable.COLUMNS.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getOwner();
        } finally {
            cursor.close();
        }
    }

    public void addOwner(Owner owner) {
        ContentValues contentValues = getContentValues(owner);
        this.mDatabase.insert(OwnerTable.NAME, null, contentValues);
    }

    public void updateOwner(Owner owner) {
        ContentValues values = getContentValues(owner);

        mDatabase.update(
                OwnerTable.NAME,
                values,
                OwnerTable.COLUMNS.UUID + " = ?",
                new String[]{owner.getId().toString()}
        );
    }

    public void deleteOwner(Owner owner) {
        ContentValues content = getContentValues(owner);

        this.mDatabase.delete(
                OwnerTable.NAME,
                OwnerTable.COLUMNS.UUID + " = ?",
                new String[]{owner.getId().toString()}
        );

    }
}
