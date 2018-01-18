package com.ayetlaeufferzangui.freeyourstuff.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private TextInputEditText firstnameView;
    private TextInputEditText lastnameView;
    private TextInputEditText emailView;
    private ImageView photoView;
    private Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstnameView = findViewById(R.id.firstname);
        lastnameView = findViewById(R.id.lastname);
        emailView = findViewById(R.id.email);
        photoView = findViewById(R.id.photo);
        updateButton = findViewById(R.id.update);


        String email = getIntent().getStringExtra("email");
        new GetUserByEmailTask().execute(email);




    }

    private class GetUserByEmailTask extends AsyncTask<String, Void, List<User>> {
        @Override
        protected List<User> doInBackground(String... params) {
            List<User> listUser = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String email = params[0];

                listUser = service.getUserByEmail(email).execute().body();

            } catch (IOException e) {
                Toast.makeText(ProfileActivity.this, "User Profile Request Failed", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return listUser;

        }

        @Override
        protected void onPostExecute(List<User> listUser) {

            String firstname = listUser.get(0).getFirstname();
            String lastname = listUser.get(0).getLastname();
            final String email = listUser.get(0).getEmail();

            //get user id from the SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String defaultValue = getResources().getString(R.string.id_user_default);
            String photoURL = sharedPref.getString(getString(R.string.photoURL), defaultValue);

            firstnameView.setText(firstname);
            lastnameView.setText(lastname);
            emailView.setText(email);
            Glide.with(getApplicationContext())
                    .load(photoURL)
                    .into(photoView);


            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UpdateUserTask().execute(firstnameView.getText().toString(), lastnameView.getText().toString() ,email );
                }
            });

        }
    }

    private class UpdateUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String firstname = params[0];
                String lastname = params[1];
                String email = params[2];

                result = service.updateUser(firstname, lastname, email).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            if(result == "true"){
                Toast.makeText(ProfileActivity.this, R.string.profile_updated, Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(ProfileActivity.this, R.string.profile_not_updated, Toast.LENGTH_SHORT).show();

            }

        }
    }
}


