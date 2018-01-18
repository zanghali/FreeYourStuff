package com.ayetlaeufferzangui.freeyourstuff.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ayetlaeufferzangui.freeyourstuff.CreateItem.CreateItemActivity;
import com.ayetlaeufferzangui.freeyourstuff.Filter.FilterActivity;
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

    private EditText searchInput;
    private Button filterButton;


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

        searchInput = view.findViewById(R.id.searchEditText);
        filterButton = view.findViewById(R.id.filterButton);
        recyclerView = view.findViewById(R.id.list_recycler_view);
        floatingActionButton = view.findViewById(R.id.floating_action_button);

        //get listItem from NavigationActivity
        listItem = (List<Item>) getArguments().getSerializable("listItem");

        //Filter button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        //List Item (RecyclerView)
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //specify an adapter
        adapter = new ListAdapter(listItem, getContext());
        recyclerView.setAdapter(adapter);

        //Add button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user id from the SharedPreferences
                SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String defaultValue = getResources().getString(R.string.id_user_default);
                String id_user = sharedPref.getString(getString(R.string.id_user), defaultValue);

                if(id_user == defaultValue){
                    //TODO dialog alert "you need to login" redirect to login
                    Toast.makeText(getActivity(), getResources().getString(R.string.need_login), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), CreateItemActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

}
