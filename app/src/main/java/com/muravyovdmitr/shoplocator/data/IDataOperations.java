package com.muravyovdmitr.shoplocator.data;

import android.content.ContentValues;

import java.util.List;
import java.util.UUID;

/**
 * Created by Dima Muravyov on 09.05.2016.
 */
public interface IDataOperations<E> {

    List<E> getItems();

    E getItem(UUID id);

    void addItem(E item);

    void updateItem(E item);

    void deleteItem(E item);
}
