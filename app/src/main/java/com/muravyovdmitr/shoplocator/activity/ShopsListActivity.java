package com.muravyovdmitr.shoplocator.activity;

import com.muravyovdmitr.shoplocator.strategy.ISingleFragmentStrategy;
import com.muravyovdmitr.shoplocator.strategy.ShopsListStrategy;

public class ShopsListActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentStrategy getLoadingStrategy() {
        return new ShopsListStrategy();
    }
}
