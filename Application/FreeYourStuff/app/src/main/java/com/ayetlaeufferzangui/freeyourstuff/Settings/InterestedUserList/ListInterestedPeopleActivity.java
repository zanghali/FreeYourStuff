package com.ayetlaeufferzangui.freeyourstuff.Settings.InterestedUserList;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.User;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ayetlaeufferzangui.freeyourstuff.Service.ENDPOINT;

public class ListInterestedPeopleActivity extends AppCompatActivity {

    private static final String TAG = "ListInterestedPeople";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserAdapter userAdapter;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_interested_people);

        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textView);

        new GetUserInterestedTask(this).execute(getIntent().getStringExtra("id_item"));
    }




    private class GetUserInterestedTask extends AsyncTask<String, Void, List<User>> {

        private Context mContext;

        public GetUserInterestedTask (Context context){
            mContext = context;
        }

        @Override
        protected List<User> doInBackground(String... params) {
            List<User> result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                String id_item = params[0];

                result = service.getUserInterestedByItem(id_item).execute().body();

            } catch (IOException e) {
                Log.e(TAG,"ERROR");
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(List<User> listUser) {
            super.onPostExecute(listUser);

            if(listUser.isEmpty()){
                textView.setText(R.string.no_user_interested);
            }else{
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                layoutManager = new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(layoutManager);

                // specify an adapter
                userAdapter = new UserAdapter(listUser);
                recyclerView.setAdapter(userAdapter);
            }


        }
    }

}
