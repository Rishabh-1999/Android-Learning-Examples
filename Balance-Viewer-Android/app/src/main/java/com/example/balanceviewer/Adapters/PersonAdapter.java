package com.example.balanceviewer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.balanceviewer.Class.Person;
import com.example.balanceviewer.DB.PersonDB;
import com.example.balanceviewer.R;

import java.util.ArrayList;

/* Made by Rishabh Anand */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    ArrayList<Person> people;
    ItemClicked activity;

    public interface  ItemClicked {
        void onItemClicked(int index);
        void notifyDataChange();
        void changeActionBar();
        void showToast(String message);
    }

    public PersonAdapter (Context context,ArrayList<Person> list) {
        activity=(ItemClicked)context;
        people=list;
    }

    //Main method where action are performed on list frag like on click change in detail frag and delete button
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name=tvName.getText().toString();
                    PersonDB db=new PersonDB((Context) activity);
                    db.open();
                    activity.onItemClicked(db.find_index(name));
                    activity.changeActionBar();
                    db.close();
                }
            });
        }
    }

    // Method to  create our own view holder
    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
        return new ViewHolder(view);
    }

    // Method to create view holder data
    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(people.get(i));
        viewHolder.tvName.setText(people.get(i).getName());
    }

    //Method to get count od object of person in an array
    @Override
    public int getItemCount() {
        return people.size();
    }
}

