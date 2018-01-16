package com.ayetlaeufferzangui.freeyourstuff.List;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ayetlaeufferzangui.freeyourstuff.CreateItem.CreateItemActivity;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;

import java.io.Serializable;
import java.util.List;


public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private List<Item> listItem;

    private FloatingActionButton floatingActionButton;


    public static ListFragment newInstance(List<Item> listItem) {
        ListFragment listFragment = new ListFragment();

        Bundle args = new Bundle();

        args.putSerializable("listItem", (Serializable) listItem);
        listFragment.setArguments(args);

        return listFragment;
    }


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

        //get listItem from NavigationActivity
        listItem = (List<Item>) getArguments().getSerializable("listItem");

        //specify an adapter
        adapter = new ListAdapter(listItem, getContext());
        recyclerView.setAdapter(adapter);



        //Button
        floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), CreateItemActivity.class);
                startActivity(intent);
            }
        });
    }

}
