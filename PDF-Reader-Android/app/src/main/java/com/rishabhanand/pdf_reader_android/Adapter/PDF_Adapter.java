package com.rishabhanand.pdf_reader_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rishabhanand.pdf_reader_android.R;

import java.io.File;
import java.util.ArrayList;

public class PDF_Adapter extends ArrayAdapter<File> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<File> pdfarray;

    public PDF_Adapter(Context context, ArrayList<File> pdfarray) {
        super(context, R.layout.pdf_layout,pdfarray);
        this.context = context;
        this.pdfarray = pdfarray;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(pdfarray.size()>0) {
            return pdfarray.size();
        } else {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View convertView,ViewGroup parent) {

        if(convertView==null) {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.pdf_layout,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.tv_pdfname= (TextView)convertView.findViewById(R.id.tv_pdfname);

            convertView.setTag(viewHolder);
//            viewHolder.tv_pdfname = (TextView) viewHolder.findViewById()
        } else {
          viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_pdfname.setText(pdfarray.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        TextView tv_pdfname;
    }
}
