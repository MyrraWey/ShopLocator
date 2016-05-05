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
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.KeyboardManager;
import com.muravyovdmitr.shoplocator.watcher.SingleTextWatcher;

import java.util.Random;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class CreateOwnerFragment extends BaseFragment {
    private static final String LOADED_OWNER_ID = "loadedOwnerId";

    private Owner mOwner;
    private boolean mLoaded;
    private TextInputLayout[] mValidationFields;

    private ImageView mOwnerImage;
    private EditText mImageUrl;
    private EditText mOwnerName;
    private TextInputLayout mImageUrlLayout;
    private TextInputLayout mOwnerNameLayout;
    private Button mSaveOwner;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearLayoutErrors(mValidationFields);
            KeyboardManager.hideKeyboard(getActivity());

            if (!validateDataFields(mValidationFields)) {
                Toast.makeText(
                        getContext(),
                        getActivity().getResources().getString(R.string.fragment_create_owner_error_message),
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            mOwner.setName(mOwnerName.getText().toString());

            if (mLoaded) {
                ShopFactory.getInstance(getContext()).updateOwner(mOwner);
            } else {
                ShopFactory.getInstance(getContext()).addOwner(mOwner);
            }

            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    public static CreateOwnerFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(LOADED_OWNER_ID, id);

        CreateOwnerFragment fragment = new CreateOwnerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        UUID id = (args != null) ? (UUID) args.getSerializable(LOADED_OWNER_ID) : null;
        if (id != null) {
            this.mLoaded = true;

            this.mOwner = ShopFactory.getInstance(getContext()).getOwner(id);
        } else {
            this.mLoaded = false;

            this.mOwner = new Owner();
            Random rand = new Random();
            this.mOwner.setImageUrl("http://lorempixel.com/200/200/people?" + rand.nextInt());
        }
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_create_owner;
    }

    @Override
    protected void findView(View view) {
        this.mOwnerImage = (ImageView) view.findViewById(R.id.fragment_create_owner_image);
        this.mImageUrl = (EditText) view.findViewById(R.id.fragment_create_owner_image_url);
        this.mOwnerName = (EditText) view.findViewById(R.id.fragment_create_owner_name);
        this.mImageUrlLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_owner_image_url_layout);
        this.mOwnerNameLayout = (TextInputLayout) view.findViewById(R.id.fragment_create_owner_name_layout);
        this.mSaveOwner = (Button) view.findViewById(R.id.fragment_create_owner_save);
    }

    @Override
    protected void setupData() {
        this.mValidationFields = new TextInputLayout[]{
                mImageUrlLayout,
                mOwnerNameLayout
        };

        this.mImageUrl.setText(this.mOwner.getImageUrl());
        this.mImageUrl.setEnabled(this.mLoaded);
        this.mImageUrl.addTextChangedListener(
                new SingleTextWatcher(this.mImageUrlLayout, getActivity().getResources().getString(R.string.fragment_create_owner_image_url_error))
        );

        this.mOwnerName.setText(this.mOwner.getName());
        this.mOwnerName.addTextChangedListener(
                new SingleTextWatcher(this.mOwnerNameLayout, getActivity().getResources().getString(R.string.fragment_create_owner_name_error))
        );

        ImageLoader.loadBitmapByUrl(getContext(), this.mOwner.getImageUrl(), this.mOwnerImage);

        this.mSaveOwner.setOnClickListener(this.mClickListener);
        this.mSaveOwner.setText(this.mLoaded ? getResources().getString(R.string.fragment_create_owner_changes) : getResources().getString(R.string.fragment_create_owner_save));
    }

    @Override
    protected int getMenuResource() {
        return R.menu.fragment_create_owner_menu;
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
