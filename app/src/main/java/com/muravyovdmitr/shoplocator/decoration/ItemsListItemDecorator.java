package com.muravyovdmitr.shoplocator.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class ItemsListItemDecorator extends RecyclerView.ItemDecoration {
    private final int mVerticalSpaceHeight = 16;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}
