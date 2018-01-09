package com.ayetlaeufferzangui.freeyourstuff.ViewItem;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.bumptech.glide.Glide;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ViewItemActivity extends AppCompatActivity {

    private static final String TAG = "ViewItemActivity";

    ImageView mPhoto;
    TextView mTitle;
    TextView mNbOfInterestedPeople;
    TextView mDistance;
    TextView mAvailability;
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        mPhoto = findViewById(R.id.photo);
        mTitle = findViewById(R.id.title);
        mNbOfInterestedPeople = findViewById(R.id.nbOfInterestedPeople);
        mDistance = findViewById(R.id.distance);
        mAvailability = findViewById(R.id.availability);
        mDescription = findViewById(R.id.description);


        new HttpAsyncTask().execute();

    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String itemId = getIntent().getStringExtra("idItem");

            return POST("http://freeyourstuff.ddns.net:3000/getItemById",itemId);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //TODO check if result contains the result of the query

            try {

                JSONArray resultArray = new JSONArray(result);
                JSONObject resultObject = resultArray.getJSONObject(0);

                //TODO handle the photo
                Item item = new Item (resultObject.getString("category"),
                        resultObject.getString("title"),
                        resultObject.getString("description"),
                        "https://media.conforama.fr/Medias/500000/90000/4000/300/80/G_594381_A.jpg",
                        resultObject.getString("address"),
                        resultObject.getString("phone"),
                        resultObject.getString("status"),
                        resultObject.getString("gps"),
                        resultObject.getString("availability"),
                        resultObject.getString("id_user")
                        );


                Glide.with(getApplicationContext())
                        .load(item.getPhoto())
                        .into(mPhoto);
                mTitle.setText(item.getTitle());
                mNbOfInterestedPeople.setText("TODO NbOfInterestedPeople");
                mDistance.setText("TODO distance");
                mAvailability.setText(item.getAvailability());
                mDescription.setText(item.getDescription());


            } catch (JSONException e) {
                e.printStackTrace();

                Log.e(TAG ,"Error");
            }

        }
    }

    public static String POST (String url, String itemId){

        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id_item", itemId);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }



    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
