package com.muravyovdmitr.shoplocator.activity;

import com.muravyovdmitr.shoplocator.strategy.ISingleFragmentStrategy;
import com.muravyovdmitr.shoplocator.strategy.MapStrategy;

/**
 * Created by Dima Muravyov on 06.05.2016.
 */
public class MapActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentStrategy getLoadingStrategy() {
        return new MapStrategy();
    }
}
