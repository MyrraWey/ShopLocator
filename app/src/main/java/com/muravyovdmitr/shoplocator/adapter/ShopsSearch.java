package com.muravyovdmitr.shoplocator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Shop;
import com.muravyovdmitr.shoplocator.util.ImageLoader;

import java.util.List;

/**
 * Created by Dima Muravyov on 11.05.2016.
 */
public class ShopsSearch extends ArrayAdapter<Shop> {
    private List<Shop> mItems;
    private Context mContext;
    private int mResource;

    public ShopsSearch(Context context, int resource, List<Shop> objects) {
        super(context, resource, objects);

        this.mItems = objects;
        this.mResource = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            view = inflater.inflate(this.mResource, parent, false);
        }

        Shop shop = this.mItems.get(position);

        ImageView image = (ImageView) view.findViewById(R.id.shops_search_image);
        TextView name = (TextView) view.findViewById(R.id.shops_search_title);
        TextView coord = (TextView) view.findViewById(R.id.shops_search_coord);

        ImageLoader.loadBitmapByUrl(this.mContext, shop.getImageUrl(), image);
        name.setText(shop.getTitle());
        coord.setText(shop.getCoord());

        return view;
    }
}
