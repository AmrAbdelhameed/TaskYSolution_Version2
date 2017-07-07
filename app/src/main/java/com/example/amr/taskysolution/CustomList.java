package com.example.amr.taskysolution;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Sample> {

    private ArrayList<Sample> data_array;

    private Activity context;

    public CustomList(Activity context, ArrayList<Sample> data_array) {
        super(context, R.layout.list_view_layout, data_array);
        this.context = context;
        this.data_array = data_array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);

        TextView textViewId = (TextView) listViewItem.findViewById(R.id.title);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.published);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imagevi);

        textViewId.setText(data_array.get(position).getTitle());
        textViewName.setText(data_array.get(position).getPublished_date());
        Picasso.with(context).load(data_array.get(position).getUrl()).into(imageView);

        return listViewItem;
    }
}