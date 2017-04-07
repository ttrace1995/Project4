package com.proj4.project4;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by traceys5 on 4/5/17.
 */
public class ListAdapter extends ArrayAdapter<BikeData> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, ArrayList<BikeData> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflate;
            inflate = LayoutInflater.from(getContext());
            view = inflate.inflate(R.layout.listview_row_layout, null);
        }

        BikeData bike = getItem(position);

        if (bike != null) {

            ImageView Image = (ImageView) view.findViewById(R.id.imageView1);
            TextView Model = (TextView) view.findViewById(R.id.Model);
            TextView Price = (TextView) view.findViewById(R.id.Price);
            TextView Description = (TextView) view.findViewById(R.id.Description);

            if (Image != null) {
                new DownloadImage(Image).execute("http://www.tetonsoftware.com/bikes/"+ bike.getPictureJPEG());
            }
            if (Model != null) {
                Model.setText(bike.getModel());
            }
            if (Price != null) {
                Price.setText(String.valueOf(bike.getPrice()));
            }
            if (Description != null) {
                Description.setText(bike.getDescription());
            }
        }
        return view;
    }
}