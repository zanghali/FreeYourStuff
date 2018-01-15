package com.ayetlaeufferzangui.freeyourstuff.ViewItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.bumptech.glide.Glide;



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

        Item item = new Item (getIntent().getStringExtra("category"),
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

    }

}
