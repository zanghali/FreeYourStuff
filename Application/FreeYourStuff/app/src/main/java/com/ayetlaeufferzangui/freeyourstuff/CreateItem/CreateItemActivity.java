package com.ayetlaeufferzangui.freeyourstuff.CreateItem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.ayetlaeufferzangui.freeyourstuff.Service.ENDPOINT;


public class CreateItemActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "CreateItemActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 555;
    private static final int REQUEST_CODE_SELECT_PHOTO = 1;
    private static final int REQUEST_CODE_TAKE_PHOTO = 0;

    private Item item;

    private Uri selectedImageURI;

    private Button mButton;
    private Button mButtonSelectPhoto;
    private Button mButtonTakePhoto;

    private MaterialBetterSpinner mCategory;
    private TextInputEditText mTitle;
    private TextInputEditText mDescription;
    private TextInputEditText mAddress;
    private TextInputEditText mPhone;
    private MaterialBetterSpinner mAvailability;

    private TextInputLayout mTitleLayout;
    private TextInputLayout mDescriptionLayout;
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
        mButtonSelectPhoto = (Button)findViewById(R.id.buttonSelectPhoto);
        mButtonTakePhoto = (Button)findViewById(R.id.buttonTakePhoto);
        mAddress = (TextInputEditText)findViewById(R.id.address);
        mPhone = (TextInputEditText)findViewById(R.id.phone);
        mAvailability = (MaterialBetterSpinner)findViewById(R.id.availability_spinner);

        mTitleLayout = (TextInputLayout) findViewById(R.id.title_layout);
        mDescriptionLayout = (TextInputLayout) findViewById(R.id.description_layout);
        mAddressLayout = (TextInputLayout) findViewById(R.id.address_layout);
        mPhoneLayout = (TextInputLayout) findViewById(R.id.phone_layout);

        initSelectFields();

        mButtonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedImageURI = null;
                if (ContextCompat.checkSelfPermission(CreateItemActivity.this, READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , REQUEST_CODE_SELECT_PHOTO);
                } else {
                    // Show rationale and request permission.
                    ActivityCompat.requestPermissions(CreateItemActivity.this,
                            new String[]{READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            }
        });

        //TODO
        mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageURI = null;
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
            }
        });

        mButton.setOnClickListener(this);

        removeErrorFromInput();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code


                } else {
                    //TODO ?
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
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
        if( selectedImageURI == null) {
            //TODO display that the user must select a picture
            Log.e(TAG, "no picture selected");
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

            //TODO check default value
            //get user id from the SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
            String defaultValue = getResources().getString(R.string.id_user_default);
            String id_user = sharedPref.getString(getString(R.string.id_user), defaultValue);

            //TODO faire un constructeur sans photo et gps (pas besoin d'envoyer GPS, calcul ddes données gps en back grace a l'adresse ??)
            item = new Item(
                    mCategory.getText().toString(),
                    mTitle.getText().toString(),
                    mDescription.getText().toString(),
                    "",
                    mAddress.getText().toString(),
                    mPhone.getText().toString(),
                    "waiting",
                    "45.741284,4.862928",
                    mAvailability.getText().toString(),
                    id_user
            );

            new UploadPhotoTask().execute();

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



    private class UploadPhotoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                File mFile = new File (getRealPathFromURI(selectedImageURI));

                // create RequestBody instance from file
                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(getContentResolver().getType(selectedImageURI)),
                                mFile
                        );

                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("photo", mFile.getName(), requestFile);

                // add another part within the multipart request
                RequestBody id_user =
                        RequestBody.create(
                                okhttp3.MultipartBody.FORM, item.getId_user());

                result = service.uploadPhoto(body, id_user).execute().body();

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
            item.setPhoto( ENDPOINT + "/assets/" + result);

            new CreateItemTask().execute();
        }
    }


    private class CreateItemTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                result = service.addItem(item).execute().body();

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




    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    selectedImageURI = imageReturnedIntent.getData();
                }

                break;
            case REQUEST_CODE_SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    selectedImageURI = imageReturnedIntent.getData();
                }
                break;
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


}
