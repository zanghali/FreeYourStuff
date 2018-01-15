package com.ayetlaeufferzangui.freeyourstuff.Settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.ItemShortList;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lothairelaeuffer on 15/01/2018.
 */

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ItemShortList> dataset;

    private final Context listFragmentContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView category;
        public TextView title;

        public ViewHolder(View v) {
            super(v);

            category = v.findViewById(R.id.category);
            title = v.findViewById(R.id.title);
        }

        //fill the cells with a parameter
        public void bind(ItemShortList mediaObject, Context listFragmentContext){

            Glide.with(listFragmentContext)
                    .load(mediaObject.getCategoryIconUrl())
                    .into(category);
            title.setText(mediaObject.getTitle());

        }

    }


    public ItemAdapter(List<Item> listItem, Context listFragmentContext) {

        this.dataset = new ArrayList<>();
        if(listItem != null){
            for(Item item : listItem){
                this.dataset.add(new ItemShortList(Category.createIconUrl(Category.valueOf(item.getCategory())), item.getTitle()));
            }
        }
        this.listFragmentContext=listFragmentContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_offer, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemShortList myObject = dataset.get(position);
        holder.bind(myObject, listFragmentContext);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}