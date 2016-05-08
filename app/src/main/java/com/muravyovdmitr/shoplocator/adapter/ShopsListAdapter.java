package com.muravyovdmitr.shoplocator.adapter;

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
public class ShopsListAdapter extends BaseListAdapter<Shop, ShopsListHolder> {

    public ShopsListAdapter(List<Shop> items) {
        super(items);
    }

    @Override
    public ShopsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_shops_list_item, parent, false);

        return new ShopsListHolder(view);
    }
}