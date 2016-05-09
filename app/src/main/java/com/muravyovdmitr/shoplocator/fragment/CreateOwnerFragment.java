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
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.database.OwnersDatabaseWrapper;
import com.muravyovdmitr.shoplocator.fragment.strategy.CreateOwnerStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.KeyboardManager;
import com.muravyovdmitr.shoplocator.watcher.ITextValidator;
import com.muravyovdmitr.shoplocator.watcher.SingleTextWatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public class CreateOwnerFragment extends BaseFragment {
    private static final String LOADED_OWNER_ID = "loadedOwnerId";

    private Owner mOwner;
    private boolean mLoaded;
    private IDataOperations mDataOperations;

    private ImageView mOwnerImage;
    private EditText mImageUrl;
    private EditText mOwnerName;
    private TextInputLayout mImageUrlLayout;
    private TextInputLayout mOwnerNameLayout;
    private Button mSaveOwner;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearLayoutErrors();
            KeyboardManager.hideKeyboard(getActivity());

            if (!validateDataFields()) {
                Toast.makeText(
                        getContext(),
                        getActivity().getResources().getString(R.string.fragment_create_owner_error_message),
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            mOwner.setName(mOwnerName.getText().toString());

            if (mLoaded) {
                mDataOperations.updateItem(mOwner);
            } else {
                mDataOperations.addItem(mOwner);
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

        this.mDataOperations = new OwnersDatabaseWrapper(getContext());

        Bundle args = this.getArguments();
        UUID id = (args != null) ? (UUID) args.getSerializable(LOADED_OWNER_ID) : null;
        if (id != null) {
            this.mLoaded = true;

            this.mOwner = (Owner) this.mDataOperations.getItem(id);
        } else {
            this.mLoaded = false;

            this.mOwner = new Owner();
            Random rand = new Random();
            this.mOwner.setImageUrl("http://lorempixel.com/200/200/people?" + rand.nextInt());
        }
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
        super.setupData();

        this.mImageUrl.setText(this.mOwner.getImageUrl());
        this.mImageUrl.setEnabled(this.mLoaded);

        this.mOwnerName.setText(this.mOwner.getName());

        ImageLoader.loadBitmapByUrl(getContext(), this.mOwner.getImageUrl(), this.mOwnerImage);

        this.mSaveOwner.setOnClickListener(this.mClickListener);
        this.mSaveOwner.setText(this.mLoaded ? getResources().getString(R.string.fragment_create_owner_changes) : getResources().getString(R.string.fragment_create_owner_save));
    }

    @Override
    protected Map<TextInputLayout, ITextValidator> getValidationMap() {
        Map<TextInputLayout, ITextValidator> map = new HashMap<>();
        map.put(
                this.mImageUrlLayout,
                new SingleTextWatcher(this.mImageUrlLayout, getActivity().getResources().getString(R.string.fragment_create_owner_image_url_error))
        );
        map.put(
                this.mOwnerNameLayout,
                new SingleTextWatcher(this.mOwnerNameLayout, getActivity().getResources().getString(R.string.fragment_create_owner_name_error))
        );

        return map;
    }

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new CreateOwnerStrategy();
    }
}
