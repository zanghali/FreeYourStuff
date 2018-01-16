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
import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ayetlaeufferzangui.freeyourstuff.Service.ENDPOINT;


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
                getIntent().getStringExtra("id_item")
        );

        //TODO handle the photo distance and nbOfPeople
        Glide.with(getApplicationContext())
                .load(item.getPhoto())
                .into(mPhoto);
        mTitle.setText(item.getTitle());
        mNbOfInterestedPeople.setText("TODO interested people");
        mDistance.setText("TODO distance");
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
                    //TODO toast doesn't appear
                    Toast.makeText(ViewItemActivity.this, "You need to connect before", Toast.LENGTH_LONG);
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
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mFloatingActionButton.setImageResource(R.drawable.ic_done_white_24dp);
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }
    }


    private class GetUserInterestedTask extends AsyncTask<Item, Void, List<User>> {

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

            if (listUser.isEmpty()){
                //no user is interested by this item => current user isn't interested
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
                        mFloatingActionButton.setImageResource(R.drawable.ic_done_white_24dp);
                        connectedUserIsInterested = true;
                        //TODO user is not interested anymore
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

}
