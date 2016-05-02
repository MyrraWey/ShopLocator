package com.muravyovdmitr.shoplocator.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopFactory {
    private static ShopFactory mShopFactory;

    private List<Shop> mShops;

    public static ShopFactory getInstance() {
        if (mShopFactory == null) {
            mShopFactory = new ShopFactory();
        }

        return mShopFactory;
    }

    private ShopFactory() {
        this.mShops = new ArrayList<>();
        generateShops(20);
    }

    private void generateShops(int count) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            Shop shop = new Shop();
            shop.setTitle("Shop #" + (i + 1));
            shop.setCoord("Coord #" + (i + 1));
            shop.setOwner("Owner #" + (i + 1));
            shop.setImageUrl("http://lorempixel.com/200/200/?" + rand.nextInt());

            this.mShops.add(shop);
        }
    }

    public List<Shop> getShops() {
        return this.mShops;
    }
}
