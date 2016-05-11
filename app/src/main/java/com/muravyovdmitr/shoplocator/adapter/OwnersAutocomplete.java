package com.muravyovdmitr.shoplocator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.muravyovdmitr.shoplocator.R;
import com.muravyovdmitr.shoplocator.data.Owner;
import com.muravyovdmitr.shoplocator.util.ImageLoader;

import java.util.List;

/**
 * Created by Dima Muravyov on 11.05.2016.
 */
public class OwnersAutocomplete extends ArrayAdapter<Owner> {
    private List<Owner> mItems;
    private Context mContext;
    private int mResource;

    public OwnersAutocomplete(Context context, int resource, List<Owner> objects) {
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

        Owner owner = this.mItems.get(position);

        ImageView image = (ImageView) view.findViewById(R.id.owners_autocomplete_image);
        TextView name = (TextView) view.findViewById(R.id.owners_list_item_name);

        ImageLoader.loadBitmapByUrl(this.mContext, owner.getImageUrl(), image);
        name.setText(owner.getName());

        return view;
    }
}
