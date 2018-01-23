package com.ayetlaeufferzangui.freeyourstuff.Settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.Navigation.NavigationActivity;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandActivity;
import com.ayetlaeufferzangui.freeyourstuff.Settings.utils.CredentialsManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private AppCompatButton offerDemandButton;
    private AppCompatButton loginButton;
    private AppCompatButton logoutButton;
    private AppCompatButton helpButton;
    private AppCompatButton profileButton;

    private ProgressBar progressBar;


    public User newUser;

    private Auth0 auth0;


    private TabLayout tabLayout;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);

        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        offerDemandButton = view.findViewById(R.id.offerDemandButton);
        loginButton = view.findViewById(R.id.loginButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        helpButton = view.findViewById(R.id.helpButton);
        profileButton = view.findViewById(R.id.profileButton);

        progressBar = view.findViewById(R.id.progressBar);

        //get user id from the SharedPreferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.id_user_default);
        final String id_user = sharedPref.getString(getString(R.string.id_user), defaultValue);
        final String email = sharedPref.getString(getString(R.string.email), defaultValue);

        String accessToken = CredentialsManager.getCredentials(getContext()).getAccessToken();
        if (accessToken == null || id_user == defaultValue || email == defaultValue) {
            // Prompt Login screen.

            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            offerDemandButton.setVisibility(View.GONE);
            profileButton.setVisibility(View.GONE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth0 = new Auth0(getContext());
                    auth0.setOIDCConformant(true);
                    login();
                }
            });


        } else {
            // Try to make an automatic login

            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            offerDemandButton.setVisibility(View.VISIBLE);
            profileButton.setVisibility(View.VISIBLE);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CredentialsManager.deleteCredentials(getContext());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(SettingsFragment.this).attach(SettingsFragment.this).commit();

                    //save user id in the SharedPreferences
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(getString(R.string.id_user));
                    editor.remove(getString(R.string.email));
                    editor.remove(getString(R.string.photoURL));
                    editor.commit();

                }
            });
            offerDemandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //content.setText("HERE ARE MY OFFERS !!!!");

                    Intent intent = new Intent(getActivity(), OfferDemandActivity.class);
                    intent.putExtra("id_user", id_user);
                    startActivity(intent);

                }
            });

            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            });

        }


    }

    private void login() {

        WebAuthProvider.init(auth0)
                .withScheme("demo")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                //.withScope("openid offline_access")
                .withScope("openid profile email")
                .start(getActivity(), callback);

    }

    private final AuthCallback callback = new AuthCallback() {
        @Override
        public void onFailure(@NonNull final Dialog dialog) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }

        @Override
        public void onFailure(AuthenticationException exception) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSuccess(@NonNull Credentials credentials) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Log In - Success", Toast.LENGTH_SHORT).show();
                }
            });
            CredentialsManager.saveCredentials(getActivity(), credentials);

            auth0 = new Auth0(getContext());
            auth0.setOIDCConformant(true);

            AuthenticationAPIClient authenticationClient = new AuthenticationAPIClient(auth0);
            authenticationClient.userInfo(CredentialsManager.getCredentials(getContext()).getAccessToken())
                    .start(new BaseCallback<UserProfile, AuthenticationException>() {

                        @Override
                        public void onSuccess(final UserProfile userProfile) {

                            String lastname = "";
                            String firstname = "";
                            String photoURL= "";
                            if (userProfile.getFamilyName() != null){
                                lastname = userProfile.getFamilyName();
                            }
                            if (userProfile.getFamilyName() != null){
                                firstname = userProfile.getFamilyName();
                            }
                            if (userProfile.getFamilyName() != null){
                                photoURL = userProfile.getPictureURL();
                            }


                            newUser = new User (lastname,
                                    firstname,
                                    userProfile.getEmail());


                            new GetUserByEmailTask().execute(userProfile.getEmail(), photoURL);

                        }

                        @Override
                        public void onFailure(AuthenticationException error) {
                            Log.e(TAG, "ERROR");
                        }
                    });

        }
    };


    private class GetUserByEmailTask extends AsyncTask<String, Void, List<User>> {

        private String photoURL;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

        }

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
                photoURL = params[1];

                listUser = service.getUserByEmail(email).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return listUser;

        }

        @Override
        protected void onPostExecute(List<User> listUser) {

            progressBar.setVisibility(View.GONE);


            if (listUser.isEmpty()){
                //if it it the first connection of the user, create a user in the db
                new AddUserTask().execute(newUser);
            }else{
                //if the user already exist in the db
                String id = listUser.get(0).getId_user();
                String email = listUser.get(0).getEmail();

                //save user id in the SharedPreferences
                SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.id_user), id);
                editor.putString(getString(R.string.email), email);
                editor.putString(getString(R.string.photoURL), photoURL);
                editor.commit();

                startActivity(new Intent(getActivity(), NavigationActivity.class));
                getActivity().finish();
            }



        }
    }

    private class AddUserTask extends AsyncTask<User, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(User... params) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                User user = params[0];

                result = service.addUser(user).execute().body();

            } catch (IOException e) {
                Log.e(TAG, "ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String id_user) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });


            //save user id in the SharedPreferences
            SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.id_user), id_user);
            editor.putString(getString(R.string.email), newUser.getEmail());
            editor.putString(getString(R.string.photoURL), newUser.getPhotoURL());
            editor.commit();

        }
    }



}

