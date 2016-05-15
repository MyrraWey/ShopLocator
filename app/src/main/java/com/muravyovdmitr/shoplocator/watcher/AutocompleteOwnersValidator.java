package com.muravyovdmitr.shoplocator.watcher;

import android.support.design.widget.TextInputLayout;

import com.muravyovdmitr.shoplocator.data.DataWrapperFactory;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;

import java.util.List;

/**
 * Created by Dima Muravyov on 07.05.2016.
 */
public class AutocompleteOwnersValidator extends ValidateWatcher {
    final IDataOperations mOwnersData = DataWrapperFactory.getOwnersDataWrapper();

    public AutocompleteOwnersValidator(TextInputLayout layout, String errorText) {
        super(layout, errorText);
    }

    @Override
    public boolean isValid(String text) {
        boolean isValid = false;

        List<Owner> owners = mOwnersData.getItems();

        for (Owner owner : owners) {
            if (owner.getName().equals(text)) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }
}
