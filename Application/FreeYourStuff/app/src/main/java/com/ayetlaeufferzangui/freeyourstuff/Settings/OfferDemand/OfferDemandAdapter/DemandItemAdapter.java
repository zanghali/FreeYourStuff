package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Chat.ChatActivity;
import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.Status;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OnItemClickListener;
import com.ayetlaeufferzangui.freeyourstuff.ViewItem.ViewItemActivity;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by lothairelaeuffer on 17/01/2018.
 */

public class DemandItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "DemandItemAdapter";

    private List<ListOfferDemand> myList;

    private final Context listFragmentContext;
    private final OnItemClickListener listener;


    private static class StatusViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title;

        StatusViewHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.status);
        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView category;
        private TextView title;
        private TextView creation_date;
        private Button delete;
        private Button sendMessage;

        private LinearLayout text;



        public ViewHolder(View v) {
            super(v);

            category = v.findViewById(R.id.category);
            title = v.findViewById(R.id.title);
            creation_date = v.findViewById(R.id.creation_date);
            text = v.findViewById(R.id.text);
            delete = v.findViewById(R.id.deleteButton);
            sendMessage = v.findViewById(R.id.sendMessageButton);
        }

        //fill the cells with a parameter
        public void bind(final Item currentItem, final Context listFragmentContext, final OnItemClickListener listener){

            Glide.with(listFragmentContext)
                    .load(Category.createIconUrl(Category.valueOf(currentItem.getCategory())))
                    .into(category);
            title.setText(currentItem.getTitle());
            try {
                creation_date.setText(currentItem.getCreation_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(currentItem);
                }
            });

            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //get user id from the SharedPreferences
                    SharedPreferences sharedPref = listFragmentContext.getSharedPreferences(listFragmentContext.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                    String defaultValue = listFragmentContext.getResources().getString(R.string.id_user_default);
                    String connectedId_user = sharedPref.getString(listFragmentContext.getString(R.string.id_user), defaultValue);


                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("id_item", currentItem.getId_item() );
                    intent.putExtra("first_person", connectedId_user );
                    intent.putExtra("second_person", currentItem.getId_user());
                    intent.putExtra("offerDemand", "demand");


                    v.getContext().startActivity(intent);
                }
            });

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


    public DemandItemAdapter(List<Item> listItem, Context listFragmentContext, OnItemClickListener listener) {

        myList = new ArrayList<>();
        for (Status status : Status.values()) {
            OfferDemandStatus header = new OfferDemandStatus();
            header.setStatus(status);
            myList.add(header);
            for (Item item : listItem) {
                if(Status.valueOf(item.getStatus()) == status){
                    OfferDemandItem offerDemandItem = new OfferDemandItem();
                    offerDemandItem.setItem(item);
                    myList.add(offerDemandItem);
                }
            }
        }

        this.listener = listener;
        this.listFragmentContext=listFragmentContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListOfferDemand.TYPE_STATUS) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_status, parent, false);
            return new StatusViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_demand, parent, false);
            return new ViewHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        int type = getItemViewType(position);
        if (type == ListOfferDemand.TYPE_STATUS) {
            OfferDemandStatus header = (OfferDemandStatus) myList.get(position);
            StatusViewHolder holder = (StatusViewHolder) viewHolder;
            // your logic here
            if(header.getStatus() != Status.waiting){
                holder.txt_title.setText(header.getStatus().toString());
            }

        } else {
            OfferDemandItem offerDemandItem = (OfferDemandItem) myList.get(position);
            ViewHolder holder = (ViewHolder) viewHolder;
            // your logic here
            holder.bind(offerDemandItem.getItem(), listFragmentContext, listener);

        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return myList.get(position).getType();
    }
}