package com.example.bestmoviescenes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Movie> {

    public ListAdapter(Context context, ArrayList<Movie> movieArrayList){
        super(context,R.layout.list_item,movieArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        LinearLayout bacgroudnImage = convertView.findViewById(R.id.bacgroundImg);
        ImageView title = convertView.findViewById(R.id.title);

        bacgroudnImage.setBackgroundResource(movie.backgroundImage);

        return convertView;
    }
}
