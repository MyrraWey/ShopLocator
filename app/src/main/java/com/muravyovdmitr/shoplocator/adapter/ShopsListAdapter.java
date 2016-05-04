package com.muravyovdmitr.shoplocator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.holder.ShopsListHolder;

import java.util.List;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ShopsListAdapter extends RecyclerView.Adapter<ShopsListHolder> {
    private List<Shop> mShops;

    private IOnShopRemove mOnShopRemove = new IOnShopRemove() {
        @Override
        public void removeShop(int position) {
            mShops.remove(position);
            notifyItemRemoved(position);
        }
    };

    public ShopsListAdapter(List<Shop> shops) {
        this.mShops = shops;
    }

    @Override
    public ShopsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_shops_list_item, parent, false);

        return new ShopsListHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopsListHolder holder, int position) {
        Shop shop = this.mShops.get(position);

        holder.setOnShopRemove(this.mOnShopRemove);
        holder.bind(shop);
    }

    @Override
    public int getItemCount() {
        return this.mShops.size();
    }
}