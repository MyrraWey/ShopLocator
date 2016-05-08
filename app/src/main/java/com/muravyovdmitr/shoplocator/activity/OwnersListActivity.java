package com.muravyovdmitr.shoplocator.activity;

import com.muravyovdmitr.shoplocator.activity.strategy.ISingleFragmentActivityStrategy;
import com.muravyovdmitr.shoplocator.activity.strategy.OwnersListActivityStrategy;

/**
 * Created by MyrraWey on 04.05.2016.
 */
public class OwnersListActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentActivityStrategy getLoadingStrategy() {
        return new OwnersListActivityStrategy();
    }
}