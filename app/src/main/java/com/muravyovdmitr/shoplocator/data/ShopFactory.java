package com.muravyovdmitr.shoplocator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.muravyovdmitr.shoplocator.database.DbSchema.ShopTable;
import com.muravyovdmitr.shoplocator.database.ShopLocatorDbHelper;
import com.muravyovdmitr.shoplocator.database.ShopsCursorWrapper;

import java.util.ArrayList;
import java.util.List;

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

    private ContentValues getContentValues(Shop shop) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopTable.COLUMNS.UUID, shop.getID().toString());
        contentValues.put(ShopTable.COLUMNS.TITLE, shop.getTitle());
        contentValues.put(ShopTable.COLUMNS.COORD, shop.getCoord());
        contentValues.put(ShopTable.COLUMNS.OWNER, shop.getOwner());
        contentValues.put(ShopTable.COLUMNS.IMAGE_URL, shop.getImageUrl());

        return contentValues;
    }

    private ShopsCursorWrapper queryCrime(String whereClause, String[] whereArgs){
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

        ShopsCursorWrapper cursor = queryCrime(null, null);
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
}
