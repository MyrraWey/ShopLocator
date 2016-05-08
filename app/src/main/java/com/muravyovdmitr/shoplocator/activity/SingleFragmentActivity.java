package com.muravyovdmitr.shoplocator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.activity.strategy.ISingleFragmentActivityStrategy;

/**
 * Created by MyrraWey on 02.05.2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected NavigationView mNavigationView;
    protected DrawerLayout mDrawer;

    protected ISingleFragmentActivityStrategy mLoadingStrategy;

    protected NavigationView.OnNavigationItemSelectedListener mOnNavBarItemSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
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
                    intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
            }

            mDrawer.closeDrawer(GravityCompat.START);

            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        this.mLoadingStrategy = getLoadingStrategy();

        Toolbar toolbar = (Toolbar) findViewById(R.id.single_fragment_toolbar);
        setSupportActionBar(toolbar);

        this.mDrawer = (DrawerLayout) findViewById(R.id.single_fragment_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                this.mDrawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        toggle.syncState();

        this.mNavigationView = (NavigationView) findViewById(R.id.single_fragment_navigation);
        this.mNavigationView.setNavigationItemSelectedListener(this.mOnNavBarItemSelected);
        this.mNavigationView.setCheckedItem(this.mLoadingStrategy.getSelectedByDefaultNavBarItem());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.single_fragment_fragment_container);

        if (fragment == null) {
            fragment = this.mLoadingStrategy.getFragment();

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

    protected abstract ISingleFragmentActivityStrategy getLoadingStrategy();
}
