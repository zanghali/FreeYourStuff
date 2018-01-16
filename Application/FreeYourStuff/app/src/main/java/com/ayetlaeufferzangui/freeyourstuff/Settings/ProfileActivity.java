package com.ayetlaeufferzangui.freeyourstuff.Settings;

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

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private TextInputEditText lastnameView;
    private TextInputEditText firstnameView;
    private TextInputEditText emailView;
    private ImageView photoView;
    private Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lastnameView = findViewById(R.id.familyName);
        firstnameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        photoView = findViewById(R.id.photo);
        updateButton = findViewById(R.id.update);


        String email = getIntent().getStringExtra("email");
        new GetUserByEmailTask().execute(email);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update user info
            }
        });

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

            //TODO save photoURL in the db or in the SharedPreferences ?
            String lastname = listUser.get(0).getLastname();
            String firstname = listUser.get(0).getFirstname();
            String email = listUser.get(0).getEmail();
            //String photoURL = userProfile.getPictureURL();

            lastnameView.setText(lastname);
            firstnameView.setText(firstname);
            emailView.setText(email);
            //Glide.with(getApplicationContext())
            //        .load(photoURL)
            //        .into(photoView);



        }
    }
}


