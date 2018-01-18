package com.ayetlaeufferzangui.freeyourstuff.ViewItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.NbOfInterestedPeople;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ayetlaeufferzangui.freeyourstuff.Service.ENDPOINT;

//TODO update nb of interested people when subscribe and unsubscribe
public class ViewItemActivity extends AppCompatActivity {

    private static final String TAG = "ViewItemActivity";

    private ImageView mPhoto;
    private TextView mTitle;
    private TextView mNbOfInterestedPeople;
    private TextView mDistance;
    private TextView mAvailability;
    private TextView mDescription;
    private FloatingActionButton mFloatingActionButton;

    private Item item;
    private String connectedId_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        mPhoto = findViewById(R.id.photo);
        mTitle = findViewById(R.id.title);
        mNbOfInterestedPeople = findViewById(R.id.nbOfInterestedPeople);
        mDistance = findViewById(R.id.distance);
        mAvailability = findViewById(R.id.availability);
        mDescription = findViewById(R.id.description);
        mFloatingActionButton = findViewById(R.id.floating_action_button);

        item = new Item (getIntent().getStringExtra("category"),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("description"),
                getIntent().getStringExtra("photo"),
                getIntent().getStringExtra("address"),
                getIntent().getStringExtra("phone"),
                getIntent().getStringExtra("status"),
                getIntent().getStringExtra("gps"),
                getIntent().getStringExtra("availability"),
                getIntent().getStringExtra("id_user"),
                getIntent().getStringExtra("id_item"),
                getIntent().getStringExtra("distance")
        );

        Glide.with(getApplicationContext())
                .load(ENDPOINT + "/assets/" + item.getPhoto())
                .into(mPhoto);
        mTitle.setText(item.getTitle());
        new GetNumberInterestedTask().execute(item);
        mDistance.setText(item.getDistance() + "m");
        mAvailability.setText(item.getAvailability());
        mDescription.setText(item.getDescription());

        //get user id from the SharedPreferences
        SharedPreferences sharedPref = getBaseContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.id_user_default);
        connectedId_user = sharedPref.getString(getString(R.string.id_user), defaultValue);

        if(connectedId_user.equals(getString(R.string.id_user_default))){
            //user is not connected to an account
            mFloatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO dialog alert "you need to login" redirect to login
                    Toast.makeText(v.getContext(), getResources().getString(R.string.need_login), Toast.LENGTH_SHORT).show();
                }
            });
        }else if (connectedId_user.equals(item.getId_user())){
            //this item has been post by the connected user
            mFloatingActionButton.setVisibility(View.GONE);
        }else{
            //check if the connected user is already interested by the item
            new GetUserInterestedTask().execute(item);
        }

    }


    private class GetUserInterestedTask extends AsyncTask<Item, Void, List<User>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mFloatingActionButton.setEnabled(false);

        }

        @Override
        protected List<User> doInBackground(Item... params) {
            List<User> result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                Item item = params[0];

                result = service.getUserInterestedByItem(item.getId_item()).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(List<User> listUser) {
            super.onPostExecute(listUser);

            mFloatingActionButton.setEnabled(true);

            if (listUser == null){
                Log.e(TAG, "ERROR");
            }else if (listUser.isEmpty()){
                //no user is interested by this item => current user isn't interested
                //by clicking the user become interested by the Item
                mFloatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
                mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SetUserInterestedTask().execute(item);
                    }
                });

            }else{
                Boolean connectedUserIsInterested = false;
                for (User currentUser : listUser){
                    if (currentUser.getId_user().equals(connectedId_user)){
                        //current user is already interested
                        connectedUserIsInterested = true;
                        mFloatingActionButton.setImageResource(R.drawable.ic_done_white_24dp);
                        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new DeleteUserInterestedByItemTask().execute(item);
                            }
                        });
                    }
                }

                if(connectedUserIsInterested==false){
                    //current user isn't interested
                    mFloatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
                    mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SetUserInterestedTask().execute(item);
                        }
                    });
                }
            }
        }
    }

    private class GetNumberInterestedTask extends AsyncTask<Item, Void, List<NbOfInterestedPeople>> {


        @Override
        protected List<NbOfInterestedPeople> doInBackground(Item... params) {
            List<NbOfInterestedPeople> result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                Item item = params[0];
                //TODO request doesn't work
                result = service.getNumberInterestByItem(item.getId_item()).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(List<NbOfInterestedPeople> listUser) {
            super.onPostExecute(listUser);

            if (listUser == null || listUser.isEmpty()){
                //no user is interested by this item
                mNbOfInterestedPeople.setText( "0" + "interested people");

            }else{
                String nbOfInterestedPeople = listUser.get(0).getNbOfInterestedPeople();
                mNbOfInterestedPeople.setText( nbOfInterestedPeople + "interested people");
            }
        }
    }

    private class DeleteUserInterestedByItemTask extends AsyncTask<Item, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mFloatingActionButton.setEnabled(false);

        }

        @Override
        protected String doInBackground(Item... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                Item item = params[0];

                result = service.deleteUserInterestedByItem(connectedId_user,item.getId_item()).execute().body();

            } catch (IOException e) {
                Log.e(TAG,getResources().getString(R.string.fail));
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mFloatingActionButton.setEnabled(true);

            if (result == "true"){
                Toast.makeText(getBaseContext(), R.string.user_not_interested_anymore, Toast.LENGTH_SHORT).show();
                mFloatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
                mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SetUserInterestedTask().execute(item);
                    }
                });

            }else if(result == "false"){
                //error request
                Toast.makeText(getBaseContext(), R.string.fail, Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SetUserInterestedTask extends AsyncTask<Item, Void, String> {

        @Override
        protected String doInBackground(Item... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                Item item = params[0];

                result = service.setUserInterestedByItem(connectedId_user, item.getId_item()).execute().body();

            } catch (IOException e) {
                Log.e(TAG,getResources().getString(R.string.fail));
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == "true"){
                Toast.makeText(getBaseContext(), R.string.user_interested, Toast.LENGTH_SHORT).show();
                mFloatingActionButton.setImageResource(R.drawable.ic_done_white_24dp);
                mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DeleteUserInterestedByItemTask().execute(item);
                    }
                });
            }else if (result == "false"){
                Toast.makeText(getBaseContext(), R.string.fail, Toast.LENGTH_SHORT).show();
            }

        }
    }

}
