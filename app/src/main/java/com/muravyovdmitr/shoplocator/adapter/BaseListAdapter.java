package com.muravyovdmitr.shoplocator.adapter;

import android.support.v7.widget.RecyclerView;

import com.muravyovdmitr.shoplocator.holder.BaseListHolder;

import java.util.List;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public abstract class BaseListAdapter<E, VH extends BaseListHolder> extends RecyclerView.Adapter<VH> {
    protected List<E> mItems;

    protected IOnItemRemove mOnItemRemove = new IOnItemRemove() {
        @Override
        public void removeItem(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    };

    public BaseListAdapter(List<E> items) {
        this.mItems = items;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        E item = this.mItems.get(position);

        holder.setOnItemRemove(this.mOnItemRemove);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
}
