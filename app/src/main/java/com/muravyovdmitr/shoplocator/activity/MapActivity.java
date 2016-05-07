package com.muravyovdmitr.shoplocator.activity;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.fragment.MapFragment;
import com.muravyovdmitr.shoplocator.strategy.ISingleFragmentStrategy;
import com.muravyovdmitr.shoplocator.strategy.MapStrategy;
import com.muravyovdmitr.shoplocator.strategy.ShopsListStrategy;

/**
 * Created by Dima Muravyov on 06.05.2016.
 */
public class MapActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentStrategy getLoadingStrategy() {
        return new MapStrategy();
    }
}
