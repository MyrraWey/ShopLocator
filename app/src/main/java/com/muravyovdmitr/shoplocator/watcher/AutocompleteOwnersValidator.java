package com.muravyovdmitr.shoplocator.watcher;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;

import java.util.List;

/**
 * Created by Dima Muravyov on 07.05.2016.
 */
public class AutocompleteOwnersValidator extends ValidateWatcher {
    public AutocompleteOwnersValidator(TextInputLayout layout, String errorText) {
        super(layout, errorText);
    }

    @Override
    public boolean isValid(String text) {
        Context context = mLayout.getContext();
        boolean isValid = false;

        IDataOperations ownersSource = new OwnersDatabaseWrapper(context);
        List<Owner> owners = ownersSource.getItems();

        for (Owner owner : owners) {
            if (owner.getName().equals(text)) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }
}
