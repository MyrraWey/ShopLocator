package com.muravyovdmitr.shoplocator.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.util.ImageLoader;

import java.util.List;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public class ShopsMapAdapter extends PagerAdapter {
    private Context mContext;
    private List<Shop> mShops;

    public ShopsMapAdapter(Context context, List<Shop> shops) {
        this.mContext = context;
        this.mShops = shops;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        Shop shop = this.mShops.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.holder_shops_list_item, collection, false);

        ((TextView) layout.findViewById(R.id.shops_list_item_title)).setText(shop.getTitle());
        ((TextView) layout.findViewById(R.id.shops_list_item_coord)).setText(shop.getCoord());
        ((TextView) layout.findViewById(R.id.shops_list_item_owner)).setText(shop.getOwner());
        ImageLoader.loadBitmapByUrl(
                this.mContext,
                shop.getImageUrl(),
                ((ImageView) layout.findViewById(R.id.shops_list_item_image)));

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mShops.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}