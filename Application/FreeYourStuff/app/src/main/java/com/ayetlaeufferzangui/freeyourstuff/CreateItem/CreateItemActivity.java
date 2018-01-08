package com.ayetlaeufferzangui.freeyourstuff.CreateItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CreateItemActivity extends AppCompatActivity {

    Button mButton;
    MaterialBetterSpinner mCategory;
    TextInputEditText mTitle;
    TextInputEditText mDescription;
    TextInputEditText mPhoto;
    TextInputEditText mAddress;
    TextInputEditText mPhone;
    MaterialBetterSpinner mAvailability;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

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


        mButton = (Button)findViewById(R.id.button);
        mCategory = (MaterialBetterSpinner)findViewById(R.id.category_spinner);
        mTitle = (TextInputEditText)findViewById(R.id.title);
        mDescription = (TextInputEditText)findViewById(R.id.description);
        mPhoto = (TextInputEditText)findViewById(R.id.photo);
        mAddress = (TextInputEditText)findViewById(R.id.address);
        mPhone = (TextInputEditText)findViewById(R.id.phone);
        mAvailability = (MaterialBetterSpinner)findViewById(R.id.availability_spinner);


        mButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //send data to server
                new HttpAsyncTask().execute();
            }
        });



    }


    public static String POST (String url, Item item){

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
            jsonObject.accumulate("category", item.getCategory());
            jsonObject.accumulate("title", item.getTitle());
            jsonObject.accumulate("description", item.getDescription());
            jsonObject.accumulate("photo", item.getPhoto());
            jsonObject.accumulate("address", item.getAddress());
            jsonObject.accumulate("phone", item.getPhone());
            jsonObject.accumulate("status", item.getStatus());
            jsonObject.accumulate("gps", item.getGps());
            jsonObject.accumulate("availability", item.getAvailability());
            jsonObject.accumulate("id_user", item.getIdUser());

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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            //TODO status, gps and idUser
            Item newItem = new Item(
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

            return POST("http://freeyourstuff.ddns.net:3000/addItem",newItem);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CreateItemActivity.this, NavigationActivity.class);
            startActivity(intent);
        }
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
