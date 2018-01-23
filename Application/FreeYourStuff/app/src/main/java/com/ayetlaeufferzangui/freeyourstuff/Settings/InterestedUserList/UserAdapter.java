package com.ayetlaeufferzangui.freeyourstuff.Settings.InterestedUserList;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Chat.ChatActivity;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lothairelaeuffer on 17/01/2018.
 */
class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> dataset;
    private String id_item;
    private String connectedId_user;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView firstname;
        private TextView lastname;
        private TextView date;
        private Button chatButton;


        public ViewHolder(View v) {
            super(v);

            firstname = v.findViewById(R.id.firstname);
            lastname = v.findViewById(R.id.lastname);
            date = v.findViewById(R.id.date);
            chatButton = v.findViewById(R.id.chatButton);
        }

        //fill the cells with a parameter
        public void bind(final User user, final String id_item, final String connectedId_user){

            firstname.setText(user.getFirstname());
            lastname.setText(user.getLastname());
            try {
                date.setText(user.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("id_item", id_item );
                    intent.putExtra("first_person", connectedId_user );
                    intent.putExtra("second_person", user.getId_user());
                    intent.putExtra("offerDemand", "offer");


                    v.getContext().startActivity(intent);
                }
            });
        }
    }


    public UserAdapter(List<User> listUser, String id_item, String connectedId_user) {
        this.dataset = new ArrayList<>();
        if(listUser != null){
            this.dataset = listUser;
        }
        this.id_item = id_item;
        this.connectedId_user = connectedId_user;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_user, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User myObject = dataset.get(position);
        holder.bind(myObject, id_item, connectedId_user);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}