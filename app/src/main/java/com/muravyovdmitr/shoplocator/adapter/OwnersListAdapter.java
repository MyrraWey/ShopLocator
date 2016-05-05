package com.muravyovdmitr.shoplocator.adapter;

import android.support.v7.widget.RecyclerView;
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
public class OwnersListAdapter extends RecyclerView.Adapter<OwnersListHolder> {
    private List<Owner> mOwners;

    private IOnItemRemove mOnOwnerRemove = new IOnItemRemove() {
        @Override
        public void removeItem(int position) {
            mOwners.remove(position);
            notifyItemRemoved(position);
        }
    };

    public OwnersListAdapter(List<Owner> owners) {
        this.mOwners = owners;
    }

    @Override
    public OwnersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_owners_list_item, parent, false);

        return new OwnersListHolder(view);
    }

    @Override
    public void onBindViewHolder(OwnersListHolder holder, int position) {
        Owner owner = this.mOwners.get(position);

        holder.setOnOwnerRemove(this.mOnOwnerRemove);
        holder.bind(owner);
    }

    @Override
    public int getItemCount() {
        return this.mOwners.size();
    }
}