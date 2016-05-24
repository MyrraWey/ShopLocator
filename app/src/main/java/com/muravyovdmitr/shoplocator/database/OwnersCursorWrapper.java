package com.muravyovdmitr.shoplocator.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.database.DbSchema.OwnerTable;

import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class OwnersCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public OwnersCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Owner getOwner() {
        String uuid = getString(getColumnIndex(OwnerTable.COLUMNS.UUID));
        String name = getString(getColumnIndex(OwnerTable.COLUMNS.NAME));
        String imageUrl = getString(getColumnIndex(OwnerTable.COLUMNS.IMAGE_URL));
        String created = getString(getColumnIndex(OwnerTable.COLUMNS.CREATED));
        String updated = getString(getColumnIndex(OwnerTable.COLUMNS.UPDATED));

        Owner owner = new Owner(UUID.fromString(uuid));
        owner.setName(name);
        owner.setImageUrl(imageUrl);
        owner.setCreated(created);
        owner.setUpdated(updated);

        return owner;
    }
}
