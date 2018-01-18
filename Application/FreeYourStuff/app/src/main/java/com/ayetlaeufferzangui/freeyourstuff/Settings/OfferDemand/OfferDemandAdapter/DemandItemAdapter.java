package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.ItemShortList;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.ViewItem.ViewItemActivity;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lothairelaeuffer on 17/01/2018.
 */

//TODO display a delete
public class DemandItemAdapter extends RecyclerView.Adapter<DemandItemAdapter.ViewHolder> {

    private List<Item> dataset;

    private final Context listFragmentContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView category;
        private TextView title;
        private TextView creation_date;

        private LinearLayout text;



        public ViewHolder(View v) {
            super(v);

            category = v.findViewById(R.id.category);
            title = v.findViewById(R.id.title);
            creation_date = v.findViewById(R.id.creation_date);
            text = v.findViewById(R.id.text);
        }

        //fill the cells with a parameter
        public void bind(final Item currentItem, final Context listFragmentContext){

            Glide.with(listFragmentContext)
                    .load(Category.createIconUrl(Category.valueOf(currentItem.getCategory())))
                    .into(category);
            title.setText(currentItem.getTitle());
            try {
                creation_date.setText(currentItem.getCreation_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                    String distance = currentItem.getDistance();

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
                    intent.putExtra("distance", distance);

                    v.getContext().startActivity(intent);
                }
            });
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                    String distance = currentItem.getDistance();

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
                    intent.putExtra("distance", distance);

                    v.getContext().startActivity(intent);
                }
            });
        }

    }


    public DemandItemAdapter(List<Item> listItem, Context listFragmentContext) {

        this.dataset = new ArrayList<>();
        if(listItem != null){
            for(Item item : listItem){
                //this.dataset.add(new ItemShortList(Category.createIconUrl(Category.valueOf(item.getCategory())), item.getTitle(), item.getId_item(), item.getCreation_date(), item.getId_user()));
            }
            this.dataset =listItem;
        }

        this.listFragmentContext=listFragmentContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_demand, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item myObject = dataset.get(position);
        holder.bind(myObject, listFragmentContext);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}