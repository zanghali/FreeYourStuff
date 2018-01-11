package com.ayetlaeufferzangui.freeyourstuff.CreateItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.ayetlaeufferzangui.freeyourstuff.Model.Availability;
import com.ayetlaeufferzangui.freeyourstuff.Model.Category;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Navigation.NavigationActivity;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateItemActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "CreateItemActivity";

    private Button mButton;

    private MaterialBetterSpinner mCategory;
    private TextInputEditText mTitle;
    private TextInputEditText mDescription;
    private TextInputEditText mPhoto;
    private TextInputEditText mAddress;
    private TextInputEditText mPhone;
    private MaterialBetterSpinner mAvailability;

    private TextInputLayout mTitleLayout;
    private TextInputLayout mDescriptionLayout;
    private TextInputLayout mPhotoLayout;
    private TextInputLayout mAddressLayout;
    private TextInputLayout mPhoneLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        mButton = (Button)findViewById(R.id.button);
        mCategory = (MaterialBetterSpinner)findViewById(R.id.category_spinner);
        mTitle = (TextInputEditText)findViewById(R.id.title);
        mDescription = (TextInputEditText)findViewById(R.id.description);
        mPhoto = (TextInputEditText)findViewById(R.id.photo);
        mAddress = (TextInputEditText)findViewById(R.id.address);
        mPhone = (TextInputEditText)findViewById(R.id.phone);
        mAvailability = (MaterialBetterSpinner)findViewById(R.id.availability_spinner);

        mTitleLayout = (TextInputLayout) findViewById(R.id.title_layout);
        mDescriptionLayout = (TextInputLayout) findViewById(R.id.description_layout);
        mPhotoLayout = (TextInputLayout) findViewById(R.id.photo_layout);
        mAddressLayout = (TextInputLayout) findViewById(R.id.address_layout);
        mPhoneLayout = (TextInputLayout) findViewById(R.id.phone_layout);

        initSelectFields();

        mButton.setOnClickListener(this);

        removeErrorFromInput();

    }

    @Override
    public void onClick(View view) {

        boolean i = true;
        if( TextUtils.isEmpty(mCategory.getText())){
            mCategory.setError( getResources().getString(R.string.category) + " " + getResources().getString(R.string.required) );
            i = false;
        }
        if( TextUtils.isEmpty(mTitle.getText())) {
            mTitleLayout.setError( getResources().getString(R.string.title) + " " + getResources().getString(R.string.required) );
            i = false;
        }
        if( TextUtils.isEmpty(mDescription.getText())) {
            mDescriptionLayout.setError( getResources().getString(R.string.description) + " " + getResources().getString(R.string.required) );
            i = false;
        }
        if( TextUtils.isEmpty(mPhoto.getText())) {
            mPhotoLayout.setError( getResources().getString(R.string.photo) + " " + getResources().getString(R.string.required) );
            i = false;
        }
        if( TextUtils.isEmpty(mAddress.getText())) {
            mAddressLayout.setError( getResources().getString(R.string.address) + " " + getResources().getString(R.string.required) );
            i = false;
        }
        if( TextUtils.isEmpty(mAvailability.getText())){
            mAvailability.setError( getResources().getString(R.string.availability) + " " + getResources().getString(R.string.required) );
            i = false;
        }

        if(i == true){
            //send data to server


            //TODO gps and idUser
            Item item = new Item(
                    mCategory.getText().toString(),
                    mTitle.getText().toString(),
                    mDescription.getText().toString(),
                    mPhoto.getText().toString(),
                    mAddress.getText().toString(),
                    mPhone.getText().toString(),
                    "waiting",
                    "48651,45684",
                    mAvailability.getText().toString(),
                    "1"
            );

            new CreateItemTask().execute(item);
        }
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


    private void removeErrorFromInput(){
        mTitle.addTextChangedListener( new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mTitleLayout.setErrorEnabled( false );
            }
        });
        mDescription.addTextChangedListener( new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mDescriptionLayout.setErrorEnabled( false );
            }
        });
        mPhoto.addTextChangedListener( new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mPhotoLayout.setErrorEnabled( false );
            }
        });
        mAddress.addTextChangedListener( new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mAddressLayout.setErrorEnabled( false );
            }
        });
        mPhone.addTextChangedListener( new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mPhoneLayout.setErrorEnabled( false );
            }
        });
    }



    private class CreateItemTask extends AsyncTask<Item, Void, String> {

        @Override
        protected String doInBackground(Item... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                Item newItem = params[0];

                result = service.addItem(newItem).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CreateItemActivity.this, NavigationActivity.class);
            startActivity(intent);


        }
    }
}
