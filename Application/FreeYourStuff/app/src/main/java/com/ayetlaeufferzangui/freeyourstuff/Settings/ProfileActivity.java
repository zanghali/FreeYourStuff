package com.ayetlaeufferzangui.freeyourstuff.Settings;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.UserProfile;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Settings.utils.CredentialsManager;
import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private Auth0 auth0;
    private UserProfile userProfile;

    private TextInputEditText familyNameView;
    private TextInputEditText nameView;
    private TextInputEditText emailView;
    private ImageView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        familyNameView = findViewById(R.id.familyName);
        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        photoView = findViewById(R.id.photo);

        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        AuthenticationAPIClient authenticationClient = new AuthenticationAPIClient(auth0);
        authenticationClient.userInfo(CredentialsManager.getCredentials(this).getAccessToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {

                    @Override
                    public void onSuccess(final UserProfile profile) {
                        userProfile = profile;

                        runOnUiThread(new Runnable() {
                            public void run() {
                                String familyName = userProfile.getFamilyName();
                                String name = userProfile.getGivenName();
                                String email = userProfile.getEmail();
                                String photoURL = userProfile.getPictureURL();

                                familyNameView.setText(familyName);
                                nameView.setText(name);
                                emailView.setText(email);
                                Glide.with(getApplicationContext())
                                        .load(photoURL)
                                        .into(photoView);
                            }
                        });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ProfileActivity.this, "User Profile Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


    }
}


