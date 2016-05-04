package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.KeyboardManager;
import com.muravyovdmitr.shoplocator.watcher.SingleTextWatcher;

import java.util.Random;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class CreateShopFragment extends BaseFragment {
    private static final String LOADED_SHOP_ID = "loadedShopId";

    private Shop mShop;
    private boolean mLoaded;
    private TextInputLayout[] mValidationFields;

    private ImageView mShopImage;
    private EditText mImageUrl;
    private EditText mShopTitle;
    private EditText mShopCoord;
    private EditText mShopOwner;
    private TextInputLayout mImageUrlLayout;
    private TextInputLayout mShopTitleLayout;
    private TextInputLayout mShopCoordLayout;
    private TextInputLayout mShopOwnerLayout;
    private Button mSaveShop;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearLayoutErrors(mValidationFields);
            KeyboardManager.hideKeyboard(getActivity());

            if (!validateDataFields(mValidationFields)) {
                Toast.makeText(
                        getContext(),
                        getActivity().getResources().getString(R.string.fragment_create_shop_error_message),
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            mShop.setOwner(mShopOwner.getText().toString());
            mShop.setTitle(mShopTitle.getText().toString());
            mShop.setCoord(mShopCoord.getText().toString());

            if(mLoaded) {
                ShopFactory.getInstance(getContext()).updateShop(mShop);
            } else {
                ShopFactory.getInstance(getContext()).addShop(mShop);
            }

            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    public static CreateShopFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(LOADED_SHOP_ID, id);

        CreateShopFragment fragment = new CreateShopFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        UUID id = (args != null) ? (UUID) args.getSerializable(LOADED_SHOP_ID) : null;
        if(id != null) {
            this.mLoaded = true;

            this.mShop = ShopFactory.getInstance(getContext()).getShop(id);
        } else {
            this.mLoaded = false;

            this.mShop = new Shop();
            Random rand = new Random();
            this.mShop.setImageUrl("http://lorempixel.com/200/200/?" + rand.nextInt());
        }
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_create_shop;
    }

    @Override
    protected void findView(View view) {
        this.mShopImage = (ImageView) view.findViewById(R.id.fragment_create_shop_image);
        this.mImageUrl = (EditText) view.findViewById(R.id.fragment_create_shop_image_url);
        this.mShopTitle = (EditText) view.findViewById(R.id.fragment_create_shop_title);
        this.mShopCoord = (EditText) view.findViewById(R.id.fragment_create_shop_coord);
        this.mShopOwner = (EditText) view.findViewById(R.id.fragment_create_shop_owner);
        this.mImageUrlLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_image_url_layout);
        this.mShopTitleLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_title_layout);
        this.mShopCoordLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_coord_layout);
        this.mShopOwnerLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_owner_layout);
        this.mSaveShop = (Button) view.findViewById(R.id.fragment_create_shop_save);
    }

    @Override
    protected void setupData() {
        this.mValidationFields = new TextInputLayout[]{
                mImageUrlLayout,
                mShopCoordLayout,
                mShopOwnerLayout,
                mShopTitleLayout
        };

        this.mImageUrl.setText(this.mShop.getImageUrl());
        this.mImageUrl.setEnabled(this.mLoaded);
        this.mImageUrl.addTextChangedListener(
                new SingleTextWatcher(this.mImageUrlLayout, getActivity().getResources().getString(R.string.fragment_create_shop_image_url_error))
        );

        this.mShopTitle.setText(this.mShop.getTitle());
        this.mShopTitle.addTextChangedListener(
                new SingleTextWatcher(this.mShopTitleLayout, getActivity().getResources().getString(R.string.fragment_create_shop_title_error))
        );

        this.mShopOwner.setText(this.mShop.getOwner());
        this.mShopOwner.addTextChangedListener(
                new SingleTextWatcher(this.mShopOwnerLayout, getActivity().getResources().getString(R.string.fragment_create_shop_owner_error))
        );

        this.mShopCoord.setText(this.mShop.getCoord());
        this.mShopCoord.addTextChangedListener(
                new SingleTextWatcher(this.mShopCoordLayout, getActivity().getResources().getString(R.string.fragment_create_shop_coord_error))
        );

        ImageLoader.loadBitmapByUrl(getContext(), this.mShop.getImageUrl(), this.mShopImage);

        this.mSaveShop.setOnClickListener(this.mClickListener);
        this.mSaveShop.setText(this.mLoaded ? getResources().getString(R.string.fragment_create_shop_changes) : getResources().getString(R.string.fragment_create_shop_save));
    }

    @Override
    protected int getMenuResource() {
        return R.menu.fragment_crate_shop_menu;
    }

    private boolean validateDataFields(TextInputLayout... textInputLayouts) {
        boolean correct = true;

        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editText = textInputLayout.getEditText().getText().toString();
            if (!SingleTextWatcher.isValid(editText)) {
                correct = false;

                break;
            }
        }

        return correct;
    }

    private void clearLayoutErrors(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {
            textInputLayout.setErrorEnabled(false);
        }
    }
}
