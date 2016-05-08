package com.muravyovdmitr.shoplocator.fragment.strategy;

import com.muravyovdmitr.shoplocator.R;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class ShopsListStrategy implements IBaseFragmentStrategy {
    @Override
    public int getViewResource() {
        return R.layout.fragment_base_list;
    }

    @Override
    public int getMenuResource() {
        return R.menu.fragment_base_list_menu;
    }

    @Override
    public int getToolbarTitleResource() {
        return R.string.fragment_shops_list_toolbar_title;
    }
}
