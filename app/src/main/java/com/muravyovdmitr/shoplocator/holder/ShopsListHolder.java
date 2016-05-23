package com.muravyovdmitr.shoplocator.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.data.DataWrapperFactory;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.fragment.CreateShopFragment;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.NetworkStateChecker;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListHolder extends BaseListHolder<Shop> {
    private ImageView mShopImage;
    private TextView mShopTitle;
    private TextView mShopCoord;
    private TextView mShopOwner;

    private Shop mShop;
    private Context mContext;
    private IOnItemRemove mOnShopRemove;

    private final IDataOperations mShopsData = DataWrapperFactory.getShopsDataWrapper();
    private final IDataOperations mOwnersData = DataWrapperFactory.getOwnersDataWrapper();

    private final OnClickListener mItemClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(
                            R.id.single_fragment_fragment_container,
                            CreateShopFragment.newInstance(mShop.getId())
                    )
                    .addToBackStack(null)
                    .commit();
        }
    };

    private final OnLongClickListener mItemLongClick = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder dialog = isShopCanBeDeleted() ?
                    getGrantedRemoveDialog() :
                    getDeniedRemoveDialog();
            dialog.show();

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

    @Override
    public void bind(Shop shop) {
        this.mShop = shop;
        String shopOwnerName = ((Owner) mOwnersData.getItem(shop.getOwner())).getName();

        this.mShopTitle.setText(shop.getTitle());
        this.mShopCoord.setText(shop.getCoord());
        this.mShopOwner.setText(shopOwnerName);
        ImageLoader.loadBitmapByUrl(this.mContext, shop.getImageUrl(), this.mShopImage);
    }

    @Override
    public void setOnItemRemove(IOnItemRemove shopRemove) {
        this.mOnShopRemove = shopRemove;
    }

    private AlertDialog.Builder getGrantedRemoveDialog() {
        return new AlertDialog.Builder(mContext)
                .setTitle(R.string.base_list_delete_dialog_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mShopsData.deleteItem(mShop);

                        if (mOnShopRemove != null) {
                            mOnShopRemove.removeItem(getAdapterPosition());
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null);
    }

    private AlertDialog.Builder getDeniedRemoveDialog() {
        return new AlertDialog.Builder(mContext)
                .setTitle(R.string.shops_list_holder_no_network)
                .setPositiveButton(android.R.string.yes, null);
    }

    private boolean isShopCanBeDeleted() {
        if (!NetworkStateChecker.isNetworkAvailable(mContext)) {
            return false;
        }

        return true;
    }
}