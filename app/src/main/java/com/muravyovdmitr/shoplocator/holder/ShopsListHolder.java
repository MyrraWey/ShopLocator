package com.muravyovdmitr.shoplocator.holder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.fragment.CreateShopFragment;
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

    private View.OnClickListener mItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(
                            R.id.single_fragment_fragment_container,
                            CreateShopFragment.newInstance(mShop.getID())
                    )
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnLongClickListener mItemLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.shops_list_delete_dialog_title)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO how to update adapter from here
                            ShopFactory.getInstance(mContext).deleteShop(mShop);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // nothing to do here
                        }
                    })
                    .show();

            return false;
        }
    };

    public ShopsListHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this.mItemClick);
        //TODO select items on long click and add Button "Delete" in the ActionBar when 1 or more items selected
        itemView.setOnLongClickListener(this.mItemLongClick);

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
