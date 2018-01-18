package com.ayetlaeufferzangui.freeyourstuff.Filter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Availability;
import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.Navigation.NavigationActivity;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "FilterActivity";

    private SeekBar mDistance;
    private TextView mDistanceValue;
    private MaterialBetterSpinner mCategory;
    private MaterialBetterSpinner mAvailability;
    private Button mCancelButton;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mDistance = findViewById(R.id.distance_seek_bar);
        mDistanceValue= findViewById(R.id.distance_text_view);
        mCategory = findViewById(R.id.category_spinner);
        mAvailability = findViewById(R.id.availability_spinner);
        mCancelButton = findViewById(R.id.cancelButton);
        mSubmitButton = findViewById(R.id.submitButton);

        initSelectFields();

        mDistance.setProgress(4);
        mDistanceValue.setText("5 km");
        mDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDistanceValue.setText(String.valueOf(progress + 1) + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String distanceFilter;
                String categoryFilter;
                String availabilityFilter;

                //get the filters
                distanceFilter = String.valueOf((mDistance.getProgress()+1)*1000);//convert to m
                if( TextUtils.isEmpty(mCategory.getText()) ){
                    categoryFilter = "";
                }else{
                    categoryFilter = mCategory.getText().toString();
                }
                if( TextUtils.isEmpty(mAvailability.getText()) ){
                    availabilityFilter = "";
                }else{
                    availabilityFilter = mAvailability.getText().toString();
                }

                Intent intent = new Intent(getBaseContext(), NavigationActivity.class);
                intent.putExtra("distance", distanceFilter);
                intent.putExtra("category", categoryFilter);
                intent.putExtra("availability", availabilityFilter);
                startActivity(intent);

            }
        });

    }


    private void initSelectFields(){
        // CATEGORY SELECT
        ArrayAdapter<Category> adapterCategory = new ArrayAdapter<Category>(this,
                android.R.layout.simple_dropdown_item_1line,
                Category.values()
        );
        MaterialBetterSpinner materialDesignSpinnerCategory = (MaterialBetterSpinner)
                findViewById(R.id.category_spinner);
        materialDesignSpinnerCategory.setAdapter(adapterCategory);

        // AVAILABILITY SELECT
        ArrayAdapter<Availability> adapterAvailability = new ArrayAdapter<Availability>(this,
                android.R.layout.simple_dropdown_item_1line,
                Availability.values()
        );
        MaterialBetterSpinner materialDesignSpinnerAvailability = (MaterialBetterSpinner)
                findViewById(R.id.availability_spinner);
        materialDesignSpinnerAvailability.setAdapter(adapterAvailability);
    }


}
