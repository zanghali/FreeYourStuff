package com.ayetlaeufferzangui.freeyourstuff.Settings.InterestedUserList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lothairelaeuffer on 17/01/2018.
 */
//TODO date quelle date ? user ou autre requete pour item

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstname;
        private TextView lastname;


        public ViewHolder(View v) {
            super(v);

            firstname = v.findViewById(R.id.firstname);
            lastname = v.findViewById(R.id.lastname);
        }

        //fill the cells with a parameter
        public void bind(User user){

            //Glide.with(listFragmentContext)
            //        .load(mediaObject.getCategoryIconUrl())
            //        .into(category);
            firstname.setText(user.getFirstname());
            lastname.setText(user.getLastname());

        }

    }


    public UserAdapter(List<User> listUser) {
        this.dataset = new ArrayList<>();
        if(listUser != null){
            this.dataset = listUser;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_user, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User myObject = dataset.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}