package com.muravyovdmitr.shoplocator.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public abstract class BaseListHolder<E> extends RecyclerView.ViewHolder implements IBaseListHolder<E> {
    public BaseListHolder(View itemView) {
        super(itemView);
    }
}
