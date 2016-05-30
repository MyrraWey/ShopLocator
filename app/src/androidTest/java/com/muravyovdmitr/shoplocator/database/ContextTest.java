package com.muravyovdmitr.shoplocator.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.muravyovdmitr.shoplocator.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Dima Muravyov on 29.05.2016.
 */
@RunWith(AndroidJUnit4.class)
public class ContextTest {
    @Test
    public void getAppContext() {
        assertNotNull("AppContext is null", InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void getTestContext() {
        assertNotNull("TestContext is null", InstrumentationRegistry.getContext());
    }

    @Test
    public void getAppRenamingDelegatingContext() {
        Context context = new RenamingDelegatingContext(
                InstrumentationRegistry.getTargetContext(),
                "test_"
        );

        assertNotNull("AppRenamingDelegatingContext is null", context);
    }

    @Test
    public void getAppResource() {
        String res = InstrumentationRegistry.getTargetContext().getResources().getString(R.string.activity_settings_mail_chooser_title);

        assertEquals("Reading App string resource", "Send E-Mail", res);
    }
}