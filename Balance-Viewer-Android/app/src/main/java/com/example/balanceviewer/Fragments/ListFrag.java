package com.example.balanceviewer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.balanceviewer.Adapters.PersonAdapter;
import com.example.balanceviewer.DB.PersonDB;
import com.example.balanceviewer.R;

/* Made by Rishabh Anand */

public class ListFrag extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    public ListFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        PersonDB db= new PersonDB(this.getActivity());
        db.open();
        myAdapter = new PersonAdapter(this.getActivity(),db.getArray() );
        db.close();
        recyclerView.setAdapter(myAdapter);
    }

    public void notifyDataChanged() {
        PersonDB db= new PersonDB(this.getActivity());
        db.open();
        myAdapter = new PersonAdapter(this.getActivity(),db.getArray() );
        db.close();
        recyclerView.setAdapter(myAdapter);
    }
}