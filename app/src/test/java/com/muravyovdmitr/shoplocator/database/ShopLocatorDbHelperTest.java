package com.muravyovdmitr.shoplocator.database;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopLocatorDbHelperTest extends AndroidTestCase {
    private ShopLocatorDbHelper mDbHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        mDbHelper = new ShopLocatorDbHelper(context);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        mDbHelper.close();
    }

    public void testDatabaseCreated() {
        assertNotNull("Database is null", mDbHelper.getWritableDatabase());
    }
}
