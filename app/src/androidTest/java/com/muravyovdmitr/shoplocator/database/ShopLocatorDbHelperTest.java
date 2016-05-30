package com.muravyovdmitr.shoplocator.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by MyrraWey on 02.05.2016.
 */
@RunWith(AndroidJUnit4.class)
public class ShopLocatorDbHelperTest {
    private ShopLocatorDbHelper mDbHelper;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Context testContext = new RenamingDelegatingContext(appContext, "test_");

        mDbHelper = new ShopLocatorDbHelper(testContext);
    }

    @After
    public void tearDown() {
        mDbHelper.close();
    }

    @Test
    public void getWritableDatabase() {
        Assert.assertNotNull("Database is null", mDbHelper.getWritableDatabase());
    }
}