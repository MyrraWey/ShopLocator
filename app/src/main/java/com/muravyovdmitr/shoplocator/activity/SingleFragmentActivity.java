package com.muravyovdmitr.shoplocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.muravyovdmitr.shoplocator.R;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.single_fragment_fragment_container);

        if (fragment == null) {
            fragment = getFragment();

            fragmentManager
                    .beginTransaction()
                    .add(R.id.single_fragment_fragment_container, fragment)
                    .commit();
        }
    }

    protected abstract Fragment getFragment();
}
