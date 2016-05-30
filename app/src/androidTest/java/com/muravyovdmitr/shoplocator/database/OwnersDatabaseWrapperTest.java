package com.muravyovdmitr.shoplocator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.database.DbSchema.OwnerTable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Dima Muravyov on 31.05.2016.
 */
@RunWith(AndroidJUnit4.class)
public class OwnersDatabaseWrapperTest {
    private Context mContext;
    private ShopLocatorDbHelper mDbHelper;
    private IDataOperations<Owner> mCrudOPerations;

    @Before
    public void setUp() {
        mContext = new RenamingDelegatingContext(
                InstrumentationRegistry.getTargetContext(),
                "test_"
        );

        mDbHelper = new ShopLocatorDbHelper(mContext);
        mCrudOPerations = new OwnersDatabaseWrapper(mContext);
    }

    @After
    public void tearDown() {
        mDbHelper.close();
    }

    @Test
    public void readDataFromDatabase() {
        Owner owner = getOwner();

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(
                "INSERT INTO " + OwnerTable.NAME + " (" +
                        OwnerTable.COLUMNS.UUID + "," +
                        OwnerTable.COLUMNS.NAME + "," +
                        OwnerTable.COLUMNS.IMAGE_URL + "," +
                        OwnerTable.COLUMNS.CREATED + "," +
                        OwnerTable.COLUMNS.UPDATED +
                        ") VALUES (?,?,?,?,?);"
        );
        statement.bindAllArgsAsStrings(
                new String[]{
                        owner.getId().toString(),
                        owner.getName(),
                        owner.getImageUrl(),
                        owner.getCreated(),
                        owner.getUpdated()
                }
        );
        statement.execute();

        Owner resultOwner = mCrudOPerations.getItem(owner.getId());
        Assert.assertTrue("Read data from database", owner.equals(resultOwner));

        resultOwner.setName("Changed name");
        Assert.assertFalse("Read data from database", owner.equals(resultOwner));
    }

    private Owner getOwner() {
        Owner owner = new Owner(UUID.randomUUID());
        owner.setName("Owner Name");
        owner.setImageUrl("ImageURL");
        owner.setCreated(new Date().toString());
        owner.setUpdated(new Date().toString());

        return owner;
    }
}
