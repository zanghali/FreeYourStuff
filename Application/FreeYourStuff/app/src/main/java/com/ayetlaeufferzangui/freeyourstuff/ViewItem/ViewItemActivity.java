package com.ayetlaeufferzangui.freeyourstuff.ViewItem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewItemActivity extends AppCompatActivity {

    private static final String TAG = "ViewItemActivity";

    ImageView mPhoto;
    TextView mTitle;
    TextView mNbOfInterestedPeople;
    TextView mDistance;
    TextView mAvailability;
    TextView mDescription;

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


        String id_item = getIntent().getStringExtra("id_item");
        Log.e(TAG, id_item);
        new ItemTask().execute(id_item);

    }


    private class ItemTask extends AsyncTask<String, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(String... params) {
            List<Item> item = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String id = params[0];

                item = service.getItemById(id).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return item;

        }

        @Override
        protected void onPostExecute(List<Item> listItem) {
            super.onPostExecute(listItem);
            //Log.e(TAG, listItem);
            Item item = listItem.get(0);

            //TODO get all the info from listItem
            Glide.with(getApplicationContext())
                    .load("https://media.conforama.fr/Medias/500000/90000/4000/300/80/G_594381_A.jpg")
                    .into(mPhoto);
            mTitle.setText(item.getTitle());
            mNbOfInterestedPeople.setText("TODO NbOfInterestedPeople");
            mDistance.setText("TODO distance");
            mAvailability.setText(item.getAvailability());
            mDescription.setText(item.getDescription());


        }
    }


}