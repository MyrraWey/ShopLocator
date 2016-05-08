package com.muravyovdmitr.shoplocator.activity;

import com.muravyovdmitr.shoplocator.strategy.ISingleFragmentStrategy;
import com.muravyovdmitr.shoplocator.strategy.OwnersListStrategy;

/**
 * Created by MyrraWey on 04.05.2016.
 */
public class OwnersListActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentStrategy getLoadingStrategy() {
        return new OwnersListStrategy();
    }
}