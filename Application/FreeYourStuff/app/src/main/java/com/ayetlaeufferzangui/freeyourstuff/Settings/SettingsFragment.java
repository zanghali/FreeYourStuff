package com.ayetlaeufferzangui.freeyourstuff.Settings;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.ayetlaeufferzangui.freeyourstuff.CreateItem.CreateItemActivity;
import com.ayetlaeufferzangui.freeyourstuff.Navigation.NavigationActivity;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Settings.utils.CredentialsManager;


public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private Button offerButton;
    private Button demandButton;
    private Button loginButton;
    private Button logoutButton;
    private Button helpButton;
    private Button profileButton;
    private TextView content;

    private Auth0 auth0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        offerButton = view.findViewById(R.id.offerButton);
        demandButton = view.findViewById(R.id.demandButton);
        loginButton = view.findViewById(R.id.loginButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        helpButton = view.findViewById(R.id.helpButton);
        profileButton = view.findViewById(R.id.profileButton);

        content = view.findViewById(R.id.content);


        String accessToken = CredentialsManager.getCredentials(getContext()).getAccessToken();
        if (accessToken == null) {
            // Prompt Login screen.

            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            offerButton.setVisibility(View.GONE);
            demandButton.setVisibility(View.GONE);
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
            offerButton.setVisibility(View.VISIBLE);
            demandButton.setVisibility(View.VISIBLE);
            profileButton.setVisibility(View.VISIBLE);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CredentialsManager.deleteCredentials(getContext());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(SettingsFragment.this).attach(SettingsFragment.this).commit();
                }
            });


            offerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content.setText("HERE ARE MY OFFERS !!!!");
                }
            });
            demandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content.setText("HERE ARE MY DEMANDS !!!!");
                }
            });

            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
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
            startActivity(new Intent(getActivity(), NavigationActivity.class));
            getActivity().finish();
        }
    };

}
