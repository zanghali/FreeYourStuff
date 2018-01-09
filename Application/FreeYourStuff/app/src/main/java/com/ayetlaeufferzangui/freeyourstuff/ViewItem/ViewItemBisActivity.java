package com.ayetlaeufferzangui.freeyourstuff.ViewItem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.bumptech.glide.Glide;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ViewItemBisActivity extends AppCompatActivity {

    private static final String TAG = "ViewItemBisActivity";

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


        //TODO handle the photo
        Item item = new Item (getIntent().getStringExtra("category"),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("description"),
                "https://media.conforama.fr/Medias/500000/90000/4000/300/80/G_594381_A.jpg",
                getIntent().getStringExtra("address"),
                getIntent().getStringExtra("phone"),
                getIntent().getStringExtra("status"),
                getIntent().getStringExtra("gps"),
                getIntent().getStringExtra("availability"),
                getIntent().getStringExtra("idUser")
        );

        Glide.with(getApplicationContext())
                .load(item.getPhoto())
                .into(mPhoto);
        mTitle.setText(item.getTitle());
        mNbOfInterestedPeople.setText("TODO NbOfInterestedPeople");
        mDistance.setText("TODO distance");
        mAvailability.setText(item.getAvailability());
        mDescription.setText(item.getDescription());

    }

}