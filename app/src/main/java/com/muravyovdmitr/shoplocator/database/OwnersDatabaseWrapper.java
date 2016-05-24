package com.muravyovdmitr.shoplocator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.database.DbSchema.OwnerTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class OwnersDatabaseWrapper extends DatabaseWrapper<Owner, OwnersCursorWrapper> implements IDataOperations<Owner> {

    public OwnersDatabaseWrapper(Context context) {
        super(context);
    }

    @Override
    protected ContentValues getContentValues(Owner item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OwnerTable.COLUMNS.UUID, item.getId().toString());
        contentValues.put(OwnerTable.COLUMNS.NAME, item.getName());
        contentValues.put(OwnerTable.COLUMNS.IMAGE_URL, item.getImageUrl());
        contentValues.put(OwnerTable.COLUMNS.CREATED, item.getCreated());
        contentValues.put(OwnerTable.COLUMNS.UPDATED, item.getUpdated());

        return contentValues;
    }

    @Override
    protected OwnersCursorWrapper queryItem(String whereClause, String[] whereArgs) {
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

    @Override
    public List<Owner> getItems() {
        List<Owner> items = new ArrayList<>();

        OwnersCursorWrapper cursor = queryItem(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getOwner());

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return items;
    }

    @Override
    public Owner getItem(UUID id) {
        OwnersCursorWrapper cursor = queryItem(
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

    @Override
    public void addItem(Owner item) {
        ContentValues contentValues = getContentValues(item);
        this.mDatabase.insert(OwnerTable.NAME, null, contentValues);
    }

    @Override
    public void updateItem(Owner item) {
        ContentValues values = getContentValues(item);

        mDatabase.update(
                OwnerTable.NAME,
                values,
                OwnerTable.COLUMNS.UUID + " = ?",
                new String[]{item.getId().toString()}
        );
    }

    @Override
    public void deleteItem(Owner item) {
        this.mDatabase.delete(
                OwnerTable.NAME,
                OwnerTable.COLUMNS.UUID + " = ?",
                new String[]{item.getId().toString()}
        );
    }
}
