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
import java.util.List;
import java.util.UUID;

/**
 * Created by Dima Muravyov on 31.05.2016.
 */
@RunWith(AndroidJUnit4.class)
public class OwnersDatabaseWrapperTest {
    private Context mContext;
    private ShopLocatorDbHelper mDbHelper;
    private IDataOperations<Owner> mCrudOperations;

    @Before
    public void setUp() {
        mContext = new RenamingDelegatingContext(
                InstrumentationRegistry.getTargetContext(),
                "test_"
        );

        mDbHelper = new ShopLocatorDbHelper(mContext);
        mCrudOperations = new OwnersDatabaseWrapper(mContext);
    }

    @After
    public void tearDown() {
        mDbHelper.close();
    }

    @Test
    public void readSingleItemFromDatabase() {
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

        Owner resultOwner = mCrudOperations.getItem(owner.getId());
        Assert.assertTrue("Read data from database", owner.equals(resultOwner));

        resultOwner.setName("Changed name");
        Assert.assertFalse("Read data from database", owner.equals(resultOwner));
    }

    @Test
    public void readItemsListFromDatabase() {
        Owner[] owners = new Owner[]{getOwner(), getOwner()};

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
                        owners[0].getId().toString(),
                        owners[0].getName(),
                        owners[0].getImageUrl(),
                        owners[0].getCreated(),
                        owners[0].getUpdated()
                }
        );
        statement.execute();
        statement.bindAllArgsAsStrings(
                new String[]{
                        owners[1].getId().toString(),
                        owners[1].getName(),
                        owners[1].getImageUrl(),
                        owners[1].getCreated(),
                        owners[1].getUpdated()
                }
        );
        statement.execute();

        List<Owner> resultOwners = mCrudOperations.getItems();
        Assert.assertEquals("Not all data saved", 2, resultOwners.size());
        Assert.assertArrayEquals("Problem with object reading", owners, resultOwners.toArray());
    }

    @Test
    public void writeDataToDatabase(){
        Owner owner = getOwner();
        mCrudOperations.addItem(owner);

        Assert.assertEquals("Writing error", owner, mCrudOperations.getItem(owner.getId()));
        Assert.assertEquals("Wrong items count", 1, mCrudOperations.getItems().size());
    }

    @Test
    public void updateDataFromDatabase(){
        Owner owner = getOwner();
        mCrudOperations.addItem(owner);

        owner.setName("New Name");
        mCrudOperations.updateItem(owner);

        Assert.assertEquals("Updating error", owner, mCrudOperations.getItem(owner.getId()));
    }
    @Test
    public void deleteDataFromDatabase(){
        Owner[] owners = new Owner[]{getOwner(), getOwner()};

        mCrudOperations.addItem(owners[0]);
        Assert.assertEquals(1,mCrudOperations.getItems().size());
        mCrudOperations.addItem(owners[1]);
        Assert.assertEquals(2,mCrudOperations.getItems().size());

        mCrudOperations.deleteItem(owners[1]);
        Assert.assertEquals(1, mCrudOperations.getItems().size());
        Assert.assertEquals(owners[0], mCrudOperations.getItems().get(0));
        mCrudOperations.deleteItem(owners[0]);
        Assert.assertEquals(0, mCrudOperations.getItems().size());
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
