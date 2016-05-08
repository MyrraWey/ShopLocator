package com.muravyovdmitr.shoplocator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.holder.OwnersListHolder;

import java.util.List;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class OwnersListAdapter extends BaseListAdapter<Owner, OwnersListHolder> {

    public OwnersListAdapter(List<Owner> items) {
        super(items);
    }

    @Override
    public OwnersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_owners_list_item, parent, false);

        return new OwnersListHolder(view);
    }
}