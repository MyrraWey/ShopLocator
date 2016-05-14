package com.muravyovdmitr.shoplocator.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.database.ShopsDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.CreateOwnerFragment;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.ShopLocatorApplication;
import com.muravyovdmitr.shoplocator.util.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class OwnersListHolder extends BaseListHolder<Owner> {
    private Owner mOwner;
    private Context mContext;
    private IOnItemRemove mOnOwnerRemove;

    private ImageView mOwnerImage;
    private TextView mOwnerName;
    private TextView mOwnerShops;

    private final IDataOperations mShopsData = new ShopsDatabaseWrapper(
            ShopLocatorApplication.getInstance().getApplicationContext()
    );

    private OnClickListener mItemClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(
                            R.id.single_fragment_fragment_container,
                            CreateOwnerFragment.newInstance(mOwner.getId())
                    )
                    .addToBackStack(null)
                    .commit();
        }
    };

    private OnLongClickListener mItemLongClick = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            //TODO add option for deleting all shops with owner
            Builder dialog = isOwnerCanBeDeleted() ?
                    getGrantedRemoveDialog() :
                    getDeniedRemoveDialog();
            dialog.show();

            return false;
        }
    };

    public OwnersListHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this.mItemClick);
        //TODO select items on long click and add Button "Delete" in the ActionBar when 1 or more items selected
        itemView.setOnLongClickListener(this.mItemLongClick);

        this.mContext = itemView.getContext();

        this.mOwnerImage = (ImageView) itemView.findViewById(R.id.owners_list_item_image);
        this.mOwnerName = (TextView) itemView.findViewById(R.id.owners_list_item_name);
        this.mOwnerShops = (TextView) itemView.findViewById(R.id.owners_list_item_owner_shops);
    }

    @Override
    public void bind(Owner owner) {
        this.mOwner = owner;

        this.mOwnerName.setText(owner.getName());

        String ownerShops = TextUtils.implode(getOwnerShops(mOwner.getId()), ", ");
        if (ownerShops.isEmpty()) {
            ownerShops = mContext.getResources().getString(R.string.activity_owners_list_empty_owner);
        }
        this.mOwnerShops.setText(ownerShops);

        ImageLoader.loadBitmapByUrl(this.mContext, owner.getImageUrl(), this.mOwnerImage);
    }

    @Override
    public void setOnItemRemove(IOnItemRemove ownerRemove) {
        this.mOnOwnerRemove = ownerRemove;
    }

    private List<String> getOwnerShops(UUID ownerId) {
        List<String> ownerShops = new ArrayList<>();

        List<Shop> shops = mShopsData.getItems();
        for (Shop shop : shops) {
            if (shop.getOwner().equals(ownerId)) {
                ownerShops.add(shop.getTitle());
            }
        }

        return ownerShops;
    }

    private Builder getGrantedRemoveDialog() {
        return new Builder(mContext)
                .setTitle(R.string.base_list_delete_dialog_title)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IDataOperations dataOperations = new OwnersDatabaseWrapper(mContext);
                        dataOperations.deleteItem(mOwner);

                        if (mOnOwnerRemove != null) {
                            mOnOwnerRemove.removeItem(getAdapterPosition());
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null);
    }

    private Builder getDeniedRemoveDialog() {
        return new Builder(mContext)
                .setTitle(R.string.owners_list_holder_has_shop)
                .setPositiveButton(android.R.string.yes, null);
    }

    private boolean isOwnerCanBeDeleted() {
        boolean result = true;

        List<Shop> shops = mShopsData.getItems();
        for (Shop shop : shops) {
            if (shop.getOwner().equals(mOwner.getId())) {
                result = false;

                break;
            }
        }

        return result;
    }
}