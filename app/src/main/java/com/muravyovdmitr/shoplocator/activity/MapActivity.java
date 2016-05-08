package com.muravyovdmitr.shoplocator.activity;

import com.muravyovdmitr.shoplocator.activity.strategy.ISingleFragmentActivityStrategy;
import com.muravyovdmitr.shoplocator.activity.strategy.MapActivityStrategy;

/**
 * Created by Dima Muravyov on 06.05.2016.
 */
public class MapActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentActivityStrategy getLoadingStrategy() {
        return new MapActivityStrategy();
    }
}
