package com.muravyovdmitr.shoplocator.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.fragment.CreateOwnerFragment;
import com.muravyovdmitr.shoplocator.util.ImageLoader;

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

    private View.OnClickListener mItemClick = new View.OnClickListener() {
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

    private View.OnLongClickListener mItemLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.base_list_delete_dialog_title)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShopFactory.getInstance(mContext).deleteOwner(mOwner);

                            if (mOnOwnerRemove != null) {
                                mOnOwnerRemove.removeItem(getAdapterPosition());
                            }
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
        //TODO implement owner shops list
        this.mOwnerShops.setText("Implement owner shops here");
        ImageLoader.loadBitmapByUrl(this.mContext, owner.getImageUrl(), this.mOwnerImage);
    }

    @Override
    public void setOnItemRemove(IOnItemRemove ownerRemove) {
        this.mOnOwnerRemove = ownerRemove;
    }
}