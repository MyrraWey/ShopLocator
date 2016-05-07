package com.muravyovdmitr.shoplocator.activity;

import android.support.v4.app.Fragment;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.fragment.OwnersListFragment;
import com.muravyovdmitr.shoplocator.strategy.ISingleFragmentStrategy;
import com.muravyovdmitr.shoplocator.strategy.OwnersListStrategy;
import com.muravyovdmitr.shoplocator.strategy.ShopsListStrategy;

/**
 * Created by MyrraWey on 04.05.2016.
 */
public class OwnersListActivity extends SingleFragmentActivity {
    @Override
    protected ISingleFragmentStrategy getLoadingStrategy() {
        return new OwnersListStrategy();
    }
}