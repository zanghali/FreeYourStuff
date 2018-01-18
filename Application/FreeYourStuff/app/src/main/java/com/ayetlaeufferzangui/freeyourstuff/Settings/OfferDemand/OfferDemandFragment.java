package com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.R;
import com.ayetlaeufferzangui.freeyourstuff.Service;
import com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter.DemandItemAdapter;
import com.ayetlaeufferzangui.freeyourstuff.Settings.OfferDemand.OfferDemandAdapter.OfferItemAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OfferDemandFragment extends Fragment {

    private static final String ID_USER = "id_user";
    private static final String OFFER_DEMAND = "offerDemand";
    private static final String TAG = "OfferDemandFragment";

    private String mId_user;
    private String mOfferDemand;

    private RecyclerView recyclerView;
    private OfferItemAdapter offerItemAdapter;
    private DemandItemAdapter demandItemAdapter;

    private TextView textView;

    public OfferDemandFragment() {
        // Required empty public constructor
    }

    public static OfferDemandFragment newInstance(String id_user, String offerDemand) {
        OfferDemandFragment fragment = new OfferDemandFragment();
        Bundle args = new Bundle();
        args.putString(ID_USER, id_user);
        args.putString(OFFER_DEMAND, offerDemand);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId_user = getArguments().getString(ID_USER);
            mOfferDemand = getArguments().getString(OFFER_DEMAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer_demand, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.list_recycler_view);
        textView = view.findViewById(R.id.textView);


        new GetOfferDemandTask().execute();
    }



    private class GetOfferDemandTask extends AsyncTask<Void, Void, List<Item>> {
        @Override
        protected List<Item> doInBackground(Void... params) {
            List<Item> result = null;
            try {
                Service service = new Retrofit.Builder()
                        .baseUrl(Service.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(Service.class);

                if(mOfferDemand=="Offer")
                    result = service.getItemByUser(mId_user).execute().body();
                else if (mOfferDemand=="Demand")
                    result = service.getItemOfUserInterestedBy(mId_user).execute().body();

            } catch (IOException e) {
                Log.e(TAG, "ERROR");
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Item> listItem) {

            if(listItem.isEmpty()){
                textView.setText(R.string.no_item);
            }else {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                //specify an adapter
                if (mOfferDemand == "Offer") {
                    offerItemAdapter = new OfferItemAdapter(listItem, getContext());
                    recyclerView.setAdapter(offerItemAdapter);
                } else if (mOfferDemand == "Demand") {
                    demandItemAdapter = new DemandItemAdapter(listItem, getContext());
                    recyclerView.setAdapter(demandItemAdapter);
                }
            }


        }
    }
}
