package com.ayetlaeufferzangui.freeyourstuff.List;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.CreateItem.CreateItemActivity;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.ViewItem.ViewItemActivity;
import com.ayetlaeufferzangui.freeyourstuff.ViewItem.ViewItemBisActivity;
import com.bumptech.glide.Glide;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    private List<ListRecyclerView> dataset;

    private final Context listFragmentContext;

    private RecyclerView mRecyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView nbOfInterestedPeople;
        public TextView distance;
        public TextView availability;
        public ImageView photo;


        public ViewHolder(View v) {
            super(v);

            title = v.findViewById(R.id.title);
            nbOfInterestedPeople = v.findViewById(R.id.nbOfInterestedPeople);
            distance = v.findViewById(R.id.distance);
            availability = v.findViewById(R.id.availability);
            photo = v.findViewById(R.id.photo);

        }

        //fill the cells with a parameter
        public void bind(ListRecyclerView mediaObject, Context listFragmentContext){
            title.setText(mediaObject.getTitle());
            nbOfInterestedPeople.setText(mediaObject.getNbOfInterestedPeople() + " persons interested");
            distance.setText(mediaObject.getDistance() + " km");
            availability.setText(mediaObject.getAvailability());
            Glide.with(listFragmentContext)
                    .load(mediaObject.getPhoto())
                    .into(photo);
        }

    }

    public ListAdapter(List<ListRecyclerView> dataset, Context listFragmentContext) {
        this.dataset = dataset;
        this.listFragmentContext=listFragmentContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list, parent, false);

        mRecyclerView = parent.findViewById(R.id.list_recycler_view);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO v1 => all the data are send to the ViewItemActivity
                //     v2 => only the idItem is send to the ViewItemActivity
                ////////////////////////
                ////////////////////////
//                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
//
//                ListRecyclerView item = dataset.get(itemPosition);
//                String idItem = item.getIdItem();
//                String category = item.getCategory();
//                String title = item.getTitle();
//                String description = "TODO";
//                String photo = "TODO";
//                String address = "TODO";
//                String phone = "TODO";
//                String status = "TODO";
//                String gps = "TODO";
//                String availability = "TODO";
//                String idUser = "TODO";
//
//                Intent intent = new Intent(v.getContext(), ViewItemBisActivity.class);
//                intent.putExtra("idItem", idItem);
//                intent.putExtra("category", category);
//                intent.putExtra("title", title);
//                intent.putExtra("description", description);
//                intent.putExtra("photo", photo);
//                intent.putExtra("address", address);
//                intent.putExtra("phone", phone);
//                intent.putExtra("status", status);
//                intent.putExtra("gps", gps);
//                intent.putExtra("availability", availability);
//                intent.putExtra("idUser", idUser);
//
//                v.getContext().startActivity(intent);
                ////////////////////////
                ////////////////////////
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);

                ListRecyclerView item = dataset.get(itemPosition);
                String idItem = item.getIdItem();

                Intent intent = new Intent(v.getContext(), ViewItemActivity.class);
                intent.putExtra("idItem", idItem);
                v.getContext().startActivity(intent);
                ////////////////////////
                ////////////////////////
            }
        });

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListRecyclerView myObject = dataset.get(position);
        holder.bind(myObject, listFragmentContext);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
