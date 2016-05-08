package com.muravyovdmitr.shoplocator.holder;

import com.muravyovdmitr.shoplocator.adapter.IOnItemRemove;

/**
 * Created by Dima Muravyov on 08.05.2016.
 */
public interface IBaseListHolder<E> {
    void bind(E item);

    void setOnItemRemove(IOnItemRemove onItemRemove);
}
