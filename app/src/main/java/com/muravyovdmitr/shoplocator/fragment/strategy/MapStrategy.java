package com.muravyovdmitr.shoplocator.fragment.strategy;

import com.muravyovdmitr.shoplocator.R;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class MapStrategy implements IBaseFragmentStrategy {
    @Override
    public int getViewResource() {
        return R.layout.fragment_map;
    }

    @Override
    public int getMenuResource() {
        return R.menu.fragment_map_menu;
    }

    @Override
    public int getToolbarTitleResource() {
        return R.string.fragment_map_toolbar_title;
    }
}
