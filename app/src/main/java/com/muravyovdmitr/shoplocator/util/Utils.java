package com.muravyovdmitr.shoplocator.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.muravyovdmitr.shoplocator.R;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class Utils {
    public static final void loadBitmapByUrl(Context context, String targetUrl, ImageView targetView) {
        Glide.with(context)
                .load(targetUrl)
                .placeholder(R.drawable.loader)
                .into(targetView);
    }
}
