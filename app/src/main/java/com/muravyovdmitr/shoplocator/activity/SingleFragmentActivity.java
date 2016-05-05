package com.muravyovdmitr.shoplocator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.muravyovdmitr.shoplocator.R;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected NavigationView mNavigationView;
    protected DrawerLayout mDrawer;

    protected NavigationView.OnNavigationItemSelectedListener mOnNavBarItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            boolean result = true;
            Intent intent;

            switch (item.getItemId()) {
                case R.id.drawer_shops:
                    intent = new Intent(getApplicationContext(), ShopsListActivity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case R.id.drawer_owners:
                    intent = new Intent(getApplicationContext(), OwnersListActivity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case R.id.drawer_map:
                    Toast.makeText(getApplication(), "map", Toast.LENGTH_SHORT).show();
                    break;
            }

            if (result) {
                mDrawer.closeDrawer(GravityCompat.START);
            }

            return result;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        this.mDrawer = (DrawerLayout) findViewById(R.id.single_fragment_drawer);
        this.mNavigationView = (NavigationView) findViewById(R.id.single_fragment_nav_view);

        this.mNavigationView.setNavigationItemSelectedListener(this.mOnNavBarItemSelected);
        this.mNavigationView.setCheckedItem(getDefaultNavBarItem());

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

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected abstract Fragment getFragment();

    protected abstract int getDefaultNavBarItem();
}
