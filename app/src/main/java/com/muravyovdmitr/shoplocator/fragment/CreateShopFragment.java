package com.muravyovdmitr.shoplocator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.OwnersAutocomplete;
import com.muravyovdmitr.shoplocator.data.DataWrapperFactory;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.fragment.strategy.CreateShopStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.KeyboardManager;
import com.muravyovdmitr.shoplocator.watcher.AutocompleteOwnersValidator;
import com.muravyovdmitr.shoplocator.watcher.ITextValidator;
import com.muravyovdmitr.shoplocator.watcher.LocationWatcher;
import com.muravyovdmitr.shoplocator.watcher.SingleTextWatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class CreateShopFragment extends BaseFragment {
    private static final String LOADED_SHOP_ID = "loadedShopId";

    private ImageView mShopImage;
    private EditText mImageUrl;
    private EditText mShopTitle;
    private EditText mShopCoord;
    private AppCompatAutoCompleteTextView mShopOwner;
    private TextInputLayout mImageUrlLayout;
    private TextInputLayout mShopTitleLayout;
    private TextInputLayout mShopCoordLayout;
    private TextInputLayout mShopOwnerLayout;
    private Button mSaveShop;

    private Shop mShop;
    private int mAutocompleteItem;
    private boolean mLoaded;
    private OwnersAutocomplete mOwnersAutocompleteAdapter;

    private final IDataOperations mShopsData = DataWrapperFactory.getShopsDataWrapper();
    private final IDataOperations mOwnersData = DataWrapperFactory.getOwnersDataWrapper();

    private final AdapterView.OnItemClickListener mAutocompleteItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mAutocompleteItem = position;
        }
    };

    private final OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            clearLayoutErrors();
            KeyboardManager.hideKeyboard(getActivity());

            if (!validateDataFields()) {
                Toast.makeText(
                        getContext(),
                        getActivity().getResources().getString(R.string.fragment_create_shop_error_message),
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            mShop.setOwner(mOwnersAutocompleteAdapter.getOwnerId(mAutocompleteItem));
            mShop.setTitle(mShopTitle.getText().toString());
            mShop.setCoord(mShopCoord.getText().toString());

            if (mLoaded) {
                mShopsData.updateItem(mShop);
            } else {
                mShopsData.addItem(mShop);
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
        if (id != null) {
            this.mLoaded = true;

            this.mShop = (Shop) this.mShopsData.getItem(id);
        } else {
            this.mLoaded = false;

            this.mShop = new Shop();
            Random rand = new Random();
            this.mShop.setImageUrl("http://lorempixel.com/200/200/city?" + rand.nextInt());
        }
    }

    @Override
    protected void findView(View view) {
        this.mShopImage = (ImageView) view.findViewById(R.id.fragment_create_shop_image);
        this.mImageUrl = (EditText) view.findViewById(R.id.fragment_create_shop_image_url);
        this.mShopTitle = (EditText) view.findViewById(R.id.fragment_create_shop_title);
        this.mShopCoord = (EditText) view.findViewById(R.id.fragment_create_shop_coord);
        this.mShopOwner = (AppCompatAutoCompleteTextView) view.findViewById(R.id.fragment_create_shop_owner);
        this.mImageUrlLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_image_url_layout);
        this.mShopTitleLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_title_layout);
        this.mShopCoordLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_coord_layout);
        this.mShopOwnerLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_shop_owner_layout);
        this.mSaveShop = (Button) view.findViewById(R.id.fragment_create_shop_save);
    }

    @Override
    protected void setupData() {
        super.setupData();

        this.mImageUrl.setText(this.mShop.getImageUrl());
        this.mImageUrl.setEnabled(this.mLoaded);

        this.mShopTitle.setText(this.mShop.getTitle());

        if (mShop.getOwner() != null) {
            mShopOwner.setText(((Owner) mOwnersData.getItem(mShop.getOwner())).getName());
        }

        this.mOwnersAutocompleteAdapter = new OwnersAutocomplete(
                getContext(),
                R.layout.view_owners_autocomplete,
                getOwnersList()
        );
        this.mShopOwner.setAdapter(this.mOwnersAutocompleteAdapter);
        this.mShopOwner.setOnItemClickListener(mAutocompleteItemClickListener);

        this.mShopCoord.setText(this.mShop.getCoord());

        ImageLoader.loadBitmapByUrl(getContext(), this.mShop.getImageUrl(), this.mShopImage);

        this.mSaveShop.setOnClickListener(this.mClickListener);
        this.mSaveShop.setText(this.mLoaded ? getResources().getString(R.string.fragment_create_shop_changes) : getResources().getString(R.string.fragment_create_shop_save));
    }

    @Override
    protected Map<TextInputLayout, ITextValidator> getValidationMap() {
        Map<TextInputLayout, ITextValidator> map = new HashMap<>();
        map.put(
                mImageUrlLayout,
                new SingleTextWatcher(this.mImageUrlLayout, getActivity().getResources().getString(R.string.fragment_create_shop_image_url_error))
        );
        map.put(
                mShopTitleLayout,
                new SingleTextWatcher(this.mShopTitleLayout, getActivity().getResources().getString(R.string.fragment_create_shop_title_error))
        );
        map.put(
                mShopOwnerLayout,
                new AutocompleteOwnersValidator(this.mShopOwnerLayout, getActivity().getResources().getString(R.string.fragment_create_shop_owner_error))
        );
        map.put(
                mShopCoordLayout,
                new LocationWatcher(this.mShopCoordLayout, getActivity().getResources().getString(R.string.fragment_create_shop_coord_error))
        );

        return map;
    }

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new CreateShopStrategy();
    }

    protected List<Owner> getOwnersList() {
        return mOwnersData.getItems();
    }
}
