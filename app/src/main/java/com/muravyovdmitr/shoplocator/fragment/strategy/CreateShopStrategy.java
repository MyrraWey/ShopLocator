package com.muravyovdmitr.shoplocator.fragment.strategy;

import com.muravyovdmitr.shoplocator.R;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class CreateShopStrategy implements IBaseFragmentStrategy {
    @Override
    public int getViewResource() {
        return R.layout.fragment_create_shop;
    }

    @Override
    public int getMenuResource() {
        return R.menu.fragment_create_shop_menu;
    }

    @Override
    public int getToolbarTitleResource() {
        return R.string.fragment_create_shop_toolbar_title;
    }
}
