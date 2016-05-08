package com.muravyovdmitr.shoplocator.fragment.strategy;

import com.muravyovdmitr.shoplocator.R;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class CreateOwnerStrategy implements IBaseFragmentStrategy {
    @Override
    public int getViewResource() {
        return R.layout.fragment_create_owner;
    }

    @Override
    public int getMenuResource() {
        return R.menu.fragment_create_owner_menu;
    }

    @Override
    public int getToolbarTitleResource() {
        return R.string.fragment_create_owner_toolbar_title;
    }
}
