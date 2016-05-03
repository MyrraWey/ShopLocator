package com.muravyovdmitr.shoplocator.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.util.ImageLoader;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListHolder extends RecyclerView.ViewHolder {
    private Shop mShop;
    private Context mContext;

    private ImageView mShopImage;
    private TextView mShopTitle;
    private TextView mShopCoord;
    private TextView mShopOwner;

    public ShopsListHolder(View itemView) {
        super(itemView);

        this.mContext = itemView.getContext();

        this.mShopImage = (ImageView) itemView.findViewById(R.id.shops_list_item_image);
        this.mShopTitle = (TextView) itemView.findViewById(R.id.shops_list_item_title);
        this.mShopCoord = (TextView) itemView.findViewById(R.id.shops_list_item_coord);
        this.mShopOwner = (TextView) itemView.findViewById(R.id.shops_list_item_owner);
    }

    public void bind(Shop shop) {
        this.mShop = shop;

        this.mShopTitle.setText(shop.getTitle());
        this.mShopCoord.setText(shop.getCoord());
        this.mShopOwner.setText(shop.getOwner());
        ImageLoader.loadBitmapByUrl(this.mContext, shop.getImageUrl(), this.mShopImage);
    }
}
