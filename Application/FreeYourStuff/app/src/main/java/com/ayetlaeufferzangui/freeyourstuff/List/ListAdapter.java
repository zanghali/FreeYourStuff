package com.ayetlaeufferzangui.freeyourstuff.List;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.ViewItem.ViewItemActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    private List<ListRecyclerView> dataset;

    private final Context listFragmentContext;

    private RecyclerView mRecyclerView;

    private List<Item> listItem;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView category;
        public TextView title;
        public ImageView photo;
        public TextView availability;
        public TextView distance;


        public ViewHolder(View v) {
            super(v);

            category = v.findViewById(R.id.category);
            title = v.findViewById(R.id.title);
            photo = v.findViewById(R.id.photo);
            availability = v.findViewById(R.id.availability);
            distance = v.findViewById(R.id.distance);

        }

        //fill the cells with a parameter
        public void bind(ListRecyclerView mediaObject, Context listFragmentContext){
            category.setText(mediaObject.getCategory());
            title.setText(mediaObject.getTitle());
            Glide.with(listFragmentContext)
                    .load(mediaObject.getPhoto())
                    .into(photo);
            availability.setText(mediaObject.getAvailability());
            distance.setText(mediaObject.getDistance() + " km");
        }

    }


    public ListAdapter(List<Item> listItem, Context listFragmentContext) {

        this.listItem = listItem;

        this.dataset = new ArrayList<>();
        if(listItem != null){
            for(Item item : listItem){

                //TODO calcul distance && photo
                String DISTANCE = "100";
                this.dataset.add(new ListRecyclerView(item.getCategory(), item.getTitle(), item.getPhoto(), item.getAvailability(), DISTANCE, item.getId_item()));
            }
        }


        this.listFragmentContext=listFragmentContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list, parent, false);

        mRecyclerView = parent.findViewById(R.id.list_recycler_view);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                Item currentItem = listItem.get(itemPosition);

                String id_item = currentItem.getId_item();
                String category = currentItem.getCategory();
                String title = currentItem.getTitle();
                String description = currentItem.getDescription();
                String photo = currentItem.getPhoto();
                String address = currentItem.getAddress();
                String phone = currentItem.getPhone();
                String status = currentItem.getStatus();
                String gps = currentItem.getGps();
                String availability = currentItem.getAvailability();
                String id_user = currentItem.getId_user();

                Intent intent = new Intent(v.getContext(), ViewItemActivity.class);
                intent.putExtra("id_item", id_item);
                intent.putExtra("category", category);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("photo", photo);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);
                intent.putExtra("status", status);
                intent.putExtra("gps", gps);
                intent.putExtra("availability", availability);
                intent.putExtra("id_user", id_user);

                v.getContext().startActivity(intent);

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
