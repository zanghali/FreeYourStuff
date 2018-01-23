package com.ayetlaeufferzangui.freeyourstuff.Navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Availability;
import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "FilterActivity";

    private SeekBar mDistance;
    private TextView mDistanceValue;
    private MaterialBetterSpinner mCategory;
    private MaterialBetterSpinner mAvailability;
    private Button mResetCategory;
    private Button mResetAvailability;
    private Button mCancelButton;
    private Button mSubmitButton;


    private String distanceFilter;
    private String categoryFilter;
    private String availabilityFilter;
    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mDistance = findViewById(R.id.distance_seek_bar);
        mDistanceValue= findViewById(R.id.distance_text_view);
        mCategory = findViewById(R.id.category_spinner);
        mAvailability = findViewById(R.id.availability_spinner);
        mResetCategory = findViewById(R.id.resetCategory);
        mResetAvailability = findViewById(R.id.resetAvailability);
        mCancelButton = findViewById(R.id.cancelButton);
        mSubmitButton = findViewById(R.id.submitButton);

        initSelectFields();



        //get the values of the filters and the search from the intent
        distanceFilter = getIntent().getStringExtra("distanceFilter");
        categoryFilter = getIntent().getStringExtra("categoryFilter");
        availabilityFilter = getIntent().getStringExtra("availabilityFilter");
        keywords = getIntent().getStringExtra("keywords");

        //set the input to the previous values
        if(distanceFilter != null && !distanceFilter.equals("")){
            mDistance.setProgress(Integer.valueOf(distanceFilter)/1000);//TODO check distance conversion
            mDistanceValue.setText( distanceFilter +" km");
        }
        if(categoryFilter != null && !categoryFilter.equals("")){
            int indexCategory = Category.valueOf(categoryFilter).ordinal();
            mCategory.setText(mCategory.getAdapter().getItem(indexCategory).toString());
        }
        if(availabilityFilter != null && !availabilityFilter.equals("")){
            int indexAvailability = Availability.valueOf(availabilityFilter).ordinal();
            mAvailability.setText(mAvailability.getAdapter().getItem(indexAvailability).toString());
        }

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
        mResetCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCategory.clearFocus();
                mCategory.setText("");
                mCategory.clearFocus();
            }
        });
        mResetAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAvailability.clearFocus();
                mAvailability.setText("");
                mAvailability.clearFocus();
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

                //get new values from the input
                distanceFilter = String.valueOf((mDistance.getProgress()+1)*1000);//convert to metre
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
                intent.putExtra("distanceFilter", distanceFilter);
                intent.putExtra("categoryFilter", categoryFilter);
                intent.putExtra("availabilityFilter", availabilityFilter);
                intent.putExtra("keywords", keywords);
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
