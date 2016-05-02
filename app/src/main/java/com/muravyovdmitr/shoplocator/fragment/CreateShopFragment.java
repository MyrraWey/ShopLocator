package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.util.Utils;

import java.util.Random;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class CreateShopFragment extends Fragment {
    private Shop mShop;

    private ImageView mShopImage;
    private EditText mImageUrl;
    private TextInputLayout mImageUrlLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_shop, container, false);

        this.mShop = new Shop();
        Random rand = new Random();
        this.mShop.setImageUrl("http://lorempixel.com/200/200/?" + rand.nextInt());

        this.mShopImage = (ImageView) view.findViewById(R.id.fragment_create_shop_image);
        this.mImageUrl = (EditText) view.findViewById(R.id.fragment_create_shop_image_url);
        this.mImageUrlLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_image_url_layout);

        this.mImageUrl.setText(this.mShop.getImageUrl());
        this.mImageUrl.setEnabled(false);

        Utils.loadBitmapByUrl(getContext(), this.mShop.getImageUrl(), this.mShopImage);

        return view;


    }
}
