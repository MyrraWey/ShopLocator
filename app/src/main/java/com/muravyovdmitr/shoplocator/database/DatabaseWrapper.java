package com.muravyovdmitr.shoplocator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public abstract class DatabaseWrapper<E, CW> {
    protected SQLiteDatabase mDatabase;

    public DatabaseWrapper(Context context) {
        this.mDatabase = new ShopLocatorDbHelper(context).getWritableDatabase();
    }

    protected abstract ContentValues getContentValues(E item);

    protected abstract CW queryItem(String whereClause, String[] whereArgs);
}
