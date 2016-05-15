package com.muravyovdmitr.shoplocator.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.adapter.ShopsMapAdapter;
import com.muravyovdmitr.shoplocator.adapter.ShopsSearch;
import com.muravyovdmitr.shoplocator.data.DataWrapperFactory;
import com.muravyovdmitr.shoplocator.data.IDataOperations;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.MapStrategy;
import com.muravyovdmitr.shoplocator.util.TextUtils;

import java.util.List;

/**
 * Created by Dima Muravyov on 06.05.2016.
 */
public class MapFragment extends BaseFragment {
    private ViewPager mShopsPager;
    private AppCompatAutoCompleteTextView mSearch;

    private List<Shop> mShops;
    private ArrayAdapter mShopsSearchAdapter;
    private GoogleMap mGoogleMap;

    private final IDataOperations mShopsData = DataWrapperFactory.getShopsDataWrapper();

    private final OnMapReadyCallback mMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;

            if (mShops != null) {
                for (Shop shop : mShops) {
                    LatLng marker = TextUtils.getLatLngFromFormattedString(shop.getCoord());
                    mGoogleMap.addMarker(new MarkerOptions().position(marker).title(shop.getTitle()));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                }
            }

        }
    };

    private final OnPageChangeListener mShopsChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Shop shop = mShops.get(position);

            LatLng shopCoord = TextUtils.getLatLngFromFormattedString(shop.getCoord());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(shopCoord));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private final OnItemClickListener mShopsSearchItemClick = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LatLng shopCoord = TextUtils.getLatLngFromFormattedString(mShops.get(position).getCoord());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(shopCoord));
            mShopsPager.setCurrentItem(position);
        }
    };

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new MapStrategy();
    }

    @Override
    protected void findView(View view) {
        this.mShopsPager = (ViewPager) view.findViewById(R.id.fragment_map_pager);
        this.mSearch = (AppCompatAutoCompleteTextView) view.findViewById(R.id.fragment_map_search);
    }

    @Override
    protected void setupData() {
        super.setupData();

        this.mShops = mShopsData.getItems();

        initMap();

        this.mShopsPager.setAdapter(new ShopsMapAdapter(getContext(), this.mShops));
        this.mShopsPager.addOnPageChangeListener(mShopsChangeListener);

        mShopsSearchAdapter = new ShopsSearch(
                getContext(),
                R.layout.view_shops_search,
                mShops
        );
        mSearch.setAdapter(mShopsSearchAdapter);
        mSearch.setOnItemClickListener(mShopsSearchItemClick);
    }

    private void initMap() {
        SupportMapFragment supportMapFragment = new SupportMapFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_map_map, supportMapFragment)
                .commit();

        supportMapFragment.getMapAsync(mMapReadyCallback);
    }
}
