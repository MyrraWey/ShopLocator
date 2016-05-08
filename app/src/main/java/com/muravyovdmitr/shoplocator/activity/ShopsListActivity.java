package com.muravyovdmitr.shoplocator.activity;

import com.muravyovdmitr.shoplocator.activity.strategy.ISingleFragmentActivityStrategy;
import com.muravyovdmitr.shoplocator.activity.strategy.ShopsListActivityStrategy;

public class ShopsListActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentActivityStrategy getLoadingStrategy() {
        return new ShopsListActivityStrategy();
    }
}
