package com.muravyovdmitr.shoplocator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.muravyovdmitr.shoplocator.database.DbSchema.OwnerTable;
import com.muravyovdmitr.shoplocator.database.DbSchema.ShopTable;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopLocatorDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ShopLocator.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SHOPS_TABLE_CREATE = "" +
            "CREATE TABLE " + ShopTable.NAME + " ( " +
            ShopTable.COLUMNS._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            ShopTable.COLUMNS.UUID + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMNS.TITLE + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMNS.OWNER + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMNS.COORD + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMNS.IMAGE_URL + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMNS.UPDATED + TEXT_TYPE +
            " );";
    private static final String OWNERS_TABLE_CREATE = "" +
            "CREATE TABLE " + OwnerTable.NAME + " ( " +
            OwnerTable.COLUMNS._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            OwnerTable.COLUMNS.UUID + TEXT_TYPE + COMMA_SEP +
            OwnerTable.COLUMNS.NAME + TEXT_TYPE + COMMA_SEP +
            OwnerTable.COLUMNS.IMAGE_URL + TEXT_TYPE + COMMA_SEP +
            OwnerTable.COLUMNS.UPDATED + TEXT_TYPE +
            " );";

    public ShopLocatorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SHOPS_TABLE_CREATE);
        db.execSQL(OWNERS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
