package com.ayetlaeufferzangui.freeyourstuff;

import com.ayetlaeufferzangui.freeyourstuff.List.ListRecyclerView;
import com.ayetlaeufferzangui.freeyourstuff.Model.Item;
import com.ayetlaeufferzangui.freeyourstuff.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lothairelaeuffer on 10/01/2018.
 */

public interface Service {

    String ENDPOINT = "http://freeyourstuff.ddns.net:3000";

    @POST("/addItem")
    Call<String> addItem(@Body Item item);

    @FormUrlEncoded
    @POST("/getItemById")
    Call<List<Item>> getItemById(@Field("id_item") String query);

    @POST("/getUserByEmail")
    Call<List<User>> getUserByEmail(@Body User email);

    @POST("/addUser")
    Call<String> addUser(@Body User user);

    @POST("/getItemList")
    Call<List<ListRecyclerView>> getItemList();
}
