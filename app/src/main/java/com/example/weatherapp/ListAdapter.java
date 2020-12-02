package com.example.weatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Weather> weatherList;

    public ListAdapter(Activity mContext,List<Weather> weatherList){
        super(mContext,R.layout.list_item,weatherList);
        this.mContext=mContext;
        this.weatherList=weatherList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null
        ,true);

        TextView tvCountry=listItemView.findViewById(R.id.tvCountry);
        TextView tvTemp=listItemView.findViewById(R.id.tvTemp);
        TextView tvFeels=listItemView.findViewById(R.id.tvFeels);

        Weather weather=weatherList.get(position);
        tvCountry.setText(weather.getCitysName());
        tvTemp.setText(weather.getTemparature());
        tvFeels.setText(weather.getFeeling());

        return listItemView;
    }
}
