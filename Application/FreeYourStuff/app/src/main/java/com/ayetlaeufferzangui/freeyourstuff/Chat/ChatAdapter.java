package com.ayetlaeufferzangui.freeyourstuff.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Message;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.util.List;

/**
 * Created by lothairelaeuffer on 23/01/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final String TAG = "ChatAdapter";

    private List<Message> dataset;
    private Context activityContext;
    private String second_user_photoURL;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateView;
        private TextView messageView;
        private LinearLayout contentView;
        private ImageView imageView;
        private LinearLayout textView;

        public ViewHolder(View v) {
            super(v);
            dateView = v.findViewById(R.id.date);
            messageView = v.findViewById(R.id.message);
            contentView = v.findViewById(R.id.content);
            imageView = v.findViewById(R.id.profilImg);
            textView = v.findViewById(R.id.text);
        }

        //fill the cells with a parameter
        public void bind(Message message, Context activityContext, String second_user_photoURL){

            try {
                dateView.setText(message.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            messageView.setText(message.getMessage());

            //get user id from the SharedPreferences
            SharedPreferences sharedPref = activityContext.getSharedPreferences(activityContext.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
            String defaultValue = activityContext.getResources().getString(R.string.id_user_default);
            final String id_user = sharedPref.getString(activityContext.getString(R.string.id_user), defaultValue);
            final String photoURL = sharedPref.getString(activityContext.getString(R.string.photoURL), defaultValue);

            if (!photoURL.equals(defaultValue)){
                Glide.with(activityContext)
                        .load(photoURL)
                        .into(imageView);
            }

            if(message.getId_sender().equals(id_user) ){

                if (!photoURL.equals(defaultValue)){
                    Glide.with(activityContext)
                            .load(photoURL)
                            .apply(RequestOptions.circleCropTransform())
                            .into(imageView);
                }else{
                    Glide.with(activityContext)
                            .load(R.drawable.ic_account_circle_black_24dp)
                            .into(imageView);
                }

                View v0 = contentView.getChildAt(0);
                View v1 = contentView.getChildAt(1);
                contentView.removeAllViews();
                contentView.addView(v1);
                contentView.addView(v0);

                textView.setGravity(Gravity.RIGHT);
            } else{
                if (second_user_photoURL != null && !second_user_photoURL.equals("")){
                    Glide.with(activityContext)
                            .load(second_user_photoURL)
                            .apply(RequestOptions.circleCropTransform())
                            .into(imageView);
                }else{
                    Glide.with(activityContext)
                            .load(R.drawable.ic_account_circle_black_24dp)
                            .into(imageView);
                }
            }

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<Message> dataset, Context activityContext, String second_user_photoURL) {
        this.dataset = dataset;
        this.activityContext = activityContext;
        this.second_user_photoURL = second_user_photoURL;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_chat, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Message message = dataset.get(position);
        holder.bind(message, activityContext, second_user_photoURL);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}