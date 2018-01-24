package com.ayetlaeufferzangui.freeyourstuff.Chat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ayetlaeufferzangui.freeyourstuff.Model.Message;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private TextView userView;
    private Button sendMessage;
    private EditText newMessageView;
    private Button doneButton;

    private String id_item;
    private String first_person;
    private String second_person;
    private String offerDemand;

    private User second_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.chat_recycler_view);
        userView = findViewById(R.id.user);
        sendMessage = findViewById(R.id.sendMessage);
        newMessageView = findViewById(R.id.newMessage);
        doneButton = findViewById(R.id.doneButton);

        Log.e(TAG, "id_item");
        Log.e(TAG, getIntent().getStringExtra("id_item"));
        Log.e(TAG, "first_person");
        Log.e(TAG, getIntent().getStringExtra("first_person"));
        Log.e(TAG, "second_person");
        Log.e(TAG, getIntent().getStringExtra("second_person"));
        Log.e(TAG, "offerDemand");
        Log.e(TAG, getIntent().getStringExtra("offerDemand"));

        id_item = getIntent().getStringExtra("id_item");
        first_person = getIntent().getStringExtra("first_person");//connected_user
        second_person = getIntent().getStringExtra("second_person");
        offerDemand = getIntent().getStringExtra("offerDemand");

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = newMessageView.getText().toString();
                newMessageView.setText("");
                new AddChatTask().execute(new Message(message, id_item, first_person, second_person));
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Pour le requete updateUserItemStatus
                En premier votre id utilisateur
                En deuxième l'id utilisateur de l'utilisateur intéressé
                En troisième l'id item
                Exemple: Pour que l'acheteur (id=11) finisse de son coté la transaction de l'item 13
                {"id_user":"11","id_userInterestedBy":"11","id_item":"13"}
                Exemple: Pour que le vendeur (id=4) finisse de son coté la transaction de l'item 13
                {"id_user":"4","id_userInterestedBy":"11","id_item":"13"}
                 */
                if(offerDemand.equals("offer")){
                    new UpdateItemStatusTask().execute( first_person, second_person, id_item);
                }else if (offerDemand.equals("demand")){
                    new UpdateItemStatusTask().execute( first_person, first_person, id_item);
                }
            }
        });


        new GetUserById().execute(second_person);



    }




    private class GetUserById extends AsyncTask<String, Void, List<User>>{

        @Override
        protected List<User> doInBackground(String... strings) {
            List<User> users = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                users = service.getUserById(strings[0]).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);

            if (users == null || users.isEmpty()){
                //error
            }else{
                second_user = users.get(0);
                userView.setText(second_user.getFirstname() + " " + second_user.getLastname());
                new GetChatTask().execute(id_item, first_person, second_person);
            }

        }
    }


    private class GetChatTask extends AsyncTask<String, Void, List<Message>>{


        @Override
        protected List<Message> doInBackground(String... strings) {
            List<Message> messages = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String id_item = strings[0];
                String first_person = strings[1];
                String second_person = strings[2];


                messages = service.getChat(id_item, first_person, second_person).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return messages;
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);

            if( messages == null ){
                //error
            }else if (messages.isEmpty()){
                //no message
            }else{
                //recyclerView
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.getLayoutManager().scrollToPosition(messages.size()-1);

                //specify an adapter
                chatAdapter = new ChatAdapter( messages, getBaseContext(), second_user.getPhoto() );
                recyclerView.setAdapter(chatAdapter);
            }
        }
    }


    private class AddChatTask extends AsyncTask<Message, Void, String>{

        @Override
        protected String doInBackground(Message... messages) {
            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                result = service.addChat(messages[0]).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null || result == "false"){
                //error
            }else{
                new GetChatTask().execute(id_item, first_person, second_person);
            }

        }
    }

    private class UpdateItemStatusTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String id_user = strings[0];//first_person
                String id_userInterestedBy = strings[1];//second_person
                String id_item = strings[2];//id_item

                result = service.updateItemStatus( id_user, id_userInterestedBy, id_item).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null || result == "false"){
                //error
                Log.e(TAG,"ERROR");
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChatActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }
}
