package com.cstup.e_mandi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.City;

import java.util.ArrayList;

public class cityAdapter extends BaseAdapter {

    private  Context context;
    private ArrayList<City> cities;
    public cityAdapter(Context context , ArrayList<City> cities){
        this.context = context;
        this.cities = cities;
    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.cust_spinner_view , null , true);
        TextView tv = v.findViewById(R.id.tv);
        tv.setText(cities.get(position).getName());
        return v;
    }
}
