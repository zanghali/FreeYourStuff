package com.ayetlaeufferzangui.freeyourstuff.List;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayetlaeufferzangui.freeyourstuff.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private static final String TAG = "ListAdapter";

    private List<ListRecyclerView> myDataSet;
    private ListAdapter adapter;

    private RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.list_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //add a separation between each item of the RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // set the data
        myDataSet=new ArrayList<>();
        myDataSet.add(new ListRecyclerView(
                "Ski",
                "https://images.evo.com/imgp/700/105894/460134/clone.jpg",
                4,
                2.0,
                "asap",
                "Sport"
                ));
        myDataSet.add(new ListRecyclerView(
                "Ski",
                "https://images.evo.com/imgp/700/105894/460134/clone.jpg",
                4,
                2.0,
                "asap",
                "Sport"
        ));

        adapter = new ListAdapter(myDataSet, getContext());
        recyclerView.setAdapter(adapter);


    }
}
