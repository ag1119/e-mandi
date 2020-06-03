package com.cstup.e_mandi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cstup.e_mandi.R;
import com.cstup.e_mandi.model.Get.State;

import java.util.ArrayList;

public class stateAdapter extends BaseAdapter {
    private ArrayList<State> states;
    private Context context;
    public stateAdapter(Context context , ArrayList<State> states){
        this.context = context;
        this.states = states;
    }
    @Override
    public int getCount() {
        return states.size();
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
        tv.setText(states.get(position).getName());
        return v;
    }
}
