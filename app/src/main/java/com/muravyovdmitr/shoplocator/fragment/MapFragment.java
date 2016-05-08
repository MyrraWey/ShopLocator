package com.muravyovdmitr.shoplocator.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.data.ShopFactory;
import com.muravyovdmitr.shoplocator.fragment.strategy.IBaseFragmentStrategy;
import com.muravyovdmitr.shoplocator.fragment.strategy.MapStrategy;
import com.muravyovdmitr.shoplocator.util.ImageLoader;
import com.muravyovdmitr.shoplocator.util.TextUtils;

import java.util.List;

/**
 * Created by Dima Muravyov on 06.05.2016.
 */
public class MapFragment extends BaseFragment {
    private ViewPager mShopsPager;

    private List<Shop> mShops;
    private GoogleMap mGoogleMap;

    private OnMapReadyCallback mMapReadyCallback = new OnMapReadyCallback() {
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

    @Override
    protected IBaseFragmentStrategy getLoadingStrategy() {
        return new MapStrategy();
    }

    @Override
    protected void findView(View view) {
        this.mShopsPager = (ViewPager) view.findViewById(R.id.fragment_map_pager);
    }

    @Override
    protected void setupData() {
        super.setupData();

        this.mShops = ShopFactory.getInstance(getContext()).getShops();

        initMap();

        this.mShopsPager.setAdapter(new CustomPagerAdapter(getContext(), this.mShops));
        this.mShopsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
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

    // +++++++++++++++++++++++++++

    public class CustomPagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<Shop> mShops;

        public CustomPagerAdapter(Context context, List<Shop> shops) {
            this.mContext = context;
            this.mShops = shops;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            Shop shop = this.mShops.get(position);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.holder_shops_list_item, collection, false);

            ((TextView) layout.findViewById(R.id.shops_list_item_title)).setText(shop.getTitle());
            ((TextView) layout.findViewById(R.id.shops_list_item_coord)).setText(shop.getCoord());
            ((TextView) layout.findViewById(R.id.shops_list_item_owner)).setText(shop.getOwner());
            ImageLoader.loadBitmapByUrl(
                    this.mContext,
                    shop.getImageUrl(),
                    ((ImageView) layout.findViewById(R.id.shops_list_item_image)));

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return mShops.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
