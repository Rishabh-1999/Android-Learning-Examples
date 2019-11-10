package com.example.risha.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter{
    //inheritance
    Context context;
    ArrayList<Data> list;
    LayoutInflater inflater;

    CustomAdapter(Context context, ArrayList list){
        this.context = context;
        this.list=list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row,viewGroup,false);
        TextView  name = (TextView)view.findViewById(R.id.name);
        TextView number = view.findViewById(R.id.number);
        name.setText(list.get(i).getName());
        number.setText(list.get(i).getNumber());
        return view;
    }
}
